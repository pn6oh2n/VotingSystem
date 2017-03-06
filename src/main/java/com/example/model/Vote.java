package com.example.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class Vote extends BaseEntity {
    @ManyToOne//(fetch = FetchType.LAZY)
    Restaurant restaurant;

    Date voteDate;

    @ManyToOne//(fetch = FetchType.LAZY)
    User user;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
