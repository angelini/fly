package com.angelini.fly;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RouteTreeNode {
	
	private String route;
	private Method method;
	
	private List<RouteTreeNode> subTrees;
	
	public RouteTreeNode(String route, Method method) {
		this.route = route;
		this.method = method;
		this.subTrees = new ArrayList<RouteTreeNode>();
	}
	
	public void addNode(RouteTreeNode node) {
		this.subTrees.add(node);
	}
	
	public RouteTreeNode getMatchNextLevel(String segment, boolean leaf) {
		RouteTreeNode specificMatch = null;
		RouteTreeNode nonSpecificMatch = null;
		
		for (RouteTreeNode node : subTrees) {
			if (leaf && node.getMethod() == null) {
				continue;
			}
			
			if (node.isSpecific() && node.getRoute().equals(segment)) {
				specificMatch = node;
			}
			
			if (!node.isSpecific()) {
				nonSpecificMatch = node;
			}
		}
		
		if (specificMatch != null) { return specificMatch; }
		
		return nonSpecificMatch;
	}
	
	public boolean isSpecific() {
		if (route.length() > 0 && route.charAt(0) == ':') {
			return false;
		}
		
		return true;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public List<RouteTreeNode> getSubTrees() {
		return subTrees;
	}

	public void setSubTrees(List<RouteTreeNode> subTrees) {
		this.subTrees = subTrees;
	}

}
