package com.bookingprojectn1.repository;


import com.bookingprojectn1.entity.Favourite;
import com.bookingprojectn1.entity.Payment;
import com.bookingprojectn1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

}
