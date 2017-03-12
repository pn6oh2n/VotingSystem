package com.example;

import com.example.json.JsonUtil;
import com.example.model.User;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;

import static com.example.Util.API_V1;
import static com.example.model.Role.ROLE_ADMIN;
import static com.example.model.Role.ROLE_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTests extends BaseTests {

    private static final String URL = API_V1 + "/users";

    @Test
    public void testGetAll() throws Exception {
        User admin = getCreatedUser("admin", ROLE_ADMIN, true);
        User user = getCreatedUser("user", ROLE_USER, true);
        User user1 = getCreatedUser("user1", ROLE_USER, true);
        User user2 = getCreatedUser("user2", ROLE_USER, true);
        mockMvc.perform(get(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print());
        MATCHER_USER.assertCollectionEquals(Arrays.asList(admin, user, user1, user2), userRepository.findAll());
    }

    @Test
    public void testGet() throws Exception {
        User user = getCreatedUser("user", ROLE_USER, true);
        ResultActions action = mockMvc.perform(get(URL + "/" + user.getId())
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print());
        User returned = MATCHER_USER.fromJsonAction(action);
        MATCHER_USER.assertEquals(user, returned);
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(URL + "/0")
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDelete() throws Exception {
        User user = getCreatedUser("user", ROLE_USER, true);
        mockMvc.perform(delete(URL + "/" + user.getId())
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isOk());
        MATCHER_USER.assertCollectionEquals(Collections.singletonList(userRepository.findByName("admin")), userRepository.findAll());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(URL + "/0")
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testCreate() throws Exception {
        User created = getCreatedUser("user", ROLE_USER, false);
        ResultActions action = mockMvc.perform(post(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
                .content(JsonUtil.writeValue(created))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User returned = MATCHER_USER.fromJsonAction(action);
        created.setId(returned.getId());
        MATCHER_USER.assertEquals(created, returned);
    }

    @Test
    public void testUpdate() throws Exception {
        User user = getCreatedUser("user", ROLE_USER, true);
        user.setName("user updated");
        user.setPassword("12345");
        ResultActions action = mockMvc.perform(put(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
                .content(JsonUtil.writeValue(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User returned = MATCHER_USER.fromJsonAction(action);
        MATCHER_USER.assertEquals(user, returned);
    }
}