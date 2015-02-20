package org.uchicago.pairs.core;

import org.uchicago.pairs.core.PairsHelper.OrderList;
import org.uchicago.pairs.core.PairsHelper.Quote;

import com.optionscity.freeway.api.Order;

public interface PairsInterface{
	
		// Build in max limit for long and short - likely 50-100

		/*
		 * This method is called when new quotes for the stocks are distributed. 
		 * New price information will be passed on via the quotes argument. 
		 * You implementation should return an array of orders indicating your actions for each of the stocks.  
		 * 1 for buy, -1 for sell and 0 for inaction. 
		 */
		public void getNewQuotes(Quote[] quotes, OrderList orders);

		/*
		 * This function is invoked to confirm that the orders you submitted were filled. 
		 * The reason that this second function is needed is due to the case that the order
		 * of securities in the returned array is different from the Quote the system sends out.
		 */
		public void orderUpdate(Order[] orders); // should 
				
		public void currentSymbols(String[] symbols);

		// Tell them that order ignored because they weren't flat
		//public void ordersIgnored(Order[] orders);

}
