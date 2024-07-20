package com.pharmacystore.dao;

import java.util.List;

import com.pharmacystore.pojo.*;

public interface UserDAO {

	boolean register(User user);
	boolean checkUser(User user);
	
	boolean cancelOrder(Order o);
	List<Order> displaymycancelledorder(User u);
}
