package com.recruitment.casttest.domain.user.dao.impl;

import com.recruitment.casttest.common.exception.BusinessException;
import com.recruitment.casttest.common.exception.NotFoundException;
import com.recruitment.casttest.common.exception.PersistenceException;
import com.recruitment.casttest.domain.user.dao.UserDao;
import com.recruitment.casttest.domain.user.dto.UserDto;
import com.recruitment.casttest.domain.user.entity.UserEntity;
import com.recruitment.casttest.domain.user.mapper.UserMapper;
import com.recruitment.casttest.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto create(UserDto dto) {
        try {
            UserEntity saved = this.userRepository.save(this.userMapper.toEntity(dto));
            return this.userMapper.toDto(saved);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }

    }

    @Override
    public UserDto getById(Long id) {
        try {
            UserEntity byId = this.userRepository
                    .findById(id).orElseThrow(() ->
                            new NotFoundException(String.format("User with id %s not found", id)));

            return this.userMapper.toDto(byId);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean existsById(Long id) throws BusinessException {
        try {
            return this.userRepository.existsById(id);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
