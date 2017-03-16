package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"})})
public class Menu extends BaseEntity{

    @NotNull
    @ManyToOne
    private Restaurant restaurant;

    @NotNull
    private Date date;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Dish> dishes;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
