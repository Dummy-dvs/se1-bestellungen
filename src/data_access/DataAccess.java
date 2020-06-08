package data_access;

import model.Article;
import model.Customer;
import model.Order;
import system.ComponentLifecycle;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface DataAccess extends ComponentLifecycle {
	static DataAccess getInstance(){
		return DataAccessImpl.getInstance();
	}
	DataRepository<Customer,String> customers();
	DataRepository<Article,String> articles();
	DataRepository<Order,Long> orders();
	interface MockDataBuilder{
		void buildCustomers();
		void buildArticles();
		void buildOrders(Consumer<Order> fillOrder);
	}
	Optional<MockDataBuilder> buildMockData();
}
