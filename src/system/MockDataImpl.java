package system;

import model.Article;
import model.Customer;
import model.Order;
import model.OrderItem;

import java.util.*;

class MockDataImpl implements DataAccess {
	private final List<Customer> customers = new ArrayList<>();
	private final List<Article> articles = new ArrayList<>();
	private final HashMap<String, Article> articlesPL = new HashMap<String, Article>();
	private final List<Order> orders = new ArrayList<>();
	HashMap<String, Long> priceTable;
	private final Object[][] priceTable_Normal
			= new Object[][]{
			{"SKU-458362", 299}, //Tasse
			{"SKU-693856", 149}, //Becher
			{"SKU-518957", 2000}, //Kanne
			{"SKU-638035", 649}, //Teller
			{"SKU‐386035", 2999}, //Kaffm.
			{"SKU‐443803", 1999}, //Teekoch.
	};
	private final Object[][] priceTable_Discount
			= new Object[][]{
			{"SKU-458362", 249}, //Tasse
			{"SKU-693856", 99}, //Becher
			{"SKU-518957", 1499}, //Kanne
			{"SKU-638035", 499}, //Teller
			{"SKU‐386035", 2499}, //Kaffm.
			{"SKU‐443803", 1499}, //Teekoch.
	};

	public MockDataImpl() {
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
		articles.add(new Article("SKU‐386035", "Kaffemaschine", 2999, 500));
		articles.add(new Article("SKU‐443803", "Teekocher", 1999, 2000));
		applyPriceTable(priceTable_Normal);
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

	public long getArticlePrice(Article a) {
		return priceTable.containsKey(a.getId()) ? priceTable.get(a.getId()) : a.getUnitPrice();
	}

	public void adjustPrice(Article article, Object[][] priceTable) {
		for (var art : priceTable)
			if (art[0].equals(article.getId()))
				this.priceTable.put((String) art[0], ((Integer) art[1]).longValue());
		createPTArticles();
	}

	@Override
	public List<Customer> getCustomers() {
		return customers;
	}

	@Override
	public List<Article> getArticles() {
		return List.copyOf(articlesPL.values());
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
		return i > 0 && i < articlesPL.size() ? articlesPL.get(articles.get(i).getId()) : null;
	}

	@Override
	public Order getOrder(int i) {
		return i > 0 && i < orders.size() ? orders.get(i) : null;
	}

	@Override
	public DataAccess add(Order order) {
		orders.add(order);
		for (var oi : order.getItems()) {
			var a = articlesPL.get(oi.getArticle().getId());
			a.setUnitsInStore(a.getUnitsInStore() - oi.getUnitsOrdered());
		}
		return this;
	}

	@Override
	public void startup() {
		var a1 = articlesPL.get("SKU-458362");
		var a2 = articlesPL.get("SKU-693856");
		var a3 = articlesPL.get("SKU-518957");
		var a4 = articlesPL.get("SKU-638035");
		var a5 = articlesPL.get("SKU‐386035");
		var a6 = articlesPL.get("SKU‐443803");
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
