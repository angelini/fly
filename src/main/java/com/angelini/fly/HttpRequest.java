package com.angelini.fly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

public class HttpRequest {
	
	private HttpServletRequest req;
	private Map<String, String> params;
	private MultiMap<String> query;
	
	public HttpRequest(HttpServletRequest req) {
		this.req = req;
		params = new HashMap<String, String>();
		
		query = new MultiMap<String>();
		if (this.getQueryString() != null) {
			UrlEncoded.decodeTo(this.getQueryString(), query, "UTF-8");
		}
	}
	
	public String getParam(String key) {
		return params.get(key);
	}
	
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public String getQuery(String key) {
		return query.getString(key);
	}
	
	public Integer getQueryInt(String key) {
		try {
			return Integer.parseInt(query.getString(key));
		} catch (Exception e) {
			return null;
		}
	}
	
	public Double getQueryDouble(String key) {
		try {
			return Double.parseDouble(query.getString(key));
		} catch (Exception e) {
			return null;
		}
	}
	
	public Date getQueryDate(String key) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			if (query.getString(key) == null) { return null; }
			return df.parse(query.getString(key));
		} catch (ParseException e) {
			return null;
		}
	}
	
	// Delegate Methods

	public boolean authenticate(HttpServletResponse arg0) throws IOException,
			ServletException {
		return req.authenticate(arg0);
	}

	public AsyncContext getAsyncContext() {
		return req.getAsyncContext();
	}

	public Object getAttribute(String arg0) {
		return req.getAttribute(arg0);
	}

	public Enumeration<String> getAttributeNames() {
		return req.getAttributeNames();
	}

	public String getAuthType() {
		return req.getAuthType();
	}

	public String getCharacterEncoding() {
		return req.getCharacterEncoding();
	}

	public int getContentLength() {
		return req.getContentLength();
	}

	public String getContentType() {
		return req.getContentType();
	}

	public String getContextPath() {
		return req.getContextPath();
	}

	public Cookie[] getCookies() {
		return req.getCookies();
	}

	public long getDateHeader(String arg0) {
		return req.getDateHeader(arg0);
	}

	public DispatcherType getDispatcherType() {
		return req.getDispatcherType();
	}

	public String getHeader(String arg0) {
		return req.getHeader(arg0);
	}

	public Enumeration<String> getHeaderNames() {
		return req.getHeaderNames();
	}

	public Enumeration<String> getHeaders(String arg0) {
		return req.getHeaders(arg0);
	}

	public ServletInputStream getInputStream() throws IOException {
		return req.getInputStream();
	}

	public int getIntHeader(String arg0) {
		return req.getIntHeader(arg0);
	}

	public String getLocalAddr() {
		return req.getLocalAddr();
	}

	public String getLocalName() {
		return req.getLocalName();
	}

	public int getLocalPort() {
		return req.getLocalPort();
	}

	public Locale getLocale() {
		return req.getLocale();
	}

	public Enumeration<Locale> getLocales() {
		return req.getLocales();
	}

	public String getMethod() {
		return req.getMethod();
	}

	public String getParameter(String arg0) {
		return req.getParameter(arg0);
	}

	public Map<String, String[]> getParameterMap() {
		return req.getParameterMap();
	}

	public Enumeration<String> getParameterNames() {
		return req.getParameterNames();
	}

	public String[] getParameterValues(String arg0) {
		return req.getParameterValues(arg0);
	}

	public Part getPart(String arg0) throws IOException, ServletException {
		return req.getPart(arg0);
	}

	public Collection<Part> getParts() throws IOException, ServletException {
		return req.getParts();
	}

	public String getPathInfo() {
		return req.getPathInfo();
	}

	public String getPathTranslated() {
		return req.getPathTranslated();
	}

	public String getProtocol() {
		return req.getProtocol();
	}

	public String getQueryString() {
		return req.getQueryString();
	}

	public BufferedReader getReader() throws IOException {
		return req.getReader();
	}

	public String getRemoteAddr() {
		return req.getRemoteAddr();
	}

	public String getRemoteHost() {
		return req.getRemoteHost();
	}

	public int getRemotePort() {
		return req.getRemotePort();
	}

	public String getRemoteUser() {
		return req.getRemoteUser();
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {
		return req.getRequestDispatcher(arg0);
	}

	public String getRequestURI() {
		return req.getRequestURI();
	}

	public StringBuffer getRequestURL() {
		return req.getRequestURL();
	}

	public String getRequestedSessionId() {
		return req.getRequestedSessionId();
	}

	public String getScheme() {
		return req.getScheme();
	}

	public String getServerName() {
		return req.getServerName();
	}

	public int getServerPort() {
		return req.getServerPort();
	}

	public ServletContext getServletContext() {
		return req.getServletContext();
	}

	public String getServletPath() {
		return req.getServletPath();
	}

	public HttpSession getSession() {
		return req.getSession();
	}

	public HttpSession getSession(boolean arg0) {
		return req.getSession(arg0);
	}

	public Principal getUserPrincipal() {
		return req.getUserPrincipal();
	}

	public boolean isAsyncStarted() {
		return req.isAsyncStarted();
	}

	public boolean isAsyncSupported() {
		return req.isAsyncSupported();
	}

	public boolean isRequestedSessionIdFromCookie() {
		return req.isRequestedSessionIdFromCookie();
	}

	public boolean isRequestedSessionIdFromURL() {
		return req.isRequestedSessionIdFromURL();
	}

	public boolean isRequestedSessionIdValid() {
		return req.isRequestedSessionIdValid();
	}

	public boolean isSecure() {
		return req.isSecure();
	}

	public boolean isUserInRole(String arg0) {
		return req.isUserInRole(arg0);
	}

	public void login(String arg0, String arg1) throws ServletException {
		req.login(arg0, arg1);
	}

	public void logout() throws ServletException {
		req.logout();
	}

	public void removeAttribute(String arg0) {
		req.removeAttribute(arg0);
	}

	public void setAttribute(String arg0, Object arg1) {
		req.setAttribute(arg0, arg1);
	}

	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {
		req.setCharacterEncoding(arg0);
	}

	public AsyncContext startAsync() throws IllegalStateException {
		return req.startAsync();
	}

	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1)
			throws IllegalStateException {
		return req.startAsync(arg0, arg1);
	}

}
