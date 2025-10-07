package com.fooddeliveryapp.consoleapp.service;

import com.fooddeliveryapp.consoleapp.model.MenuItem;
import com.fooddeliveryapp.consoleapp.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public void addMenuItem(MenuItem menuItem) {
        menuItemRepository.save(menuItem);
    }

    public MenuItem saveMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    public List<MenuItem> getMenuItemsByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
