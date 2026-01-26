package com.exam.custom_exceptions;

public class DuplicateResourceException extends RuntimeException {
	public DuplicateResourceException(String msg) {
		super(msg);
	}
}
