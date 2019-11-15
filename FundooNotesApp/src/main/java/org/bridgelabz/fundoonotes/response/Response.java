package org.bridgelabz.fundoonotes.response;

import lombok.Data;

@Data
public class Response {

	private String message;
	private int statusCode;
	private String token;
	private Object obj;

	public Response(String message, int statusCode,String token,Object obj) {
		this.message = message;
		this.statusCode = statusCode;
		this.token=token;
		this.obj=obj;
	}
	public Response(String message, int statusCode,String token) {
		this.message = message;
		this.statusCode = statusCode;
		this.token=token;
	}
}
