package com.fdmgroup.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.fdmgroup.model.User;


public class UserLoginValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "user.name.empty");
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password.empty");
	}

}
