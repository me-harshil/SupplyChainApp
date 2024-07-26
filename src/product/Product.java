package product;

import inventory.*;
import java.util.*;
import getintinput.*;

public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;

    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static void loadProducts(List<String[]> productData, Inventory inventory) {
        for (String[] row : productData) {
            try {
                String id = row[0];
                String name = row[1];
                double price = Double.parseDouble(row[2]);
                int quantity = Integer.parseInt(row[3]);
                inventory.addProduct(new Product(id, name, price, quantity));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Error loading product data: " + e.getMessage());
            }
        }
    }

    public static void viewProducts(Inventory inventory) {

        // Display all products in inventory
        System.out.println("Products in Inventory:");
        if (inventory.getProducts().isEmpty()) {
            System.out.println("No products available.");
        } else {
            int i = 1;
            for (Product product : inventory.getProducts()) {
                System.out.println(i + ". Product ID: " + product.getId() + ", Product Name: " + product.getName()
                        + ", Price: " + product.getPrice() + " Rs., Quantity: " + product.getQuantity());
                i++;
            }

        }
    }

    public static boolean addProduct(Inventory inventory, Scanner scanner) {
        System.out.print("Enter Product ID: ");
        String productId = scanner.next();

        if (inventory.getProductById(productId) != null) {
            System.out.println("Product with ID " + productId + " already exists.");
            return false;
        }

        scanner.nextLine(); // Consume newline character
        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter Product Price: ");
        double productPrice;
        try {
            productPrice = scanner.nextDouble();
            if (productPrice <= 0) {
                System.out.println("Price must be greater than 0.");
                return false;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for price. Please enter a valid number.");
            return false;
        }
        System.out.print("Enter Product Quantity: ");
        int productQuantity = GetIntInput.getIntInput(scanner);
        if (productQuantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return false;
        }

        inventory.addProduct(new Product(productId, productName, productPrice, productQuantity));
        System.out.println("Product added successfully.");
        return true;
    }
}
