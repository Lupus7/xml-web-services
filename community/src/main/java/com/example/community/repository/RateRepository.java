package com.example.community.repository;

import com.example.community.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findAllByCarId(Long carId);

    Rate findByBooking(long booking);
    List<Rate> findAllByApprovedIsFalseAndCommentIsNot(String comment);
}
