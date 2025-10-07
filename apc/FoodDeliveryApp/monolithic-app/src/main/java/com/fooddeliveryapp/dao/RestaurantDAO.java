package com.fooddeliveryapp.dao;

import com.fooddeliveryapp.model.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(Restaurant restaurant) {
        entityManager.persist(restaurant);
    }

    public Restaurant getById(long id) {
        return entityManager.find(Restaurant.class, id);
    }

    public List<Restaurant> getAll() {
        return entityManager.createQuery("from Restaurant", Restaurant.class).getResultList();
    }

    @Transactional
    public void update(Restaurant restaurant) {
        entityManager.merge(restaurant);
    }

    @Transactional
    public void delete(long id) {
        Restaurant r = entityManager.find(Restaurant.class, id);
        if (r != null) {
            entityManager.remove(r);
        }
    }
}
