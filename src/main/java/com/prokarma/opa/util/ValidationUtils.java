package com.prokarma.opa.util;

public final class ValidationUtils {

	public static boolean isEmpty(String value) {
		return (value == null) || "".equals(value);
	}

	private ValidationUtils() {
		throw new AssertionError("No instace of this class ever!");
	}
}
