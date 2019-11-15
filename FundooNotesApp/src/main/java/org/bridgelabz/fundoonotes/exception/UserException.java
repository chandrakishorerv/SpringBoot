package org.bridgelabz.fundoonotes.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class UserException extends RuntimeException{

	private static final long serialVersionUID = 6847026414125155938L;
	
	private  String message;
    
	public UserException(String message){
		super(message);
		this.message=message;
		log.info("exception message entereedd       "+     message);
	}
	
}
