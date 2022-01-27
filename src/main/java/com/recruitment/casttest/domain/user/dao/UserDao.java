package com.recruitment.casttest.domain.user.dao;

import com.recruitment.casttest.common.exception.BusinessException;
import com.recruitment.casttest.common.exception.PersistenceException;
import com.recruitment.casttest.domain.user.dto.UserDto;

public interface UserDao {

    /**
     * Create new user
     *
     * @param dto dto.
     * @return dto
     * @throws PersistenceException e.
     */
    UserDto create(UserDto dto) throws PersistenceException;

    /**
     * Get a user by it's id.
     *
     * @param id id
     * @return user with id.
     * @throws PersistenceException e.
     */
    UserDto getById(Long id) throws PersistenceException;

    /**
     * @param id user id.
     * @return true if user exists , false otherwise?
     * @throws BusinessException e.
     */
    boolean existsById(Long id) throws BusinessException;
}
