package com.example.dao;

import com.example.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findByDate(Date date);
    Menu findByDateAndRestaurantId(Date date, Integer restaurantId);
}
