package org.uchicago.options.core;

import org.uchicago.options.OptionsSignals.BrokerOrder;
import org.uchicago.options.OptionsSignals.EndSignal;

import com.optionscity.freeway.api.messages.Signal;
import com.optionscity.freeway.api.services.IPlaybackService.ISignalProcessor;

public class OptionsSignalProcessor implements ISignalProcessor {

	/*
	 * Not necessary for the competition
	 */
	public String asString(Signal signal) {
		return null;
	}

	/*
	 * Used to go from data String in the playback file to a Signal for the platform
	 * 
	 * Quote Example: S,1000,quote;80;100;102
	 * End Example: S,1000,end
	 * Penalty Example; S,1000,penalty
	 * 
	 */
	public Signal fromString(String data) {
		String[] parts = data.split(";");
		if (parts[0].equals("broker")) {
			int strike = Integer.parseInt(parts[1]);
			double price = Integer.parseInt(parts[2]);
			int direction = Integer.parseInt(parts[3]);
			double vol = Integer.parseInt(parts[4]);
			BrokerOrder signal = new BrokerOrder(strike, price, direction, vol);
			return signal;
		}
		else if (parts[0].equals("end")) {
			return new EndSignal();
		}
		return null;
	}

}
