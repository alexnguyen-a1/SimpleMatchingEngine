import java.util.Vector;

public class OrderBook {
		private Vector<Order> buyOrders;
		private Vector<Order> sellOrders;
		
		public OrderBook() {
			this.buyOrders = new Vector<Order>();
			this.sellOrders = new Vector<Order>();
		}
		
		private void processBuyOrder(Order order) {
			int n = sellOrders.size();
			if(n!=0) {
			if (sellOrders.lastElement().getPrice()<=order.getPrice()) {
			for (int index = this.sellOrders.size()-1;index>=0;index--) {
				Order currOrder= this.sellOrders.get(index);
				if (currOrder.getPrice()>order.getPrice()){
					break;
				}
				if (currOrder.getQuantity() > order.getQuantity()) {
					System.out.println("Process Buy Amount:" + order.getQuantity());
					currOrder.removeQuantity(order.getQuantity());
					sellOrders.setElementAt(currOrder,index);
					
				}
				if (currOrder.getQuantity() == order.getQuantity()) {
					sellOrders.removeElementAt(index);
					return;
					
				}
				if (currOrder.getQuantity()<order.getQuantity()) {
					order.removeQuantity(currOrder.getQuantity());
					sellOrders.removeElementAt(index);
					continue;
				}
			}
			}
			}
			System.out.println("Border");
			addBuyOrder(order);
		}
		private void processSellOrder(Order order) {
			int n = buyOrders.size();
			if(n!=0) {
			if (buyOrders.lastElement().getPrice()<=order.getPrice()) {
			for (int index = this.buyOrders.size()-1;index>=0;index--) {
				Order currOrder= this.buyOrders.get(index);
				if (currOrder.getPrice()>order.getPrice()){
					break;
				}
				if (currOrder.getQuantity() > order.getQuantity()) {
					System.out.println("Process Sale Amount:" + order.getQuantity());
					currOrder.removeQuantity(order.getQuantity());
					buyOrders.setElementAt(currOrder,index);
					
				}
				if (currOrder.getQuantity() == order.getQuantity()) {
					buyOrders.removeElementAt(index);
					return;
					
				}
				if (currOrder.getQuantity()<order.getQuantity()) {
					order.removeQuantity(currOrder.getQuantity());
					buyOrders.removeElementAt(index);
					continue;
				}
			}
			}
			}
			
			addSellOrder(order);
		}
		
		private void addBuyOrder(Order order) {
			if (this.buyOrders.size()==0){
				this.buyOrders.add(order);
			}
			for (int index = this.buyOrders.size()-1;index>=0;index--) {
				Order currOrder= this.buyOrders.get(index);
				if(currOrder.getPrice()<order.getPrice()) {
					if (index==this.buyOrders.size()-1){
						this.buyOrders.add(order);
					}
					else {
					this.buyOrders.insertElementAt(order, index+1);
					}
					break;
				}
			}
		}
		private void addSellOrder(Order order) {
			if (this.sellOrders.size()==0){
				this.sellOrders.add(order);
			}
			for (int index = this.sellOrders.size()-1;index>=0;index--) {
				Order currOrder= this.sellOrders.get(index);
				if(currOrder.getPrice()>order.getPrice()) {
					System.out.println("index:");
					System.out.println(index);
					if (index==this.sellOrders.size()-1){
						this.sellOrders.add(order);
					}
					else {
					this.sellOrders.insertElementAt(order, index+1);
					}
					break;
				}
			}
			System.out.println("check2");
		}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Order order1=new Order("Alex","GOOG",10,10.11,true);
		Order order2=new Order("Blex","GOOG", 10,10.12,false);
		OrderBook b = new OrderBook();
		b.processBuyOrder(order1);
		b.processSellOrder(order2);
		System.out.println(b.buyOrders.size());
		for (Object o : b.buyOrders) {
			System.out.print("buy");
		    System.out.print(o + " ");
		}
		for (Order o : b.sellOrders) {
			System.out.print("sell");
		    System.out.print(o + " ");
		}
		
		
	}



}
