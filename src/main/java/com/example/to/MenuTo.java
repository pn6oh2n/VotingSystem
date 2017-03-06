package com.example.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.List;

public class MenuTo extends BaseTo {

    private final Integer restaurant;

    private final Date menuDate;

    private final List<Integer> dishes;

    public MenuTo(@JsonProperty("id") Integer id,
                  @JsonProperty("restaurant") Integer restaurant,
                  @JsonProperty("menuDate") Date menuDate,
                  @JsonProperty("dishes") List<Integer> dishes) {
        super(id);
        this.restaurant = restaurant;
        this.menuDate = menuDate;
        this.dishes = dishes;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public Date getMenuDate() {
        return menuDate;
    }

    public List<Integer> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "restaurant=" + restaurant +
                ", menuDate=" + menuDate +
                ", dishes=" + dishes +
                '}';
    }
}
