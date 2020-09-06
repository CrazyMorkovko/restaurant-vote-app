package com.carrot.restaurant_vote.validation;

import java.lang.annotation.*;
import javax.validation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUserNameValidator.class)
public @interface UniqueUserName {
    String message() default "There is already user with this name!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
