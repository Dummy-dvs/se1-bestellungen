package model;

public class Article {
	private String id;
	private String description;
	private long unitPrice;
	private int unitsInStore;

	public Article(String id, String desc, long price, int units) {
		setId(id);
		setDescription(desc);
		setUnitPrice(price);
		setUnitsInStore(units);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? "" : description;
	}

	public long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(long unitPrice) {
		this.unitPrice = unitPrice < 0 ? 0 : unitPrice;
	}

	public int getUnitsInStore() {
		return unitsInStore;
	}

	public void setUnitsInStore(int unitsInStore) {
		this.unitsInStore = Math.max(unitsInStore, 0);
	}
}
