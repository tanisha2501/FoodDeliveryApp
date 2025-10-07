package com.fooddeliveryapp.dao;

import com.fooddeliveryapp.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Create a new user
    @Transactional
    public void create(User user) {
        entityManager.persist(user);
    }

    // Get user by ID
    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    // Get user by email
    public User getByEmail(String email) {
        return entityManager.createQuery("from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    // Get all users
    public List<User> getAll() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    // Update user
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    // Delete user
    @Transactional
    public void delete(long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}
