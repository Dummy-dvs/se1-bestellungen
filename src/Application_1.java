import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Article;
import model.Customer;
import model.Order;
import model.OrderItem;

public class Application_1 {

	public static void main(String[] args) {
		System.out.println("Bestellsystem SE1.");

		Customer cEric = new Customer("C86516", "Eric Schulz-Mueller", "eric2346@gmail.com");
		Customer cAnne = new Customer("C64327", "Meyer, Anne", "+4917223524");
		Customer cNadine = new Customer("C12396", "Nadine Ulla Blumenfeld", "+4915292454");

		Article a1 = new Article("SKU-458362", "Tasse", 299, 2000);
		Article a2 = new Article("SKU-693856", "Becher", 149, 8400);
		Article a3 = new Article("SKU-518957", "Kanne", 2000, 2400);
		Article a4 = new Article("SKU-638035", "Teller", 649, 7000);

		// Eric's 1st order
		Order o5234 = new Order(5234968294L, new Date(), cEric);
		OrderItem oi1 = new OrderItem(a3.getDescription(), a3, 1);    // 1 Kanne (a3)
		o5234.addItem(oi1);    // add OrderItem to Order 5234968294L

		// Eric's 2nd order
		Order o8592 = new Order(8592356245L, new Date(), cEric);
		o8592.addItem(    // add three OrderItems to Order 8592356245L
				new OrderItem(a4.getDescription(), a4, 4)        // 4 Teller (a4)
		).addItem(new OrderItem(a2.getDescription(), a2, 8)        // 8 Becher (a2)
		).addItem(new OrderItem("passende Tassen", a1, 4)        // 4 passende Tassen (a1)
		);

		// Anne's order
		Order o3563 = new Order(3563561357L, new Date(), cAnne);
		o3563.addItem(new OrderItem(a3.getDescription() + " aus Porzellan", a3, 1));

		// Nadine's order
		Order o6135 = new Order(6135735635L, new Date(), cNadine);
		o6135.addItem(    // 12 Teller
				new OrderItem(a4.getDescription() + " blau/weiss Keramik", a4, 12));

		// ADDED ORDERS AND CUSTOMERS:
		var timo = new Customer("C0xC0FFEE","Timo Werner", "tw@gmail.com");
		var sandra = new Customer("C666","Sandra Müller", "samue62@gmail.com");
		var kaffemaschine = new Article("Mr Coffee", "Kaffemaschine", 2999, 500);
		var teekocher = new Article("Mr Tea", "Teekocher", 1999, 2000);
		var wernerOrder = new Order(9235856245L, new Date(), timo).addItem(new OrderItem(kaffemaschine.getDescription(), kaffemaschine, 1)).addItem(new OrderItem(a1.getDescription(), a1, 6));
		var muellerOrder = new Order(7335856245L, new Date(), sandra).addItem(new OrderItem(teekocher.getDescription(), teekocher, 1)).addItem(new OrderItem(a2.getDescription(), a2, 4)).addItem(new OrderItem(a4.getDescription(), a4, 4));
		List<Order> orders = new ArrayList<Order>(List.of(o5234, o8592, o3563, o6135,wernerOrder,muellerOrder));

		printOrders(orders);
	}

	private static void printOrders(List<Order> orders) {
		for (Order order : orders) {
			long id = order.getId();
			String name = order.getCustomer().getFirstName();
			StringBuffer sb = new StringBuffer();    // unwind ordered articles
			sb.append(name).append("'s Bestellung #(").append(id).append("): ");
			for (int i = 0; i < order.getItems().size(); i++) {
				OrderItem item = order.getItems().get(i);
				sb.append(i > 0 ? ", " : "").append(item.getUnitsOrdered()).append("x ").append(item.getDescription());
			}
			System.out.println(sb.append(".").toString());
		}
	}
}
