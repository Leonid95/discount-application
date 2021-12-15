package project.discountapplication.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.discountapplication.dao.CategoryDao;
import project.discountapplication.dao.DiscountDao;
import project.discountapplication.dao.UserDao;
import project.discountapplication.entity.Discount;
import project.discountapplication.entity.User;

@Controller
public class DiscountController {

	@Autowired
	DiscountDao discountDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	CategoryDao categoryDao;

	@GetMapping("/add-discount")
	public String addNewDiscount(Model theModel, HttpServletRequest request) {
		
		theModel.addAttribute("categories", categoryDao.getAllCategories());

		theModel.addAttribute("discount", new Discount());

		User currentUser = userDao.getCurrentUser();

		theModel.addAttribute("user", currentUser);

		return "discount-form";
	}

	@GetMapping("/update-discount")
	public String updateDiscount(@RequestParam("discountId") long discountId, Model theModel,
			HttpServletRequest request) {
		
		theModel.addAttribute("categories", categoryDao.getAllCategories());

		Discount theDiscount = discountDao.getDiscountById(discountId);

		// Set the discount as a model attribute to pre-populate the form
		theModel.addAttribute("discount", theDiscount);

		User currentUser = userDao.getCurrentUser();
		
		long userId = currentUser.getId();
		
		// If the discount was not created by the user and the user is not an administrator, 
		// then operation must be forbidden	
		if(!discountDao.checkOwner(userId, discountId) && !currentUser.isAdmin()) {
			return "forbidden";
		}

		theModel.addAttribute("user", currentUser);

		return "discount-form";
	}

	@PostMapping("/save-discount")
	public String saveDiscount(@ModelAttribute("discount") Discount theDiscount, Model theModel) {
		
		// Check if values of created discount are correct
		
		
		// If the discount with current title already exists and it is not the same discount then the user must get back to the discount form
		if(discountDao.isTitleUsed(theDiscount.getTitle(), theDiscount.getId())) {
			theModel.addAttribute("titleError", "Current discount title already exists");
			
			theModel.addAttribute("categories", categoryDao.getAllCategories());
			
			return "discount-form";
		}
		
		// If end date is earlier then start date then the user must get back the discount form
		if(theDiscount.getStartDate() != null && theDiscount.getStartDate().compareTo(theDiscount.getEndDate()) > 0) {
			theModel.addAttribute("dateError", "End date must always be later then start date.");
			
			theModel.addAttribute("categories", categoryDao.getAllCategories());
			
			return "discount-form";
		}
		
		
		
		discountDao.saveDiscount(theDiscount);

		User currentUser = userDao.getCurrentUser();

		// If the user is administrator then it must be redirected to the
		// administrator's discount view.
		// Otherwise it must be redirected to the client's discount view.
		if (currentUser.isAdmin()) {
			return "redirect:/admin/discount-list";
		} else {
			return "redirect:/client/discount-list";
		}
	}

	@GetMapping("delete-discount")
	public String deleteDiscount(@RequestParam("discountId") long discountID, Model theModel) {
		
		Discount theDiscount = discountDao.getDiscountById(discountID);
		
		User currentUser = userDao.getCurrentUser();
		
		long userID = currentUser.getId();
		
		// If the discount was not created by the user and the user is not an administrator, 
		// then operation must be forbidden	
		if(!discountDao.checkOwner(userID, discountID) && !currentUser.isAdmin()) {
			return "forbidden";
		}
		
		discountDao.deleteDiscount(discountID);
		
		        

		// If the user is administrator then it must be redirected to the
		// administrator's discount view.
		// Otherwise it must be redirected to the client's discount view.
		if (currentUser.isAdmin()) {
			return "redirect:/admin/discount-list";
		} else {
			return "redirect:/client/discount-list";
		}
	}

}
