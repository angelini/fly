package com.angelini.fly;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Router {
	public static final int GET = 1;
	public static final int POST = 2;
	public static final int DELETE = 3;
	public static final int PUT = 4;
	
	int verb();
	String route();
}
