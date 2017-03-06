package com.example;

import com.example.dao.DishRepository;
import com.example.json.JsonUtil;
import com.example.model.Dish;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

//@AutoConfigureMockMvc
@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
//@Configuration
@WebAppConfiguration
@ComponentScan(basePackages = {"com.example"}) // are you sure you wanna scan all the packages?
@EnableJpaRepositories//(basePackageClasses = DishRepository.class) // assuming you have all the spring data repo in the same package
@PropertySource("classpath:application.properties")
public class UserDaoTest {

	/*@Autowired
	private ApplicationContext wac;*/

	/*@Autowired
	private TestRestTemplate restTemplate;*/

	//@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DishRepository dishRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
    public void testDishRepository() {
		Dish dish = new Dish();
		dish.setName("dish1");
		dish.setPrice(99.99);
		dishRepository.save(dish);

		dish = dishRepository.findOne(1);
		dish.setPrice(33.33);
		dishRepository.save(dish);

    	Assert.assertNotNull(dish);
    	Assert.assertEquals(33.33, dish.getPrice(), 0.001);
    	Assert.assertEquals("dish1", dish.getName());
    }

	@Test
	public void testDishController() throws Exception {

		Dish dish = new Dish();
		dish.setName("dish1");
		dish.setPrice(99.99);
		dishRepository.save(dish);

		mockMvc.perform(get("/dish")).andExpect(status().isOk());

		mockMvc.perform(get("/dish/1")
				//.with(userHttpBasic(ADMIN))
				.content(JsonUtil.writeValue(dish)))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		;
	}

}