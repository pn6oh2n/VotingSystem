package com.example;

import org.junit.Test;
import org.springframework.http.HttpHeaders;

import static com.example.Util.API_V1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VotingSystemTests extends BaseTests {

	@Test
	public void testSecurity() throws Exception {
		mockMvc.perform(get(API_V1)).andExpect(status().isUnauthorized());
		mockMvc.perform(get(API_V1 + "/users").header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_ADMIN)).andExpect(status().isOk());
		mockMvc.perform(get(API_V1 + "/users").header(HttpHeaders.AUTHORIZATION, HTTP_AUTH_USER)).andExpect(status().isForbidden());
	}
}