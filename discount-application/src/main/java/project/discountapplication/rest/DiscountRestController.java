package project.discountapplication.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exception.InvalidDiscountException;
import project.discountapplication.dao.CategoryDao;
import project.discountapplication.dao.DiscountDao;
import project.discountapplication.dao.UserDao;
import project.discountapplication.entity.Category;
import project.discountapplication.entity.Discount;
import project.discountapplication.entity.User;

@RestController
@RequestMapping("/api")
public class DiscountRestController {

	@Autowired
	DiscountDao discountDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	CategoryDao categoryDao;

	@GetMapping("/discounts")
	public Collection<Discount> getAllDiscounts() {
		return discountDao.getAllDiscounts();
	}

	@GetMapping("/valid-discounts")
	public Collection<Discount> getValidDiscounts() {
		return discountDao.getAllValidDiscounts();
	}

	@GetMapping("/discounts/{discountId}")
	public Discount getTheDiscounts(@PathVariable int discountId) {
		Discount theDiscount = discountDao.getDiscountById(discountId);

		return theDiscount;
	}

	@PostMapping("/discounts")
	public Discount addDiscount(@RequestBody Discount theDiscount) {

		theDiscount.setId(0);

		theDiscount.setUserId(userDao.getCurrentUser().getId());

		// If the discount with current title already exists and it is not the same
		// discount then exception must be thrown
		if (discountDao.isTitleUsed(theDiscount.getTitle(), theDiscount.getId())) {
			throw new InvalidDiscountException("Current discount title was already used");
		}

		// If end date is earlier then start date then exception must be
		if (theDiscount.getStartDate() != null && theDiscount.getStartDate().compareTo(theDiscount.getEndDate()) > 0) {
			throw new InvalidDiscountException("Date values are invalid");
		}

		discountDao.saveDiscount(theDiscount);
		return theDiscount;
	}

	@PutMapping("/discounts")
	public Discount updateDiscount(@RequestBody Discount theDiscount) {

		User currentUser = userDao.getCurrentUser();

		long userId = currentUser.getId();

		long discountId = theDiscount.getId();

		// If the discount was not created by the user and the user is not an
		// administrator,
		// then operation must be forbidden
		if (!discountDao.checkOwner(userId, discountId) && !currentUser.isAdmin()) {
			throw new AccessDeniedException("Clients can modify only their own discounts");
		}

		theDiscount.setUserId(currentUser.getId());

		// If the discount with current title already exists and it is not the same
		// discount then exception must be thrown
		if (discountDao.isTitleUsed(theDiscount.getTitle(), theDiscount.getId())) {
			throw new InvalidDiscountException("Current discount title was already used");
		}

		// If end date is earlier then start date then exception must be
		if (theDiscount.getStartDate() != null && theDiscount.getStartDate().compareTo(theDiscount.getEndDate()) > 0) {
			throw new InvalidDiscountException("Date values are invalid");
		}

		discountDao.saveDiscount(theDiscount);
		return theDiscount;
	}

	@DeleteMapping("discounts/{discountId}")
	public String deleteDiscount(@PathVariable long discountId) {
		User currentUser = userDao.getCurrentUser();
		
		// Discount with given id is requested to check if it exists
		Discount theDiscount = discountDao.getDiscountById(discountId);

		long userId = currentUser.getId();

		// If the discount was not created by the user and the user is not an
		// administrator,
		// then operation must be forbidden
		if (!discountDao.checkOwner(userId, discountId) && !currentUser.isAdmin()) {
			throw new AccessDeniedException("Clients can modify only their own discounts");
		}
		
		discountDao.deleteDiscount(discountId);

		return "Deleted discount id - " + discountId;
	}
	
	
	@GetMapping("/users")
	public Collection<User> getUsers() {
		return userDao.getAllUsers();
	}
	
	
	@GetMapping("/categories")
	public Collection<Category> getCategories() {
		return categoryDao.getAllCategories();
	}
	
	
	

}
