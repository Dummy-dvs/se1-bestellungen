package system;

import model.Order;
import model.OrderItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

class LogicImpl implements Logic {
	//VAT berechnung 19% + 1 aka 19 + 100
	private static final long VAT = 119;

	@Override
	public void printOrders(List<Order> orders) {
		long totalPrice = 0, totalVAT = 0;
		var sb = new StringBuilder();
		for (Order order : orders) {
			var id = order.getId();
			var name = order.getCustomer().getFirstName();
			sb.append(name).append("'s Bestellung #(").append(id).append("): ");
			for (var i = 0; i < order.getItems().size(); i++) {
				var item = order.getItems().get(i);
				sb.append(i > 0 ? ", " : "").append(item.getUnitsOrdered()).append("x ").append(item.getDescription());
			}
			sb.append(".\n");
			sb.append(" ==> ");
			for (var i = 0; i < order.getItems().size(); i++) {
				var item = order.getItems().get(i);
				sb.append(item.getUnitsOrdered()).append("x ").append(fmtPrice(item.getArticle().getUnitPrice())).append(i < order.getItems().size() - 1 ? ", " : "");
			}
			var total = calculateTotal(order);
			totalPrice += total;
			var vat = calculateIncludedVAT(order);
			totalVAT += vat;
			sb.append(" ==> Total: ").append(fmtPrice(total)).append(" (").append(fmtPrice(vat, "MWSt)\n"));
			System.out.println(sb.toString());
			sb.setLength(0);
		}
		System.out.println(sb.append(" == ==> Total (all orders): ").append(fmtPrice(totalPrice)).append(" (").append(fmtPrice(calcVAT(totalPrice), "MWSt)")));
	}

	@Override
	public void printInventory() {
		var da = Builder.getInstance().dataAccess();
		var articles = da.getArticles();
		int idlen = 0, desclen = 0, unitlen = 0, pricelen = 0, valuelen = 0;
		long totals = 0;
		for (var a : articles) {
			if (a.getId().length() > idlen)
				idlen = a.getId().length();
			if (a.getDescription().length() > desclen)
				desclen = a.getDescription().length();
			if (DFStock.format(a.getUnitsInStore()).length() > unitlen)
				unitlen = DFStock.format(a.getUnitsInStore()).length();
			var up = ((double) a.getUnitPrice()) / 100.0;
			if (DFMoney.format(up).length() > pricelen)
				pricelen = DFMoney.format(up).length();
			totals += a.getUnitPrice() * a.getUnitsInStore();
		}
		valuelen = fmtPrice(totals).length();
		int outputWidth = 0;
		System.out.println("\nAvailable Inventory:");
		String format = "%-" + idlen + "s, %-" + desclen + "s -->  %" + unitlen + "s units x %" + pricelen + "s, total value: %" + valuelen + "s";
		for (var a : articles) {
			var result = String.format(format, a.getId(), a.getDescription(), DFStock.format(a.getUnitsInStore()), DFMoney.format(((double) a.getUnitPrice()) / 100.0), fmtPrice(a.getUnitPrice() * a.getUnitsInStore()));
			System.out.println(result);
			outputWidth = result.length();
		}
		System.out.println(String.format("%" + outputWidth + "c", '-').replace(' ', '-'));
		System.out.printf("%" + outputWidth + "s\n", fmtPrice(totals));
	}
	private final DecimalFormat DFMoney = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("de")));
	private final DecimalFormat DFStock = new DecimalFormat("#,##0", new DecimalFormatSymbols(new Locale("de")));
	private String fmtPrice(long price) {return fmtPrice(price, "EUR");}
	private String fmtPrice(long price, String currency) {
		double total = ((double) price) / 100.0; // convert cent to Euro
		String fmtPrice = DFMoney.format(total);
		fmtPrice += (currency != null && currency.length() > 0) ? " " + currency : "";
		return fmtPrice;
	}
	@Override
	public long calculateTotal(Order order) {
		long ret = 0;
		for (var oi : order.getItems())
			ret += oi.getUnitsOrdered() * oi.getArticle().getUnitPrice();
		return ret;
	}

	private long calcVAT(long cost) {
		return cost - (cost * 100 / VAT);
	}

	@Override
	public long calculateIncludedVAT(Order order) {
		return calcVAT(calculateTotal(order));
	}

	@Override
	public void startup() {
		printInventory();
	}

	@Override
	public void shutdown() {

	}
}
