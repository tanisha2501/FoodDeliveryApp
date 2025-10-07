package com.fooddeliveryapp.service;

import com.fooddeliveryapp.dao.UserDAO;
import com.fooddeliveryapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public void createUser(User user) {
        userDAO.create(user);
    }

    public User getUserById(long id) {
        return userDAO.getById(id);
    }

    public User getUserByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void deleteUser(long id) {
        userDAO.delete(id);
    }
}
