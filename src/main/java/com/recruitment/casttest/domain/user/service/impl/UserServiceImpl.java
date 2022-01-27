package com.recruitment.casttest.domain.user.service.impl;

import com.recruitment.casttest.common.exception.BusinessException;
import com.recruitment.casttest.common.exception.NotFoundException;
import com.recruitment.casttest.domain.user.dao.UserDao;
import com.recruitment.casttest.domain.user.dto.UserDto;
import com.recruitment.casttest.domain.user.form.UserCreateForm;
import com.recruitment.casttest.domain.user.mapper.UserMapper;
import com.recruitment.casttest.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public UserDto create(UserCreateForm form) throws BusinessException {
        try {
            return this.userDao.create(this.userMapper.toDto(form));
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public UserDto getById(Long id) throws BusinessException {
        try {
            return this.userDao.getById(id);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public boolean existsById(Long id) throws BusinessException {
        try {
            return this.userDao.existsById(id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}
