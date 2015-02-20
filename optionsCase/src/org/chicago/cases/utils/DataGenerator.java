package org.chicago.cases.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.math3.analysis.function.Exp;
import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.analysis.function.Sqrt;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.util.Precision;


public class DataGenerator {	
	
	public final static int[] strikes 				= {80,90,100,110,120};
	public final static int[] directions 			= {-1,1};
	public final static double[] brokerFactors 	= {.95,1.05};
	
	// GBM params for vol process
	public static double mu  	= 0; 	  	// Mean
	public static double vol  = .05;   	// Stdev
	
	// Initial values
	public final static double 	SIG_0  = 0.3;
	public final static int 	TIME_0 = 0;
	public final static int 	startTime   = 1000; // Start time in milliseconds
	
	// Black Scholes Params
	public static double r = .01;			// Constant interest rate
	public static double X = 100;			// Constant underlying
	public static double L = 1;			// Constant time until expiration
	
	
	// MD Params
	public static double T = 2;
	public static double dt = .001;
	
	// Arrays representing each process
	public static double[] 	sig;			// Vol process
	public static double[] 	e;				// Price factors
	public static double[] 	z;  			// Standard normal Guassian rvs for GBM
	public static double[] 	epsilon; 		//Standard normal Guassian rvs for noise factors
	public static double[] 	time;
	public static int[] 	strike;		
	public static int[]		direction;
	public static double[] 	price;
	

	// Black Scholes' price of a European call option
	public static double bsCallPrice(double underlying, double strike, double rate, double vol, double expTime){
		
		NormalDistribution N = new NormalDistribution(); // standard gaussian
		Exp e  = new Exp();
		Sqrt s = new Sqrt();
		
		double d1 = calculateD1(underlying,strike,rate,vol,expTime);
		double d2 = d1 - vol*s.value(expTime);
		
		return N.cumulativeProbability(d1)*underlying-N.cumulativeProbability(d2)*strike*e.value(-rate*expTime);		
	}
	
	// Helper function to calculate d1
	private static double calculateD1(double underlying, double strike, double rate, double vol, double expTime){
		Log l = new Log();
		Sqrt s = new Sqrt();
		
		return (l.value(underlying/strike)+(rate+vol*vol/2)*expTime)/s.value(vol*vol*expTime);		
	}		
	
	public static void main(String[] args) {
		
		Exp ex 	= new Exp();
		Sqrt s 	= new Sqrt();
		UniformIntegerDistribution u = new UniformIntegerDistribution(0,1);
		NormalDistribution n = new NormalDistribution();
		NormalDistribution m = new NormalDistribution(1,.05);
				
		int size 	= ((int) (T/dt));		
		sig 		= new double[size];				
		e   		= new double[size];				
		z			= new double[size];  			
		epsilon 	= new double[size];
		time 		= new double[size];
		
		price 		= new double[size];
		strike 		= new int[size];
		direction 	= new int[size];
		
		sig[0] = SIG_0;
		time[0] = TIME_0;
		
		// Generate data
		for(int i =0; i < size; ++i){			
			
			int uniform = u.sample();
			strike[i] = strikes[(new UniformIntegerDistribution(0,4)).sample()];
			direction[i] = directions[uniform];
			e[i] = brokerFactors[uniform];
			z[i] = n.sample();			
			epsilon[i] = m.sample();			
			
			if(i>0){				
				sig[i] = sig[i-1]*ex.value(vol*s.value(dt)*z[i]+mu*dt);
				time[i] = time[i-1] + dt*1000000;
			}
			
			//price[i] = e[i] * epsilon[i] * bsCallPrice(X,strike[i],r,0,sig[i],L);
			price[i] = bsCallPrice(X,strike[i],r,sig[i],L);
		}	
		
		// Write Data
		try {			
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/ekutan/code/uchicago2015/case1/market-data/data_broker_orders.csv")));

			for(int i = 0; i < size; ++i){
				//System.out.println("S,"+time[i]+",broker;"+strike[i]+";"+Precision.round(price[i],2)+";"+direction[i]+";");
				bw.write("S,"+time[i]+",broker;"+strike[i]+";"+Precision.round(price[i],2)+";"+direction[i]+";"+e[i]+";"+epsilon[i]);
				bw.newLine();				
				bw.write("S,"+time[i]+",volSignal;"+Precision.round(100*sig[i],2));
				bw.newLine();					
			}		
			
			bw.flush();
			bw.close();
		}
		catch (Throwable e) {
			e.printStackTrace();
		}			
	}
}
