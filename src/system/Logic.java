package system;

import model.Order;

public interface Logic extends ComponentLifecycle{
	void printOrders(Iterable<Order> orders);
	void printInventory();
	long calculateTotal(Order order);
	long calculateIncludedVAT(Order order);
}
