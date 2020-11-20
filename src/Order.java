
public class Order {
	private String user;
	private String ticker;
	private int quantity;
	private double price;
	private boolean isBuy;
	
	Order(String user, String ticker, int quantity, double price , boolean isBuy) {
		this.user=user;
		this.ticker=ticker;
		this.quantity=quantity;
		this.price=price;
	}
	double getPrice() {
		return this.price;
	}
	int getQuantity() {
		return this.quantity;
	}
	void removeQuantity(int amount) {
		this.quantity = this.quantity - amount;
	}
}
