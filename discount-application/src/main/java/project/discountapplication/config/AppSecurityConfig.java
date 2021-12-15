package project.discountapplication.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



@Configuration
@EnableWebSecurity
@Order(1)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	UserDetailsService userDetailsService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	        .antMatchers(HttpMethod.GET, "/api/discounts/**")
	        .antMatchers(HttpMethod.GET, "/api/valid-discounts/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic().disable();
		
		http.csrf().disable().authorizeRequests();
		
		http.authorizeRequests()
		.antMatchers("/admin/**").hasRole("administrator")
		.antMatchers("/client/**").hasRole("client")
		.antMatchers("/add-discount").hasAnyRole("administrator", "client")
		.antMatchers("/update-discount").hasAnyRole("administrator", "client")
		.antMatchers("/delete-discount").hasAnyRole("administrator", "client")
		// The beginning of spring rest security configurations
		.antMatchers(HttpMethod.POST, "/api/discounts/**").hasAnyRole("administrator", "client")
		.antMatchers(HttpMethod.PUT, "/api/discounts/**").hasAnyRole("administrator", "client")
		.antMatchers(HttpMethod.DELETE, "/api/discounts/**").hasAnyRole("administrator", "client")
		.antMatchers("/api/users/**").hasRole("administrator")
		.antMatchers("/api/categories/**").hasRole("administrator")
		// The end of spring rest security configurations
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/authenticateTheUser")
		.permitAll()
		.and()
		.logout()
		.permitAll()
		.and()
		.httpBasic();
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
