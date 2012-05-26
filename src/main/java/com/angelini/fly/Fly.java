package com.angelini.fly;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fly {
	
	private int port;
	private FlyDB db;
	private ServletContextHandler context;
	
	private static Logger log = LoggerFactory.getLogger(Fly.class);
	
	public Fly(int port, FlyDB db) {
		this.port = port;
		this.db = db;
		
		context = new ServletContextHandler();
	    context.setContextPath("/");
	    
	    this.addStaticFolder("/htdocs", "/js/*");
		this.addStaticFolder("/htdocs", "/images/*");
		this.addStaticFolder("/htdocs", "/img/*");
		this.addStaticFolder("/htdocs", "/css/*");
	}
	
	public void addStaticFolder(String base, String content) {
		context.addServlet(new ServletHolder(new ClasspathFilesServlet(base)), content);
	}
	
	public void addHTMLFolder(String folder) {
		context.addServlet(new ServletHolder(new LayoutTemplater(folder)), "/*");
	}
	
	public void addServlet(Class<?> servlet, String base) throws RouterException {
		context.addServlet(new ServletHolder(new FlyRouter(db, servlet)), base);
	}

	public void start() throws Exception {
		Server server = new Server(port);
		server.setHandler(context);
		
		log.info("Server starting on port {}", port);
		server.start();
		server.join();
	}
	    
}
