package com.angelini.fly;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class HttpResponse {
	
	private HttpServletResponse res;
	
	public HttpResponse(HttpServletResponse res) {
		this.res = res;
	}
	
	public void sendObject(Object obj) throws IOException {
		res.setContentType("application/json");
		res.setStatus(200);
		res.getWriter().print(new Gson().toJson(obj));
	}

	// Delegate Methods
	
	public void addCookie(Cookie arg0) {
		res.addCookie(arg0);
	}

	public void addDateHeader(String arg0, long arg1) {
		res.addDateHeader(arg0, arg1);
	}

	public void addHeader(String arg0, String arg1) {
		res.addHeader(arg0, arg1);
	}

	public void addIntHeader(String arg0, int arg1) {
		res.addIntHeader(arg0, arg1);
	}

	public boolean containsHeader(String arg0) {
		return res.containsHeader(arg0);
	}

	public String encodeRedirectURL(String arg0) {
		return res.encodeRedirectURL(arg0);
	}

	public String encodeURL(String arg0) {
		return res.encodeURL(arg0);
	}

	public void flushBuffer() throws IOException {
		res.flushBuffer();
	}

	public int getBufferSize() {
		return res.getBufferSize();
	}

	public String getCharacterEncoding() {
		return res.getCharacterEncoding();
	}

	public String getContentType() {
		return res.getContentType();
	}

	public String getHeader(String arg0) {
		return res.getHeader(arg0);
	}

	public Collection<String> getHeaderNames() {
		return res.getHeaderNames();
	}

	public Collection<String> getHeaders(String arg0) {
		return res.getHeaders(arg0);
	}

	public Locale getLocale() {
		return res.getLocale();
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return res.getOutputStream();
	}

	public int getStatus() {
		return res.getStatus();
	}

	public PrintWriter getWriter() throws IOException {
		return res.getWriter();
	}

	public boolean isCommitted() {
		return res.isCommitted();
	}

	public void reset() {
		res.reset();
	}

	public void resetBuffer() {
		res.resetBuffer();
	}

	public void sendError(int arg0, String arg1) throws IOException {
		res.sendError(arg0, arg1);
	}

	public void sendError(int arg0) throws IOException {
		res.sendError(arg0);
	}

	public void sendRedirect(String arg0) throws IOException {
		res.sendRedirect(arg0);
	}

	public void setBufferSize(int arg0) {
		res.setBufferSize(arg0);
	}

	public void setCharacterEncoding(String arg0) {
		res.setCharacterEncoding(arg0);
	}

	public void setContentLength(int arg0) {
		res.setContentLength(arg0);
	}

	public void setContentType(String arg0) {
		res.setContentType(arg0);
	}

	public void setDateHeader(String arg0, long arg1) {
		res.setDateHeader(arg0, arg1);
	}

	public void setHeader(String arg0, String arg1) {
		res.setHeader(arg0, arg1);
	}

	public void setIntHeader(String arg0, int arg1) {
		res.setIntHeader(arg0, arg1);
	}

	public void setLocale(Locale arg0) {
		res.setLocale(arg0);
	}

	public void setStatus(int arg0) {
		res.setStatus(arg0);
	}

}
