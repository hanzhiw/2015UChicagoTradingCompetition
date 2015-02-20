package org.uchicago.options;

public class OptionsMathUtils {
	
	private static double pi = 3.1415926535;
	private static double S = 100.0;
	private static double T = 1.0;
	private static double r = 0.01;


	private static double getCumulativeNormalDistribution(double X){
		double a1 = 0.31938153;
		double a2 = -0.356563782;
		double a3 = 1.781477937;
		double a4 = -1.821255978;
		double a5 = 1.330274429;
		double L = Math.abs(X);
		double K = 1.0 / (1.0 + 0.2316419 * L);
		double w = 1.0 - 1.0 / Math.sqrt(2*pi)*Math.exp(-L*L/2.) * (a1*K + a2*K*K + a3*Math.pow(K,3) + a4*Math.pow(K,4) + a5*Math.pow(K,5));
		if (X<0){
			w = 1.0-w;
		}
		return w;
	}

	/**
	 * Uses Black Scholes
	 */
	public static double theoValue(double strike, double vol){

	    double d1 = (Math.log(S/strike)+(r+vol*vol/2.)*T)/(vol*Math.sqrt(T));
	    double d2 = d1-vol*Math.sqrt(T);

		return S * getCumulativeNormalDistribution(d1)-strike*Math.exp(-r*T)*getCumulativeNormalDistribution(d2);	 
	}

	/*
	 * Calculate vega of strike based on the specified vol
	 */
	public static double cal_vega(double strike, double vol){
		double d1 = (Math.log(S/strike)+(r+vol*vol/2.)*T)/(vol*Math.sqrt(T));
		double vega = (1/Math.sqrt(2*pi))*Math.exp(-(d1)*(d1)/2)*S*Math.sqrt(T)/100;
		return vega;
	}

}
