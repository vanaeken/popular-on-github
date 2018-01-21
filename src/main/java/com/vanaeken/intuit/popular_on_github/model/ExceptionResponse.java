package com.vanaeken.intuit.popular_on_github.model;

public class ExceptionResponse {

	private String message;

	public ExceptionResponse() {
	}

	public ExceptionResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
