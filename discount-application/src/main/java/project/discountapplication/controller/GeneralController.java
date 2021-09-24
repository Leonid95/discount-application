package project.discountapplication.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.discountapplication.dao.CategoryDao;
import project.discountapplication.dao.DiscountDao;
import project.discountapplication.entity.Category;
import project.discountapplication.entity.Discount;

@Controller
public class GeneralController {
	
	@Autowired
	DiscountDao discountDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	@GetMapping("/")
	public String showHome(@RequestParam(name = "categoryId", required = false) String categoryId, Model theModel) {
		
		Collection<Discount> discounts;
		
		if(categoryId != null) {
			long catId = Long.parseLong(categoryId);
			
			Category selectedCategory = categoryDao.findCategoryById(catId);
			
			discounts = selectedCategory.getValidDiscounts();
		}
		else {
			discounts = discountDao.getAllValidDiscounts();
		}
		
		theModel.addAttribute("discounts", discounts);
		
		theModel.addAttribute("categories", categoryDao.getAllCategories());
		
		
		return "home";
	}
	
	
}
