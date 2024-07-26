package order;

import product.*;
import java.util.*;
import getintinput.*;
import inventory.*;

public class Order {
    private String orderId;
    private String customerName;
    private List<Product> productsOrdered;

    public Order(String orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.productsOrdered = new ArrayList<>();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void addProduct(Product product) {
        productsOrdered.add(product);
    }

    public List<Product> getProductsOrdered() {
        return productsOrdered;
    }

    public static void placeOrder(OrderManager orderManager, Scanner scanner, Inventory inventory) {
        System.out.print("Enter Order ID: ");
        String orderId = scanner.next();

        if (orderManager.getOrderById(orderId) != null) {
            System.out.println("Order with ID " + orderId + " already exists.");
            return;
        }
        scanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        Order order = new Order(orderId, customerName);

        boolean continueAddingProducts = true;
        while (continueAddingProducts) {
            System.out.print("Enter Product ID: ");
            String productId = scanner.next();
            Product product = inventory.getProductById(productId);
            if (product == null) {
                System.out.println("Product with ID " + productId + " does not exist.");
            } else {
                System.out.print("Enter Quantity: ");
                int quantity = GetIntInput.getIntInput(scanner);
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                } else if (quantity > product.getQuantity()) {
                    System.out.println("Not enough stock available for this product.");
                } else {

                    order.addProduct(new Product(product.getId(), product.getName(), product.getPrice(), quantity));
                    System.out.println("Product added to the order.");
                }
            }

            System.out.print("Do you want to add another product to the order? (yes/no): ");
            String choice = scanner.next();
            continueAddingProducts = choice.equalsIgnoreCase("yes");
        }

        if (order.getProductsOrdered().isEmpty()) {
            System.out.println("No products added to the order. Order not placed.");
        } else {

            orderManager.placeOrder(order, inventory);
            System.out.println("Order placed successfully.");
        }
    }

    public static void viewOrders(OrderManager orderManager) {
        System.out.println("View Orders:");
        List<Order> orders = orderManager.getOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Customer Name: " + order.getCustomerName());
                System.out.println("Products Ordered:");
                List<Product> productsOrdered = order.getProductsOrdered();
                if (productsOrdered.isEmpty()) {
                    System.out.println("No products ordered.");
                } else {
                    for (Product product : productsOrdered) {

                        System.out.println("Product ID: " + product.getId() + ", Product Name: " + product.getName()
                                + ", Price: " + product.getPrice() + " Rs., Quantity: " + product.getQuantity());

                    }
                }
                System.out.println();
            }
        }
    }

}
