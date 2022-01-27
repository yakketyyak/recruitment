package com.recruitment.casttest.domain.user.controller;

import com.recruitment.casttest.domain.user.dto.UserDto;
import com.recruitment.casttest.domain.user.form.UserCreateForm;
import com.recruitment.casttest.domain.user.service.UserService;
import com.recruitment.casttest.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> create(@RequestBody @Valid final UserCreateForm form) {
        UserDto response = this.userService.create(form);
        URI uri = ControllerUtils.buildMvcPathComponent(String.valueOf(response.getId()), UserController.class);
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getById(@PathVariable Long id, @RequestParam(required = false) String cast) {
        if (Objects.nonNull(cast)) {
            log.info("Welcome bro " + cast);
        }
        return ResponseEntity.ok(this.userService.getById(id));
    }
}
