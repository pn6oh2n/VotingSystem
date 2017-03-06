package com.example.model;

import javax.persistence.Entity;

@Entity
//@Table(name="restaurants")
public class Restaurant extends NamedEntity{
    /*@OneToMany//(fetch = FetchType.LAZY)
    List<Menu> menus;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }*/
}
