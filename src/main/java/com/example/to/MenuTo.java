package com.example.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class MenuTo extends BaseTo implements Serializable {

    private final Integer restaurantId;

    private final Date date;

    private final List<Integer> dishes;

    public MenuTo(@JsonProperty("restaurantId") Integer restaurantId,
                  @JsonProperty("date") Date date,
                  @JsonProperty("dishes") List<Integer> dishes) {
        this.restaurantId = restaurantId;
        this.date = date;
        this.dishes = dishes;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public Date getDate() {
        return date;
    }

    public List<Integer> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "restaurantId=" + restaurantId +
                ", date=" + date +
                ", dishes=" + dishes +
                '}';
    }
}
