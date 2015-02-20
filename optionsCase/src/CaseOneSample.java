import java.util.List;

import org.uchicago.options.OptionsHelpers.Quote;
import org.uchicago.options.OptionsHelpers.QuoteList;
import org.uchicago.options.core.AbstractCaseOne;
import org.uchicago.options.core.OptionsInterface;

import com.optionscity.freeway.api.IDB;
import com.optionscity.freeway.api.IJobSetup;



public class CaseOneSample extends AbstractCaseOne implements OptionsInterface {

	@Override
	public void addVariables(IJobSetup setup) {
		setup.addVariable("Strategy", "Strategy to use", "string", "one");
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
		
	}

	@Override
	public void noBrokerFills() {
	}

	@Override
	public void penaltyNotice(double amount) {
	}

	@Override
	public OptionsInterface getImplementation() {
		// TODO Auto-generated method stub
		return new CaseOneSample();
	}

	@Override
	public QuoteList getCurrentQuotes() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
