package com.fooddeliveryapp.dao;

import com.fooddeliveryapp.model.MenuItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuItemDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(MenuItem item) {
        entityManager.persist(item);
    }

    public MenuItem getById(long id) {
        return entityManager.find(MenuItem.class, id);
    }

    public List<MenuItem> getAll() {
        return entityManager.createQuery("from MenuItem", MenuItem.class).getResultList();
    }

    public List<MenuItem> getByRestaurant(long restaurantId) {
        return entityManager.createQuery("FROM MenuItem m WHERE m.restaurant.id = :rid", MenuItem.class)
                .setParameter("rid", restaurantId)
                .getResultList();
    }

    @Transactional
    public void update(MenuItem item) {
        entityManager.merge(item);
    }

    @Transactional
    public void delete(long id) {
        MenuItem m = entityManager.find(MenuItem.class, id);
        if (m != null) {
            entityManager.remove(m);
        }
    }
}
