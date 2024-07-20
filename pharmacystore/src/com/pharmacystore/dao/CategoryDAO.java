package com.pharmacystore.dao;

import java.util.List;

import com.pharmacystore.pojo.Category;

public interface CategoryDAO {

	boolean addCategory(Category category);
	boolean deleteCategory(int catid);
	String getCategoryNameById(int categoryId);
	List<Category> getAllCategories();
	int counttotalcategories();
}
