# TODO: Convert FoodDeliveryApp to Console-Based Terminal Apps

## Plan Overview
Convert the user-service and order-service microservices from REST API-based to console-based terminal applications. The monolithic-app is already console-based. Create a unified console app with three panels (User, Admin, Restaurant) for different user types.

## Steps
- [x] Update user-service pom.xml to remove spring-boot-starter-web dependency
- [x] Update order-service pom.xml to remove spring-boot-starter-web dependency
- [x] Update user-service application.properties to remove server port settings
- [x] Update order-service application.properties to remove server port settings
- [x] Modify UserServiceApplication.java to add console menu for user management
- [x] Modify OrderServiceApplication.java to add console menu for order management
- [x] Remove/disable UserController.java
- [x] Remove/disable OrdersController.java
- [x] Add console-app and monolithic-app as modules in main pom.xml
- [x] Create UnifiedConsoleApp.java in console-app with three panels: User, Admin, Restaurant
- [x] Update monolithic-app pom.xml to add exec plugin for running
- [x] Update console-app pom.xml to set main class
- [x] Test the unified console app with three panels
- [x] Test user-service console app (prints microservice message)
- [x] Test order-service console app (displays console menu)
- [x] Provide instructions to run the unified console app
- [x] Implement place order functionality in unified console app
- [x] Modify deleteRestaurant() to display restaurant list after deletion
- [x] Modify viewMenuItems() to first ask for restaurant selection
- [x] Modify removeMenuItem() to first ask for restaurant selection then menu item
