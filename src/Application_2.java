import java.util.Date;
import java.util.List;

import model.Customer;
import model.Order;
import model.OrderItem;
import system.Builder;
import system.DataAccess;
import system.Logic;
import model.Article;


public class Application_2 {


	public static void main(String[] args) {
		System.out.println("Bestellsystem SE1 (v2).");

		Builder builder = Builder.getInstance();
		builder.startup();

		DataAccess data = builder.dataAccess();
		Logic logic = builder.logic();
		Customer cNadine = data.getCustomer(2);

		// build Nadine's 2nd order from existing data
		Order o7356 = new Order(7356613535L, new Date(), cNadine);
		Article teller = data.getArticle(3);
		o7356.addItem(    // 6 Teller
				new OrderItem(teller.getDescription() + " blau/weiss Keramik", teller, 6)
		);
		data.add(o7356);

		List<Order> orders = data.getOrders();
		logic.printOrders(orders);
		logic.printInventory();

		builder.shutdown();
	}

}
