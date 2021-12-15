package project.discountapplication.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import project.discountapplication.entity.Discount;
import project.discountapplication.entity.Role;
import project.discountapplication.entity.User;

@Repository
public class UserDaoImpl implements UserDao, UserDetailsService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Collection<User> getAllUsers() {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Query<User> theQuery = currentSession.createQuery("from User");

		Collection<User> users = null;

		try {
			users = theQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	@Transactional
	public void deleteUser(long userId) {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<User> theQuery = currentSession.createQuery("delete from User where id = :userId");
		
		theQuery.setParameter("userId", userId);
		
		theQuery.executeUpdate();
	}

	@Override
	@Transactional
	public void saveUser(User theUser) {

		// Encode User password
		theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));

		// Get the current Hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		System.out.println("Saving the user: " + theUser);

		currentSession.saveOrUpdate(theUser);
	}

	@Override
	@Transactional
	public void saveUser(User theUser, String existingPassword) {
		// If new password was not set then the previous encoded password must be
		// provided
		theUser.setPassword(existingPassword);

		// Get the current Hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		System.out.println("Saving the user: " + theUser);

		currentSession.saveOrUpdate(theUser);
	}

	@Override
	public User findByUsername(String UserName) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// now retrieve/read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
		theQuery.setParameter("uName", UserName);
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		Role role = roleDao.findRoleById(user.getRoleId());

		Collection<? extends GrantedAuthority> mappedRole = mapRoleToAuthorities(role);

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				mappedRole);
	}

	private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Role userRole) {
		ArrayList<Role> roles = new ArrayList<>();
		roles.add(userRole);

		return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
				.collect(Collectors.toList());
	}

	@Override
	// This method gets currently logged in user
	public User getCurrentUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();

		User currentUser = findByUsername(username);

		return currentUser;
	}

	@Override
	public boolean isUsernameUsed(String username, long userId) {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Query<User> theQuery = currentSession.createQuery("from User where username = :user_name AND id != :userId");

		theQuery.setParameter("user_name", username);
		theQuery.setParameter("userId", userId);

		List<User> users = null;

		try {
			users = theQuery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (users != null && users.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isEmailUsed(String email, long userId) {
		// Get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		Query<User> theQuery = currentSession.createQuery("from User where email = :user_email AND id != :userId");

		theQuery.setParameter("user_email", email);
		theQuery.setParameter("userId", userId);

		List<User> users = null;

		try {
			users = theQuery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (users != null && users.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User findById(long userId) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// now retrieve/read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where id=:uId", User.class);
		theQuery.setParameter("uId", userId);
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

}
