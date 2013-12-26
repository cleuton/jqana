package com.obomprogramador.tools.jqana.model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Processes all the project's packages.
 * @author Cleuton Sampaio
 *
 */
public interface ProjectProcessor {
	/**
	 * This method parses the specified folder, processing each found package, 
	 * and returning a project consolidated measurement.
	 * @param projectRootFolderName Project's root folder name.
	 * @return Project consolidated measurement.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	public Measurement process(String projectName, File projectSourceRoot) throws URISyntaxException, IOException, JAXBException, ParserConfigurationException, TransformerException, ClassNotFoundException, InstantiationException, IllegalAccessException;
}