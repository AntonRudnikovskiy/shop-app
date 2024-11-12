package shop.app.shopapp;

import org.springframework.boot.SpringApplication;

public class TestShopAppApplication {

    public static void main(String[] args) {
        SpringApplication.from(ShopAppApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
