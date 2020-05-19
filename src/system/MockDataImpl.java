package system;

import model.Article;
import model.Customer;
import model.Order;
import model.OrderItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class MockDataImpl implements DataAccess {
	private final List<Customer> customers = new ArrayList<>();
	private final List<Article> articles = new ArrayList<>();
	private final List<Order> orders = new ArrayList<>();
	public MockDataImpl(){
		//Customers
		customers.add(new Customer("C86516", "Eric Schulz-Mueller", "eric2346@gmail.com"));
		customers.add(new Customer("C64327", "Meyer, Anne", "+4917223524"));
		customers.add(new Customer("C12396", "Nadine Ulla Blumenfeld", "+4915292454"));
		customers.add(new Customer("C0xC0FFEE", "Timo Werner", "tw@gmail.com"));
		customers.add(new Customer("C666", "Sandra Müller", "samue62@gmail.com"));
		//Articles
		articles.add(new Article("SKU-458362", "Tasse", 299, 2000));
		articles.add(new Article("SKU-693856", "Becher", 149, 8400));
		articles.add(new Article("SKU-518957", "Kanne", 2000, 2400));
		articles.add(new Article("SKU-638035", "Teller", 649, 7000));
		articles.add(new Article("Mr Coffee", "Kaffemaschine", 2999, 500));
		articles.add(new Article("Mr Tea", "Teekocher", 1999, 2000));
	}
	@Override
	public List<Customer> getCustomers() {
		return customers;
	}
	@Override
	public List<Article> getArticles() {
		return Collections.unmodifiableList(articles);
	}
	@Override
	public List<Order> getOrders() {
		return Collections.unmodifiableList(orders);
	}
	@Override
	public Customer getCustomer(int i) {
		return i > 0 && i < customers.size() ? customers.get(i) : null;
	}
	@Override
	public Article getArticle(int i) {
		return i > 0 && i < articles.size() ? articles.get(i) : null;
	}
	@Override
	public Order getOrder(int i) {
		return i > 0 && i < orders.size() ? orders.get(i) : null;
	}
	@Override
	public DataAccess add(Order order) {
		orders.add(order);
		for (var oi : order.getItems()) {
			var a = articles.get(articles.indexOf(oi.getArticle()));
			a.setUnitsInStore(a.getUnitsInStore() - oi.getUnitsOrdered());
		}
		return this;
	}
	@Override
	public void startup() {
		var a1=articles.get(0);
		var a2=articles.get(1);
		var a3=articles.get(2);
		var a4=articles.get(3);
		var a5=articles.get(4);
		var a6=articles.get(5);
		//Orders
		add(new Order(5234968294L, new Date(), customers.get(0)).addItem(new OrderItem(a3.getDescription(), a3, 1)));
		add(new Order(8592356245L, new Date(), customers.get(0)).addItem(new OrderItem(a4.getDescription(), a4, 4)).addItem(new OrderItem(a2.getDescription(), a2, 8)).addItem(new OrderItem("passende Tassen", a1, 4)));
		add(new Order(3563561357L, new Date(), customers.get(1)).addItem(new OrderItem(a3.getDescription() + " aus Porzellan", a3, 1)));
		add(new Order(6135735635L, new Date(), customers.get(2)).addItem(new OrderItem(a4.getDescription() + " blau/weiss Keramik", a4, 12)));
		add(new Order(9235856245L, new Date(), customers.get(3)).addItem(new OrderItem(a5.getDescription(), a5, 1)).addItem(new OrderItem(a1.getDescription(), a1, 6)));
		add(new Order(7335856245L, new Date(), customers.get(4)).addItem(new OrderItem(a6.getDescription(), a6, 1)).addItem(new OrderItem(a2.getDescription(), a2, 4)).addItem(new OrderItem(a4.getDescription(), a4, 4)));
	}
	@Override
	public void shutdown() {
		customers.clear();
		articles.clear();
		orders.clear();
	}
}
