package com.ecommerceGUI.controls;

import com.ecommerce.Product;
import com.ecommerceGUI.Util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ProductItem extends GridPane {
	private boolean isCartItem;
	private double total = 0;
	private Product product;

    private EventHandler<ActionEvent> onAddHandler;
    private EventHandler<ActionEvent> onSubstractHandler;

    public ProductItem(Product product, boolean isCartItem) {
        this.isCartItem = isCartItem;
        this.product = product;
        initialize();
    }

    private void initialize() {
    	setPadding(new Insets(2));
    	setMargin(this, new Insets(1));
    	Border border;
    	
        //product
        Label lblProduct = new Label(product.getName());
        //price
		Label lblPrice = new Label("$" + String.valueOf(product.getPrice()));
        
        if(isCartItem) {
        	border = new Border(new BorderStroke(Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1)));
        	//image
        	Circle imgProduct = Util.getCircleImage(50, product.getImagePath());
            
            //quantity
    		Label lblQuantity = new Label(String.valueOf(product.getQuantity()));
            //subtotal
    		total = product.getQuantity() * product.getPrice();
    		Label lblSubtotal = new Label("$" + total);

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPrefWidth(70);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPrefWidth(100);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPrefWidth(30);
            ColumnConstraints col4 = new ColumnConstraints();
            col4.setPrefWidth(50);
            ColumnConstraints col5 = new ColumnConstraints();
            col5.setPrefWidth(50);
            getColumnConstraints().addAll(col1, col2, col3, col4, col5);
                        
    		add(imgProduct, 0, 0);
    		add(lblProduct, 1, 0);
    		add(lblQuantity, 2, 0);
    		add(lblPrice, 3, 0);
    		add(lblSubtotal, 4, 0);
        }
        else {
        	border = new Border(new BorderStroke(Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1)));
        
        	//image
        	Circle imgProduct = Util.getCircleImage(80, product.getImagePath());

            Button addButton = Util.createCircularButton("+");
            addButton.setOnAction(event -> {
    	        if (onAddHandler != null) {
    	        	onAddHandler.handle(event);
    	        }
            });

            Button substractButton = Util.createCircularButton("-");
            substractButton .setOnAction(event -> {
    	        if (onSubstractHandler != null) {
    	            onSubstractHandler.handle(event);
    	        }
            });
            
    		add(imgProduct, 0, 0);
    		add(lblProduct, 0, 1);
    		add(lblPrice, 0, 2);
    		add(substractButton, 0, 3);
    		add(addButton, 1, 3);
    		
            GridPane.setColumnSpan(imgProduct, GridPane.REMAINING);
            GridPane.setColumnSpan(lblProduct, GridPane.REMAINING);
            GridPane.setColumnSpan(lblPrice, GridPane.REMAINING);

            GridPane.setHalignment(imgProduct, HPos.CENTER);
            GridPane.setHalignment(lblProduct, HPos.CENTER);
            GridPane.setHalignment(lblPrice, HPos.CENTER);
            GridPane.setHalignment(substractButton, HPos.LEFT);
            GridPane.setHalignment(addButton, HPos.RIGHT);
        }

        setBorder(border);

    }
    
    public double getTotal() {
    	return total;
    }

    public Product getProduct() {
    	return product;
    }
    
    public void setOnAdd(EventHandler<ActionEvent> handler) {
        this.onAddHandler = handler;
    }

    public void setOnSubstract(EventHandler<ActionEvent> handler) {
        this.onSubstractHandler = handler;
    }
}