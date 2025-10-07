package com.fooddeliveryapp.service;

import com.fooddeliveryapp.dao.RestaurantDAO;
import com.fooddeliveryapp.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantDAO restaurantDAO;

    public void createRestaurant(Restaurant restaurant) {
        restaurantDAO.create(restaurant);
    }

    public Restaurant getRestaurantById(long id) {
        return restaurantDAO.getById(id);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantDAO.getAll();
    }

    public void updateRestaurant(Restaurant restaurant) {
        restaurantDAO.update(restaurant);
    }

    public void deleteRestaurant(long id) {
        restaurantDAO.delete(id);
    }
}
