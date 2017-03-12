package com.example;

import com.example.json.JsonUtil;
import com.example.model.Dish;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;

import static com.example.Util.API_V1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DishControllerTests extends BaseTests {

    private static final String URL = API_V1 + "/dishes";

    @Test
    public void testGetAll() throws Exception {
        Dish dish1 = getCreatedDish("dish1", 33.33, true);
        Dish dish2 = getCreatedDish("dish2", 66.66, true);
        mockMvc.perform(get(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print());
        MATCHER_DISH.assertCollectionEquals(Arrays.asList(dish1, dish2), dishRepository.findAll());
    }

    @Test
    public void testGet() throws Exception {
        Dish dish = getCreatedDish("dish1", 33.33, true);
        ResultActions action = mockMvc.perform(get(URL + "/" + dish.getId())
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print());
        Dish returned = MATCHER_DISH.fromJsonAction(action);
        MATCHER_DISH.assertEquals(dish, returned);
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(URL + "/0")
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDelete() throws Exception {
        Dish dish = getCreatedDish("dish1", 33.33, true);
        mockMvc.perform(delete(URL + "/" + dish.getId())
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isOk());
        MATCHER_DISH.assertCollectionEquals(Collections.emptyList(), dishRepository.findAll());
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
        Dish created = getCreatedDish("dish1", 33.33, false);
        ResultActions action = mockMvc.perform(post(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
                .content(JsonUtil.writeValue(created))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Dish returned = MATCHER_DISH.fromJsonAction(action);
        created.setId(returned.getId());
        MATCHER_DISH.assertEquals(created, returned);
    }

    @Test
    public void testUpdate() throws Exception {
        Dish dish = getCreatedDish("dish1", 33.33, true);
        dish.setPrice(99.99);
        ResultActions action = mockMvc.perform(put(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
                .content(JsonUtil.writeValue(dish))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Dish returned = MATCHER_DISH.fromJsonAction(action);
        MATCHER_DISH.assertEquals(dish, returned);
    }
}