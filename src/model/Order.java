package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Order {
	private final long id;
	private Date date;
	private final Customer customer;
	private final List<OrderItem> orderItemList;

	public Order(long id, Date date, Customer customer) {
		this.id = id;
		this.date = date;
		this.customer = customer;
		orderItemList = new ArrayList<>();
	}
	public long getId() {
		return id;
	}
	public Date getDate() {
		return date;
	}
	public Order setDate(Date date) {
		this.date = date;
		return this;
	}
	public Customer getCustomer() {
		return customer;
	}
	public List<OrderItem> getItems() {
		return Collections.unmodifiableList(orderItemList);
	}
	public Order addItem(OrderItem item) {
		orderItemList.add(item);
		return this;
	}
	public Order removeItem(OrderItem item) {
		orderItemList.remove(item);
		return this;
	}
}
