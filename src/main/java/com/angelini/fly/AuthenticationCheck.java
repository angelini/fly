package com.angelini.fly;

public interface AuthenticationCheck {

	public void init(FlyDB db);
	
	public String check(String username, String password);
	
}
