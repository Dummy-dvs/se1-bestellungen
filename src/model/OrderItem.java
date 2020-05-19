package model;

public class OrderItem {
	private String description;
	private final Article article;
	private int unitsOrdered;
	public OrderItem(String descr, Article article, int units) {
		description = descr;
		this.article = article;
		unitsOrdered = units;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Article getArticle() {
		return article;
	}
	public int getUnitsOrdered() {
		return unitsOrdered;
	}
	public void setUnitsOrdered(int unitsOrdered) {
		this.unitsOrdered = unitsOrdered;
	}
}
