package com.recruitment.casttest.domain.user.service;

import com.recruitment.casttest.common.exception.BusinessException;
import com.recruitment.casttest.domain.user.dto.UserDto;
import com.recruitment.casttest.domain.user.form.UserCreateForm;

public interface UserService {

    UserDto create(UserCreateForm form) throws BusinessException;

    UserDto getById(Long id) throws BusinessException;

    boolean existsById(Long id) throws BusinessException;

}
