package com.example;

import com.example.json.JsonUtil;
import com.example.model.Restaurant;
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

public class RestaurantControllerTests extends BaseTests {

    private static final String URL = API_V1 + "/restaurants";

    @Test
    public void testGetAll() throws Exception {
        Restaurant restaurant1 = getCreatedRestaurant("restaurant1", true);
        Restaurant restaurant2 = getCreatedRestaurant("restaurant2", true);
        mockMvc.perform(get(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print());
        MATCHER_RESTAURANT.assertCollectionEquals(Arrays.asList(restaurant1, restaurant2), restaurantRepository.findAll());
    }

    @Test
    public void testGet() throws Exception {
        Restaurant restaurant = getCreatedRestaurant("restaurant1", true);
        ResultActions action = mockMvc.perform(get(URL + "/" + restaurant.getId())
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print());
        Restaurant returned = MATCHER_RESTAURANT.fromJsonAction(action);
        MATCHER_RESTAURANT.assertEquals(restaurant, returned);
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(URL + "/0")
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDelete() throws Exception {
        Restaurant restaurant = getCreatedRestaurant("restaurant1", true);
        mockMvc.perform(delete(URL + "/" + restaurant.getId())
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
                .andExpect(status().isOk());
        MATCHER_RESTAURANT.assertCollectionEquals(Collections.emptyList(), restaurantRepository.findAll());
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
        Restaurant created = getCreatedRestaurant("restaurant1", false);
        ResultActions action = mockMvc.perform(post(URL)
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
                .content(JsonUtil.writeValue(created))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Restaurant returned = MATCHER_RESTAURANT.fromJsonAction(action);
        created.setId(returned.getId());
        MATCHER_RESTAURANT.assertEquals(created, returned);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant restaurant = getCreatedRestaurant("restaurant1", true);
        restaurant.setName("restaurant1 updated");
        ResultActions action = mockMvc.perform(put(URL + "/" + restaurant.getId())
                .header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
                .content(JsonUtil.writeValue(restaurant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Restaurant returned = MATCHER_RESTAURANT.fromJsonAction(action);
        MATCHER_RESTAURANT.assertEquals(restaurant, returned);
    }
}