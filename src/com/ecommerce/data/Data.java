package com.ecommerce.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ecommerce.Customer;
import com.ecommerce.Product;
import com.ecommerce.orders.Order;
import com.ecommerceGUI.Util;

public class Data {
	public static List<Product> Products;
	public static List<Customer> Customers;
	public static List<Order> Orders;
	static {
		Products = new ArrayList<>();
        Products.add(new Product(1, "Vegetal Oil", 12.50, "4862375478234", "img/product_image_1.png"));
        Products.add(new Product(2, "Bread", 2.75, "1234567890123", "img/product_image_2.png"));
        Products.add(new Product(3, "Milk", 3.50, "2345678901234", "img/product_image_3.png"));
        Products.add(new Product(4, "Eggs", 4.25, "3456789012345", "img/product_image_4.png"));
        Products.add(new Product(5, "Cheese", 5.80, "4567890123456", "img/product_image_5.png"));
        Products.add(new Product(6, "Chicken", 7.90, "5678901234567", "img/product_image_6.png"));
        Products.add(new Product(7, "Pasta", 2.20, "6789012345678", "img/product_image_7.png"));
        Products.add(new Product(8, "Tomatoes", 1.50, "7890123456789", "img/product_image_8.png"));
        Products.add(new Product(9, "Cereal", 4.75, "8901234567890", "img/product_image_9.png"));
        Products.add(new Product(10, "Coffee", 6.30, "9012345678901", "img/product_image_10.png"));
        Products.add(new Product(11, "Juice", 3.25, "0123456789012", "img/product_image_11.png"));
        Products.add(new Product(12, "Apples", 2.10, "1123456789013", "img/product_image_12.png"));
        Products.add(new Product(13, "Butter", 4.60, "2123456789014", "img/product_image_13.png"));
        Products.add(new Product(14, "Yogurt", 3.80, "3123456789015", "img/product_image_14.png"));
        Products.add(new Product(15, "Bananas", 1.95, "4123456789016", "img/product_image_15.png"));
        Products.add(new Product(16, "Fish", 8.50, "5123456789017", "img/product_image_16.png"));
        Products.add(new Product(17, "Rice", 2.40, "6123456789018", "img/product_image_17.png"));
        Products.add(new Product(18, "Oranges", 2.25, "7123456789019", "img/product_image_18.png"));
        Products.add(new Product(19, "Chocolate", 4.95, "8123456789020", "img/product_image_19.png"));
        Products.add(new Product(20, "Toothpaste", 1.75, "9123456789021", "img/product_image_20.png"));

        Util.saveBase64Image(1, ProductBase64Images.vegetalOil);
        Util.saveBase64Image(2, ProductBase64Images.bread);
        Util.saveBase64Image(3, ProductBase64Images.milk);
        Util.saveBase64Image(4, ProductBase64Images.eggs);
        Util.saveBase64Image(5, ProductBase64Images.cheese);
        Util.saveBase64Image(6, ProductBase64Images.chicken);
        Util.saveBase64Image(7, ProductBase64Images.pasta);
        Util.saveBase64Image(8, ProductBase64Images.tomatoes);
        Util.saveBase64Image(9, ProductBase64Images.cereal);
        Util.saveBase64Image(10, ProductBase64Images.coffee);
        Util.saveBase64Image(11, ProductBase64Images.juice);
        Util.saveBase64Image(12, ProductBase64Images.apples);
        Util.saveBase64Image(13, ProductBase64Images.butter);
        Util.saveBase64Image(14, ProductBase64Images.yogurt);
        Util.saveBase64Image(15, ProductBase64Images.bananas);
        Util.saveBase64Image(16, ProductBase64Images.fish);
        Util.saveBase64Image(17, ProductBase64Images.rice);
        Util.saveBase64Image(18, ProductBase64Images.oranges);
        Util.saveBase64Image(19, ProductBase64Images.chocolate);
        Util.saveBase64Image(20, ProductBase64Images.toothpaste);


		Customers = new ArrayList<>();
        Customers.add(new Customer(1, "Liam Miller", "707 Spruce St, Anaheim, CA 92801"));
        Customers.add(new Customer(2, "Alice Johnson", "123 Main St, Los Angeles, CA 90001"));
        Customers.add(new Customer(3, "Bob Smith", "456 Oak St, San Francisco, CA 94102"));
        Customers.add(new Customer(4, "Eva Davis", "789 Pine St, San Diego, CA 92101"));
        Customers.add(new Customer(5, "David Wilson", "101 Elm St, Sacramento, CA 95814"));
        Customers.add(new Customer(6, "Sophia Martinez", "202 Maple St, Oakland, CA 94601"));
        Customers.add(new Customer(7, "Daniel Jones", "303 Cedar St, San Jose, CA 95101"));
        Customers.add(new Customer(8, "Olivia Brown", "404 Birch St, Fresno, CA 93701"));
        Customers.add(new Customer(9, "James Taylor", "505 Walnut St, Long Beach, CA 90802"));
        Customers.add(new Customer(10, "Emma White", "606 Willow St, Riverside, CA 92501"));

        
        Orders = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
        	int customerID = new Random().nextInt(10);
            Customer customer = Customers.get(customerID);
            Order order = new Order(i, customer);

            int productsQuantity = new Random().nextInt(10) + 1;
            for (int j = 1; j <= productsQuantity; j++) {
            	int productID = new Random().nextInt(20);
                Product product = Products.get(productID);
            	if(!order.getProducts().contains(product)) {
            		product.setQuantity(new Random().nextInt(5) + 1);
                    order.addProduct(product, j);
            	}
            }

            Orders.add(order);
        }
	}
}