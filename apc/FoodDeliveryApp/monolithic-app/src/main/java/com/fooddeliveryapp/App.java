package com.fooddeliveryapp;

import com.fooddeliveryapp.config.AppConfig;
import com.fooddeliveryapp.model.*;
import com.fooddeliveryapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        App app = context.getBean(App.class);
        app.run();

        context.close();
    }

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private OrdersService ordersService;

    public void run() {
        while (true) {
            System.out.println("\n--- Food Delivery System ---");
            System.out.println("1. Place Order");
            System.out.println("2. Admin Login");
            System.out.println("3. User Login");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    try {
                        placeOrder();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    break;
                case 2:
                    try {
                        adminLogin();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    break;
                case 3:
                    try {
                        userLogin();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    break;
                case 0:
                    System.out.println("Exiting... Bye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void placeOrder() throws InterruptedException {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }

        displayRestaurants(restaurants);
        System.out.print("Select restaurant: ");
        int ridChoice = Integer.parseInt(scanner.nextLine());
        if (ridChoice < 1 || ridChoice > restaurants.size()) {
            System.out.println("Invalid restaurant selection!");
            return;
        }
        long rid = restaurants.get(ridChoice - 1).getId();

        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurant(rid);
        if (menuItems.isEmpty()) {
            System.out.println("No menu items available for this restaurant.");
            return;
        }

        List<Orders> ordersList = new ArrayList<>();
        double total = 0;

        while (true) {
            displayMenuItems(menuItems);
            System.out.print("Select item: ");
            int itemChoice = Integer.parseInt(scanner.nextLine());
            if (itemChoice == 0)
                break;
            if (itemChoice < 1 || itemChoice > menuItems.size()) {
                System.out.println("Invalid selection!");
                continue;
            }
            MenuItem selected = menuItems.get(itemChoice - 1);
            System.out.print("Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());

            Orders order = new Orders();
            order.setMenuItem(selected);
            order.setQuantity(qty);
            order.setTotal(selected.getPrice() * qty);
            order.setStatus("Pending");

            ordersList.add(order);
            total += order.getTotal();
        }

        if (ordersList.isEmpty()) {
            System.out.println("No items selected. Order cancelled.");
            return;
        }

        // ------------------ LOGIN ------------------
        System.out.println("\n--- Enter Your Details ---");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userService.getUserByEmail(email);
        if (user == null) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(password);
            userService.createUser(newUser);
            user = newUser;
            System.out.println("Account created successfully!");
        } else {
            if (!user.getPassword().equals(password)) {
                System.out.println("Incorrect password. Order cannot be linked.");
                return;
            }
            System.out.println("Logged in successfully!");
        }

        // ------------------ ADDRESS ------------------
        System.out.print("Enter Delivery Address: ");
        String deliveryAddress = scanner.nextLine();

        // ------------------ PAYMENT ------------------
        System.out.println("\n--- Payment ---");
        System.out.println("Total Amount: ₹" + total);
        System.out.println("Choose Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. UPI");
        int payChoice = Integer.parseInt(scanner.nextLine());

        String paymentMethod;
        switch (payChoice) {
            case 1:
                paymentMethod = "Cash";
                break;
            case 2:
                paymentMethod = "Card";
                break;
            case 3:
                paymentMethod = "UPI";
                break;
            default:
                paymentMethod = "Unknown";
                break;
        }

        System.out.println("Processing payment");
        Thread.sleep(1000);
        System.out.println("Payment of ₹" + total + " successful via " + paymentMethod);

        for (Orders order : ordersList) {
            order.setUser(user);
            order.setDeliveryAddress(deliveryAddress);
            order.setStatus("Completed");
            ordersService.createOrder(order);
        }

        System.out.println("Order placed successfully!");
    }

    private void displayRestaurants(List<Restaurant> restaurants) {
        System.out.println("\n--- Available Restaurants ---");
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant r = restaurants.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, r.getName(), r.getAddress());
        }
    }

    private void displayMenuItems(List<MenuItem> menuItems) {
        System.out.println("\n--- Menu Items ---");
        System.out.printf("%-3s %-25s %-10s%n", "No", "Item Name", "Price");
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem m = menuItems.get(i);
            System.out.printf("%-3d %-25s ₹%-10.2f%n", i + 1, m.getName(), m.getPrice());
        }
        System.out.println("0. Finish Order");
    }

    private void adminLogin() throws InterruptedException {
        System.out.print("Admin Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (!email.equals("xyz@gmail.com") || !password.equals("1234")) {
            System.out.println("Invalid admin credentials!");
            return;
        }

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Users");
            System.out.println("2. Add Restaurant");
            System.out.println("3. Add Menu Item");
            System.out.println("4. View All Orders");
            System.out.println("5. Cancel Order");
            System.out.println("6. View Restaurants");
            System.out.println("7. Delete Restaurant");
            System.out.println("8. View Menu Items");
            System.out.println("9. Remove Menu Item");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

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
                    cancelOrder();
                    break;
                case 6:
                    viewRestaurants();
                    break;
                case 7:
                    deleteRestaurant();
                    break;
                case 8:
                    viewMenuItems();
                    break;
                case 9:
                    removeMenuItem();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void viewAllUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println("\n--- Users ---");
        for (User u : users) {
            System.out.println(u.getId() + ". " + u.getName() + " - " + u.getEmail());
        }
    }

    private void addRestaurant() {
        System.out.print("Restaurant Name: ");
        String name = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        Restaurant r = new Restaurant();
        r.setName(name);
        r.setAddress(address);
        restaurantService.createRestaurant(r);
        System.out.println("Restaurant added successfully!");
    }

    private void addMenuItem() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }

        displayRestaurants(restaurants);
        System.out.print("Select restaurant: ");
        int ridChoice = Integer.parseInt(scanner.nextLine());
        if (ridChoice < 1 || ridChoice > restaurants.size())
            return;
        long rid = restaurants.get(ridChoice - 1).getId();

        System.out.print("Menu Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Restaurant r = restaurantService.getRestaurantById(rid);
        MenuItem m = new MenuItem();
        m.setRestaurant(r);
        m.setName(name);
        m.setPrice(price);
        menuItemService.createMenuItem(m);

        System.out.println("Menu item added successfully!");
    }

    private void viewAllOrders() {
        List<Orders> orders = ordersService.getAllOrders();
        System.out.println("\n--- Orders ---");
        for (Orders o : orders) {
            System.out.println("OrderID: " + o.getId() + " | UserID: " + o.getUser().getId()
                    + " | Total: ₹" + o.getTotal() + " | Status: " + o.getStatus());
        }
    }

    private void userLogin() throws InterruptedException {
        System.out.print("User Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userService.getUserByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("Invalid credentials!");
            return;
        }

        System.out.println("Logged in successfully!");
        userMenu(user);
    }

    private void userMenu(User user) throws InterruptedException {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View My Orders");
            System.out.println("2. Update Order");
            System.out.println("3. Delete Order");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewMyOrders(user);
                    break;
                case 2:
                    updateMyOrder(user);
                    break;
                case 3:
                    deleteMyOrder(user);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void viewMyOrders(User user) {
        List<Orders> orders = ordersService.getOrdersByUserId(user.getId());
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\n--- My Orders ---");
        for (Orders o : orders) {
            System.out.println("OrderID: " + o.getId() + " | Item: " + o.getMenuItem().getName() + " | Qty: "
                    + o.getQuantity() + " | Total: ₹" + o.getTotal() + " | Status: " + o.getStatus() + " | Address: "
                    + o.getDeliveryAddress());
        }
    }

    private void updateMyOrder(User user) throws InterruptedException {
        viewMyOrders(user);
        System.out.print("Enter Order ID to update: ");
        long oid = Long.parseLong(scanner.nextLine());
        Orders order = ordersService.getOrderById(oid);
        if (order == null || order.getUser().getId() != user.getId()) {
            System.out.println("Invalid Order ID!");
            return;
        }

        List<MenuItem> menuItems = menuItemService
                .getMenuItemsByRestaurant(order.getMenuItem().getRestaurant().getId());
        if (menuItems.isEmpty()) {
            System.out.println("No menu items available for update.");
            return;
        }

        displayMenuItems(menuItems);
        System.out.print("Select new item (0 to keep current): ");
        int itemChoice = Integer.parseInt(scanner.nextLine());
        if (itemChoice != 0) {
            if (itemChoice < 1 || itemChoice > menuItems.size()) {
                System.out.println("Invalid selection!");
                return;
            }
            MenuItem newItem = menuItems.get(itemChoice - 1);
            order.setMenuItem(newItem);
            order.setTotal(order.getMenuItem().getPrice() * order.getQuantity());
        }

        System.out.print("New Quantity (" + order.getQuantity() + "): ");
        String qtyStr = scanner.nextLine();
        if (!qtyStr.isEmpty()) {
            int qty = Integer.parseInt(qtyStr);
            order.setQuantity(qty);
            order.setTotal(order.getMenuItem().getPrice() * qty);
        }

        System.out.print("New Delivery Address (" + order.getDeliveryAddress() + "): ");
        String addr = scanner.nextLine();
        if (!addr.isEmpty())
            order.setDeliveryAddress(addr);

        ordersService.updateOrder(order);
        System.out.println("Order updated!");

        System.out.println("Choose Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. UPI");
        int payChoice = Integer.parseInt(scanner.nextLine());

        String paymentMethod;
        switch (payChoice) {
            case 1:
                paymentMethod = "Cash";
                break;
            case 2:
                paymentMethod = "Card";
                break;
            case 3:
                paymentMethod = "UPI";
                break;
            default:
                paymentMethod = "Unknown";
                break;
        }

        System.out.println("Processing payment...");
        Thread.sleep(1000);
        System.out.println("Payment of ₹" + order.getTotal() + " successful via " + paymentMethod + "!");
    }

    private void deleteMyOrder(User user) {
        viewMyOrders(user);
        System.out.print("Enter Order ID to delete: ");
        long oid = Long.parseLong(scanner.nextLine());
        Orders order = ordersService.getOrderById(oid);
        if (order == null || order.getUser().getId() != user.getId()) {
            System.out.println("Invalid Order ID!");
            return;
        }

        System.out.print("Confirm delete (yes/no): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes")) {
            ordersService.deleteOrder(oid);
            System.out.println("Order deleted!");
        }
    }

    private void cancelOrder() {
        viewAllOrders();
        System.out.print("Enter Order ID to cancel: ");
        long oid = Long.parseLong(scanner.nextLine());
        Orders order = ordersService.getOrderById(oid);
        if (order == null) {
            System.out.println("Invalid Order ID!");
            return;
        }

        System.out.print("Confirm cancel (yes/no): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes")) {
            ordersService.cancelOrder(oid);
            System.out.println("Order cancelled!");
        }
    }

    private void viewRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        System.out.println("\n--- Restaurants ---");
        for (Restaurant r : restaurants) {
            System.out.println(r.getId() + ". " + r.getName() + " - " + r.getAddress());
        }
    }

    private void deleteRestaurant() {
        viewRestaurants();
        System.out.print("Enter Restaurant ID to delete: ");
        long rid = Long.parseLong(scanner.nextLine());
        Restaurant restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant == null) {
            System.out.println("Invalid Restaurant ID!");
            return;
        }

        System.out.print("Confirm delete (yes/no): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes")) {
            restaurantService.deleteRestaurant(rid);
            System.out.println("Restaurant deleted!");
        }
    }

    private void viewMenuItems() {
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        System.out.println("\n--- Menu Items ---");
        for (MenuItem m : menuItems) {
            System.out.println("ID: " + m.getId() + " | Restaurant: " + m.getRestaurant().getName() +
                    " | Name: " + m.getName() + " | Price: ₹" + m.getPrice());
        }
    }

    private void removeMenuItem() {
        viewMenuItems();
        System.out.print("Enter Menu Item ID to remove: ");
        long mid = Long.parseLong(scanner.nextLine());
        MenuItem menuItem = menuItemService.getMenuItemById(mid);
        if (menuItem == null) {
            System.out.println("Invalid Menu Item ID!");
            return;
        }

        System.out.print("Confirm remove (yes/no): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes")) {
            menuItemService.deleteMenuItem(mid);
            System.out.println("Menu item removed!");
        }
    }
}
