package sampleCase;

import java.util.List;

/*
 * This class represents a simple attempt at taking the case documentation and
 * translating it to a "code contract".  This contract in Java terms is called an
 * interface.  We will force the students to implement this interface so that their
 * expectations of what is required from a coding perspective is very straight-forward.
 * 
 * See the sample implementation for more details
 */
public interface SampleCaseInterface {
	
		/*
		 * This is called when the algo is started to let the implementation
		 * know what instruments they can expect to use.  They can then use
		 * this method to initialize any data structure they'll be using
		 */
		void defineInstruments(String[] allSymbols);
		
		/*
		 * This is called when there is a new bid and offer on an instrument.
		 * The instrument is defined by the idSymbok, and the quote is represented
		 * by the bid and ask arguments
		 */
		void newBidAsk(String idSymbol, double bid, double ask);
		
		/*
		 * This is called to confirm an order this algo placed was filled.  The idSymbol
		 * specified the instrument the fill notification is for.  The quantity
		 * and fillPrice represent the corresponding values when crossed with the market.
		 */
		void orderFilled(String idSymbol, int quantity, double fillPrice);
		
		/*
		 * This method is called periodically to give the implementation a chance to place
		 * their new quotes into the market.  The point is to place orders based on your theoretical
		 * value, assuming the market will revert to what you're expecting if you notice a distortion
		 * in the market when compared to your theoretical values.
		 */
		List<OrderInfo> placeOrders();

}
