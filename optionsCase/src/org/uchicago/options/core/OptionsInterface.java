package org.uchicago.options.core;

import java.util.List;

import org.uchicago.options.OptionsHelpers.QuoteList;

import com.optionscity.freeway.api.IDB;
import com.optionscity.freeway.api.IJobSetup;

/*
 * Note: Track internal positions per strike
 * Use current vol to compare against ceiling vol
 * Assess penalty per broker order
 */

public interface OptionsInterface {
	
	public void addVariables(IJobSetup setup);
	
	public void initializeAlgo(IDB dataBase, List<String> instruments);
	
	public QuoteList getCurrentQuotes();
	
	public void newFill(int strike, int side, double price);
	
	public void noBrokerFills();
	
	public void penaltyNotice(double amount);

}
