import org.uchicago.index.core.AbstractIndexCase;
import org.uchicago.index.core.IndexCase;

import com.optionscity.freeway.api.IDB;
import com.optionscity.freeway.api.IJobSetup;


public class IndexCaseSample extends AbstractIndexCase implements IndexCase {



    /* Personal latest copy of information */
    double[] my_portfolioWeights;


    private double[] evenlyDistributeWeightsAcrossTradables(boolean[] tradables){
		// Help function that evenly divide weight across every tradable asset

		// 1. Find how many assets we can hold
		int numberTradable = 0;
		for (int i=0; i<30; i++){
			if(tradables[i]){
				numberTradable++;
			}
		}

		// 2. Calculate even weight
		double evenWeight = 1.0/numberTradable;

		// 3. Allocate that weight to the assets we're allowed to trade
		double[] portfolioWeights = new double[30];
		for (int i=0; i<30; i++){
			if(tradables[i]){
				portfolioWeights[i] = evenWeight;
			}
		}

		// 4. Return portfolio weights
		return portfolioWeights;
    }

	@Override
	public double[] initalizePosition(double[] underlyingPrices, double indexValue, double[] trueWeights, boolean[] tradables) {
		// We just distribute portfolio weights evenly across every tradable asset
		my_portfolioWeights = evenlyDistributeWeightsAcrossTradables(tradables);

		return my_portfolioWeights;
	}

	@Override
	public double[] updatePosition(int currentTime, double[] underlyingPrices, double indexValue) {
		// This strategy ignores all price changes
		return my_portfolioWeights;
	}

	@Override
	public void regulationAnnouncement(int currentTime, int timeTakeEffect, boolean[] tradables) {
		// Update portfolio weights based on tradable assets
		my_portfolioWeights = evenlyDistributeWeightsAcrossTradables(tradables);
	}

	@Override
	public void penaltyNotification(int currentTime, boolean[] tradables) {
		// Do nothing - this also will not face any penalty
	}

	public IndexCase getImplementation() {
		return new IndexCaseSample();
	}

	@Override
	public void addVariables(IJobSetup setup) {
        // Registers a variable with the system.
        setup.addVariable("someFactor", "factor used to adjust something", "int", "2");
	}

	@Override
	public void initializeAlgo(IDB database) {		            
        // Databases can be used to store data between rounds
	}

}
