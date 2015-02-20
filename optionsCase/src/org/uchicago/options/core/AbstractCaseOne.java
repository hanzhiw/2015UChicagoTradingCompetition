package org.uchicago.options.core;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.uchicago.options.OptionsHelpers.Quote;
import org.uchicago.options.OptionsHelpers.QuoteList;
import org.uchicago.options.OptionsSignals.BrokerOrder;
import org.uchicago.options.OptionsSignals.EndSignal;
import org.uchicago.options.utils.InstrumentUtilities;
import org.uchicago.options.utils.InstrumentUtilities.Case;
import org.uchicago.options.utils.TeamUtilities;

import com.optionscity.freeway.api.AbstractJob;
import com.optionscity.freeway.api.IContainer;
import com.optionscity.freeway.api.IDB;
import com.optionscity.freeway.api.IGrid;
import com.optionscity.freeway.api.IJobSetup;
import com.optionscity.freeway.api.Prices;


public abstract class AbstractCaseOne extends AbstractJob {
	
	private OptionsInterface impl;
	private IDB db;
	DecimalFormat df = new DecimalFormat("##.##");
	
	private List<String> optionList = new ArrayList<String>();
	private Map<String, Integer> positionMap = new ConcurrentHashMap<String, Integer>();
	private List<TradeInfo> trades = new ArrayList<TradeInfo>();
	private List<TradeInfo> penalties = new ArrayList<TradeInfo>();
	private static IDB tradesDB;
	private static IDB pnlDB;
	private IGrid stats;
	private IGrid market;
	private IGrid limitGrid;
	private IGrid forecastGrid;
	private IGrid dataGrid;
	private String teamCode;
	
	private IDB teamDB;
	
	
	class TradeInfo {
		
		final String idSymbol;
		final int position;
		final double price;
		
		private TradeInfo(String idSymbol, int position, double price) {
			this.idSymbol = idSymbol;
			this.position = position;
			this.price = price;
		}
		
	}
	
	protected double calculatePNL() {
		return calculatePNL(trades);
	}

	private double calculatePNL(List<TradeInfo> tradeSource) {
		Prices snow = instruments().getAllPrices("SNOW-E");
		Prices robot = instruments().getAllPrices("ROBOT-E");
		double bidAverage = ((snow.bid + robot.bid) / 2.0);
		double askAverage = ((snow.ask + robot.ask) / 2.0);
		double settlement = ((bidAverage + askAverage) / 2.0);
		double pnl = 0;
		for (TradeInfo trade : tradeSource) {
			double cost = trade.position * trade.price;
			double value = trade.position * settlement;
			pnl += value - cost;
		}
		return pnl;
	}
	
	public void install(IJobSetup setup) {
		getImplementation().addVariables(setup);
	}

	public void begin(IContainer container) {
		super.begin(container);
	
		teamCode = getStringVar("Team_Code");

		if (teamCode.isEmpty())
			container.failJob("Please set a Team_Code in the configuration");
		if (!TeamUtilities.validateTeamCode(teamCode))
			container.failJob("The specified Team Code is not a valid code.  Please enter the code provided to your team.");
		
		log("Team Code is, " + teamCode);
		
		List<String> products = InstrumentUtilities.getSymbolsForTeamByCase(Case.OPTIONS, teamCode);
		for (String product : products) {
			instruments().startSymbol(product);
			container.filterMarketMessages(product + ";;;;;;");
			log("filtering for " + product);
		}
		
		List<String> optionList = new ArrayList<String>();
		products = InstrumentUtilities.getOptionsForCase(Case.OPTIONS);
		for (String option : products) {
			if (option.contains(teamCode)) {
				optionList.add(option);
			}
		}
		
	
		
		container.subscribeToSignals();
		container.getPlaybackService().register(new OptionsSignalProcessor());
		
		impl = getImplementation();
		log("CaseOne implementation detected to be " + impl.getClass().getSimpleName());
		
		teamDB = container.getDB(teamCode);
		impl.initializeAlgo(db, optionList);
	}
	
	public void onSignal(BrokerOrder signal) {
		QuoteList quote = impl.getCurrentQuotes();
		
		int strike = signal.strike;
		Quote strikeQuote = quote.getQuote(strike);
	}
	
	
	public void onSignal(EndSignal msg) {
		
	}

	public abstract OptionsInterface getImplementation();

}
