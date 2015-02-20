package org.uchicago.index.core;

import org.uchicago.index.core.Case3Objects.PortfolioWeights;
import org.uchicago.index.core.Case3Objects.TradableSet;

public interface IndexCase {

	public PortfolioWeights InitializePosition(double[] underlyingPrices, double indexValue, TradableSet tradables);
	
	public PortfolioWeights PositionUpdates(int t, double[] underlyingPrices, double indexValue);
	
	public void RegulationChangeAnnouncement(int t, TradableSet tradableStocks);
	
	
}
