package com.epam.jmp2017.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ConditionDescription {
    /**
     * Parameter labels.
     */
    String[] parameters();
    /**
     * Method description.
     */
    String description();
}
