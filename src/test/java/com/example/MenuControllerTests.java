package com.example;

import com.example.json.JsonUtil;
import com.example.model.Menu;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;

import static com.example.Util.API_V1;
import static com.example.Util.getTodaySQLDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuControllerTests extends BaseTests {

	private static final String URL = API_V1 + "/menus";

	@Test
	public void testGetAll() throws Exception {
		Menu menu1 = getCreatedMenu(true);
		Menu menu2 = getCreatedMenu(true);
		mockMvc.perform(get(URL)
				.header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_USER))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print());
		MATCHER_MENU.assertCollectionEquals(Arrays.asList(menu1, menu2), menuRepository.findAll());
		mockMvc.perform(get(URL + "?date=" + getTodaySQLDate().toString())
				.header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print());
		MATCHER_MENU.assertCollectionEquals(Arrays.asList(menu1, menu2), menuRepository.findAll());
	}

	@Test
	public void testGet() throws Exception {
		Menu menu = getCreatedMenu(true);
		ResultActions action = mockMvc.perform(get(URL + "/" + menu.getId())
				.header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andDo(print());
		Menu returned = MATCHER_MENU.fromJsonAction(action);
		MATCHER_MENU.assertEquals(menu, returned);
	}

	@Test
	public void testGetNotFound() throws Exception {
		mockMvc.perform(get(URL + "/0")
				.header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void testDelete() throws Exception {
		Menu menu = getCreatedMenu(true);
		mockMvc.perform(delete(URL + "/" + menu.getId())
				.header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN))
				.andExpect(status().isOk());
		MATCHER_MENU.assertCollectionEquals(Collections.emptyList(), menuRepository.findAll());
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
		Menu created = getCreatedMenu(false);
		ResultActions action = mockMvc.perform(post(URL)
				.header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)
				.content(JsonUtil.writeValue(getMenuTo(created)))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
		Menu returned = MATCHER_MENU.fromJsonAction(action);
		created.setId(returned.getId());
		MATCHER_MENU.assertEquals(created, returned);
		MATCHER_MENU.assertEquals(created, menuRepository.findOne(returned.getId()));
	}
}