package supplier;

import java.util.*;
import getintinput.*;

public class SupplierManager {
    public List<Supplier> suppliers;
    private static final String SUPPLIER_FILE = "supplier/suppliers.csv";

    public SupplierManager() {
        this.suppliers = new ArrayList<>();
    }

    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    public Supplier getSupplierById(String supplierId) {
        for (Supplier supplier : suppliers) {
            if (supplier.getId().equals(supplierId)) {
                return supplier;
            }
        }
        return null;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public static void manageSuppliers(SupplierManager supplierManager, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Supplier Management Menu:");
            System.out.println("1. Add Supplier");
            System.out.println("2. Update Supplier");
            System.out.println("3. Remove Supplier");
            System.out.println("4. View Suppliers");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = GetIntInput.getIntInput(scanner);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    addSupplier(supplierManager, scanner);
                    break;
                case 2:
                    updateSupplier(supplierManager, scanner);
                    break;
                case 3:
                    removeSupplier(supplierManager, scanner);
                    break;
                case 4:
                    viewSuppliers(supplierManager);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 5.");
            }
        }
    }

    public static void addSupplier(SupplierManager supplierManager, Scanner scanner) {
        System.out.print("Enter Supplier ID: ");
        String supplierId = scanner.next();

        if (supplierManager.getSupplierById(supplierId) != null) {
            System.out.println("Supplier with ID " + supplierId + " already exists.");
            return;
        }
        scanner.nextLine();
        System.out.print("Enter Supplier Name: ");
        String supplierName = scanner.nextLine();
        System.out.print("Enter Supplier Contact: ");
        String supplierContact = scanner.next();

        Supplier newSupplier = new Supplier(supplierId, supplierName, supplierContact);
        supplierManager.addSupplier(newSupplier);
        System.out.println("Supplier added successfully.");
        Supplier.saveSupplierData(supplierManager, SUPPLIER_FILE);

    }

    public static void updateSupplier(SupplierManager supplierManager, Scanner scanner) {
        System.out.print("Enter Supplier ID to update: ");
        String supplierId = scanner.next();
        Supplier existingSupplier = supplierManager.getSupplierById(supplierId);
        if (existingSupplier == null) {
            System.out.println("Supplier with ID " + supplierId + " does not exist.");
            return;
        }
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter new name for supplier: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new contact for supplier: ");
        String newContact = scanner.next();
        existingSupplier.setName(newName);
        existingSupplier.setContact(newContact);
        System.out.println("Supplier updated successfully.");
        Supplier.saveSupplierData(supplierManager, SUPPLIER_FILE);

    }

    public static void removeSupplier(SupplierManager supplierManager, Scanner scanner) {
        System.out.print("Enter Supplier ID to remove: ");
        String supplierId = scanner.next();

        Supplier existingSupplier = supplierManager.getSupplierById(supplierId);
        if (existingSupplier == null) {
            System.out.println("Supplier with ID " + supplierId + " does not exist.");
            return;
        }
        supplierManager.suppliers.remove(existingSupplier);

        System.out.println("Supplier removed successfully.");
        Supplier.saveSupplierData(supplierManager, SUPPLIER_FILE);

    }

    public static void viewSuppliers(SupplierManager supplierManager) {
        System.out.println("Suppliers:");
        List<Supplier> suppliers = supplierManager.getSuppliers();
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers available.");
        } else {
            for (Supplier supplier : suppliers) {
                System.out.println("Supplier ID: " + supplier.getId() + ", Name: " + supplier.getName() + ", Contact: "
                        + supplier.getContact());
            }
        }
    }

}
