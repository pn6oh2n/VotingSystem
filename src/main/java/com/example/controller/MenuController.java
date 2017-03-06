package com.example.controller;

import com.example.dao.DishRepository;
import com.example.dao.MenuRepository;
import com.example.dao.RestaurantRepository;
import com.example.model.Dish;
import com.example.model.Menu;
import com.example.to.MenuTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @GetMapping("/menu")
    public Iterable<Menu> getMenusByDate(@RequestParam(value = "date", required = false) Date date){
        return menuRepository.findByMenuDate(date == null ? new java.sql.Date(Calendar.getInstance().getTime().getTime()) : date);
    }

    @GetMapping("/menu/{id}")
    public Menu getMenu(@PathVariable("id") Integer id){
        return menuRepository.findOne(id);
    }

    @PostMapping(value = "/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveMenu(@Valid @RequestBody MenuTo menuTo){
        Menu menu = menuTo.isNew() ? new Menu() : menuRepository.getOne(menuTo.getId());
        menu.setRestaurant(restaurantRepository.getOne(menuTo.getRestaurant()));
        menu.setMenuDate(menuTo.getMenuDate());
        List<Dish> dishes = menuTo.getDishes().stream().map(dish -> dishRepository.getOne(dish)).collect(Collectors.toList());
        menu.setDishes(dishes);
        menuRepository.save(menu);
    }

    @DeleteMapping("/menu/{id}")
    public void delMenu(@PathVariable("id") Integer id){
        menuRepository.delete(id);
    }
}
