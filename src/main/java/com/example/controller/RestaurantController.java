package com.example.controller;

import com.example.dao.RestaurantRepository;
import com.example.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.example.Util.API_V1;
import static com.example.ValidationUtil.checkNotFoundWithId;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping(value = API_V1 + "/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public List<Restaurant> getAll(){
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable("id") Integer id){
        return checkNotFoundWithId(restaurantRepository.findOne(id), id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant update(@PathVariable("id") Integer id, @Valid @RequestBody Restaurant restaurant){
        Assert.notNull(restaurant, "restaurant must not be null");
        return checkNotFoundWithId(restaurantRepository.save(restaurant), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant){
        Assert.notNull(restaurant, "restaurant must not be null");
        Restaurant created = restaurantRepository.save(restaurant);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/restaurants" + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        Assert.notNull(id, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.findOne(id), id);
        restaurantRepository.delete(id);
    }
}
