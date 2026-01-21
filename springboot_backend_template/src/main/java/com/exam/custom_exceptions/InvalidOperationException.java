package com.exam.custom_exceptions;

public class InvalidOperationException extends RuntimeException {
	public InvalidOperationException(String msg) {
		super(msg);
	}
}
