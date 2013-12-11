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
package com.obomprogramador.tools.jqana.model.defaultimpl;

import java.util.List;

import org.w3c.dom.Document;

import com.obomprogramador.tools.jqana.model.Measurement;
import com.obomprogramador.tools.jqana.model.XmlGenerator;

/**
 * Default implementation of XmlGenerator.
 * 
 * @see XmlGenerator
 * 
		<?xml version="1.0" encoding="UTF-8"?>
		<jqana-report>
		    <version></version>
		    <date></date>
		    <project></project>
		    <package-summary>
		        <package>
		            <name></name>
		            <metrics>
		                <metric>
		                    <name></name>
		                    <value></value>
		                    <violated></violated>
		                    <message></message>
		                </metric>
		            </metrics>
		        </package>
		    </package-summary>
		    <package-detail>
		        <package>
		            <class>
		                <name></name>
		                <metric>
		                    <name></name>
		                    <value></value>
		                    <violated></violated>
		                    <message></message>
		                </metric>
		                <methods>
		                    <method>
			                    <name></name>
			                    <metric>
			                    	<name></name>
			                    	<value></value>
			                    	<violated></violated>
			                    	<message></message>
			                	</metric>
		                    </method>
		                </methods>
		            </class>
		        </package>
		    </package-detail>
		</jqana-report>  
 *
 * 
 * @author Cleuton Sampaio
 *
 */
public class DefaultXmlGenerator implements XmlGenerator {

	public DefaultXmlGenerator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the list of measurements, and classifies it according to 
	 * package and class. 
	 * The aggregated values are calculated as simple average, i.e.
	 * the Class value for Cyclomatic Complexity is the average of it's
	 * methods Cyclomatic Complexity metrics.
	 */
	@Override
	public Document serialize(List<Measurement> measurements) {
		Document report = null;
		return report;
	}

}