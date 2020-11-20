import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderBook {
		Vector<Order> buyOrders;
		Vector<Order> sellOrders;
		private static Lock lock = new ReentrantLock(true);
		private static Condition newTransaction= lock.newCondition();
		public OrderBook() {
			this.buyOrders = new Vector<Order>();
			this.sellOrders = new Vector<Order>();
		}
		public Vector<Order> getBuyOrders(){
			return buyOrders;
		}
		public Vector<Order> getSellOrders(){
			return buyOrders;
		}
		void processBuyOrder(Order order) {
			lock.lock();
			try {
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
			addBuyOrder(order);
			newTransaction.signalAll();
			}finally {
				lock.unlock();
			}
			
		}
		void processSellOrder(Order order) {
			lock.lock();
			try{
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
			newTransaction.signalAll();
			}
			finally {
				lock.unlock();
			}
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
		System.out.println("Test Run");
		Order order1=new Order("Alex","GOOG",10,10.11,true);
		Order order2=new Order("Blex","GOOG", 10,10.12,false);
		OrderBook b = new OrderBook();
		b.processBuyOrder(order1);
		b.processBuyOrder(order2);
		b.processBuyOrder(order2);
		b.processBuyOrder(order2);
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
