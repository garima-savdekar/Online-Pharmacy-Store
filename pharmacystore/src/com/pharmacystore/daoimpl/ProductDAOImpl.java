package com.pharmacystore.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pharmacystore.connection.DbConnection;
import com.pharmacystore.dao.ProductDAO;
import com.pharmacystore.pojo.Product;

public class ProductDAOImpl implements ProductDAO {

	@Override
	public boolean addProduct(Product p) {
		try (Connection con = DbConnection.getDatabaseConnection()) {
			PreparedStatement pst = con.prepareStatement("insert into product "
					+ "(productname,price,quantity,description,categoryId)" + " values(?,?,?,?,?)");

			pst.setString(1, p.getProductname());
			pst.setInt(2, p.getPrice());
			pst.setInt(3, p.getQuantity());
			pst.setString(4, p.getDescription());
			pst.setInt(5, p.getCategoryid());

			int count = pst.executeUpdate();

			if (count > 0)
				return true;
			else
				return false;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteProduct(int productId) {

		try (Connection con = DbConnection.getDatabaseConnection()) {
			PreparedStatement pst = con.prepareStatement("delete from product where " + "productid = ?");

			pst.setInt(1, productId);

			int count = pst.executeUpdate();

			if (count > 0)
				return true;
			else
				return false;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateProduct(Product p) {

		boolean status = false;

		try (Connection con = DbConnection.getDatabaseConnection()) {
			PreparedStatement pst = con.prepareStatement("update product " + "set productname = ? , price = ?"
					+ " , quantity = ? , description = ? " + "where productid = ?");

			pst.setString(1, p.getProductname());
			pst.setInt(2, p.getPrice());
			pst.setInt(3, p.getQuantity());
			pst.setString(4, p.getDescription());
			pst.setInt(5, p.getProductid());

			int count = pst.executeUpdate();

			if (count > 0)
				return true;
			else
				return false;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Product> getAllProducts(int start, int total) {
		List<Product> productList = new ArrayList<>();

		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con.prepareStatement("select * from product limit ? , ?");

			pst.setInt(1, start - 1);
			pst.setInt(2, total);

			ResultSet rs = pst.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					Product product = new Product();
					product.setProductid(rs.getInt("productid"));
					product.setProductname(rs.getString("productname"));
					product.setPrice(rs.getInt("price"));
					product.setQuantity(rs.getInt("quantity"));
					product.setDescription(rs.getString("description"));
					product.setCategoryid(rs.getInt("categoryId"));

					productList.add(product);
				}
			}

			return productList;
		} catch (SQLException ex) {
			ex.printStackTrace();
			productList.clear();
			return productList;
		}
	}

	@Override
	public Product searchProduct(int productId) {

		Product product = null;

		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con.prepareStatement("select * from product" + " where productid = ?");

			pst.setInt(1, productId);

			ResultSet rs = pst.executeQuery();

			if (rs.isBeforeFirst()) {
				rs.next();

				product = new Product();
				product.setProductid(rs.getInt("productid"));
				product.setProductname(rs.getString("productname"));
				product.setPrice(rs.getInt("price"));
				product.setQuantity(rs.getInt("quantity"));
				product.setDescription(rs.getString("description"));
				product.setCategoryid(rs.getInt("categoryId"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}

		return product;
	}

	@Override
	public List<Product> getAllProducts() {

		List<Product> productList = new ArrayList<>();

		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con.prepareStatement("select * from product");

			ResultSet rs = pst.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					Product product = new Product();
					product.setProductid(rs.getInt("productid"));
					product.setProductname(rs.getString("productname"));
					product.setPrice(rs.getInt("price"));
					product.setQuantity(rs.getInt("quantity"));
					product.setDescription(rs.getString("description"));
					product.setCategoryid(rs.getInt("categoryId"));

					productList.add(product);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			productList.clear();
			return productList;
		}

		return productList;
	}

	@Override
	public boolean updateQuantityAfterOrder(int pid, int new_quantity) {

		boolean status = false;

		try (Connection con = DbConnection.getDatabaseConnection()) {
			PreparedStatement pst = con.prepareStatement("update product set " + "quantity = ? where productid = ?");

			pst.setInt(1, new_quantity);
			pst.setInt(2, pid);

			int count = pst.executeUpdate();

			if (count > 0)
				return true;
			else
				return false;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public int getQuantityOfProduct(Product p) {
		int quantity=0;
		try (Connection con = DbConnection.getDatabaseConnection()) {
			PreparedStatement pst = con.prepareStatement("select quantity from product where productid = ?");

			pst.setInt(1, p.getProductid());
			
			ResultSet rs = pst.executeQuery();
			
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					quantity=rs.getInt("quantity");
					
				}
			}
			System.out.println(quantity);
		} catch (SQLException ex) {
			ex.printStackTrace();
			
		}
		return quantity;
	}

	@Override
	public void updateQuantityAfterCancellation(Product p2) {
		try (Connection con = DbConnection.getDatabaseConnection()) {
			PreparedStatement pst = con.prepareStatement("update product set quantity = ? where productid = ?");
			pst.setInt(1, p2.getQuantity());
			pst.setInt(2, p2.getProductid());
			
			ResultSet rs = pst.executeQuery();
			int quantity=0;
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					quantity=rs.getInt("quantity");
					
				}
			}
			System.out.println(quantity);
		} catch (SQLException ex) {
			ex.printStackTrace();
			
		}
		
	}

	@Override
	public List<Product> getcategorywise(int cid) {
		List<Product> productList = new ArrayList<>();

		try (Connection con = DbConnection.getDatabaseConnection()) {

			PreparedStatement pst = con.prepareStatement("select * from product where categoryId=?");
			pst.setInt(1, cid);
			ResultSet rs = pst.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					Product product = new Product();
					product.setProductid(rs.getInt("productid"));
					product.setProductname(rs.getString("productname"));
					product.setPrice(rs.getInt("price"));
					product.setQuantity(rs.getInt("quantity"));
					product.setDescription(rs.getString("description"));
					product.setCategoryid(rs.getInt("categoryId"));

					productList.add(product);
				}
			}
			System.out.println(productList);
		} catch (SQLException ex) {
			ex.printStackTrace();
			productList.clear();
			return productList;
		}

		return productList;
		
	}
	
	
	
}
