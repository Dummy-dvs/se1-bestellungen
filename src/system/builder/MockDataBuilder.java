package system.builder;

import model.Order;

import java.util.function.Consumer;

public interface MockDataBuilder {
    void buildCustomers();
    void buildArticles();
    void buildOrders(Consumer<Order> fillOrder);
}
