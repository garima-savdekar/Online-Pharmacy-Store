package com.pharmacystore.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pharmacystore.connection.DbConnection;
import com.pharmacystore.dao.CategoryDAO;
import com.pharmacystore.pojo.Category;

public class CategoryDAOImpl implements CategoryDAO {

	@Override
	public boolean addCategory(Category category) {

		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con.prepareStatement("insert into category(categoryName) values(?)");

			pst.setString(1, category.getCategoryName());

			int count = pst.executeUpdate();

			if (count > 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteCategory(int catid) {

		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con.prepareStatement("delete from category where categoryId = ?");

			pst.setInt(1, catid);

			int count = pst.executeUpdate();

			if (count > 0)
				return true;
			else
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getCategoryNameById(int categoryId) {

		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con.prepareStatement("select categoryName " + "from category where categoryId = ?");

			pst.setInt(1, categoryId);

			ResultSet rs = pst.executeQuery();

			if (rs.isBeforeFirst()) {
				rs.next();
				String categoryName = rs.getString("categoryName");
				return categoryName;
			} else
				return "";

		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	public List<Category> getAllCategories() {
		List<Category> lst = new ArrayList<>();

		try (Connection con = DbConnection.getDatabaseConnection()) {
			PreparedStatement pst = con.prepareStatement("SELECT * FROM category");

			ResultSet rs = pst.executeQuery();

			if (rs.isBeforeFirst()) {

				while (rs.next()) {
					Category cat = new Category();
					cat.setCategoryId(rs.getInt("categoryId"));
					cat.setCategoryName(rs.getString("categoryName"));
					lst.add(cat);
				}
			}

			return lst;
		} catch (SQLException e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}

	@Override
	public int counttotalcategories() {
		int count=0;
		try (Connection con = DbConnection.getDatabaseConnection()) {
			PreparedStatement pst = con.prepareStatement("SELECT count(distinct categoryId) FROM category");

			ResultSet rs = pst.executeQuery();
			if (rs.isBeforeFirst()) {

				while (rs.next()) {
			count=rs.getInt(1);
				}}
			System.out.println(count);
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		return count;
	}
	
	
}
