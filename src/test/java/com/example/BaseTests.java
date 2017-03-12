package com.example;

import com.example.dao.*;
import com.example.model.*;
import com.example.to.MenuTo;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.Util.getTodaySQLDate;
import static com.example.model.Role.ROLE_ADMIN;
import static com.example.model.Role.ROLE_USER;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseTests {

	protected static final String HTTP_AUTH_ADMIN = "Basic " + Base64Utils.encodeToString("admin:admin".getBytes());
	protected static final String HTTP_AUTH_USER = "Basic " + Base64Utils.encodeToString("user:user".getBytes());
	protected static final ModelMatcher<Dish> MATCHER_DISH = ModelMatcher.of(Dish.class);
	protected static final ModelMatcher<Menu> MATCHER_MENU = ModelMatcher.of(Menu.class);
	protected static final ModelMatcher<User> MATCHER_USER = ModelMatcher.of(User.class);
	protected static final ModelMatcher<Restaurant> MATCHER_RESTAURANT = ModelMatcher.of(Restaurant.class);

	protected MockMvc mockMvc;

	@Autowired
	private FilterChainProxy filterChainProxy;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	protected RestaurantRepository restaurantRepository;

	@Autowired
	protected DishRepository dishRepository;

	@Autowired
	protected MenuRepository menuRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected VoteRepository voteRepository;

	@PostConstruct
	public void setup() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext)
				.dispatchOptions(true)
				.addFilters(filterChainProxy)
				.build();
	}

	@Before
	public void initRepo(){
		menuRepository.deleteAll();
		voteRepository.deleteAll();
		dishRepository.deleteAll();
		restaurantRepository.deleteAll();
		userRepository.deleteAll();
		getCreatedUser("admin", ROLE_ADMIN, true);
		getCreatedUser("user", ROLE_USER, true);
	}

	public User getCreatedUser(String name, Role role, Boolean save){
		User user = userRepository.findByName(name);
		if (user == null) user = new User();
		user.setName(name);
		user.setPassword(name);
		user.setRole(role);
		if (save) userRepository.save(user);
		return user;
	}

	public Dish getCreatedDish(String name, Double price, Boolean save) {
		Dish dish = new Dish();
		dish.setName(name);
		dish.setPrice(price);
		if (save) dishRepository.save(dish);
		return dish;
	}

	public Restaurant getCreatedRestaurant(String name, Boolean save) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(name);
		if (save) restaurantRepository.save(restaurant);
		return restaurant;
	}

	public Menu getCreatedMenu(Boolean save) {
		Menu menu = new Menu();
		menu.setRestaurant(getCreatedRestaurant("restaurant1", true));
		menu.setDate(getTodaySQLDate());
		List<Dish> dishes = new ArrayList<>();
		dishes.add(getCreatedDish("dish1", 33.33, true));
		dishes.add(getCreatedDish("dish2", 66.66, true));
		menu.setDishes(dishes);
		if (save) menuRepository.save(menu);
		return menu;
	}

	public MenuTo getMenuTo(Menu menu){
		return new MenuTo(
				menu.getRestaurant().getId(),
				menu.getDate(),
				menu.getDishes().stream().map(BaseEntity::getId).collect(Collectors.toList()));
	}

	public Vote getCreatedVote(Boolean save) {
		Vote vote = new Vote();
		vote.setRestaurant(getCreatedRestaurant("restaurant1", true));
		vote.setDate(getTodaySQLDate());
		vote.setUser(getCreatedUser("user", ROLE_USER, true));
		if (save) voteRepository.save(vote);
		return vote;
	}
}
