package com.ecommerceGUI;

import com.ecommerceGUI.forms.CommerceForm;
import com.ecommerceGUI.forms.CustomerForm;
import com.ecommerceGUI.forms.OrderForm;
import com.ecommerceGUI.forms.ProductForm;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EcommerceUI extends Application {

	private ToolBar toolBar;
	private Button btnNewOrder;
	private Button btnOrders;
	private Button btnProducts;
	private Button btnCustomers;
	private BorderPane root;
	private ProductForm productForm;
	private CommerceForm commerceForm;
	private CustomerForm customerForm;
	private OrderForm orderForm;
	
    public void start(Stage stage) {
    	toolBar = new ToolBar();
    	btnNewOrder = new Button("New Order");
    	btnNewOrder.setOnAction(event -> newOrder());
    	
    	btnOrders = new Button("Orders");
    	btnOrders.setOnAction(event -> orders());
    	
    	btnProducts = new Button("Products");
    	btnProducts.setOnAction(event -> products());
    	
    	btnCustomers = new Button("Customers");
    	btnCustomers.setOnAction(event -> customers());
    	
    	toolBar.getItems().addAll(btnNewOrder, btnProducts, btnCustomers, btnOrders);
    	
        root = new BorderPane();
        root.setTop(toolBar);     
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(850);
        stage.setTitle("E-commerce Application");
        stage.setResizable(true);
        stage.show();        
    }
    
    private void newOrder() {	
    	if(!hasOpenForm(CommerceForm.class)) {
    		if(commerceForm == null) {
    			commerceForm = new CommerceForm();
    		}
    		displayForm(commerceForm);
    	}
    }
    
    private void orders() {    	
    	if(!hasOpenForm(OrderForm.class)) {
    		if(orderForm == null) {
    			orderForm = new OrderForm();
    		}
    		displayForm(orderForm);
    	}
    }

    private void products() {
    	if(!hasOpenForm(ProductForm.class)) {
    		if(productForm == null) {
            	productForm = new ProductForm();
    		}
    		displayForm(productForm);
    	}
    }
    
    private void customers() {
    	if(!hasOpenForm(CustomerForm.class)) {
    		if(customerForm == null) {
    			customerForm = new CustomerForm();
    		}
            displayForm(customerForm);
    	}
    }
    
    private boolean hasOpenForm(Class<?> formType) {
        Node existingForm = null;

        for (Node node : root.getChildren()) {
            if (formType.isInstance(node)) {
                existingForm = node;
                break;
            }
        }

        return existingForm != null;
    }
    
    private void displayForm(GridPane form) {
        root.getChildren().clear();
        root.setTop(toolBar);
        root.setCenter(form);
    }
    
	public static void main(String[] args) {
        launch(args);
    }
}