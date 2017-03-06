package com.example.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.util.List;

@Entity
//@Table(name = "menus")
public class Menu extends BaseEntity{

    @ManyToOne//(fetch = FetchType.LAZY)
    Restaurant restaurant;

    Date menuDate;

    @ManyToMany //(fetch = FetchType.LAZY)
    List<Dish> dishes;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(Date menuDate) {
        this.menuDate = menuDate;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
