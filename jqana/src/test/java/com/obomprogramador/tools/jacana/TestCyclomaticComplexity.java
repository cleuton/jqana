                package com.obomprogramador.tools.jacana;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.context.GlobalConstants;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.Measurement.MEASUREMENT_TYPE;
import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.Parser;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;
import com.obomprogramador.tools.jqana.model.defaultimpl.MaxLimitVerificationAlgorithm;
import com.obomprogramador.tools.jqana.model.defaultimpl.MetricValue;
import com.obomprogramador.tools.jqana.parsers.CyclomaticComplexityParser;

import static com.obomprogramador.tools.jqana.context.GlobalConstants.*;

public class TestCyclomaticComplexity {
	
	private Context context;
	
	public String[][] testClasses = {
					{"abc","ClassA","1","1","7"},
					{"abc","ClassAfilha","0","1","2"},
					{"abc","ClassB","0","1","2"},
					{"abc","ConstructorCountSelf","1","1","3"},
					{"abc","DuplicateConstructor","1","1","5"},
					{"abc","Teste2","14","1","13"},
					{"abc","TesteLCom4Um","1.5","1","12"},
					{"abc","TesteLcomMaiorQueUm","1.1","2","16"},
					{"abc","TesteRfc","1.7","1","9"},
					{"abc","TesteSuperclass","1","1","3"},
					{"def","Blocks","5.7","1","5"},
					{"def","Teste","10","1","9"}
	};
	

	@Test
	public void testAllClassesCC() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		context = new Context();
		ResourceBundle bundle = ResourceBundle.getBundle("report");
		context.setBundle(bundle);
		for (int x=0; x<testClasses.length; x++) {
			
			String uri = getSource("unit-test-sources/" + testClasses[x][0] + "/" + testClasses[x][1] + ".java"); 
			Measurement packageMeasurement = new Measurement();
			packageMeasurement.setName(testClasses[x][0]);
			packageMeasurement.setType(MEASUREMENT_TYPE.PACKAGE_MEASUREMENT);
			Parser parser = new CyclomaticComplexityParser(packageMeasurement, context);
			Measurement mt = parser.parse( null, uri);
			MetricValue mv = packageMeasurement.getMetricValue(context.getBundle().getString("metric.cc.name"));
			assertTrue(mt != null);
			double diffCC = Math.abs(mv.getValue() - Double.parseDouble(testClasses[x][2]));
			System.out.println("Testing class: " + testClasses[x][0] + "." + testClasses[x][1] + " cc: " + mv.getValue());
			assertTrue(diffCC < 0.5);

			printPackage(1, packageMeasurement);			
		}
		
				
	}

	

	
	
	public void printPackage(int identation, Measurement mt) {
		String line = ""; 
		line += StringUtils.leftPad(line,identation);
		MetricValue mv = mt.getMetricValue(context.getBundle().getString("metric.cc.name"));
		switch (mt.getType()) {
		case PACKAGE_MEASUREMENT:
			line += "Package: " + mt.getName(); 
			line += ", Metric: "
					+ mv.getName()
					+ ", value (avg): "
					+ mv.getValue();
			break;
		case CLASS_MEASUREMENT:
			line += "Class: " + mt.getName();
			line += ", Metric: "
					+ mv.getName()
					+ ", value (avg): "
					+ mv.getValue();
			break;
		case METHOD_MEASUREMENT:
			line += "Method: " + mt.getName(); 
			line += ", Metric: "
					+ mv.getName()
					+ ", value: "
					+ mv.getValue();
		}
		
		line += ", violated: "
			+ mv.isViolated();
		System.out.println(line);
		for (Measurement m2 : mt.getInnerMeasurements()) {
			printPackage(identation + 4, m2);
		}
	}
	
	private String getSource(String string) {
		String sourceUri = this.getClass().getClassLoader().getResource(string).getFile();
		String sourceCode = null;
		sourceCode = readFile(sourceUri);
		return sourceCode;
	}
	
	private String readFile(String fileName)  {
		BufferedReader br = null;
	    StringBuilder sb = new StringBuilder();
	    try {
	    	br = new BufferedReader(new FileReader(fileName));
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } catch (IOException e) {
			e.printStackTrace();
		} finally {
	        try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return sb.toString();
	}

}