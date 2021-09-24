package project.discountapplication.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import project.discountapplication.dao.CategoryDao;
import project.discountapplication.entity.Category;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {
	
	@Autowired
	CategoryDao categoryDao;

	@Override
	public Category convert(String categoryId) {
		long catId = Long.parseLong(categoryId);
		
		Category category = categoryDao.findCategoryById(catId);
		
		
		return category;
	}

}
