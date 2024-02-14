package com.ecommerceGUI.forms;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import com.ecommerce.Product;
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
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

public class ProductForm extends GridPane{
	private Label lblTitle;
	private Label lblFilter;
    private Label lblName;
    private Label lblPrice;
    private Label lblCodebar;

    private int productId;
    private TextField txtFilter;
    private TextField txtName;
    private TextField txtPrice;
    private TextField txtCodebar;
    private Circle imageView;
    private File imageFile;

    private Button btnFilter;
    private Button btnClear;
    private Button btnSave;
    private Button btnCancel;
    Button btnSelectImage;
    
    private FileChooser fileChooser;

    private TableView<Product> productTable;
    private TableColumn<Product, Integer> colId;
    private TableColumn<Product, String> colImage;
    private TableColumn<Product, String> colName;
    private TableColumn<Product, String> colPrice;
    private TableColumn<Product, String> colCodebar;
    private TableColumn<Product, Void> colActions;
    
    private List<Product> filteredProducts;

    public ProductForm() {
    	try {
    		initializeComponents();
    		initializeTable();
    		initialieEvents();
    		configLayout();
    		
            setupTable();
		} catch (Exception e) {
		    Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);  
		}
    }

    private void initializeComponents() {
    	try {
            lblFilter = new Label("Filter:");
            lblName = new Label("Name:");
            lblPrice = new Label("Price:");
            lblCodebar = new Label("Codebar:");

            txtFilter = new TextField();
            txtName = new TextField();
            txtPrice = new TextField();
            txtCodebar = new TextField();
            
            lblTitle = new Label("Products");
            lblTitle.setFont(new Font(20));

        	btnFilter = new Button("Filter");

        	btnClear = new Button("Clear Fields");

        	btnSave = new Button("Save");

        	btnCancel = new Button("Cancel");
        	btnCancel.setVisible(false);
        	
        	btnSelectImage = new Button("Select Image");
        	
        	imageView = Util.getCircleImage(100, "");
        	imageView.setVisible(false);
            
            fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );
		} catch (Exception e) {
		    Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);  
		}
    }
    
    private void initializeTable() {
    	try {
    		filteredProducts = Data.Products;
    		
            productTable = new TableView<>();
            colId = new TableColumn<>("ID");
            colImage = new TableColumn<>("Image");
            colName = new TableColumn<>("Name");
            colPrice = new TableColumn<>("Price");
            colCodebar = new TableColumn<>("Codebar");
            colActions = new TableColumn<>("Actions");

            colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProductID()).asObject());
            colImage.setCellFactory(cellData -> new TableCell<>() {
                private final Circle imageView = Util.getCircleImage(30, "");

                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                    	Util.updateCircleImage(imageView, getTableRow().getItem().getImagePath());
                        setGraphic(imageView);
                    }
                }
            });
            colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            colPrice.setCellValueFactory(cellData -> new SimpleStringProperty("$" + Util.toFormattedString(cellData.getValue().getPrice())));
            colCodebar.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodebar()));

            colActions.setCellFactory(param -> new TableCell<>() {
                private final Button editButton = new Button("Edit");
                private final Button deleteButton = new Button("Delete");

                {
                    editButton.setOnAction(event -> editProduct(getTableRow().getItem()));
                    deleteButton.setOnAction(event -> deleteProduct(getTableRow().getItem()));
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

            productTable.getColumns().add(colId);
            productTable.getColumns().add(colImage);
            productTable.getColumns().add(colName);
            productTable.getColumns().add(colPrice);
            productTable.getColumns().add(colCodebar);
            productTable.getColumns().add(colActions);
		} catch (Exception e) {
		    Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);  
		}
    }
    
    private void initialieEvents() {
    	try {
        	btnFilter.setOnAction(event -> filter());

        	btnClear.setOnAction(event -> clearFilter());

    	    btnSave.setOnAction(event -> save());

        	btnCancel.setOnAction(event -> cancel());

        	btnSelectImage.setOnAction(event -> selectImage());
		} catch (Exception e) {
		    Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);  
		}
    }
    
    private void configLayout() {
    	try {    
            add(lblTitle, 0, 0);
            add(lblFilter, 2, 0);
            add(txtFilter, 3, 0);
    	    add(btnFilter, 4, 0);
    	    add(btnClear, 5, 0);
            add(lblName, 0, 1);
            add(txtName, 0, 2);
            add(lblPrice, 0, 3);
            add(txtPrice, 0, 4);
            add(lblCodebar, 0, 5);
            add(txtCodebar, 0, 6);
        	add(btnSelectImage, 0, 7);
        	add(imageView, 0, 8);
    	    add(btnCancel, 0, 9);
    	    add(btnSave, 1, 9);
            add(productTable, 2, 1);

            GridPane.setColumnSpan(txtName, 2);
            GridPane.setColumnSpan(txtPrice, 2);
            GridPane.setColumnSpan(txtCodebar, 2);
            GridPane.setColumnSpan(btnSelectImage, 2);
            GridPane.setColumnSpan(imageView, 2);
            BorderPane.setMargin(this, new Insets(5));
            GridPane.setMargin(productTable, new Insets(5, 0, 0, 5));
            GridPane.setRowSpan(productTable, GridPane.REMAINING);
            GridPane.setColumnSpan(productTable, GridPane.REMAINING);
		} catch (Exception e) {
		    Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);  
		}
    }
    
    private void selectImage() {
        File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());
        if (selectedFile != null) {
            imageFile = selectedFile;
            Util.updateCircleImage(imageView, imageFile.getPath());
            imageView.setVisible(true);
        }
    }

    private void setupTable() {
    	try {            
            productTable.getItems().clear();
            productTable.getItems().addAll(filteredProducts);
		} catch (Exception e) {
		    Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);  
		}
    }

    private void filter() {
    	try {
        	String filterText =txtFilter.getText().trim().toLowerCase();
        	if(filterText.trim().length() > 0) {
                filteredProducts = new ArrayList<>();
                for (Product product : Data.Products) {
                	String productName = product.getName().toLowerCase();
                    if (productName.contains(filterText.trim())) {
                        filteredProducts.add(product);
                    }
                }
        	}
        	else {
        	    filteredProducts = Data.Products;
        	}
    		setupTable();
		} catch (Exception e) {
		    Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);  
		}
    }
    
    private void clearFilter() {
    	txtFilter.setText("");
        filteredProducts = Data.Products;
    }
    
    private void save() {
    	try {
    		if(validateProductFields()) {
        	    int id = productId;
        	    String name = txtName.getText();
        	    double price = Double.parseDouble(txtPrice.getText());
        	    String codebar = txtCodebar.getText();
        	    String imagePath = imageFile.getPath();
        	
        	    Product currentProduct =  new Product(id, name, price, codebar, imagePath);
        	    if (currentProduct.getProductID() == 0) {//insert
        	    	currentProduct.setProductID(Data.Products.get(Data.Products.size() - 1).getProductID() + 1);
        	        Data.Products.add(currentProduct);
        	    } else {//update
        	        for (Product product : Data.Products) {
        	            if (product.getProductID() == id) {
        	                product.setName(name);
        	                product.setPrice(price);
        	                product.setCodebar(codebar);
        	                product.setImagePath(imagePath);
        	                break;
        	            }
        	        }
        	    }

        	    Util.saveImage(imageFile, currentProduct.getProductID());
        	    Util.displayMessage("Product", "Product added seccessfuly", Alert.AlertType.INFORMATION);        
        	    clearFields();
        	    filteredProducts = Data.Products;
        	    setupTable();
    		}
		} catch (Exception e) {
		    Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);  
		}
	}
    private boolean validateProductFields() {
        String name = txtName.getText().trim();
        String priceText = txtPrice.getText().trim();
        String codebar = txtCodebar.getText().trim();

        if (name.isEmpty()) {
            Util.displayMessage("Product", "Invalid product name", Alert.AlertType.WARNING);
            return false;
        }

        if (priceText.isEmpty()) {
            Util.displayMessage("Product", "Invalid product price", Alert.AlertType.WARNING);
            return false;
        }

        try {
            double price = Double.parseDouble(priceText);
            if (price < 0) {
                Util.displayMessage("Product", "Product price cannot be negative", Alert.AlertType.WARNING);
                return false;
            }
        } catch (NumberFormatException e) {
            Util.displayMessage("Product", "Invalid product price", Alert.AlertType.WARNING);
            return false;
        }

        // Validar código de barras con una expresión regular
        if (!codebar.matches("\\d+")) {
            Util.displayMessage("Product", "Invalid product codebar (numeric characters only)", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }
    
    private void cancel() {
    	clearFields();
        btnCancel.setVisible(false);
    	imageView.setVisible(false);
    }

    private void editProduct(Product product) {
    	try {
            productId = product.getProductID();
            txtName.setText(product.getName());
            txtPrice.setText(String.valueOf(product.getPrice()));
            txtCodebar.setText(product.getCodebar());
            btnCancel.setVisible(true);
        	imageView.setVisible(true);
            Util.updateCircleImage(imageView, product.getImagePath());	
		} catch (Exception e) {
		    Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);  
		}
    }

    private void deleteProduct(Product product) {
    	try {
            if (product != null) {
            	if(canDeleteProduct(product)) {
                	ButtonType result = Util.displayMessage("Product", "Are you sure you want to delete the product " + product.getName() + "?", Alert.AlertType.CONFIRMATION);
                    
                    if (result == ButtonType.OK) {
                        Data.Products.remove(product);
                        productTable.getItems().remove(product);
                    }
                    
                    filteredProducts = Data.Products;
                    setupTable();
            	}
            }
		} catch (Exception e) {
		    Util.displayMessage("Something went wrong.", e.getMessage(), Alert.AlertType.INFORMATION);  
		}
    }
    
    private Boolean canDeleteProduct(Product product) {
    	for (Order order : Data.Orders) {
    		List<Product> products = order.getProducts();
			for(Product orderProduct : products) {
				if(orderProduct.getProductID() == product.getProductID()) {
					Util.displayMessage("Product.", "You can't delete " + product.getName() + " because it's contained in another order.", Alert.AlertType.INFORMATION);
					return false;
				}
			}
		}
    	return true;
    }

    private void clearFields() {
        productId = 0;
        txtName.clear();
        txtPrice.clear();
        txtCodebar.clear();
//        imageView.setImage(null);
    }
}