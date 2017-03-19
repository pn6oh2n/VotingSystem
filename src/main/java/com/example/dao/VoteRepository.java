package com.example.dao;

import com.example.model.Vote;
import com.example.to.VoteResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Vote findByUserIdAndDate(Integer userId, Date date);

    @Query("select new com.example.to.VoteResult(v.restaurant, count(v)) from Vote v where v.date = :date group by v.restaurant")
    List<VoteResult> getResults(@Param("date") Date date);
}
