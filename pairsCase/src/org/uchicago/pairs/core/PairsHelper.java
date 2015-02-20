package org.uchicago.pairs.core;

public class PairsHelper {

	// ------------- Ticker -----------------------------

	public enum Ticker {
		S1, S2, S3, S4, S5
	}

	// ------------- Quote -----------------------------

	public static class Quote {

		public final double bid;
		public final double offer;
		public final Ticker ticker;

		public Quote(Ticker ticker, double bid, double offer){
			this.bid = bid;
			this.offer = offer;
			this.ticker = ticker;
		}

		public String toString(){
			return "Ticker=" + ticker + ", bid=" + bid + ", offer=" + offer;
		}
	}

	// ------------- Order -----------------------------

	public enum OrderState {
		FILLED, REJECTED
	}
	
	public static class Order {
		
		Ticker symbol;
		OrderState state;
		
	}
	
	public static class OrderList {
		
	}

}
