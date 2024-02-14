package com.ecommerceGUI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.Base64;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Util {
	public static String toFormattedString(Double number) {
		return new DecimalFormat("#.00").format(number);
	}
	public static ButtonType displayMessage(String title, String message, Alert.AlertType type) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        return alert.showAndWait().orElse(ButtonType.CANCEL);
	}
	
	public static String saveImage(File sourceFile, int productId) {
        try {
            String fileName = "product_image_" + productId + getFileExtension(sourceFile.getName());
            String destinationPath = "img/" + fileName;

            Files.copy(sourceFile.toPath(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);

            return destinationPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String saveBase64Image(int productId, String imageBase64) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(imageBase64);

            String fileName = "product_image_" + productId + ".png";
            String destinationPath = "img/" + fileName;
            
            if (!Files.exists(Paths.get("img"))) {
                Files.createDirectory(Paths.get("img"));
            }
            
            if (!Files.exists(Path.of(destinationPath))) {
                Files.createFile(Path.of(destinationPath));
            }

            try (FileOutputStream fos = new FileOutputStream(destinationPath)) {
                fos.write(decodedBytes);
            }

            return destinationPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }

    public static Circle getCircleImage(int radius, String path) {
    	Circle img = new Circle(radius,radius,radius * 0.5);
    	img.setStroke(Color.LIGHTGRAY);
    	if(path.trim().length() > 0) {
        	img.setFill(new ImagePattern(new Image(new File(path).toURI().toString())));
    	}
        return img;
    }

    public static void updateCircleImage(Circle imageView, String path) {
    	if(imageView != null && path.trim().length() > 0) {
    		imageView.setFill(new ImagePattern(new Image(new File(path).toURI().toString())));
    	}
    }

    public static Button createCircularButton(String text) {
        Button button = new Button(text);
        button.setShape(new javafx.scene.shape.Circle(30));
        button.setPrefSize(25, 25);
        return button;
    }
}