import com.optionscity.freeway.api.IDB;
import com.optionscity.freeway.api.IJobSetup;
import org.uchicago.pairs.core.AbstractPairsCase;
import org.uchicago.pairs.PairsHelper.OrderState;
import org.uchicago.pairs.PairsHelper.Order;
import org.uchicago.pairs.PairsHelper.Quote;
import org.uchicago.pairs.PairsHelper.Ticker;
import org.uchicago.pairs.core.PairsInterface;
import org.uchicago.pairs.PairsUtils;




public class PairsCaseSample extends AbstractPairsCase implements PairsInterface {

    private IDB myDatabase;

    //keeping track of the # of symbols for current round
    int numSymbols;
    //declare Order[] orders
    Order[] orders;
    //variables to store current price information
    double priceHuron, priceSuperior, priceMichigan, priceOntario, priceErie;


    @Override
    public void addVariables(IJobSetup setup) {
        setup.addVariable("Strategy", "Strategy to use", "string", "one");
    }

    @Override
    public void initializeAlgo(IDB dataBase) {
        String strategy = getStringVar("Strategy");
        if (strategy.contains("one")) {
            // do strategy one
        }
    }

    @Override
    public void currentSymbols(Ticker[] symbols) {
        String rv="";
        numSymbols = symbols.length;
        for (Ticker s : symbols){
            rv = rv + s.name() + " ";
        }
        log("The tickers available for this round is " + rv);
        //initiate Order[]
        orders = PairsUtils.initiateOrders(symbols);
    }

    @Override
    public Order[] getNewQuotes(Quote[] quotes) {

        if (numSymbols == 2) {
            priceHuron = quotes[0].bid;
            priceSuperior = quotes[1].bid;
            return roundOneStrategy(priceHuron, priceSuperior);

        } else if (numSymbols == 3){
            priceHuron = quotes[0].bid;
            priceSuperior = quotes[1].bid;
            priceMichigan = quotes[2].bid;
            return roundTwoStrategy(priceHuron, priceSuperior, priceMichigan);
        } else{
            priceHuron = quotes[0].bid;
            priceSuperior = quotes[1].bid;
            priceMichigan = quotes[2].bid;
            priceOntario = quotes[3].bid;
            priceErie = quotes[4].bid;
            return roundThreeStrategy(priceHuron, priceSuperior, priceMichigan, priceOntario, priceErie);
        }
    }

    //helper function that implements a dummy strategy for round 1
    public Order[] roundOneStrategy (double priceHuron, double priceSuperior){
        if (Math.abs(priceHuron - priceSuperior)> 5) {
            if (priceHuron > priceSuperior) {
                orders[0].quantity = -2;
                orders[1].quantity = 2;
            } else {
                orders[0].quantity = 2;
                orders[1].quantity = -2;
            }
            return orders;
        } else if (Math.abs(priceHuron - priceSuperior ) < 1){
            if (priceHuron > priceSuperior){
                orders[0].quantity = 2;
                orders[1].quantity = -2;
            } else {
                orders[0].quantity = -2;
                orders[1].quantity = 2;
            }
            return orders;
        } else {
            orders[0].quantity = 0;
            orders[1].quantity = 0;
        }
        return orders;
    }
    //helper function that implements a dummy strategy for round 2
    public Order[] roundTwoStrategy(double priceHuron, double priceSuperior, double priceMichigan) {
        if (Math.abs(priceHuron - priceSuperior) > 5) {
            if (priceHuron > priceSuperior) {
                orders[0].quantity = -2;
                orders[1].quantity = 2;
                orders[2].quantity = 1;
            } else {
                orders[0].quantity = 2;
                orders[1].quantity = -2;
                orders[2].quantity = -1;
            }
            return orders;
        } else if (Math.abs(priceHuron - priceSuperior) < 1) {
            if (priceHuron > priceSuperior) {
                orders[0].quantity = 2;
                orders[1].quantity = -2;
                orders[2].quantity = -1;
            } else {
                orders[0].quantity = -2;
                orders[1].quantity = 2;
                orders[2].quantity = 1;
            }
            return orders;
        } else {
            orders[0].quantity = 0;
            orders[1].quantity = 0;
            orders[2].quantity = 0;
        }
        return orders;
    }
    //helper function that implements a dummy strategy for round 2
    public Order[] roundThreeStrategy(double priceHuron, double priceSuperior, double priceMichigan, double priceOntario, double priceErie){
        return orders;
    }

    @Override
    public void ordersConfirmation(Order[] orders) {
        for (Order o : orders){
            if (o.state != OrderState.FILLED){
                if (o.state == OrderState.REJECTED){
                    log("My order for " + o.ticker + "is rejected, time to check my position/limit");
                }
            }else{
                log("My order for " + o.ticker + "is filled");
            }
        }
    }

    @Override
    public PairsInterface getImplementation() {
        return null;
    }
}
