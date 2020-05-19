package system;

import model.Article;
import model.Customer;
import model.Order;

import java.util.List;

public interface DataAccess extends ComponentLifecycle {
	List<Customer> getCustomers();
	List<Article> getArticles();
	List<Order> getOrders();
	Customer getCustomer(int i);
	Article getArticle(int i);
	Order getOrder(int i);
	DataAccess add(Order order);
}
