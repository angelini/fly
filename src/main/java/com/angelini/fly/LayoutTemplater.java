package com.angelini.fly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LayoutTemplater extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String LAYOUT = "/layout/layout.html";
	
	private String folder;
	private String layout;
	
	private static Logger log = LoggerFactory.getLogger(LayoutTemplater.class);
	
	public LayoutTemplater(String folder) {
		try {
			this.folder = folder;
			this.layout = getClasspathFile(LAYOUT);
			
		} catch (IOException e) {
			log.error("Error building layout", e);
		}
	}
	
	private String getClasspathFile(String path) throws IOException {
		InputStream streamBody = this.getClass().getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(streamBody));
		StringBuilder sb = new StringBuilder();
		 
    	String line;
    	while ((line = br.readLine()) != null) {
    		sb.append(line + "\n");
    	}
    	
    	br.close();
    	return sb.toString();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String path = (req.getPathInfo() == "/") ? "/index.html" : req.getPathInfo();
			String html = this.layout.replace("{{body}}", getClasspathFile(folder + path));
			
			resp.setStatus(200);
			resp.getWriter().print(html);
			
		} catch (Exception e) {
			resp.setStatus(404);
		}
	}
	
}
