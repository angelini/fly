package com.angelini.fly;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public Fly(int port, FlyDB db, String html) throws IOException {
		initialize(port, db, html, null);
	}
	
	public Fly(int port, FlyDB db, String html, String components) throws IOException {
		initialize(port, db, html, components);
	}
	
	private void initialize(int port, FlyDB db, String html, String compFolder) throws IOException {
		this.port = port;
		this.db = db;
		
		context = new ServletContextHandler();
	    context.setContextPath("/");
	    
	    this.addStaticFolder("/htdocs", "/js/*");
		this.addStaticFolder("/htdocs", "/images/*");
		this.addStaticFolder("/htdocs", "/img/*");
		this.addStaticFolder("/htdocs", "/css/*");
		
		Map<String, String> components = new HashMap<String, String>();
		
		if (components != null) {
			File dir = new File(getClass().getResource(compFolder).getPath());		
			List<File> files = Utils.readDir(dir, new ArrayList<File>());
			
			for (File file : files) {
				String comp = Utils.readFile(file);
				components.put(file.getName(), comp);
			}
		}
		
		LayoutTemplater layout = new LayoutTemplater(html, components);
		
		context.addServlet(new ServletHolder(layout), "/*");
	}
	
	public void addStaticFolder(String base, String content) {
		context.addServlet(new ServletHolder(new ClasspathFilesServlet(base)), content);
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
