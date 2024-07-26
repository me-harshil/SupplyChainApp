package supplier;

import java.util.*;
import filehandler.*;

public class Supplier {
    private String id;
    private String name;
    private String contact;

    public Supplier(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public static void loadSuppliers(List<String[]> supplierData, SupplierManager supplierManager) {
        for (String[] row : supplierData) {
            try {
                String id = row[0];
                String name = row[1];
                String contact = row[2];
                supplierManager.addSupplier(new Supplier(id, name, contact));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error loading supplier data: " + e.getMessage());
            }
        }
    }

    public static void saveSupplierData(SupplierManager supplierManager, String fileName) {
        List<String[]> data = new ArrayList<>();
        String[] header = { "Supplier ID", "Supplier Name", "Contact" };
        data.add(header);
        for (Supplier supplier : supplierManager.getSuppliers()) {
            String[] row = { supplier.getId(), supplier.getName(), supplier.getContact() };
            data.add(row);
        }
        FileHandler.writeDataToCSV(fileName, data);

    }
}
