package project.discountapplication.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable();
		
		http.authorizeRequests()
		.antMatchers("/admin/**").hasRole("administrator")
		.antMatchers("/client/**").hasRole("client")
		.antMatchers("/add-discount").hasAnyRole("administrator", "client")
		.antMatchers("/update-discount").hasAnyRole("administrator", "client")
		.antMatchers("/delete-discount").hasAnyRole("administrator", "client")
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/authenticateTheUser")
		.permitAll()
		.and()
		.logout()
		.permitAll();
	}
	
	
	// Using BCrypt password encoding to encode user passwords
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();		
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		
		auth.setUserDetailsService(userDetailsService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); // set the password encoder
		
		return auth;
	}
}
