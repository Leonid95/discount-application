package project.discountapplication.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import project.discountapplication.entity.Category;
import project.discountapplication.entity.Discount;
import project.discountapplication.entity.User;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Collection<Category> getAllCategories() {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Category> theQuery = currentSession.createQuery("from Category");

		List<Category> categories = null;

		try {
			categories = theQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return categories;
	}

	@Override
	@Transactional
	public void saveCategory(Category theCategory) {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theCategory);
	}

	@Override
	@Transactional
	public void deleteCategory(long categoryId) {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Category> theQuery = currentSession.createQuery("delete from Category where id = :cId");

		theQuery.setParameter("cId", categoryId);

		theQuery.executeUpdate();

	}

	@Override
	public Category findCategoryById(long categoryId) {

		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Category category = currentSession.get(Category.class, categoryId);

		return category;
	}

	@Override
	// Check if title was already used by another category
	public boolean isTitleUsed(String title, long id) {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// Find all categories with the same title but with another id
		Query<Category> theQuery = currentSession.createQuery("from Category where title = :cTitle AND id != :cId");

		theQuery.setParameter("cTitle", title);

		theQuery.setParameter("cId", id);

		List<Category> categories = null;

		try {
			categories = theQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (categories != null && categories.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
