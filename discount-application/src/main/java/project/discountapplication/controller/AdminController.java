package project.discountapplication.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.discountapplication.dao.CategoryDao;
import project.discountapplication.dao.DiscountDao;
import project.discountapplication.dao.UserDao;
import project.discountapplication.entity.Category;
import project.discountapplication.entity.Discount;
import project.discountapplication.entity.User;
import project.discountapplication.validation.CustomEmailValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	DiscountDao discountDao;

	@Autowired
	UserDao userDao;

	@Autowired
	CategoryDao categoryDao;

	@GetMapping("/discount-list")
	public String getDiscounts(@RequestParam(name = "categoryId", required = false) String categoryId, Model theModel) {
		Collection<Discount> discounts;

		if (categoryId != null) {
			long catId = Long.parseLong(categoryId);

			Category selectedCategory = categoryDao.findCategoryById(catId);

			discounts = selectedCategory.getDiscounts();
		} else {
			discounts = discountDao.getAllDiscounts();
		}

		theModel.addAttribute("discounts", discounts);
		theModel.addAttribute("categories", categoryDao.getAllCategories());
		theModel.addAttribute("isUserAdmin", true);
		return "discounts";
	}

	@GetMapping("/user-list")
	public String getUsers(Model theModel) {
		theModel.addAttribute("users", userDao.getAllUsers());

		return "user-list";
	}

	@GetMapping("/add-user")
	public String addUser(Model theModel) {

		theModel.addAttribute("user", new User());

		return "user-form";
	}

	@GetMapping("/update-user")
	public String updateUser(@RequestParam("userId") long userId, HttpSession httpSession, Model theModel) {

		User theUser = userDao.findById(userId);

		System.out.println("User to update: " + theUser);

		httpSession.setAttribute("previousPassword", theUser.getPassword());

		theModel.addAttribute("update", "true");

		theModel.addAttribute("user", theUser);

		return "user-form";
	}

	@PostMapping("/save-user")
	public String saveUser(@ModelAttribute("user") User theUser, HttpSession httpSession, Model theModel) {

		if (userDao.isUsernameUsed(theUser.getUsername(), theUser.getId())) {
			theModel.addAttribute("usernameUsedError", "Current username was already used by another user");

			return "user-form";
		}

		if (userDao.isEmailUsed(theUser.getEmail(), theUser.getId())) {
			theModel.addAttribute("emailUsedError", "Current email was already used by another user");

			return "user-form";
		}

		CustomEmailValidator emailValidator = new CustomEmailValidator();

		if (!emailValidator.isEmailValid(theUser.getEmail())) {
			theModel.addAttribute("emailIncorrectError", "Your email is incorrect");

			return "user-form";
		}

		String previousPassword = (String) httpSession.getAttribute("previousPassword");

		httpSession.setAttribute("previousPassword", "");

		// If the new password value was provided, then it must be set
		if (theUser.getPassword() != null && theUser.getPassword() != "") {
			userDao.saveUser(theUser);
		}
		// If new password was not provided but there was a previous password,
		// then the previous password must be set
		else if (previousPassword != null && previousPassword != "") {
			userDao.saveUser(theUser, previousPassword);
		}
		// If new password was not provided and there was no any previous password,
		// then the user must get back to the form.
		else {
			theModel.addAttribute("emptyPassword", "You must provide some password");

			return "user-form";
		}

		System.out.println("User saved: " + theUser);

		return "redirect:/admin/user-list";
	}

	@GetMapping("/delete-user")
	public String deleteUser(@RequestParam("userId") long userId, Model theModel) {

		userDao.deleteUser(userId);

		return "redirect:/admin/user-list";
	}

	@GetMapping("/category-list")
	public String getCategories(Model theModel) {
		theModel.addAttribute("categories", categoryDao.getAllCategories());

		return "category-list";
	}

	@GetMapping("/add-category")
	public String addCategory(Model theModel) {

		theModel.addAttribute("category", new Category());

		return "category-form";
	}

	@GetMapping("/update-category")
	public String updateCategory(@RequestParam("categoryId") long categoryId, Model theModel) {

		Category theCategory = categoryDao.findCategoryById(categoryId);

		theModel.addAttribute("category", theCategory);

		return "category-form";
	}

	@PostMapping("/save-category")
	public String saveCategory(@ModelAttribute("category") Category theCategory, Model theModel) {

		if (categoryDao.isTitleUsed(theCategory.getTitle(), theCategory.getId())) {
			theModel.addAttribute("titleError", "Another category already has this title. Please try another one.");

			return "category-form";
		}

		categoryDao.saveCategory(theCategory);

		return "redirect:/admin/category-list";
	}

	@GetMapping("/delete-category")
	public String deleteCategory(@RequestParam("categoryId") long categoryId, Model theModel) {

		categoryDao.deleteCategory(categoryId);

		return "redirect:/admin/category-list";
	}
}
