package com.pharmacystore.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pharmacystore.connection.DbConnection;
import com.pharmacystore.dao.UserDAO;
import com.pharmacystore.pojo.Order;
import com.pharmacystore.pojo.User;

public class UserDAOImpl implements UserDAO {

	@Override
	public boolean register(User u) {

		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con.prepareStatement("insert into user values(?,?,?,?,?,?,?,?)");
			pst.setString(1, u.getUserid());
			pst.setString(2, u.getPassword());
			pst.setString(3, u.getEmailid());
			pst.setInt(4, u.getAge());
			pst.setString(5, u.getContact());
			pst.setString(6, u.getCity());
			pst.setString(7, u.getState());
			pst.setString(8, u.getPincode());

			int count = pst.executeUpdate();

			if (count > 0)
				return true;
			else
				return false;
		} catch (SQLException | NullPointerException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean checkUser(User user) {

		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con
					.prepareStatement("select * from user where userid like ? " + "and password like ?");

			pst.setString(1, user.getUserid());
			pst.setString(2, user.getPassword());

			ResultSet rs = pst.executeQuery();

			if (rs.isBeforeFirst())
				return true;
			else
				return false;
		} catch (SQLException | NullPointerException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean cancelOrder(Order o) {
		
		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con
					.prepareStatement("update productorder set cancelled=? where orderid=?");

			pst.setBoolean(1, true);
			pst.setInt(2, o.getOrderid());
			
			int count = pst.executeUpdate();

			if (count > 0)
				return true;
			else
				return false;
			
		} catch (SQLException | NullPointerException ex) {
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public List<Order> displaymycancelledorder(User u) {
		List<Order> list = new ArrayList<>();

		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con.prepareStatement("select * from productorder where customerid=? and cancelled=?");
			pst.setString(1,u.getUserid());
			pst.setBoolean(2, true);

			ResultSet rs = pst.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					Order order = new Order();
					order.setOrderid(rs.getInt("orderid"));
					order.setOrderedunits(rs.getInt("orderdunits"));
					order.setAddress(rs.getString("address"));
					order.setOrdereddate(rs.getDate("ordereddate"));
					order.setRequesteddate(rs.getDate("requesteddate"));
					order.setAccepted(rs.getBoolean("accepted"));
					order.setCancelled(rs.getBoolean("cancelled"));
					order.setConfirmed(rs.getBoolean("confirmed"));
					order.setBillamount(rs.getInt("billamount"));
					order.setCustomerid(rs.getString("customerid"));
					order.setProductid(rs.getInt("productid"));

					list.add(order);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			list.clear();
			return list;
		}
		return list;
	}
	
	
	
	
}
