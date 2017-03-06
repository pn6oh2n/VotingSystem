package com.example.model;

import javax.persistence.Entity;

@Entity
//@Table(name = "dishes")
public class Dish extends NamedEntity{
    //@ManyToOne//(fetch = FetchType.LAZY)
    //Menu menu;
    Double price;

    /*public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }*/

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
