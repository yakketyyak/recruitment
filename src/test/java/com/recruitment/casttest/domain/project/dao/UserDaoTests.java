package com.recruitment.casttest.domain.project.dao;


import com.recruitment.casttest.common.exception.PersistenceException;
import com.recruitment.casttest.domain.project.AbstractUnitTests;
import com.recruitment.casttest.domain.user.dao.impl.UserDaoImpl;
import com.recruitment.casttest.domain.user.dto.UserDto;
import com.recruitment.casttest.domain.user.entity.UserEntity;
import com.recruitment.casttest.domain.user.mapper.UserMapper;
import com.recruitment.casttest.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link com.recruitment.casttest.domain.user.dao.impl.UserDaoImpl}
 */
class UserDaoTests extends AbstractUnitTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserDaoImpl dao;

    private UserDto dto;
    private UserEntity entity;

    @BeforeEach
    void setUp() {
        Long userId = new Random().nextLong();
        dto = UserDto.builder()
                .id(userId)
                .firstName("firstName")
                .age(29)
                .build();

        entity = UserEntity.builder()
                .id(userId)
                .firstName("firstName")
                .age(29)
                .build();
    }


    @Test
    void testCreateSuccess() {

        when(this.userMapper.toEntity(any(UserDto.class))).thenReturn(entity);
        when(this.userRepository.save(any(UserEntity.class))).thenReturn(entity);
        when(this.userMapper.toDto(any(UserEntity.class))).thenReturn(dto);

        UserDto savedDto = this.dao.create(dto);

        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(savedDto.getAge()).isEqualTo(dto.getAge());

    }

    @Test
    void testCreateFailed() {
        when(this.userMapper.toEntity(any(UserDto.class))).thenThrow(PersistenceException.class);

        Assertions.assertThrows(PersistenceException.class, () -> this.dao.create(dto));
    }

    @Test
    void testGetByIdSuccess() {

        when(this.userRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(this.userMapper.toDto(any(UserEntity.class))).thenReturn(dto);

        UserDto response = this.dao.getById(new Random().nextLong());

        assertThat(response).isNotNull();
        assertThat(response.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(response.getAge()).isEqualTo(dto.getAge());
    }

    @Test
    void testGetByIdFailed() {

        when(this.userRepository.findById(anyLong())).thenThrow(PersistenceException.class);

        Assertions.assertThrows(PersistenceException.class, () -> this.dao.getById(new Random().nextLong()));
    }
}
