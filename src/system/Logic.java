package system;

import model.Order;

import java.util.List;

public interface Logic extends ComponentLifecycle{
	void printOrders(List<Order> orders);
	void printInventory();
	long calculateTotal(Order order);
	long calculateIncludedVAT(Order order);
}
