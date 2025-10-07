package com.fooddeliveryapp.orderservice;

import com.fooddeliveryapp.orderservice.model.Orders;
import com.fooddeliveryapp.orderservice.service.OrdersService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrderServiceApplication.class, args);
        OrdersService ordersService = context.getBean(OrdersService.class);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Order Service Console ---");
            System.out.println("1. Create Order");
            System.out.println("2. Get Order by ID");
            System.out.println("3. Get Orders by User ID");
            System.out.println("4. Get All Orders");
            System.out.println("5. Update Order");
            System.out.println("6. Delete Order");
            System.out.println("7. Cancel Order");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createOrder(scanner, ordersService);
                    break;
                case 2:
                    getOrderById(scanner, ordersService);
                    break;
                case 3:
                    getOrdersByUserId(scanner, ordersService);
                    break;
                case 4:
                    getAllOrders(ordersService);
                    break;
                case 5:
                    updateOrder(scanner, ordersService);
                    break;
                case 6:
                    deleteOrder(scanner, ordersService);
                    break;
                case 7:
                    cancelOrder(scanner, ordersService);
                    break;
                case 0:
                    System.out.println("Exiting Order Service...");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private static void createOrder(Scanner scanner, OrdersService ordersService) {
        System.out.print("User ID: ");
        Long userId = Long.parseLong(scanner.nextLine());
        System.out.print("Menu Item ID: ");
        Long menuItemId = Long.parseLong(scanner.nextLine());
        System.out.print("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Total: ");
        double total = Double.parseDouble(scanner.nextLine());
        System.out.print("Delivery Address: ");
        String deliveryAddress = scanner.nextLine();

        Orders order = new Orders();
        order.setUserId(userId);
        order.setMenuItemId(menuItemId);
        order.setQuantity(quantity);
        order.setTotal(total);
        order.setStatus("Pending");
        order.setDeliveryAddress(deliveryAddress);

        ordersService.createOrder(order);
        System.out.println("Order created successfully!");
    }

    private static void getOrderById(Scanner scanner, OrdersService ordersService) {
        System.out.print("Order ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        Orders order = ordersService.getOrderById(id);
        if (order != null) {
            System.out.println("ID: " + order.getId() + ", UserID: " + order.getUserId() +
                    ", MenuItemID: " + order.getMenuItemId() + ", Quantity: " + order.getQuantity() +
                    ", Total: " + order.getTotal() + ", Status: " + order.getStatus() +
                    ", Address: " + order.getDeliveryAddress());
        } else {
            System.out.println("Order not found!");
        }
    }

    private static void getOrdersByUserId(Scanner scanner, OrdersService ordersService) {
        System.out.print("User ID: ");
        Long userId = Long.parseLong(scanner.nextLine());
        List<Orders> orders = ordersService.getOrdersByUserId(userId);
        System.out.println("\n--- Orders for User " + userId + " ---");
        for (Orders order : orders) {
            System.out.println("ID: " + order.getId() + ", MenuItemID: " + order.getMenuItemId() +
                    ", Quantity: " + order.getQuantity() + ", Total: " + order.getTotal() +
                    ", Status: " + order.getStatus() + ", Address: " + order.getDeliveryAddress());
        }
    }

    private static void getAllOrders(OrdersService ordersService) {
        List<Orders> orders = ordersService.getAllOrders();
        System.out.println("\n--- All Orders ---");
        for (Orders order : orders) {
            System.out.println("ID: " + order.getId() + ", UserID: " + order.getUserId() +
                    ", MenuItemID: " + order.getMenuItemId() + ", Quantity: " + order.getQuantity() +
                    ", Total: " + order.getTotal() + ", Status: " + order.getStatus() +
                    ", Address: " + order.getDeliveryAddress());
        }
    }

    private static void updateOrder(Scanner scanner, OrdersService ordersService) {
        System.out.print("Order ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());
        Orders order = ordersService.getOrderById(id);
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        System.out.print("New Quantity (" + order.getQuantity() + "): ");
        String qtyStr = scanner.nextLine();
        if (!qtyStr.isEmpty()) order.setQuantity(Integer.parseInt(qtyStr));

        System.out.print("New Total (" + order.getTotal() + "): ");
        String totalStr = scanner.nextLine();
        if (!totalStr.isEmpty()) order.setTotal(Double.parseDouble(totalStr));

        System.out.print("New Status (" + order.getStatus() + "): ");
        String status = scanner.nextLine();
        if (!status.isEmpty()) order.setStatus(status);

        System.out.print("New Delivery Address (" + order.getDeliveryAddress() + "): ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) order.setDeliveryAddress(address);

        ordersService.updateOrder(order);
        System.out.println("Order updated successfully!");
    }

    private static void deleteOrder(Scanner scanner, OrdersService ordersService) {
        System.out.print("Order ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());
        ordersService.deleteOrder(id);
        System.out.println("Order deleted successfully!");
    }

    private static void cancelOrder(Scanner scanner, OrdersService ordersService) {
        System.out.print("Order ID to cancel: ");
        Long id = Long.parseLong(scanner.nextLine());
        ordersService.cancelOrder(id);
        System.out.println("Order cancelled successfully!");
    }
}
