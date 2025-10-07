package com.fooddeliveryapp.consoleapp.service;

import com.fooddeliveryapp.consoleapp.model.Orders;
import com.fooddeliveryapp.consoleapp.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    public void createOrder(Orders order) {
        ordersRepository.save(order);
    }

    public Orders getOrderById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }

    public List<Orders> getOrdersByUserId(Long userId) {
        return ordersRepository.findByUserId(userId);
    }

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public void updateOrder(Orders order) {
        ordersRepository.save(order);
    }

    public void deleteOrder(Long id) {
        ordersRepository.deleteById(id);
    }

    public void cancelOrder(Long id) {
        Orders order = getOrderById(id);
        if (order != null) {
            order.setStatus("Cancelled");
            updateOrder(order);
        }
    }
}
