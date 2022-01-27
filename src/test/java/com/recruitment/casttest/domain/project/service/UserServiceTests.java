package com.recruitment.casttest.domain.project.service;

import com.recruitment.casttest.common.exception.BusinessException;
import com.recruitment.casttest.common.exception.NotFoundException;
import com.recruitment.casttest.domain.project.AbstractUnitTests;
import com.recruitment.casttest.domain.user.dao.impl.UserDaoImpl;
import com.recruitment.casttest.domain.user.dto.UserDto;
import com.recruitment.casttest.domain.user.entity.UserEntity;
import com.recruitment.casttest.domain.user.form.UserCreateForm;
import com.recruitment.casttest.domain.user.mapper.UserMapper;
import com.recruitment.casttest.domain.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link com.recruitment.casttest.domain.user.service.impl.UserServiceImpl}
 */
class UserServiceTests extends AbstractUnitTests {

    @Mock
    private UserDaoImpl userDao;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl service;

    private UserDto dto;
    private UserCreateForm form;


    @BeforeEach
    void setUp() {
        Long userId = new Random().nextLong();
        dto = UserDto.builder()
                .id(userId)
                .firstName("firstName")
                .age(29)
                .build();

        form = UserCreateForm.builder()
                .firstName("firstName")
                .age(29)
                .build();
    }

    @Test
    void testCreateSuccess() {
        when(this.userMapper.toDto(any(UserCreateForm.class))).thenReturn(dto);
        when(this.userDao.create(any(UserDto.class))).thenReturn(dto);

        UserDto savedDto = this.service.create(form);

        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(savedDto.getAge()).isEqualTo(dto.getAge());
    }

    @Test
    void testCreateFailed() {
        when(this.userMapper.toDto(any(UserEntity.class))).thenThrow(BusinessException.class);

        Assertions.assertThrows(BusinessException.class, () -> this.service.create(form));
    }


    @Test
    void testGetByIdFailed() {
        when(this.userDao.getById(anyLong())).thenThrow(NotFoundException.class);
        Assertions.assertThrows(NotFoundException.class, () -> this.service.getById(new Random().nextLong()));
    }
}

