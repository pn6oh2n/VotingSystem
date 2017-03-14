package com.example.dao;

import com.example.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Vote findByUserIdAndDate(Integer userId, Date date);
}
