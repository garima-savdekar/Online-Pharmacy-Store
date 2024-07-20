package com.pharmacystore.connection;

import java.sql.DriverManager;

public interface DbDetails {

	String CONSTR = "jdbc:mysql://localhost:3306/"
			+ "pharmacystoredb";
	String USERNAME = "root";
	String PASSWORD = "root@123";
	String DBDRIVER = "com.mysql.cj.jdbc.Driver";
}


