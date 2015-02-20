package org.uchicago.options;

import com.optionscity.freeway.api.messages.Signal;

public class OptionsSignals {
	
	public static class EndSignal extends Signal {	
		public EndSignal() {
			super(EndSignal.class.getSimpleName());
		}	
	}
	
	public static class BrokerOrder extends Signal {
		
		public final int direction;
		public final double price;
		public final int strike;
		public final double vol;
		
		public BrokerOrder(int strike, double price, int direction, double vol) {
			this.strike = strike;
			this.price = price;
			this.direction = direction;
			this.vol = vol;
		}
	}

}