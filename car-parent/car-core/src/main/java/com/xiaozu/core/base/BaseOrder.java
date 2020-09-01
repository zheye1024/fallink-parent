package com.xiaozu.core.base;

import com.xiaozu.core.exception.ValidateException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

public class BaseOrder implements Serializable {
    public void check() {
        Set<ConstraintViolation<BaseOrder>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(this, new Class[0]);
        this.validate(constraintViolations);
    }

    public void checkWithGroup(Class... groups) {
        Set<ConstraintViolation<BaseOrder>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(this, groups);
        this.validate(constraintViolations);
    }

    protected <T> void validate(Set<ConstraintViolation<T>> constraintViolations) {
        ValidateException exception = null;
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            exception = new ValidateException();
            Iterator i$ = constraintViolations.iterator();
            while (i$.hasNext()) {
                ConstraintViolation<T> constraintViolation = (ConstraintViolation) i$.next();
                exception.addError(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
        }

        if (exception != null) {
            throw exception;
        }
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
