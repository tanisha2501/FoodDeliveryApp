package com.fooddeliveryapp.service;

import com.fooddeliveryapp.dao.OrdersDAO;
import com.fooddeliveryapp.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrdersDAO ordersDAO;

    public void createOrder(Orders order) {
        ordersDAO.create(order);
    }

    public Orders getOrderById(long id) {
        return ordersDAO.getById(id);
    }

    public List<Orders> getAllOrders() {
        return ordersDAO.getAll();
    }

    public List<Orders> getOrdersByUserId(long userId) {
        return ordersDAO.getByUserId(userId);
    }

    public void updateOrder(Orders order) {
        ordersDAO.update(order);
    }

    public void deleteOrder(long id) {
        ordersDAO.delete(id);
    }

    public void cancelOrder(long id) {
        ordersDAO.updateStatus(id, "Cancelled");
    }
}
