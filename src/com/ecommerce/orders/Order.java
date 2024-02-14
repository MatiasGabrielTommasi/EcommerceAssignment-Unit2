package com.ecommerce.orders;

import java.util.ArrayList;
import java.util.List;
import com.ecommerce.Customer;
import com.ecommerce.Product;

public class Order {
	private int orderID;
	private Customer customer;
	private List<Product> products;
	
	public Order(int orderID, Customer customer) {
		this.orderID = orderID;
		this.customer = customer;
		this.products = new ArrayList<>();
	}

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product, int quantity) {
        for (Product existingProduct : products) {
            if (existingProduct.getProductID() == product.getProductID()) {
                existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
                return;
            }
        }
        product.setQuantity(quantity);
        products.add(product);
    }

    public void removeProduct(Product product, int quantity) {
        for (Product existingProduct : products) {
            if (existingProduct.getProductID() == product.getProductID()) {
                if (quantity < existingProduct.getQuantity()) {
                    existingProduct.setQuantity(existingProduct.getQuantity() - quantity);
                } else {
                    products.remove(existingProduct);
                }
                return;
            }
        }
    }
}