package com.angelini.fly;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasspathFilesServlet extends DefaultServlet {

	private static final long serialVersionUID = 1L;
	private final String basePath;
	
	private static Logger log = LoggerFactory.getLogger(ClasspathFilesServlet.class);

	public ClasspathFilesServlet(String basePath) {
		this.basePath = basePath;
	}

	public Resource getResource(String pathInContext) {
		try {
			return Resource.newClassPathResource(basePath + pathInContext);
		} catch (Exception e) {
			log.info("File Servlet error: ", e);
			return null;
		}
	};

}
