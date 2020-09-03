package com.f2m.forms.utils;

public class AquariusException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1300143414706798486L;

	public AquariusException(String message) {
        super(message);
    }
	
	public AquariusException(int code) {
		super(Integer.toString(code));
	}
}
