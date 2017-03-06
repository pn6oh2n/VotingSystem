package com.example.controller;

import com.example.dao.RestaurantRepository;
import com.example.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @GetMapping("/restaurant")
    public Iterable<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAll();
    }

    @GetMapping("/restaurant/{id}")
    public Restaurant getRestaurant(@PathVariable("id") Integer id){
        return restaurantRepository.findOne(id);
    }

    @PostMapping(value = "/restaurant", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveRestaurant(@Valid @RequestBody Restaurant restaurant){
        restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/restaurant/{id}")
    public void delRestaurant(@PathVariable("id") Integer id){
        restaurantRepository.delete(id);
    }
}
