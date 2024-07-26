package order;

import java.util.*;
import product.*;
import inventory.*;

public class OrderManager {
    private List<Order> orders;
    private static final String PRODUCT_FILE = "product/products.csv";

    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    public void placeOrder(Order order, Inventory inventory) {

        orders.add(order);

        for (Product orderedProduct : order.getProductsOrdered()) {

            Product inventoryProduct = inventory.getProductById(orderedProduct.getId());
            if (inventoryProduct != null) {

                inventoryProduct.setQuantity(inventoryProduct.getQuantity() - orderedProduct.getQuantity());

            } else {
                System.out.println("Error: Product with ID " + orderedProduct.getId() + " not found in inventory.");
            }
        }

        Inventory.saveInventoryData(inventory, PRODUCT_FILE);
    }

    public void cancelOrder(Scanner scanner, Inventory inventory) {
        System.out.print("Enter Order ID to cancel: ");
        String orderId = scanner.next();

        Optional<Order> optionalOrder = orders.stream().filter(order -> order.getOrderId().equals(orderId)).findFirst();

        if (optionalOrder.isPresent()) {

            Order canceledOrder = optionalOrder.get();

            orders.remove(canceledOrder);

            for (Product product : canceledOrder.getProductsOrdered()) {

                Product inventoryProduct = inventory.getProductById(product.getId());
                if (inventoryProduct != null) {

                    inventoryProduct.setQuantity(inventoryProduct.getQuantity() + product.getQuantity());
                } else {
                    System.out.println("Product with ID " + product.getId() + " not found in inventory.");
                }
            }

            System.out.println("Order with ID " + orderId + " has been cancelled.");
        } else {

            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    public Order getOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
