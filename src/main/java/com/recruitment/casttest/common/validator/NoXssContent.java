package com.recruitment.casttest.common.validator;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

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
@Constraint(validatedBy = NoXssContent.NoXssContentValidator.class)
public @interface NoXssContent {

    String message() default "{xss.content.not.allowed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Getter
    @Setter
    class NoXssContentValidator implements ConstraintValidator<NoXssContent, String> {

        private NoXssContent noXssContent;

        @Override
        public void initialize(NoXssContent noXssContent) {
            this.noXssContent = noXssContent;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            if (StringUtils.isBlank(value)) {
                return true;
            }
            return Jsoup.isValid(value, Safelist.none());
        }
    }
}
