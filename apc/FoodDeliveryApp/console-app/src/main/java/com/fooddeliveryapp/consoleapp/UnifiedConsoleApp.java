package com.fooddeliveryapp.consoleapp;

import com.fooddeliveryapp.consoleapp.model.MenuItem;
import com.fooddeliveryapp.consoleapp.model.Orders;
import com.fooddeliveryapp.consoleapp.model.Restaurant;
import com.fooddeliveryapp.consoleapp.model.User;
import com.fooddeliveryapp.consoleapp.service.MenuItemService;
import com.fooddeliveryapp.consoleapp.service.OrdersService;
import com.fooddeliveryapp.consoleapp.service.RestaurantService;
import com.fooddeliveryapp.consoleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class UnifiedConsoleApp implements CommandLineRunner {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UserService userService;

    private final Scanner scanner = new Scanner(System.in);

    private User loggedInUser = null;

    private boolean adminLoggedIn = false;

    public static void main(String[] args) {
        SpringApplication.run(UnifiedConsoleApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        startMenu();
    }

    private int getChoice() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private double getDouble() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private long getLong() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public void startMenu() {
        while (true) {
            System.out.println("\n--- Unified Food Delivery Console App ---");
            System.out.println("1. User Panel");
            System.out.println("2. Admin Panel");
            System.out.println("3. Restaurant Panel");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = getChoice();

            switch (choice) {
                case 1:
                    userPanel();
                    break;
                case 2:
                    if (!adminLoggedIn) {
                        loginAdmin();
                        if (!adminLoggedIn) {
                            break;
                        }
                    }
                    adminPanel();
                    break;
                case 3:
                    restaurantPanel();
                    break;
                case 0:
                    System.out.println("Exiting Unified Console App...");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void userPanel() {
        while (true) {
            System.out.println("\n--- User Panel ---");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. View My Orders");
            System.out.println("4. Delete Order");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            int choice = getChoice();

            switch (choice) {
                case 1:
                    loginUser();
                    break;
                case 2:
                    signupUser();
                    break;
                case 3:
                    viewMyOrders();
                    break;
                case 4:
                    deleteOrder();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void adminPanel() {
        while (true) {
            System.out.println("\n--- Admin Panel ---");
            System.out.println("1. View All Users");
            System.out.println("2. Add Restaurant");
            System.out.println("3. Add Menu Item");
            System.out.println("4. View All Orders");
            System.out.println("5. View Restaurants");
            System.out.println("6. Delete Restaurant");
            System.out.println("7. View Menu Items");
            System.out.println("8. Remove Menu Item");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            int choice = getChoice();

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    addRestaurant();
                    break;
                case 3:
                    addMenuItem();
                    break;
                case 4:
                    viewAllOrders();
                    break;
                case 5:
                    viewRestaurants();
                    break;
                case 6:
                    deleteRestaurant();
                    break;
                case 7:
                    viewMenuItems();
                    break;
                case 8:
                    removeMenuItem();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void restaurantPanel() {
        while (true) {
            System.out.println("\n--- Restaurant Panel ---");
            System.out.println("1. Place Order");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            int choice = getChoice();

            switch (choice) {
                case 1:
                    placeOrder();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void loginUser() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Optional<User> userOpt = userService.findByEmailAndPassword(email, password);
        if (userOpt.isPresent()) {
            loggedInUser = userOpt.get();
            System.out.println("Login successful. Welcome, " + loggedInUser.getName() + "!");
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private void loginAdmin() {
        System.out.print("Admin Email: ");
        String email = scanner.nextLine();
        System.out.print("Admin Password: ");
        String password = scanner.nextLine();

        if ("admin@gmail.com".equals(email) && "admin".equals(password)) {
            adminLoggedIn = true;
            System.out.println("Admin login successful!");
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private void signupUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            System.out.println("Email already registered. Please login.");
            return;
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        userService.saveUser(newUser);
        System.out.println("Signup successful. You can now login.");
    }

    private void viewMyOrders() {
        if (loggedInUser == null) {
            System.out.println("Please login first.");
            return;
        }
        List<Orders> orders = ordersService.getOrdersByUserId(loggedInUser.getId());
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("\n--- My Orders ---");
            for (Orders order : orders) {
                System.out.println("Order ID: " + order.getId() + ", Menu Item ID: " + order.getMenuItemId() +
                        ", Quantity: " + order.getQuantity() + ", Total: " + order.getTotal() +
                        ", Delivery Address: " + order.getDeliveryAddress() +
                        ", Payment Method: " + order.getPaymentMethod());
            }
        }
    }

    private void updateOrder() {
        // Removed as per user request
    }

    private void deleteOrder() {
        if (loggedInUser == null) {
            System.out.println("Please login first.");
            return;
        }
        List<Orders> orders = ordersService.getOrdersByUserId(loggedInUser.getId());
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        System.out.println("Select an order to delete:");
        for (int i = 0; i < orders.size(); i++) {
            Orders order = orders.get(i);
            System.out.println((i + 1) + ". Order ID: " + order.getId() + ", Menu Item ID: " + order.getMenuItemId() +
                    ", Quantity: " + order.getQuantity() + ", Total: " + order.getTotal());
        }
        System.out.print("Enter choice: ");
        int choice = getChoice();
        if (choice < 1 || choice > orders.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        Orders selectedOrder = orders.get(choice - 1);
        ordersService.deleteOrder(selectedOrder.getId());
        System.out.println("Order deleted successfully!");
    }

    private void placeOrder() {
        if (loggedInUser == null) {
            System.out.println("Please login first to place an order.");
            return;
        }

        System.out.println("\n--- Place Order ---");

        // Select Restaurant
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }
        System.out.println("Select a restaurant:");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i + 1) + ". " + restaurants.get(i).getName());
        }
        System.out.print("Enter choice: ");
        int restaurantChoice = getChoice();
        if (restaurantChoice < 1 || restaurantChoice > restaurants.size()) {
            System.out.println("Invalid restaurant choice.");
            return;
        }
        Restaurant selectedRestaurant = restaurants.get(restaurantChoice - 1);

        // Select Menu Item
        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurantId(selectedRestaurant.getId());
        if (menuItems.isEmpty()) {
            System.out.println("No menu items available for this restaurant.");
            return;
        }
        System.out.println("Select a menu item:");
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - Price: " + item.getPrice());
        }
        System.out.print("Enter choice: ");
        int menuItemChoice = getChoice();
        if (menuItemChoice < 1 || menuItemChoice > menuItems.size()) {
            System.out.println("Invalid menu item choice.");
            return;
        }
        MenuItem selectedMenuItem = menuItems.get(menuItemChoice - 1);

        // Quantity
        System.out.print("Enter quantity: ");
        int quantity = getChoice();
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        // Calculate total
        double total = selectedMenuItem.getPrice() * quantity;
        System.out.println("Total price: " + total);

        // Delivery Address
        System.out.print("Enter delivery address: ");
        String deliveryAddress = scanner.nextLine();

        // Payment Method
        System.out.println("Select payment method:");
        System.out.println("1. Cash");
        System.out.println("2. UPI");
        System.out.println("3. Card");
        System.out.print("Enter choice: ");
        int paymentChoice = getChoice();
        String paymentMethod;
        switch (paymentChoice) {
            case 1:
                paymentMethod = "Cash";
                break;
            case 2:
                paymentMethod = "UPI";
                break;
            case 3:
                paymentMethod = "Card";
                break;
            default:
                System.out.println("Invalid payment method.");
                return;
        }

        // Create order
        Orders order = new Orders();
        order.setUserId(loggedInUser.getId());
        order.setMenuItemId(selectedMenuItem.getId());
        order.setQuantity(quantity);
        order.setTotal(total);
        order.setDeliveryAddress(deliveryAddress);
        order.setPaymentMethod(paymentMethod);

        ordersService.createOrder(order);
        System.out.println("Order placed successfully! Total: $" + total);
    }

    // Admin Panel Methods

    private void viewAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("\n--- All Users ---");
            for (User user : users) {
                System.out.println("User ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
            }
        }
    }

    private void addRestaurant() {
        System.out.print("Enter restaurant name: ");
        String name = scanner.nextLine();
        System.out.print("Enter restaurant address: ");
        String address = scanner.nextLine();

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);

        restaurantService.saveRestaurant(restaurant);
        System.out.println("Restaurant added successfully.");
    }

    private void addMenuItem() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available. Please add a restaurant first.");
            return;
        }
        System.out.println("Select a restaurant:");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i + 1) + ". " + restaurants.get(i).getName());
        }
        System.out.print("Enter choice: ");
        int restaurantChoice = getChoice();
        if (restaurantChoice < 1 || restaurantChoice > restaurants.size()) {
            System.out.println("Invalid restaurant choice.");
            return;
        }
        Restaurant selectedRestaurant = restaurants.get(restaurantChoice - 1);

        System.out.print("Enter menu item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = getDouble();

        MenuItem menuItem = new MenuItem();
        menuItem.setName(itemName);
        menuItem.setPrice(price);
        menuItem.setRestaurantId(selectedRestaurant.getId());

        menuItemService.saveMenuItem(menuItem);
        System.out.println("Menu item added successfully.");
    }

    private void viewAllOrders() {
        List<Orders> orders = ordersService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("\n--- All Orders ---");
            for (Orders order : orders) {
                System.out.println("Order ID: " + order.getId() + ", User ID: " + order.getUserId() +
                        ", Menu Item ID: " + order.getMenuItemId() + ", Quantity: " + order.getQuantity() +
                        ", Total: " + order.getTotal() +
                        ", Delivery Address: " + order.getDeliveryAddress() + ", Payment Method: " + order.getPaymentMethod());
            }
        }
    }

    private void cancelOrder() {
        System.out.print("Enter Order ID to cancel: ");
        Long orderId = getLong();
        ordersService.cancelOrder(orderId);
        System.out.println("Order cancelled successfully!");
    }

    private void viewRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants found.");
        } else {
            System.out.println("\n--- All Restaurants ---");
            for (Restaurant restaurant : restaurants) {
                System.out.println("Restaurant ID: " + restaurant.getId() + ", Name: " + restaurant.getName() +
                        ", Address: " + restaurant.getAddress());
            }
        }
    }

    private void deleteRestaurant() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }
        System.out.println("Select a restaurant to delete:");
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant restaurant = restaurants.get(i);
            System.out.println((i + 1) + ". " + restaurant.getName() + " - " + restaurant.getAddress());
        }
        System.out.print("Enter choice: ");
        int choice = getChoice();
        if (choice < 1 || choice > restaurants.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        Restaurant selectedRestaurant = restaurants.get(choice - 1);
        restaurantService.deleteRestaurant(selectedRestaurant.getId());
        System.out.println("Restaurant deleted successfully!");
        viewRestaurants();
    }

    private void viewMenuItems() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }
        System.out.println("Select a restaurant to view menu items:");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i + 1) + ". " + restaurants.get(i).getName());
        }
        System.out.print("Enter choice: ");
        int restaurantChoice = getChoice();
        if (restaurantChoice < 1 || restaurantChoice > restaurants.size()) {
            System.out.println("Invalid restaurant choice.");
            return;
        }
        Restaurant selectedRestaurant = restaurants.get(restaurantChoice - 1);

        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurantId(selectedRestaurant.getId());
        if (menuItems.isEmpty()) {
            System.out.println("No menu items found for this restaurant.");
        } else {
            System.out.println("\n--- Menu Items for " + selectedRestaurant.getName() + " ---");
            for (MenuItem item : menuItems) {
                System.out.println("Menu Item ID: " + item.getId() + ", Name: " + item.getName() +
                        ", Price: " + item.getPrice());
            }
        }
    }

    private void removeMenuItem() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }
        System.out.println("Select a restaurant:");
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i + 1) + ". " + restaurants.get(i).getName());
        }
        System.out.print("Enter choice: ");
        int restaurantChoice = getChoice();
        if (restaurantChoice < 1 || restaurantChoice > restaurants.size()) {
            System.out.println("Invalid restaurant choice.");
            return;
        }
        Restaurant selectedRestaurant = restaurants.get(restaurantChoice - 1);

        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurantId(selectedRestaurant.getId());
        if (menuItems.isEmpty()) {
            System.out.println("No menu items available for this restaurant.");
            return;
        }
        System.out.println("Select a menu item to remove:");
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - Price: " + item.getPrice());
        }
        System.out.print("Enter choice: ");
        int menuItemChoice = getChoice();
        if (menuItemChoice < 1 || menuItemChoice > menuItems.size()) {
            System.out.println("Invalid menu item choice.");
            return;
        }
        MenuItem selectedMenuItem = menuItems.get(menuItemChoice - 1);

        menuItemService.deleteMenuItem(selectedMenuItem.getId());
        System.out.println("Menu item removed successfully!");
    }

    private void runService(String serviceName) {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            String command;
            if (serviceName.equals("monolithic-app")) {
                command = "mvn exec:java";
            } else {
                command = "mvn spring-boot:run";
            }
            pb.command("cmd", "/c", "cd " + serviceName + " && " + command);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error running " + serviceName + ": " + e.getMessage());
        }
    }
}
