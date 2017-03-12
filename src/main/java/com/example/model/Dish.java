package com.example.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Dish extends NamedEntity{

    @NotNull
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
