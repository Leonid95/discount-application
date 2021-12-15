package project.discountapplication.rest;

import javax.servlet.annotation.HandlesTypes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import project.discountapplication.exception.DiscountNotFoundException;
import project.discountapplication.exception.InvalidDiscountException;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ApplicationErrorResponse> handleException(DiscountNotFoundException exc){
		ApplicationErrorResponse response = new ApplicationErrorResponse();
		
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setMessage(exc.getMessage());
		response.setTimestamp(System.currentTimeMillis());
		
		ResponseEntity<ApplicationErrorResponse> responseEntity = 
				new ResponseEntity(response, HttpStatus.NOT_FOUND);
				
		
		return responseEntity;
	}
	
	@ExceptionHandler
	public ResponseEntity<ApplicationErrorResponse> handleException(Exception exc){
		ApplicationErrorResponse response = new ApplicationErrorResponse();
		
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMessage(exc.getMessage());
		response.setTimestamp(System.currentTimeMillis());
		
		ResponseEntity<ApplicationErrorResponse> responseEntity = 
				new ResponseEntity(response, HttpStatus.BAD_REQUEST);
				
		
		return responseEntity;
	}
}
