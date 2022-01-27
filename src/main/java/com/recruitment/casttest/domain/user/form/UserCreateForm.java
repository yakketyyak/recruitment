package com.recruitment.casttest.domain.user.form;

import com.recruitment.casttest.common.validator.AddressValidation;
import com.recruitment.casttest.common.validator.NoXssContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateForm {

    @NotBlank
    @NoXssContent
    private String firstName;
    @NoXssContent
    private String lastName;
    @NotNull
    @Min(value = 18, message = "{age.must.be.greater.than.eighteen}")
    private Integer age;
    @NotBlank
    @NoXssContent
    @AddressValidation
    private String address;
}
