package org.bridgelabz.fundoonotes.exception;

import org.bridgelabz.fundoonotes.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public final ResponseEntity<Response> handleAllExceptions(UserException ex) {
      log.info("custom exception hadler     "+ ex.getMessage()); 
		  Response response=new Response(ex.getMessage(),400,"token passed");
	      
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	
	
	
	
	/*
	 * @ExceptionHandler(UserException.class) public final ResponseEntity<Response>
	 * handleUserNotFoundException(UserException ex, WebRequest request) { Response
	 * response=new Response(ex.getMessage(),400,"tokien passed2"); return new
	 * ResponseEntity<>(response, HttpStatus.NOT_FOUND); }
	 */
    }

