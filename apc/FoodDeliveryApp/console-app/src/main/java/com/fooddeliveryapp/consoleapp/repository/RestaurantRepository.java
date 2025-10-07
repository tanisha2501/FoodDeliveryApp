package com.fooddeliveryapp.consoleapp.repository;

import com.fooddeliveryapp.consoleapp.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
