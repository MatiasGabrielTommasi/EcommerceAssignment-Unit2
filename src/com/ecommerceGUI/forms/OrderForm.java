package com.ecommerceGUI.forms;

import java.io.File;

import com.ecommerce.Customer;
import com.ecommerce.Product;
import com.ecommerce.data.Data;
import com.ecommerce.orders.Order;
import com.ecommerceGUI.Util;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class OrderForm extends GridPane {
    private Label lblTitle;
    private Label lblOrderID;
    private Label lblCustomerName;
    private Label lblTotal;
    
    private Label lblFilter;
    private TextField txtFilter;
    private TableView<Order> orderTableView;
    private TableView<Product> productDetailsTableView;
    

    public OrderForm() {
        initialize();
        setupOrderTableView();
        setupProductDetailsTableView();
        updateOrderTableView();

        GridPane.setColumnSpan(orderTableView, 3);
        GridPane.setColumnSpan(productDetailsTableView, 3);
    }

    private void initialize() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(10));
        
        lblTitle = new Label("Orders");
        lblTitle.setFont(new Font(20));
        lblCustomerName = new Label("");
        lblOrderID = new Label("");
        lblTotal = new Label("");
        lblFilter = new Label("Filter Orders:");
        txtFilter = new TextField();

        Button btnFilter = new Button("Filter");
        btnFilter.setOnAction(event -> filterOrders());

        orderTableView = new TableView<>();
        productDetailsTableView = new TableView<>();

        add(lblTitle, 0, 0);
        add(lblFilter, 0, 1);
        add(txtFilter, 1, 1);
        add(btnFilter, 2, 1);
        add(lblOrderID, 3, 1);
        add(lblCustomerName, 4, 1);
        add(lblTotal, 5, 1);
        add(orderTableView, 0, 2);
        add(productDetailsTableView, 3, 2);
    }

    private void setupOrderTableView() {
        TableColumn<Order, Integer> colOrderID = new TableColumn<>("Order ID");
        TableColumn<Order, String> colCustomerName = new TableColumn<>("Customer Name");
        TableColumn<Order, Double> colTotal = new TableColumn<>("Total");
        TableColumn<Order, Button> colDetailsButton = new TableColumn<>("Details");

        colOrderID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderID()).asObject());
        colCustomerName.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue().getCustomer();
            return new SimpleStringProperty(customer.getName());
        });
        colTotal.setCellValueFactory(cellData -> {
            double total = cellData.getValue().getProducts().stream()
                    .mapToDouble(product -> product.getPrice() * product.getQuantity())
                    .sum();
            
            String formattedTotal = Util.toFormattedString(total);
            
            return new SimpleDoubleProperty(Double.parseDouble(formattedTotal)).asObject();
        });

        colDetailsButton.setCellFactory(param -> new TableCell<>() {
            private final Button detailsButton = new Button("Details");

            {
                detailsButton.setOnAction(event -> showProductDetails(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(detailsButton);
                }
            }
        });

        orderTableView.getColumns().add(colOrderID);
        orderTableView.getColumns().add(colCustomerName);
        orderTableView.getColumns().add(colTotal);
        orderTableView.getColumns().add(colDetailsButton);
    }

    private void setupProductDetailsTableView() {
        TableColumn<Product, Void> colImage = new TableColumn<>("Image");
        TableColumn<Product, String> colProductName = new TableColumn<>("Product Name");
        TableColumn<Product, Double> colPrice = new TableColumn<>("Price");
        TableColumn<Product, Integer> colQuantity = new TableColumn<>("Quantity");
        TableColumn<Product, Double> colTotal = new TableColumn<>("Total");

        colImage.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                	Product product = (Product) getTableRow().getItem();
                    String imagePath = product.getImagePath();

                    if (imagePath != null && !imagePath.isEmpty()) {
                        Image image = new Image(new File(imagePath).toURI().toString());
                        imageView.setImage(image);
                        setGraphic(imageView);
                    }else {
                        setGraphic(null);
                    }
                }
            }
        });
        colProductName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        colQuantity.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        colTotal.setCellValueFactory(cellData -> {
            double total = cellData.getValue().getPrice() * cellData.getValue().getQuantity();

            String formattedTotal = Util.toFormattedString(total);
            
            return new SimpleDoubleProperty(Double.parseDouble(formattedTotal)).asObject();
        });
        
        productDetailsTableView.getColumns().add(colImage);
        productDetailsTableView.getColumns().add(colProductName);
        productDetailsTableView.getColumns().add(colPrice);
        productDetailsTableView.getColumns().add(colQuantity);
        productDetailsTableView.getColumns().add(colTotal);
    }

    private void updateOrderTableView() {
        ObservableList<Order> orders = FXCollections.observableArrayList(Data.Orders);
        orderTableView.setItems(orders);
    }

    private void filterOrders() {
    	String customerName = txtFilter.getText();
        ObservableList<Order> filteredOrders = FXCollections.observableArrayList();

        for (Order order : Data.Orders) {
            String orderCustomerName = order.getCustomer().getName().toLowerCase();
            if (orderCustomerName.contains(customerName.toLowerCase())) {
                filteredOrders.add(order);
            }
        }

        orderTableView.setItems(filteredOrders);
    }

    private void showProductDetails(Order order) {
        if (order != null) {
        	double total = 0;
        	for(Product product : order.getProducts()) {
        		total += product.getPrice() * product.getQuantity();
        	}        	
        	
        	lblOrderID.setText("OrderID: " + order.getOrderID());
        	lblCustomerName.setText("Customer: " + order.getCustomer().getName());
        	lblTotal.setText("Total: " + Util.toFormattedString(total));
            ObservableList<Product> products = FXCollections.observableArrayList(order.getProducts());
            productDetailsTableView.setItems(products);
        }
    }
}
