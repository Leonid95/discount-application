package project.discountapplication.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import project.discountapplication.dao.UserDao;
import project.discountapplication.entity.User;

//@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserDao userDao;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		System.out.println("\n\nIn customAuthenticationSuccessHandler\n\n");

		String userName = authentication.getName();

		System.out.println("userName=" + userName);

		User theUser = userDao.findByUsername(userName);

		// now place in the session
		HttpSession session = request.getSession();
		session.setAttribute("user", theUser);
		
		
		response.sendRedirect(request.getContextPath());

	}

}
