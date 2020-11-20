import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatchingEngine {
	OrderBook book = new OrderBook();
	Order order1=new Order("SeLlAtMaRkEtOpEn","GOOG",10,10.11,true);
	public void runThreads() {
		ExecutorService executor = Executors.newCachedThreadPool();
		// setting equal transaction amounts to check for concurrency
		for (int i=0;i<100;i++) {
			System.out.println("check");
			// executes task immediately
			executor.execute(new BuyTask());
			// executor.execute(new SellTask());
			
		}
		executor.shutdown();
		try {
			executor.awaitTermination(1000000,TimeUnit.SECONDS);
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		private class BuyTask implements Runnable{
			public void run() {
				synchronized(book) {
					book.processBuyOrder(order1);
				}
			}
		}

		private class SellTask implements Runnable{
			public void run() {
				synchronized(book) {
					book.processSellOrder(order1);
				}
			}
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatchingEngine demo = new MatchingEngine();
		demo.runThreads();
		System.out.println("buy");
		System.out.println(demo.book.getBuyOrders().size());
		System.out.println("sells");
		System.out.println(demo.book.sellOrders.size());
		return;

	}

}
