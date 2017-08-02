package com.bank.challenge.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {TransactionDateValidator.class})
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface NotBeforeSixtySecondsAgo {

  String message() default "Transaction timestamp is before 60 seconds ago";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}

