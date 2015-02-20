package org.uchicago.options;

public class OptionsHelpers {

	
	public static class Quote {	
		public final int strike;
		public final double bid;
		public final double offer;
		
		public Quote(int strike, double bid, double offer) {
			this.strike = strike;
			this.offer = offer;
			this.bid = bid;
		}
	}
	
	
	public static class QuoteList {
		
		public final Quote quoteEighty; // 100
		public final Quote quoteNinety;
		public final Quote quoteHundred;
		public final Quote QuoteHundredTen;
		public final Quote quoteHundredTwenty;
		
		public QuoteList(Quote quoteEighty, Quote quoteNinety, Quote quoteHundred, Quote QuoteHundredTen, Quote quoteHundredTwenty) {
			this.quoteEighty = quoteEighty;
			this.quoteNinety = quoteNinety;
			this.quoteHundred = quoteHundred;
			this.QuoteHundredTen = QuoteHundredTen;
			this.quoteHundredTwenty = quoteHundredTwenty;
		}
		
		public Quote getQuote(int strike) {
			return null;
		}
		
	}

}

