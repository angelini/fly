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
	private boolean requireAuth = false;
	private ServletContextHandler context;
	private Authentication auth;
	
	private String html;
	private String compFolder;
	private Map<String, Class<?>> servlets;
	
	private static Logger log = LoggerFactory.getLogger(Fly.class);
	
	public Fly(int port, FlyDB db, String html) throws IOException {
		initialize(port, db, html, null);
	}
	
	public Fly(int port, FlyDB db, String html, String components) throws IOException {
		initialize(port, db, html, components);
	}
	
	private void initialize(int port, FlyDB db, String html, String compFolder) {
		this.port = port;
		this.db = db;
		this.html = html;
		this.compFolder = compFolder;
		
		this.auth = new Authentication();
		this.servlets = new HashMap<String, Class<?>>();
		
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
	
	public void addServlet(Class<?> servlet, String base) throws RouterException {
		servlets.put(base, servlet);
	}
	
	public void requireAuth(Class<?> authClass) throws IOException {
		if (requireAuth) {
			throw new RuntimeException("Can only require authentication once");
		}
		
		requireAuth = true;
		context.addServlet(new ServletHolder(new AuthServlet(db, auth, authClass)), "/auth/*");
	}

	public void start() throws Exception {
		if (!requireAuth) {
			auth = null;
		}
		
		for (Map.Entry<String, Class<?>> servlet : servlets.entrySet()) {
			context.addServlet(new ServletHolder(new FlyRouter(db, servlet.getValue(), auth)), servlet.getKey());
		}
		
		LayoutTemplater layout = new LayoutTemplater(html, readComponents(compFolder), auth);
		
		context.addServlet(new ServletHolder(layout), "/*");
		
		Server server = new Server(port);
		server.setHandler(context);
		
		log.info("Server starting on port {}", port);
		server.start();
		server.join();
	}
	
	private Map<String, String> readComponents(String compFolder) throws IOException {
		Map<String, String> components = new HashMap<String, String>();
		
		if (components != null) {
			File dir = new File(getClass().getResource(compFolder).getPath());		
			List<File> files = Utils.readDir(dir, new ArrayList<File>());
			
			for (File file : files) {
				String comp = Utils.readFile(file);
				components.put(file.getName(), comp);
			}
		}
		
		return components;
	}
	    
}
