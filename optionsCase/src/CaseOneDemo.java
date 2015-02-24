import java.util.List;

import org.uchicago.options.OptionsHelpers.Quote;
import org.uchicago.options.OptionsHelpers.QuoteList;
import org.uchicago.options.OptionsMathUtils;
import org.uchicago.options.core.AbstractCaseOne;
import org.uchicago.options.core.OptionsInterface;

import com.optionscity.freeway.api.IDB;
import com.optionscity.freeway.api.IJobSetup;



public class CaseOneDemo extends AbstractCaseOne implements OptionsInterface {

    private IDB myDatabase;

    // Note that this implementation uses price of 100 call for ALL options
<<<<<<< HEAD:optionsCase/src/CaseOneSample.java
    double currentBid = case1util.BlackSholes(100, 0.3)-1;
    double currentAsk = CaseOneUtil.BlackSholes(100, 0.3)+1;
=======
    double currentBid = OptionsMathUtils.theoValue(100, 0.3)-1;
    double currentAsk = OptionsMathUtils.theoValue(100, 0.3)+1;
>>>>>>> 0e54d6c889bd820f5884f65827d57ba91330240e:optionsCase/src/CaseOneDemo.java

    @Override
	public void addVariables(IJobSetup setup) {
    	setup.addVariable("TeamCode", "Team code given to you", "string", "");
        setup.addVariable("factor", "Some factor", "double", "0.2");
	}

	@Override
	public void initializeAlgo(IDB dataBase, List<String> instruments) {
		String strategy = getStringVar("Strategy");
		if (strategy.contains("one")) {
			// do strategy one
		}
	}
	
	@Override
	public void newFill(int strike, int side, double price) {
		// TODO Auto-generated method stub
<<<<<<< HEAD:optionsCase/src/CaseOneSample.java
        log("My logic received a quote Fill, price=" + price + ", strike=" + strike + ", direction=" + side);
=======
        //log("My logic received a quote Fill, price=" + price + ", strike=" + strike + ", direction=" + side);
>>>>>>> 0e54d6c889bd820f5884f65827d57ba91330240e:optionsCase/src/CaseOneDemo.java
        currentBid -= 1;
        currentAsk += 1;
	}

    @Override
    public QuoteList getCurrentQuotes(){
        // TODO Auto-generated method stub
        //log("My Case1 implementation received a request for current quotes");
        Quote quoteEighty = new Quote(80, currentBid,currentAsk);
        Quote quoteNinety = new Quote(90, currentBid,currentAsk);
        Quote quoteHundred = new Quote(100, currentBid,currentAsk);
        Quote quoteHundredTen = new Quote(110, currentBid,currentAsk);
        Quote quoteHundredTwenty = new Quote(120, currentBid,currentAsk);

        return new QuoteList(quoteEighty,quoteNinety,quoteHundred,quoteHundredTen,quoteHundredTwenty);
    }

	@Override
	public void noBrokerFills() {

	}


	@Override
	public void penaltyNotice(double amount) {
		// TODO Auto-generated method stub
        log("Penalty received in the amount of " + amount);
    }


	@Override
	public OptionsInterface getImplementation() {
		// TODO Auto-generated method stub
<<<<<<< HEAD:optionsCase/src/CaseOneSample.java
		return new CaseOneSample();
	}

=======
		return new CaseOneDemo();
	}
>>>>>>> 0e54d6c889bd820f5884f65827d57ba91330240e:optionsCase/src/CaseOneDemo.java

	
}
