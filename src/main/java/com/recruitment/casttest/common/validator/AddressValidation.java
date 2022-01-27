package com.recruitment.casttest.common.validator;

import com.recruitment.casttest.common.enums.AddressValue;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressValidation.AddressValidator.class)
public @interface AddressValidation {

    String message() default "{address.must.be.only.france}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Getter
    @Setter
    class AddressValidator implements ConstraintValidator<AddressValidation, String> {

        private AddressValidation addressValidation;

        @Override
        public void initialize(AddressValidation noXssContent) {
            this.addressValidation = addressValidation;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            if (StringUtils.isBlank(value)) {
                return true;
            }
            return AddressValue.FRANCE.getValue().equals(value);
        }
    }
}
