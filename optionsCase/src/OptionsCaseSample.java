import java.util.List;

import org.uchicago.options.OptionsHelpers.Quote;
import org.uchicago.options.OptionsHelpers.QuoteList;
import org.uchicago.options.OptionsMathUtils;
import org.uchicago.options.core.AbstractOptionsCase;
import org.uchicago.options.core.OptionsInterface;

import com.optionscity.freeway.api.IDB;
import com.optionscity.freeway.api.IJobSetup;



public class OptionsCaseSample extends AbstractOptionsCase implements OptionsInterface {

    private IDB myDatabase;

    // Note that this implementation uses price of 100 call for ALL options
    double currentBid = OptionsMathUtils.theoValue(100, 0.3)-1;
    double currentAsk = OptionsMathUtils.theoValue(100, 0.3)+1;

    @Override
	public void addVariables(IJobSetup setup) {
        setup.addVariable("Strategy", "Strategy to use", "string", "one");
	}

	@Override
	public void initializeAlgo(IDB dataBase, List<String> instruments) {
		// TODO Auto-generated method stub
		// blah
		String strategy = getStringVar("Strategy");
		if (strategy.contains("one")) {
			// do strategy one
		}
	}
	
	@Override
	public void newFill(int strike, int side, double price) {
		// TODO Auto-generated method stub
        log("My logic received a quote Fill, price=" + price + ", strike=" + strike + ", direction=" + side);
        currentBid -= 1;
        currentAsk += 1;
	}

    @Override
    public QuoteList getCurrentQuotes(){
        // TODO Auto-generated method stub
        log("My Case1 implementation received a request for current quotes");
        Quote quoteEighty = new Quote(80, currentBid,currentAsk);
        Quote quoteNinety = new Quote(90, currentBid,currentAsk);
        Quote quoteHundred = new Quote(100, currentBid,currentAsk);
        Quote quoteHundredTen = new Quote(110, currentBid,currentAsk);
        Quote quoteHundredTwenty = new Quote(120, currentBid,currentAsk);

        return new QuoteList(quoteEighty,quoteNinety,quoteHundred,quoteHundredTen,quoteHundredTwenty);
    }

	@Override
	public void noBrokerFills() {
		// TODO Auto-generated method stub
        log("No match against broker the broker orders...time to adjust some levers?");
    }

	@Override
	public void penaltyNotice(double amount) {
		// TODO Auto-generated method stub
        log("Penalty received in the amount of " + amount);
    }

	@Override
	public OptionsInterface getImplementation() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
