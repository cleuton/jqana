/**
 * jQana - Open Source Java(TM) code quality analyzer.
 * 
 * Copyright 2013 Cleuton Sampaio de Melo Jr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Project website: http://www.jqana.com
 */
package com.obomprogramador.tools.jqana.mavenplugin;



import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;
import org.w3c.dom.Document;

import com.obomprogramador.tools.jqana.context.Context;
import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultProjectProcessor;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultXml2HtmlConverter;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultXmlGenerator;

/**
 * This is the Mojo that implements jQana maven plugin.
 * 
 * @author Cleuton Sampaio
 *
 */
@Mojo(name = "report")
public class JqanaMojo extends AbstractMavenReport {
	
    /**
     * Report output directory. Note that this parameter is only relevant if the goal is run from the command line or
     * from the default build lifecycle. If the goal is run indirectly as part of a site generation, the output
     * directory configured in the Maven Site Plugin is used instead.
     */
    @Parameter( defaultValue = "${project.reporting.outputDirectory}" )
    private File outputDirectory;
    
    /**
     * The source dir
     */
    @Parameter( defaultValue = "${project.build.sourceDirectory}" )
    private File sourceDirectory;
    /**
     * The Maven Project.
     */
    @Component
    private MavenProject project;
 
    /**
     * Doxia Site Renderer.
     */
    @Component
    protected Renderer siteRenderer;

	public JqanaMojo() {
		super();
	}

	@Override
	public String getDescription(Locale arg0) {
		return getBundle(arg0).getString( "report.description" );
	}

	@Override
	public String getName(Locale arg0) {
		return getBundle(arg0).getString( "report.name" );
	}

	@Override
	public String getOutputName() {
		return "jQana report";
	}

	@Override
	protected void executeReport(Locale locale) throws MavenReportException {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		Date date = new Date();
		String reportDate = df.format(date);
		
		Sink sink = getSink();
	    sink.head();
	    sink.title();
	    sink.text(this.getName(locale));
	    sink.title_();
	    sink.head_();
	    sink.body();
	    createReportBegin(sink,reportDate,locale);
	    reportPackageMetrics(sink,locale);
	    sink.section1_();
	    sink.body_();
	    sink.flush();
	    sink.close();
	}

	private void createReportBegin(Sink sink, String reportDate, Locale locale) {
		sink.section1();
		sink.sectionTitle1();
		String rBegin = getBundle(locale).getString("report.begin");
		sink.text(rBegin);
		sink.sectionTitle1_();
		sink.lineBreak();
		sink.text(getBundle(locale).getString("report.dateHeader") + ": " + reportDate);
	}

	private void reportPackageMetrics(Sink sink, Locale locale) {
		sink.sectionTitle1();
		sink.text(getBundle(locale).getString("report.projectTitle"));
		sink.sectionTitle1_();
		
		try {
			DefaultProjectProcessor dpp = new DefaultProjectProcessor(new Context());
			dpp.setLog(getLog());
			File sourceDir = new File(this.project.getModel().getBuild().getSourceDirectory().replace("\\", "/"));
			Measurement projectMeasurement = dpp.process(this.getProject().getName(), sourceDir);
			DefaultXmlGenerator generator = new DefaultXmlGenerator();
			Document report = generator.serialize(projectMeasurement);
			getLog().debug(generator.xml2String(report));
			DefaultXml2HtmlConverter converter = new DefaultXml2HtmlConverter();
			String output = converter.convert(generator.xml2String(report));
			sink.rawText(output);
		}
		catch (Exception ex) {
			getLog().error(ex);
		}
	}
	
	@Override
	protected String getOutputDirectory() {
		return outputDirectory.getAbsolutePath();
	}

	@Override
	protected MavenProject getProject() {
		return project;
	}

	@Override
	protected Renderer getSiteRenderer() {
		return siteRenderer;
	}

    private ResourceBundle getBundle( Locale locale ) {
        return ResourceBundle.getBundle( "report", locale, this.getClass().getClassLoader() );
    }
}
