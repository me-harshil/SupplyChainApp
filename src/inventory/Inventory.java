package inventory;

import java.util.*;
import product.*;
import getintinput.*;
import filehandler.*;

public class Inventory {
    private List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void updateStock(String productId, int newQuantity) {
        Product product = getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        product.setQuantity(newQuantity);
    }

    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }

    public static boolean updateInventory(Inventory inventory, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Remove Product");
            System.out.println("2. Update Stock");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = GetIntInput.getIntInput(scanner);
            switch (choice) {
                case 1:
                    System.out.print("Enter Product ID to remove: ");
                    String productIdRemove = scanner.next();
                    Product productRemove = inventory.getProductById(productIdRemove);
                    if (productRemove == null) {
                        System.out.println("Product with ID " + productIdRemove + " does not exist.");
                        return false;
                    }
                    inventory.getProducts().remove(productRemove);
                    System.out.println("Product removed successfully.");
                    return true;

                case 2:
                    System.out.print("Enter Product ID to update inventory: ");
                    String productId = scanner.next();
                    Product product = inventory.getProductById(productId);
                    if (product == null) {
                        System.out.println("Product with ID " + productId + " does not exist.");
                        return false;
                    }
                    System.out.print("Enter new quantity: ");
                    int newQuantity;
                    try {
                        newQuantity = GetIntInput.getIntInput(scanner);
                        if (newQuantity < 0) {
                            throw new IllegalArgumentException("Quantity cannot be negative.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid integer.");
                        return false;
                    }
                    product.setQuantity(newQuantity);

                    System.out.println("Inventory updated successfully.");
                    return true;
                case 3:
                    exit = true;
                    return false;

                default:
                    System.out.println("Invalid choice! Please enter a number 1 or 2.");
                    break;

            }
        }
        return true;
    }

    public static void saveInventoryData(Inventory inventory, String fileName) {
        List<String[]> data = new ArrayList<>();
        String[] header = { "Product ID", "Product Name", "Price", "Quantity" };
        data.add(header);
        for (Product product : inventory.getProducts()) {
            String[] row = {
                    product.getId(),
                    product.getName(),
                    String.valueOf(product.getPrice()),
                    String.valueOf(product.getQuantity())
            };
            data.add(row);
        }
        FileHandler.writeDataToCSV(fileName, data);
       
    }

}
