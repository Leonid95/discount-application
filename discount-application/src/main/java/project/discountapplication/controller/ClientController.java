package project.discountapplication.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.discountapplication.dao.CategoryDao;
import project.discountapplication.dao.DiscountDao;
import project.discountapplication.dao.UserDao;
import project.discountapplication.entity.Category;
import project.discountapplication.entity.Discount;
import project.discountapplication.entity.User;

@Controller
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	DiscountDao discountDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	
	@GetMapping("discount-list")
	public String getDiscountList(@RequestParam(name = "categoryId", required = false) String categoryId, Model theModel) {
		User currentUser = userDao.getCurrentUser();
		
		Collection <Discount> userDiscounts = discountDao.getDiscountsByUserId(currentUser.getId());
		
		// If the categoryId parameter was provided
		// then all the categories that don't belong to current category must be removed from the list
		if (categoryId != null) {
			long catId = Long.parseLong(categoryId);

			Category selectedCategory = categoryDao.findCategoryById(catId);

			removeNotRequiredDiscounts(userDiscounts, selectedCategory);
		}
		
		theModel.addAttribute("discounts", userDiscounts);
		
		theModel.addAttribute("categories", categoryDao.getAllCategories());
		
		return "discounts";
	}
	
	// Remove all the discounts that are not belonging to the required category
	public void removeNotRequiredDiscounts(Collection <Discount> discounts, Category category) {
		Iterator<Discount> discountIterator = discounts.iterator();
		
		while(discountIterator.hasNext()) {
			Discount currentDiscount = discountIterator.next();
			
			if(currentDiscount.getCategory() != category) {
				discountIterator.remove();
			}
		}
	}
}
