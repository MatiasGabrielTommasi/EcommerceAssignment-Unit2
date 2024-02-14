package com.ecommerceGUI.forms;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.Customer;
import com.ecommerce.data.Data;
import com.ecommerce.orders.Order;
import com.ecommerceGUI.Util;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class CustomerForm extends GridPane {
    private Label lblTitle;
    private Label lblFilter;
    private Label lblName;
    private Label lblAddress;

    private int customerId;
    private TextField txtFilter;
    private TextField txtName;
    private TextField txtAddress;

    private Button btnFilter;
    private Button btnClear;
    private Button btnSave;
    private Button btnCancel;

    private TableView<Customer> customerTable;
    private TableColumn<Customer, Integer> colId;
    private TableColumn<Customer, String> colName;
    private TableColumn<Customer, String> colAddress;
    private TableColumn<Customer, Void> colActions;

    private List<Customer> filteredCustomers;

    public CustomerForm() {
        try {
            initialize();
            setupTable();
            BorderPane.setMargin(this, new Insets(5));
            GridPane.setMargin(customerTable, new Insets(5, 0, 0, 5));
            GridPane.setRowSpan(customerTable, GridPane.REMAINING);
            GridPane.setColumnSpan(customerTable, GridPane.REMAINING);
        } catch (Exception e) {
            Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    private void initialize() {
        try {
            this.filteredCustomers = Data.Customers;

            lblFilter = new Label("Filter:");
            lblName = new Label("Name:");
            lblAddress = new Label("Address:");

            txtFilter = new TextField();
            txtName = new TextField();
            txtAddress = new TextField();

            lblTitle = new Label("Customers");
            lblTitle.setFont(new Font(20));

            btnFilter = new Button("Filter");
            btnFilter.setOnAction(event -> filter());

            btnClear = new Button("Clear Fields");
            btnClear.setOnAction(event -> clearFilter());

            btnSave = new Button("Save");
            btnSave.setOnAction(event -> save());

            btnCancel = new Button("Cancel");
            btnCancel.setOnAction(event -> cancel());
            btnCancel.setVisible(false);

            add(lblTitle, 0, 0);
            add(lblFilter, 2, 0);
            add(txtFilter, 3, 0);
            add(btnFilter, 4, 0);
            add(btnClear, 5, 0);
            add(lblName, 0, 1);
            add(txtName, 0, 2);
            add(lblAddress, 0, 3);
            add(txtAddress, 0, 4);
    	    add(btnCancel, 0, 5);
    	    add(btnSave, 1, 5);

            GridPane.setColumnSpan(txtName, 2);
            GridPane.setColumnSpan(txtAddress, 2);
        } catch (Exception e) {
            Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    private void setupTable() {
        try {
            if (customerTable == null) {
                customerTable = new TableView<>();
                colId = new TableColumn<>("ID");
                colName = new TableColumn<>("Name");
                colAddress = new TableColumn<>("Address");
                colActions = new TableColumn<>("Actions");

                colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerID()).asObject());
                colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
                colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

                colActions.setCellFactory(param -> new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setOnAction(event -> editCustomer(getTableRow().getItem()));
                        deleteButton.setOnAction(event -> deleteCustomer(getTableRow().getItem()));
                    }

                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(editButton, deleteButton));
                        }
                    }
                });

                customerTable.getColumns().add(colId);
                customerTable.getColumns().add(colName);
                customerTable.getColumns().add(colAddress);
                customerTable.getColumns().add(colActions);

                add(customerTable, 2, 1);
            }

            customerTable.getItems().clear();
            customerTable.getItems().addAll(filteredCustomers);
        } catch (Exception e) {
            Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    private void filter() {
        try {
            String filterText = txtFilter.getText().trim().toLowerCase();
            if (filterText.trim().length() > 0) {
                filteredCustomers = new ArrayList<>();
                for (Customer customer : Data.Customers) {
                    String customerName = customer.getName().toLowerCase();
                    if (customerName.contains(filterText.trim())) {
                        filteredCustomers.add(customer);
                    }
                }
            } else {
                filteredCustomers = Data.Customers;
            }
            setupTable();
        } catch (Exception e) {
            Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    private void clearFilter() {
        txtFilter.setText("");
        filteredCustomers = Data.Customers;
    }

    private void save() {
        try {
            if (validateCustomerFields()) {
                int id = customerId;
                String name = txtName.getText();
                String address = txtAddress.getText();

                if (id == 0) {// insert
                    id = Data.Customers.get(Data.Customers.size() - 1).getCustomerID() + 1;
                    Customer newCustomer = new Customer(id, name, address);
                    Data.Customers.add(newCustomer);
                } else {// update
                    for (Customer customer : Data.Customers) {
                        if (customer.getCustomerID() == id) {
                            customer.setName(name);
                            customer.setAddress(address);
                            break;
                        }
                    }
                }

                Util.displayMessage("Customer", "Customer added successfully", Alert.AlertType.INFORMATION);
                clearFields();
                filteredCustomers = Data.Customers;
                setupTable();
            }
        } catch (Exception e) {
            Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    private boolean validateCustomerFields() {
        try {
            if (txtName.getText().trim().length() == 0) {
                Util.displayMessage("Customer", "Invalid customer name", Alert.AlertType.WARNING);
                return false;
            }
            if (txtAddress.getText().trim().length() == 0) {
                Util.displayMessage("Customer", "Invalid customer address", Alert.AlertType.WARNING);
                return false;
            }
        } catch (NumberFormatException e) {
            Util.displayMessage("Customer", "Invalid customer details", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void cancel() {
        clearFields();
        btnCancel.setVisible(false);
    }

    private void editCustomer(Customer customer) {
        try {
            customerId = customer.getCustomerID();
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            btnCancel.setVisible(true);
        } catch (Exception e) {
            Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    private void deleteCustomer(Customer customer) {
        try {
            if (customer != null) {
                if (canDeleteCustomer(customer)) {
                    ButtonType result = Util.displayMessage("Customer",
                            "Are you sure you want to delete the customer " + customer.getName() + "?",
                            Alert.AlertType.CONFIRMATION);

                    if (result == ButtonType.OK) {
                        Data.Customers.remove(customer);
                        customerTable.getItems().remove(customer);
                    }
                }
            }
        } catch (Exception e) {
            Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    private Boolean canDeleteCustomer(Customer customer) {
        for (Order order : Data.Orders) {
            if (order.getCustomer().getCustomerID() == customer.getCustomerID()) {
                Util.displayMessage("Customer.",
                        "You can't delete " + customer.getName() + " because it's associated with an order.",
                        Alert.AlertType.INFORMATION);
                return false;
            }
        }
        return true;
    }

    private void clearFields() {
        customerId = 0;
        txtName.clear();
        txtAddress.clear();
    }
}