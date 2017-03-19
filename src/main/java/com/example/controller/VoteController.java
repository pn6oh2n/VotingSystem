package com.example.controller;

import com.example.AuthorizedUser;
import com.example.dao.RestaurantRepository;
import com.example.dao.UserRepository;
import com.example.dao.VoteRepository;
import com.example.exception.TooLateException;
import com.example.model.Vote;
import com.example.to.VoteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static com.example.Util.*;

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
    public List<VoteResult> getResults(@RequestParam(value = "date", required = false) Date date){
        return voteRepository.getResults(date == null ? getTodaySQLDate() : date);
    }

    @PostMapping
    @Transactional
    public void create(@RequestParam(value = "idRestaurant") Integer idRestaurant){
        Vote vote = voteRepository.findByUserIdAndDate(AuthorizedUser.id(), getTodaySQLDate());
        if (vote != null) {
            if (getTodayHourOfDay() >= 11) {
                throw new TooLateException("It is too late, vote can't be changed");
            }
        } else {
            vote = new Vote();
        }
        vote.setDate(getTodaySQLDate());
        vote.setRestaurant(restaurantRepository.getOne(idRestaurant));
        vote.setUser(userRepository.getOne(AuthorizedUser.id()));
        voteRepository.save(vote);
    }
}
