import java.util.*;
import product.*;
import customer.*;
import supplier.*;
import order.*;
import inventory.*;
import filehandler.*;
import getintinput.*;
import java.io.*;

public class SupplyChainApp {
    public static final String PRODUCT_FILE = "product/products.csv";
    public static final String CUSTOMER_FILE = "customer/customers.csv";
    public static final String SUPPLIER_FILE = "supplier/suppliers.csv";

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private static boolean isLoggedIn = false;
    private static String loggedInUser = "";

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        OrderManager orderManager = new OrderManager();
        CustomerManager customerManager = new CustomerManager();
        SupplierManager supplierManager = new SupplierManager();

        // Load initial data from CSV files
        try {
            loadInitialData(inventory, customerManager, supplierManager);
        } catch (IOException e) {
            System.err.println("Error loading initial data: " + e.getMessage());
            return;
        }

        System.out.println("Welcome to the Supply Chain Management System!");
        System.out.println("Let's manage your inventory and orders efficiently.");

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            if (!isLoggedIn) {
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();
                if (authenticateUser(username, password)) {
                    isLoggedIn = true;
                    loggedInUser = username;
                    System.out.println("Login successful.");
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                    continue;
                }
            }

            System.out.println("Supply Chain Management System");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Place Order");
            System.out.println("4. Cancel Order");
            System.out.println("5. View Orders");
            System.out.println("6. Update Inventory");
            System.out.println("7. Manage Customers");
            System.out.println("8. Manage Suppliers");
            System.out.println("9. Logout");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = GetIntInput.getIntInput(scanner);

            switch (choice) {
                case 1:
                    // Clear the console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    if (isLoggedIn) {
                        try {
                            if (Product.addProduct(inventory, scanner)) {
                                Inventory.saveInventoryData(inventory, PRODUCT_FILE);
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error adding product: " + e.getMessage());
                        }
                    } else {
                        System.out.println("You need to login to add a product.");
                    }
                    break;
                case 2:
                    // Clear the console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    try {
                        Product.viewProducts(inventory);
                    } catch (Exception e) {
                        System.out.println("Error viewing products: " + e.getMessage());
                    }
                    break;
                case 3:
                    // Clear the console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    if (isLoggedIn) {
                        try {
                            Order.placeOrder(orderManager, scanner, inventory);
                            Inventory.saveInventoryData(inventory, PRODUCT_FILE);
                        } catch (Exception e) {
                            System.out.println("Error placing order: " + e.getMessage());
                        }
                    } else {
                        System.out.println("You need to login to place an order.");
                    }
                    break;
                case 4:
                    // Clear the console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    if (isLoggedIn) {
                        try {
                            orderManager.cancelOrder(scanner, inventory);
                            Inventory.saveInventoryData(inventory, PRODUCT_FILE);
                        } catch (Exception e) {
                            System.out.println("Error cancelling order: " + e.getMessage());
                        }
                    } else {
                        System.out.println("You need to login to cancel an order.");
                    }
                    break;
                case 5:
                    // Clear the console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    try {
                        Order.viewOrders(orderManager);
                    } catch (Exception e) {
                        System.out.println("Error viewing orders: " + e.getMessage());
                    }
                    break;
                case 6:
                    // Clear the console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    if (isLoggedIn) {
                        try {
                            boolean result = Inventory.updateInventory(inventory, scanner);
                            if (result) {
                                Inventory.saveInventoryData(inventory, PRODUCT_FILE);
                            }
                        } catch (Exception e) {
                            System.out.println("Error updating inventory: " + e.getMessage());
                        }
                    } else {
                        System.out.println("You need to login to update inventory.");
                    }
                    break;
                case 7:
                    // Clear the console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    if (isLoggedIn) {
                        try {
                            CustomerManager.manageCustomers(customerManager, scanner);
                        } catch (Exception e) {
                            System.out.println("Error managing customers: " + e.getMessage());
                        }
                    } else {
                        System.out.println("You need to login to manage customers.");
                    }
                    break;
                case 8:
                    // Clear the console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    if (isLoggedIn) {
                        try {
                            SupplierManager.manageSuppliers(supplierManager, scanner);
                        } catch (Exception e) {
                            System.out.println("Error managing suppliers: " + e.getMessage());
                        }
                    } else {
                        System.out.println("You need to login to manage suppliers.");
                    }
                    break;
                case 9:
                    // Clear the console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    if (isLoggedIn) {
                        isLoggedIn = false;
                        loggedInUser = "";
                        System.out.println("Logged out successfully.");
                    } else {
                        System.out.println("You are not logged in.");
                    }
                    break;
                case 10:
                    // Clear the console
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 10.");
            }
        }

        scanner.close();
    }

    private static boolean authenticateUser(String username, String password) {
        return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
    }

    private static void loadInitialData(Inventory inventory, CustomerManager customerManager,
            SupplierManager supplierManager) throws IOException {
        // Load initial product data from CSV file
        List<String[]> productData = FileHandler.readDataFromCSV(PRODUCT_FILE);
        Product.loadProducts(productData, inventory);

        // Load initial customer data from CSV file
        List<String[]> customerData = FileHandler.readDataFromCSV(CUSTOMER_FILE);
        Customer.loadCustomers(customerData, customerManager);

        // Load initial supplier data from CSV file
        List<String[]> supplierData = FileHandler.readDataFromCSV(SUPPLIER_FILE);
        Supplier.loadSuppliers(supplierData, supplierManager);
    }
}
