package com.example.controller;

import com.example.dao.DishRepository;
import com.example.dao.MenuRepository;
import com.example.dao.RestaurantRepository;
import com.example.model.Dish;
import com.example.model.Menu;
import com.example.to.MenuTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.Util.API_V1;
import static com.example.Util.getTodaySQLDate;
import static com.example.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = API_V1 + "/menus")
public class MenuController {

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    @Autowired
    public MenuController(MenuRepository menuRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @GetMapping
    public List<Menu> getByDate(@RequestParam(value = "date", required = false) Date date){
        return menuRepository.findByDate(date == null ? getTodaySQLDate() : date);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public Menu get(@PathVariable("id") Integer id){
        return checkNotFoundWithId(menuRepository.findOne(id), id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> create(@Valid @RequestBody MenuTo menuTo){
        Assert.notNull(menuTo, "menu must not be null");
        Assert.notEmpty(menuTo.getDishes(), "dish list must not be empty");
        Menu oldMenu = menuRepository.findByDateAndRestaurantId(menuTo.getDate(),menuTo.getRestaurantId());
        if (oldMenu != null) menuRepository.delete(oldMenu);
        Menu menu = new Menu();
        menu.setRestaurant(restaurantRepository.findOne(menuTo.getRestaurantId()));
        menu.setDate(menuTo.getDate());
        List<Dish> dishes = menuTo.getDishes().stream().map(dishRepository::findOne).collect(Collectors.toList());
        menu.setDishes(dishes);
        menuRepository.save(menu);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/menus" + "/{id}")
                .buildAndExpand(menu.getId())
                .toUri();
        return ResponseEntity.created(uri).body(menu);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        Assert.notNull(id, "menu must not be null");
        checkNotFoundWithId(menuRepository.findOne(id), id);
        menuRepository.delete(id);
    }
}
