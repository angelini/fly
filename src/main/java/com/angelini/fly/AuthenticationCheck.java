package com.angelini.fly;

public interface AuthenticationCheck {

	public void init(FlyDB db);
	
	public boolean check(String username, String password);
	
}
