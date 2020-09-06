package com.carrot.restaurant_vote.validation;

import javax.validation.*;

import com.carrot.restaurant_vote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && !userService.isEmailExist(value);
	}
}
