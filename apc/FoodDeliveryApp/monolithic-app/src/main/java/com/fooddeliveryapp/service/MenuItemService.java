package com.fooddeliveryapp.service;

import com.fooddeliveryapp.dao.MenuItemDAO;
import com.fooddeliveryapp.model.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemDAO menuItemDAO;

    public void createMenuItem(MenuItem item) {
        menuItemDAO.create(item);
    }

    public MenuItem getMenuItemById(long id) {
        return menuItemDAO.getById(id);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemDAO.getAll();
    }

    public List<MenuItem> getMenuItemsByRestaurant(long restaurantId) {
        return menuItemDAO.getByRestaurant(restaurantId);
    }

    public void updateMenuItem(MenuItem item) {
        menuItemDAO.update(item);
    }

    public void deleteMenuItem(long id) {
        menuItemDAO.delete(id);
    }
}
