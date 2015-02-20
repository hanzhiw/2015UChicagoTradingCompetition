package org.uchicago.index.core;

public class Case3Objects {
	// ------------- PortfolioWeights -----------------------------

	public static class PortfolioWeights {

		public final double[] weights;

		public PortfolioWeights(double[] weights){
			this.weights = weights;
			double totalWeight = this.sum();
			for(int i=0; i<30; i += 1){
				this.weights[i]/=totalWeight;
			}
		}

		public double sum(){
			double totalWeight = 0.0;
			for(double weight: this.weights){
				totalWeight += weight;
			}
			return totalWeight;
		}

		public String toString(){
			String printOut = "PortfolioWeights:\n";
			for(int i=0; i < 30; i += 1){
				printOut += "  " + i +": " + this.weights[i];
			}
			return printOut;
		}
	}

	
	// ------------- TradableSet -----------------------------
	public static class TradableSet {

		public final boolean[] tradables;

		public TradableSet(boolean[] tradables){
			this.tradables = tradables;
		}

		public double numberTradable(){
			double numberTradable = 0.0;
			for (boolean tradable: this.tradables){
				if(tradable){
					numberTradable += 1;
				}
			}
			return numberTradable;
		}

		public String toString(){
			String printOut = "Tradables:\n";
			for(int i=0; i<30; i += 1){
				printOut += "  " + i +": " + this.tradables[i];
			}
			return printOut;
		}
	}

}
