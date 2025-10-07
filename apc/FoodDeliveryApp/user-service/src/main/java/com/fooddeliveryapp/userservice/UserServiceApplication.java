package com.fooddeliveryapp.userservice;

import com.fooddeliveryapp.userservice.model.User;
import com.fooddeliveryapp.userservice.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        System.out.println("UserServiceApplication is now a microservice. Use the unified console app to interact.");
    }

    private static void createUser(Scanner scanner, UserService userService) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        userService.createUser(user);
        System.out.println("User created successfully!");
    }

    private static void getUserById(Scanner scanner, UserService userService) {
        System.out.print("User ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        User user = userService.getUserById(id);
        if (user != null) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
        } else {
            System.out.println("User not found!");
        }
    }

    private static void getUserByEmail(Scanner scanner, UserService userService) {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        User user = userService.getUserByEmail(email);
        if (user != null) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
        } else {
            System.out.println("User not found!");
        }
    }

    private static void getAllUsers(UserService userService) {
        List<User> users = userService.getAllUsers();
        System.out.println("\n--- All Users ---");
        for (User user : users) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
        }
    }

    private static void updateUser(Scanner scanner, UserService userService) {
        System.out.print("User ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());
        User user = userService.getUserById(id);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.print("New Name (" + user.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) user.setName(name);

        System.out.print("New Email (" + user.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) user.setEmail(email);

        System.out.print("New Password: ");
        String password = scanner.nextLine();
        if (!password.isEmpty()) user.setPassword(password);

        userService.updateUser(user);
        System.out.println("User updated successfully!");
    }

    private static void deleteUser(Scanner scanner, UserService userService) {
        System.out.print("User ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());
        userService.deleteUser(id);
        System.out.println("User deleted successfully!");
    }
}
