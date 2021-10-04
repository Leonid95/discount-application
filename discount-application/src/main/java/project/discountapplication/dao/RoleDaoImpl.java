package project.discountapplication.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.discountapplication.entity.Category;
import project.discountapplication.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Role findRoleById(Long roleId) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Role> theQuery = currentSession.createQuery("from Role where id=:roleId");

		theQuery.setParameter("roleId", roleId);

		Role theRole = theQuery.getSingleResult();

		// TODO Auto-generated method stub
		return theRole;
	}

	@Override
	public Collection<Role> getAllRoles() {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Role> theQuery = currentSession.createQuery("from Role");

		List<Role> roles = null;

		try {
			roles = theQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roles;
	}

}
