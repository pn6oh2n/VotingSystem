package com.example.controller;

import com.example.dao.DishRepository;
import com.example.dao.MenuRepository;
import com.example.dao.RestaurantRepository;
import com.example.dao.UserRepository;
import com.example.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.model.Role.ADMIN;
import static com.example.model.Role.USER;

@RestController
public class RootController {

    private String getRndPwd(){return String.valueOf(ThreadLocalRandom.current().nextInt(10000,1000000));}

    private Double getRndDouble(){return ThreadLocalRandom.current().nextDouble(10, 100);}

    private Integer getRndInteger(){return ThreadLocalRandom.current().nextInt(1, 3);}

    private Date getNowSQLDate(){
        return new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }

    /*@Autowired
    DataSource dataSource;*/

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private MenuRepository menuRepository;

    /*@Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    @GetMapping("/test")
    public void insertTestData(){

        for (int i = 0; i < 3; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setName("restaurant" + (i + 1));
            restaurantRepository.save(restaurant);
        }

        User admin = new User();
        admin.setName("admin");
        admin.setPassword("qwerty");
        admin.setRole(ADMIN);
        userRepository.save(admin);

        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setName("user" + (i + 1));
            user.setPassword(getRndPwd());
            user.setRole(USER);
            userRepository.save(user);
        }

        for (int i = 0; i < 5; i++) {
            Dish dish = new Dish();
            dish.setName("dish" + (i + 1));
            dish.setPrice(getRndDouble());
            dishRepository.save(dish);
        }

        Dish dish = dishRepository.findOne(3);
        dish.setPrice(99.99);
        dishRepository.save(dish);

        Menu menu = new Menu();
        menu.setRestaurant(restaurantRepository.getOne(1));
        menu.setMenuDate(getNowSQLDate());
        List<Dish> dishes1 = new ArrayList<>();
        dishes1.add(dishRepository.getOne(1));
        dishes1.add(dishRepository.getOne(2));
        dishes1.add(dishRepository.getOne(3));
        menu.setDishes(dishes1);
        menuRepository.save(menu);

        menu = new Menu();
        menu.setRestaurant(restaurantRepository.getOne(2));
        menu.setMenuDate(getNowSQLDate());
        List<Dish> dishes2 = new ArrayList<>();
        dishes2.add(dishRepository.getOne(4));
        dishes2.add(dishRepository.getOne(5));
        menu.setDishes(dishes2);
        menuRepository.save(menu);

        menu = menuRepository.findOne(1);
        menu.setRestaurant(restaurantRepository.findOne(2));
        menuRepository.save(menu);

        Vote vote = new Vote();
        vote.setVoteDate(getNowSQLDate());
        vote.setRestaurant(restaurantRepository.getOne(1));
        vote.setUser(userRepository.getOne(1));

    }
}
