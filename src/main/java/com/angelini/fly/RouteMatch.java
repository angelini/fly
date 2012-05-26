package com.angelini.fly;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RouteMatch {

	private Method method;
	private Map<String, String> params;
	
	public RouteMatch() {
		params = new HashMap<String, String>();
	}
	
	public String getParam(String key) {
		return params.get(key);
	}
	
	public void setParam(String key, String val) {
		params.put(key, val);
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
}
