package customer;

import java.util.*;
import filehandler.*;

public class Customer {
    private String id;
    private String name;
    private String email;

    public Customer(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and setters for id, name, and email
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void loadCustomers(List<String[]> customerData, CustomerManager customerManager) {
        if (customerData == null || customerManager == null) {
            throw new IllegalArgumentException("Customer data or customer manager cannot be null.");
        }
        if (customerData.isEmpty()) {
            System.out.println("No customer data provided.");
            return;
        }
        // Skip the header row
        // customerData.remove(0);

        for (String[] row : customerData) {
            if (row.length < 3) {
                System.out.println("Invalid customer data format.");
                continue;
            }
            try {
                String id = row[0];
                String name = row[1];
                String email = row[2];
                customerManager.addCustomer(new Customer(id, name, email));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error parsing customer data: " + e.getMessage());
            }
        }
    }

    public static void saveCustomerData(CustomerManager customerManager, String fileName) {
        if (customerManager == null || fileName == null) {
            throw new IllegalArgumentException("Customer manager or file name cannot be null.");
        }
        List<String[]> data = new ArrayList<>();
        String[] header = { "Customer ID", "Customer Name", "Email" };
        data.add(header);
        for (Customer customer : customerManager.getCustomers()) {
            String[] row = { customer.getId(), customer.getName(), customer.getEmail() };
            data.add(row);
        }
        FileHandler.writeDataToCSV(fileName, data);
   
    }

}
