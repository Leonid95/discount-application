package project.discountapplication.dao;

import java.util.Collection;

import project.discountapplication.entity.Category;

public interface CategoryDao {
	Collection <Category> getAllCategories();
	
	void saveCategory(Category theCategory);
	
	void deleteCategory(long categoryId);
	
	Category findCategoryById(long categoryId);
	
	boolean isTitleUsed(String title, long id);
}
