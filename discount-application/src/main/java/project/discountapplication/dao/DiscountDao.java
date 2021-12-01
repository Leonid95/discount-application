package project.discountapplication.dao;

import java.util.Collection;

import project.discountapplication.entity.Discount;

public interface DiscountDao {
	
	Collection<Discount> getAllDiscounts();
	
	Collection<Discount> getAllValidDiscounts();
	
	Collection<Discount> getDiscountsByUserId(long uId);
	
	void saveDiscount(Discount theDiscount);
	
	
	Discount getDiscountById(long id);
	
	// Check weather title was already used by the discount with another id
	boolean isTitleUsed(String title, long id);

	void deleteDiscount(long discountID);
	
}
