package com.ecommerceGUI.forms;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.Customer;
import com.ecommerce.Product;
import com.ecommerce.data.Data;
import com.ecommerce.orders.Order;
import com.ecommerceGUI.controls.ProductItem;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class CommerceForm extends GridPane {
	private Label lblTitle;
	private Label lblProducts = new Label("Products");
	private Label lblCustomer = new Label("Customer: ");
	private Label lblTotal;
	private GridPane productGrid;
    private GridPane cartGrid;
    private GridPane endBox;
	private ScrollPane productScroll;
    private ScrollPane cartScroll;
    private ComboBox<Customer> customerComboBox;
    private List<Product> cartItems = new ArrayList<>();
    private List<Product> productList;
    private int maxColumns = 3;

    public CommerceForm() {
        initialize();
    }

    private void initialize() {
    	productList = Data.Products;
    	
    	lblTitle = new Label("New Order");
    	lblTitle.setFont(new Font(20));
    	lblTotal = new Label("$0.00");
    	lblTotal.setFont(new Font(20));
    	
    	Label lblTotalTitle = new Label("Total:");
    	lblTotalTitle.setFont(new Font(20));
    	
        productGrid = new GridPane();
        productScroll = new ScrollPane(productGrid);
        productScroll.setFitToWidth(true);
        productScroll.setFitToHeight(true);
        productScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 

        cartGrid = new GridPane();
        cartScroll = new ScrollPane(cartGrid);
        cartScroll.setFitToWidth(true);
        cartScroll.setFitToHeight(true);
        cartScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        cartScroll.setPrefHeight(250);
        
        endBox = new GridPane();

        productGrid.setPrefWidth(300);

        ColumnConstraints col0 = new ColumnConstraints();
        col0.setPercentWidth(250);
        cartGrid.getColumnConstraints().add(col0);

        RowConstraints row0 = new RowConstraints();
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(350);
        RowConstraints row2 = new RowConstraints();
        endBox.getRowConstraints().addAll(row0, row1, row2);
    	
        customerComboBox = new ComboBox<>(FXCollections.observableArrayList(Data.Customers));

        HBox customerContainer = new HBox(lblCustomer, customerComboBox);
        lblCustomer.setAlignment(Pos.CENTER_LEFT);
        
        HBox totalContainer = new HBox(lblTotalTitle, lblTotal);
        lblTotalTitle.setAlignment(Pos.CENTER_LEFT);
        lblTotal.setAlignment(Pos.CENTER_RIGHT);
        
        Button btnRegiterOrder = new Button("Register Order");
        btnRegiterOrder.setOnAction(event -> registerOrder());
        
        endBox.add(customerContainer, 0, 0);
        endBox.add(cartScroll, 0, 1);
        endBox.add(totalContainer, 0, 2);
        endBox.add(btnRegiterOrder, 0, 3);

        add(lblTitle, 0, 0);
        add(lblProducts, 0, 1);
        add(productScroll, 0, 2);
        add(endBox, 1, 1);
        
        setRowSpan(endBox, 2);
        
        setPadding(new Insets(10));

        loadProducts();
        loadCart();
    }

    private void loadProducts() {
        int currentColumnIndex = 0;
        int currentRowIndex = 0;
    	for(Product product : productList) {
            ProductItem productItem = new ProductItem(product, false);
            productItem.setOnAdd(event -> {
            	addCartItem(product);
            });
            productItem.setOnSubstract(event -> {
            	removeCartItem(product);
            });
            productGrid.add(productItem, currentColumnIndex, currentRowIndex);
            
            if(currentColumnIndex == maxColumns - 1) {
            	currentColumnIndex = 0;
            	currentRowIndex++;
            }
            else {
            	currentColumnIndex++;
            }
    	}
    }
    
    private void loadCart() {
    	cartGrid.getChildren().clear();
    	double subtotal = 0;
        int cartRowIndex = 0;
    	for(Product product : cartItems) {
            ProductItem productItem = new ProductItem(product, true);
			cartGrid.add(productItem, 0, cartRowIndex);
			cartRowIndex++;
			subtotal += product.getPrice() * product.getQuantity();
    	}    	
    	//total
    	lblTotal.setText("$" + subtotal);
    }
    
    private void addCartItem(Product productParam) {
        try {
            if (productParam == null) {
                throw new IllegalArgumentException("Product cannot be null.");
            }

            Product existingProduct = null;
            for(Product product : cartItems) {
            	if(productParam.getProductID() == product.getProductID()) {
            		existingProduct = product;
            		break;
            	}
            }

            if (existingProduct != null) {
            	existingProduct.setQuantity(existingProduct.getQuantity() + 1);

            } else {
            	productParam.setQuantity(1);
                cartItems.add(productParam);
            }

        	loadCart();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void removeCartItem(Product productParam) {
        try {
            Product existingProduct = null;
            for(Product product : cartItems) {
            	if(productParam.getProductID() == product.getProductID()) {
            		existingProduct = product;
            		break;
            	}
            }

            if(existingProduct != null) {
                if (existingProduct.getQuantity() == 1) {
                    cartItems.remove(existingProduct);
                } else {
                    existingProduct.setQuantity(existingProduct.getQuantity() - 1);
                }

            	loadCart();
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private void registerOrder() {
    	if(validateOrderFields()) {
            try {
                Customer selectedCustomer = customerComboBox.getValue();

                if (selectedCustomer == null) {
                    System.err.println("Error: Please select a customer.");
                    return;
                }

                Order order = new Order(Data.Orders.get(Data.Orders.size() - 1).getOrderID() + 1, selectedCustomer);

                for (Product product : cartItems) {
                    order.addProduct(product, product.getQuantity());
                }

                Data.Orders.add(order);

                cartItems.clear();
                loadCart();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
    	}
    }
    
    private boolean validateOrderFields() {
        if (customerComboBox.getValue() == null) {
            System.err.println("Error: Please select a customer.");
            return false;
        }

        if (cartItems.isEmpty()) {
            System.err.println("Error: The cart is empty. Add products to the cart.");
            return false;
        }
        
        return true;
    }
}