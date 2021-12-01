package project.discountapplication.dao;

import java.util.Collection;

import project.discountapplication.entity.User;

public interface UserDao {
	Collection<User> getAllUsers();

	void deleteUser(long userId);

	void saveUser(User theUser);
	
	void saveUser(User theUser, String existingPassword);

	User findByUsername(String UserName);
	
	// This method gets currently logged in user
	User getCurrentUser();
	
	boolean isUsernameUsed(String username, long userId);
	
	boolean isEmailUsed(String email, long userId);

	User findById(long userId);
}
