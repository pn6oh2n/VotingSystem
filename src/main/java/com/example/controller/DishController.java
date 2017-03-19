package com.example.controller;

import com.example.dao.DishRepository;
import com.example.model.Dish;
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
@RequestMapping(value = API_V1 + "/dishes")
public class DishController {

    private final DishRepository dishRepository;

    @Autowired
    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping
    public List<Dish> getAll(){
        return dishRepository.findAll();
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable("id") Integer id){
        return checkNotFoundWithId(dishRepository.findOne(id), id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Dish update(@PathVariable("id") Integer id, @Valid @RequestBody Dish dish){
        Assert.notNull(dish, "dish must not be null");
        return checkNotFoundWithId(dishRepository.save(dish), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish){
        Assert.notNull(dish, "dish must not be null");
        Dish created = dishRepository.save(dish);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/dishes" + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        Assert.notNull(id, "dish must not be null");
        checkNotFoundWithId(dishRepository.findOne(id), id);
        dishRepository.delete(id);
    }
}
