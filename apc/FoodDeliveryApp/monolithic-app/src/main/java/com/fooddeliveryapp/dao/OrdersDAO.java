package com.fooddeliveryapp.dao;

import com.fooddeliveryapp.model.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdersDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void create(Orders order) {
        entityManager.persist(order);
    }

    public Orders getById(long id) {
        return entityManager.find(Orders.class, id);
    }

    public List<Orders> getAll() {
        return entityManager.createQuery("from Orders", Orders.class).getResultList();
    }

    public List<Orders> getByUserId(long userId) {
        return entityManager.createQuery("from Orders where user.id = :userId", Orders.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Transactional
    public void update(Orders order) {
        entityManager.merge(order);
    }

    @Transactional
    public void delete(long id) {
        Orders o = entityManager.find(Orders.class, id);
        if (o != null) {
            entityManager.remove(o);
        }
    }

    @Transactional
    public void updateStatus(long id, String status) {
        Orders order = entityManager.find(Orders.class, id);
        if (order != null) {
            order.setStatus(status);
            entityManager.merge(order);
        }
    }
}
