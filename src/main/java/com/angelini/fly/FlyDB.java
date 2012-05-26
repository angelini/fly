package com.angelini.fly;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class FlyDB {
	
	private BoneCP pool;
	
	public FlyDB(String jdbc, String user, String pass) throws SQLException, PropertyVetoException, Exception {
		Class.forName("java.sql.Driver");
		
		BoneCPConfig config = new BoneCPConfig();
	    config.setJdbcUrl(jdbc);
	    config.setUsername(user);
	    config.setPassword(pass);
	    
	    config.setMinConnectionsPerPartition(5);
		config.setMaxConnectionsPerPartition(20);
		config.setPartitionCount(1);
	    
	    pool = new BoneCP(config);
	}
	
	public Connection getConn() throws SQLException {
		return pool.getConnection();
	}
	
}
