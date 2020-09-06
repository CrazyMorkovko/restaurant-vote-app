package com.carrot.restaurant_vote.validation;

import java.lang.annotation.*;
import javax.validation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
	String message() default "There is already user with this email!";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
