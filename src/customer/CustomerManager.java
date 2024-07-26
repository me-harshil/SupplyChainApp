package customer;

import java.util.*;
import getintinput.*;

public class CustomerManager {
    public List<Customer> customers;
    private static final String CUSTOMER_FILE = "customer/customers.csv";

    public CustomerManager() {
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        customers.add(customer);
    }

    public Customer getCustomerById(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty.");
        }
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public static void manageCustomers(CustomerManager customerManager, Scanner scanner) {
        if (customerManager == null || scanner == null) {
            throw new IllegalArgumentException("Customer manager or scanner cannot be null.");
        }
        boolean exit = false;
        while (!exit) {
            System.out.println("Customer Management Menu:");
            System.out.println("1. Add Customer");
            System.out.println("2. Update Customer");
            System.out.println("3. Remove Customer");
            System.out.println("4. View Customers");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = GetIntInput.getIntInput(scanner);

            switch (choice) {
                case 1:
                    addCustomer(customerManager, scanner);
                    break;
                case 2:
                    updateCustomer(customerManager, scanner);
                    break;
                case 3:
                    removeCustomer(customerManager, scanner);
                    break;
                case 4:
                    viewCustomers(customerManager);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 5.");
            }
        }
    }

    public static void addCustomer(CustomerManager customerManager, Scanner scanner) {
        if (customerManager == null || scanner == null) {
            throw new IllegalArgumentException("Customer manager or scanner cannot be null.");
        }
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.next();

        if (customerManager.getCustomerById(customerId) != null) {
            System.out.println("Customer with ID " + customerId + " already exists.");
            return;
        }
        scanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String customerEmail = scanner.next();

        Customer newCustomer = new Customer(customerId, customerName, customerEmail);
        customerManager.addCustomer(newCustomer);
        System.out.println("Customer added successfully.");
        Customer.saveCustomerData(customerManager, CUSTOMER_FILE);
    }

    public static void updateCustomer(CustomerManager customerManager, Scanner scanner) {
        if (customerManager == null || scanner == null) {
            throw new IllegalArgumentException("Customer manager or scanner cannot be null.");
        }
        System.out.print("Enter Customer ID to update: ");
        String customerId = scanner.next();
        Customer existingCustomer = customerManager.getCustomerById(customerId);
        if (existingCustomer == null) {
            System.out.println("Customer with ID " + customerId + " does not exist.");
            return;
        }
        scanner.nextLine();
        System.out.print("Enter new name for customer: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new email for customer: ");
        String newEmail = scanner.next();
        existingCustomer.setName(newName);
        existingCustomer.setEmail(newEmail);
        System.out.println("Customer updated successfully.");

        Customer.saveCustomerData(customerManager, CUSTOMER_FILE);
    }

    public static void removeCustomer(CustomerManager customerManager, Scanner scanner) {
        if (customerManager == null || scanner == null) {
            throw new IllegalArgumentException("Customer manager or scanner cannot be null.");
        }
        System.out.print("Enter Customer ID to remove: ");
        String customerId = scanner.next();

        Customer existingCustomer = customerManager.getCustomerById(customerId);
        if (existingCustomer == null) {
            System.out.println("Customer with ID " + customerId + " does not exist.");
            return;
        }
        customerManager.customers.remove(existingCustomer);

        System.out.println("Customer removed successfully.");
        Customer.saveCustomerData(customerManager, CUSTOMER_FILE);
    }

    public static void viewCustomers(CustomerManager customerManager) {
        if (customerManager == null) {
            throw new IllegalArgumentException("Customer manager cannot be null.");
        }
        System.out.println("Customers:");
        List<Customer> customers = customerManager.getCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers available.");
        } else {
            for (Customer customer : customers) {
                System.out.println("Customer ID: " + customer.getId() + ", Name: " + customer.getName() + ", Email: "
                        + customer.getEmail());
            }
        }
    }

}
