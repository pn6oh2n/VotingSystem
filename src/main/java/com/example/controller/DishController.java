package com.example.controller;

import com.example.dao.DishRepository;
import com.example.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class DishController {

    @Autowired
    private DishRepository dishRepository;

    @GetMapping("/dish")
    public Iterable<Dish> getAllDishes(){
        return dishRepository.findAll();
    }

    @GetMapping("/dish/{id}")
    public Dish getDish(@PathVariable("id") Integer id){
        return dishRepository.findOne(id);
    }

    @PostMapping(value = "/dish", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveDish(@Valid @RequestBody Dish dish){
        dishRepository.save(dish);
    }

    @DeleteMapping("/dish/{id}")
    public void delDish(@PathVariable("id") Integer id){
        dishRepository.delete(id);
    }
}
