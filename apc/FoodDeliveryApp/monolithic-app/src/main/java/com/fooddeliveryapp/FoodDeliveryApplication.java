package com.fooddeliveryapp;

import com.fooddeliveryapp.config.AppConfig;
import com.fooddeliveryapp.App;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FoodDeliveryApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        App app = context.getBean(App.class);
        app.run();

        context.close();
    }
}
