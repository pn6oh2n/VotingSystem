package com.example.controller;

import com.example.AuthorizedUser;
import com.example.dao.RestaurantRepository;
import com.example.dao.UserRepository;
import com.example.dao.VoteRepository;
import com.example.exception.TooLateException;
import com.example.model.Restaurant;
import com.example.model.Vote;
import com.example.to.VoteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.example.Util.API_V1;
import static com.example.Util.getTodayHourOfDay;
import static com.example.Util.getTodaySQLDate;

@RestController
@RequestMapping(value = API_V1 + "/votes")
public class VoteController {

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    private final VoteRepository voteRepository;

    @Autowired
    public VoteController(RestaurantRepository restaurantRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping
    public Iterable<VoteResult> getResults(@RequestParam(value = "date", required = false) Date date){
        List<VoteResult> voteResults = new ArrayList<>();
        Vote vote = new Vote();
        vote.setDate(date == null ? getTodaySQLDate() : date);
        for (Restaurant restaurant: restaurantRepository.findAll()
                ) {
            vote.setRestaurant(restaurant);
            Long votesCount = voteRepository.count(Example.of(vote));
            voteResults.add(new VoteResult(restaurant,votesCount.intValue()));
        }
        return voteResults;
    }

    @PostMapping("/{idRestaurant}")
    public void addVote(@PathVariable("idRestaurant") Integer idRestaurant){
        Vote vote = voteRepository.findByRestaurantIdAndUserIdAndDate(idRestaurant, AuthorizedUser.id(), getTodaySQLDate());
        if (vote == null){
            vote = new Vote();
        } else {
            if (getTodayHourOfDay() >= 11) {
                throw new TooLateException("It is too late, vote can't be changed");
            }
        }
        vote.setDate(getTodaySQLDate());
        vote.setRestaurant(restaurantRepository.getOne(idRestaurant));
        vote.setUser(userRepository.getOne(AuthorizedUser.id()));
        voteRepository.save(vote);
    }
}
