package com.bocsh.web;

import com.bocsh.domain.User;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<User> json;

    @Autowired
    private JacksonTester<List<User>> userList;

    @Test
    //获取管理员用户
    void getAdminUser() throws Exception {

        mvc.perform(get("/users/1111"))
                .andExpect(status().isOk())
                .andExpect(content().string("myUser"));

    }

    @Test
    //获取普通用户
    void getNormalUser() throws Exception {

        mvc.perform(get("/users/1122"))
                .andExpect(status().isOk())
                .andExpect(content().string("Tom"));

    }

    @Test
    //获取普通用户
    void getSpeclUser() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/users/2222")).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);

        assertThat(this.json.parseObject(content).getName()).isEqualTo("Alice");

    }

    /* 获取用户列表 */
    @Test
    void getUserList() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/users/list")).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);

        List<User> list = this.userList.parseObject(content);
        assertThat(list.get(0).getName()).isEqualTo("Alice");
        assertThat(list.get(0).getSchool()).isEqualTo("oxford");

    }
}