package system.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import data_access.DataAccess;
import model.Article;
import model.Customer;
import model.Order;
import model.OrderItem;


/**
 * Local singleton instance that implements the DataAccess.MockDataBuilder
 * interface and that fills repositories with some mock data.
 *
 * @author sgra64
 */
class MockDataBuilderImpl implements MockDataBuilder {

	private final DataAccess dataAccess;
	private final List<Article> articles = new ArrayList<>();
	private final HashMap<String, Article> articlesPL = new HashMap<String, Article>();
	private final ArrayList<Customer> customers = new ArrayList<>();
	private final HashMap<String, Order> orders = new HashMap<>();
	HashMap<String, Long> priceTable;

	private static final Object[][] priceTable_Normal = new Object[][]{
			{"SKU-458362", 299},    // Tasse
			{"SKU-693856", 149},    // Becher
			{"SKU-518957", 2000},    // Kanne
			{"SKU-638035", 649},    // Teller
			{"SKU-386035", 2999},    // Kaffeemaschine
			{"SKU-443803", 1999},    // Teekocher
	};

	@SuppressWarnings("unused")
	private static final Object[][] priceTable_Discount = new Object[][]{
			{"SKU-458362", 249},    // Tasse
			{"SKU-693856", 99},    // Becher
			{"SKU-518957", 1499},    // Kanne
			{"SKU-638035", 499},    // Teller
			{"SKU-386035", 2499},    // Kaffeemaschine
			{"SKU-443803", 1499},    // Teekocher
	};

	/*
	 * Local constructor.
	 */
	public MockDataBuilderImpl(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}


	@Override
	public void buildCustomers() {
		if (dataAccess.getCustomerData().count() <= 0) {
			customers.add(new Customer("C86516", "Eric Schulz-Mueller", "eric2346@gmail.com"));
			customers.add(new Customer("C64327", "Meyer, Anne", "+4917223524"));
			customers.add(new Customer("C12396", "Nadine Ulla Blumenfeld", "+4915292454"));
			customers.add(new Customer("C16865", "Timo Werner", "tw@gmail.com"));
			customers.add(new Customer("C66865", "Sandra Müller", "samue62@gmail.com"));
			dataAccess.getCustomerData().saveAll(customers);
		}
	}

	private void createPTArticles() {
		articlesPL.clear();
		for (var a : articles) {
			var id = a.getId();
			if (priceTable.containsKey(id))
				a.setUnitPrice(priceTable.get(id));
			articlesPL.put(id, a);
		}
	}

	public void applyPriceTable(Object[][] newPriceList) {
		priceTable = new HashMap<String, Long>();
		for (var art : newPriceList)
			priceTable.put((String) art[0], ((Integer) art[1]).longValue());
		createPTArticles();
	}

	@Override
	public void buildArticles() {
		if (dataAccess.getArticleData().count() <= 0) {
			articles.add(new Article("SKU-458362", "Tasse", 299, 2000));
			articles.add(new Article("SKU-693856", "Becher", 149, 8400));
			articles.add(new Article("SKU-518957", "Kanne", 2000, 2400));
			articles.add(new Article("SKU-638035", "Teller", 649, 7000));
			articles.add(new Article("SKU‐386035", "Kaffemaschine", 2999, 500));
			articles.add(new Article("SKU‐443803", "Teekocher", 1999, 2000));
			applyPriceTable(priceTable_Normal);
			dataAccess.getArticleData().saveAll(articlesPL.values());
		}
	}

	private void add(Order order) {
		dataAccess.getOrderData().save(order);
		for (var oi : order.getItems()) {
			var a = articlesPL.get(oi.getArticle().getId());
			a.setUnitsInStore(a.getUnitsInStore() - oi.getUnitsOrdered());
		}
	}

	@Override
	public void buildOrders(Consumer<Order> fillOrder) {
		var a1 = articles.get(0);
		var a2 = articles.get(1);
		var a3 = articles.get(2);
		var a4 = articles.get(3);
		var a5 = articles.get(4);
		var a6 = articles.get(5);
		// Eric's 1st order
		add(new Order(5234968294L, new Date(), customers.get(0)).addItem(new OrderItem(a3.getDescription(), a3, 1)));
		add(new Order(8592356245L, new Date(), customers.get(0)).addItem(new OrderItem(a4.getDescription(), a4, 4)).addItem(new OrderItem(a2.getDescription(), a2, 8)).addItem(new OrderItem("passende Tassen", a1, 4)));
		add(new Order(3563561357L, new Date(), customers.get(1)).addItem(new OrderItem(a3.getDescription() + " aus Porzellan", a3, 1)));
		add(new Order(6135735635L, new Date(), customers.get(2)).addItem(new OrderItem(a4.getDescription() + " blau/weiss Keramik", a4, 12)));
		add(new Order(9235856245L, new Date(), customers.get(3)).addItem(new OrderItem(a5.getDescription(), a5, 1)).addItem(new OrderItem(a1.getDescription(), a1, 6)));
		add(new Order(7335856245L, new Date(), customers.get(4)).addItem(new OrderItem(a6.getDescription(), a6, 1)).addItem(new OrderItem(a2.getDescription(), a2, 4)).addItem(new OrderItem(a4.getDescription(), a4, 4)));


		// Need to track orders
		for (var o : orders.values())
			fillOrder.accept(o);
		// build Nadine's 2nd order from existing data
		Order o7356 = new Order(7356613535L, new Date(), customers.get(2));
		//Article teller = data.getArticle( 3 );

		dataAccess.getArticleData().findById("SKU-638035" /*SKU for Teller*/).ifPresent(teller -> {
			o7356.addItem(    // 6 Teller
					new OrderItem(teller.getDescription() + " blau/weiss Keramik", teller, 6)
			);
			fillOrder.accept(o7356);
		});
		add(o7356);
		fillOrder.accept(o7356);

		dataAccess.getOrderData().saveAll(orders.values());
	}


	/*
	 * Private methods.
	 */

	private void adjustPrice(Article article, Object[][] priceTable) {
		String id = article.getId();
		for (Object[] line : priceTable) {
			String sku = (String) line[0];
			if (sku.equals(id)) {
				long price = (int) line[1];
				article.setUnitPrice(price);
			}
		}
	}

}
