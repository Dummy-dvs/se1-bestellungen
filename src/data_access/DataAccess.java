package data_access;

import model.Article;
import model.Customer;
import model.Order;
import system.ComponentLifecycle;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface DataAccess extends ComponentLifecycle {
	/**
	 * Provides access to the Customer repository
	 * @return Customer repository
	 */
	DataRepository<Customer,String> getCustomerData();
	/**
	 * Provides access to the Article repository
	 * @return Article repository
	 */
	DataRepository<Article,String> getArticleData();
	/**
	 * Provides access to the Order repository
	 * @return Order repository
	 */
	DataRepository<Order,Long> getOrderData();
}
