package com.recruitment.casttest.domain.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recruitment.casttest.AbstractTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public abstract class AbstractControllerTests extends AbstractTests {

    @Autowired
    protected MockMvc mvc;

    protected ObjectMapper mapper = new ObjectMapper();
}
