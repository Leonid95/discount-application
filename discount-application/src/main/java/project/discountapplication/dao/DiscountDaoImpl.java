package project.discountapplication.dao;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import exception.DiscountNotFoundException;
import javassist.tools.rmi.ObjectNotFoundException;
import project.discountapplication.entity.Discount;

@Repository
public class DiscountDaoImpl implements DiscountDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Collection<Discount> getAllDiscounts() {

		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Discount> theQuery = currentSession.createQuery("from Discount");

		List<Discount> discounts = null;

		try {
			discounts = theQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return discounts;
	}

	@Override
	public Collection<Discount> getAllValidDiscounts() {
		
		List<Discount> validDiscounts = new ArrayList<>();
		
		validDiscounts.addAll(getAllDiscounts());

		// Remove all invalid discounts from the list
		removeInvalidDiscounts(validDiscounts);

		return validDiscounts;
	}

	@Override
	@Transactional
	public void saveDiscount(Discount theDiscount) {
		// Get the current Hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		System.out.println(theDiscount);

		currentSession.saveOrUpdate(theDiscount);
	}

	// Following function removes all the invalid discounts from the list of
	// discounts
	// Invalid discounts are all the discounts that have visibility status set as
	// false
	// and also all the discounts that are remaining after their end date expired
	private void removeInvalidDiscounts(List<Discount> discounts) {
		Date currentDate = new Date();

		for (int i = 0; i < discounts.size();) {
			if (discounts.get(i).getStatus() == false || (discounts.get(i).getEndDate() != null && discounts.get(i).getEndDate().compareTo(currentDate) < 0)) {
				discounts.remove(i);
			} else {
				i++;
			}
		}
	}

	@Override
	public Discount getDiscountById(long id) {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Discount discount = currentSession.get(Discount.class, id);
		
		if(discount == null) {
			throw new DiscountNotFoundException("Discount with id " + id + " was not found");
		}

		return discount;
	}

	@Override
	@Transactional
	public void deleteDiscount(long discountId) {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Discount> theQuery = currentSession.createQuery("delete from Discount where id=:discount_id");

		theQuery.setParameter("discount_id", discountId);

		theQuery.executeUpdate();
	}

	// Check weather title was already used by the discount with another id
	@Override
	public boolean isTitleUsed(String title, long id) {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Discount> theQuery = currentSession.createQuery("from Discount where title = :discountTitle AND id != :discountId");

		theQuery.setParameter("discountTitle", title);
		theQuery.setParameter("discountId", id);
		
		List <Discount> discounts = null;

		try {
			discounts = theQuery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(discounts != null && discounts.size() > 0) {
			return true;
		}
		else {
			return false;
		}

		
	}

	@Override
	public Collection<Discount> getDiscountsByUserId(long uId) {
		// Get the current hibernate session
				Session currentSession = entityManager.unwrap(Session.class);

				Query<Discount> theQuery = currentSession.createQuery("from Discount WHERE userId=:uId");
				
				theQuery.setParameter("uId", uId);

				List<Discount> discounts = null;

				try {
					discounts = theQuery.getResultList();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return discounts;
	}

	@Override
	public boolean checkOwner(long userId, long discountId) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Long> theQuery = currentSession.createQuery("SELECT userId from Discount WHERE id=:dId");
		
		theQuery.setParameter("dId", discountId);
		
		long realUserId = 0L;
		
		try {
			realUserId = theQuery.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return (userId == realUserId);
	}

}
