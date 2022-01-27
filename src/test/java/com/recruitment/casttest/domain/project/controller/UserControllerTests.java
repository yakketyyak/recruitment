package com.recruitment.casttest.domain.project.controller;


import com.recruitment.casttest.domain.user.controller.UserController;
import com.recruitment.casttest.domain.user.dto.UserDto;
import com.recruitment.casttest.domain.user.form.UserCreateForm;
import com.recruitment.casttest.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for {@link com.recruitment.casttest.domain.user.controller.UserController}
 */
@WebMvcTest(controllers = UserController.class)
public class UserControllerTests extends AbstractControllerTests {

    @MockBean
    private UserService userService;

    @Test
    void testCreateSuccess() throws Exception {
        Long userId = new Random().nextLong();
        UserDto dto = UserDto.builder()
                .id(userId)
                .firstName("firstName")
                .address("France")
                .age(31)
                .build();

        UserCreateForm form = UserCreateForm.builder()
                .firstName("firstName")
                .address("France")
                .age(29)
                .build();

        when(this.userService.create(any(UserCreateForm.class))).thenReturn(dto);

        this.mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(form)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.address").value("France"))
                .andExpect(jsonPath("$.age").value(31));
    }

    @Test
    void testCreateBadRequest() throws Exception {

        when(this.userService.create(any(UserCreateForm.class))).thenReturn(UserDto.builder().build());

        this.mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(UserCreateForm.builder().build())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateBadRequestForXss() throws Exception {

        when(this.userService.create(any(UserCreateForm.class))).thenReturn(UserDto.builder().build());

        this.mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(UserCreateForm.builder().lastName("<script>").build())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetById() throws Exception {

        Long userId = new Random().nextLong();
        UserDto dto = UserDto.builder()
                .id(userId)
                .firstName("firstName")
                .lastName("lastName")
                .address("France")
                .age(32)
                .build();

        when(this.userService.getById(anyLong())).thenReturn(dto);

        this.mvc.perform(get("/users/{id}", userId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.address").value("France"))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.age").value(32));
    }
}
