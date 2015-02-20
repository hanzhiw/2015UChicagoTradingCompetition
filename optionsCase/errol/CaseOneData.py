####################################################
# Optionscity
# UChicago Midwest Trading Competition
# Case 1 Data Simulation
####################################################

import numpy as np
import math as m
import matplotlib.pyplot as plt


# PART 0: DEFINE REQUIRED FUNCTIONS
from scipy.stats import norm
from __future__ import division

def black_scholes_call(S,K,R,Time,Sigma):
	d1 = (m.log(S/K) + (R + .5*(Sigma**2))*Time)/(Sigma*m.sqrt(Time))
	d2 = d1 - Sigma*m.sqrt(Time)
	price = norm.cdf(d1)*S - norm.cdf(d2)*K*m.exp(-R*Time)
	return price


def black_scholes_put(S,K,R,Time,Sigma):
	d1 = (m.log(S/K) + (R + .5*(Sigma**2))*Time)/(Sigma*m.sqrt(Time))
	d2 = d1 - Sigma*m.sqrt(Time)
	price = norm.cdf(-d2)*K*m.exp(-R*Time) - norm.cdf(-d1)*S 
	return price

# PART 1: SET UP REQUIRED ARRAYS AND VARIABLES
# Variables used in generating data
T  	= 2							# Number of seconds
dt  = .001         				# Stepsize (as a fraction of a second)
N 	= int(round(T/dt))			# Number of steps, given T and dt
sig = [0]*N						# Create a vector of N points, initialized to 0
e 	= [0]*N						# Price factors
Z  	= np.random.normal(0,1,N) 	# Standard normal Guassian rvs for GBM
epsilon = np.random.normal(1,.05**2,N)	# Standard normal Guassian rvs for noise factors

strikes 	= np.array([80,90,100,110,120])	# List of potential strikes
directions  = np.array([-1,1])				# List of directions
broker_factors = np.array([.95,1.05])		# List of broker factors

# GBM params for vol process
mu  	= 0 	  	# Mean
vol     = .05   	# Stdev
sig[0]  = .3		# Initial value     			
time[0] = 0

# Black Scholes paramaters
r = .01					# Constant interest rate
X = 100					# Constant underlying
L = 1					# Constant time until expiration
startTime   = 1000      # Start time in milliseconds

# Paramaters used in data playback file
strike  = [0]*N				
direction = [0]*N
price 	= [0]*N
time 	= [0]*N
time[0] = startTime
 
# PART 2: GENERATE DATA
for i in range(0,N):
	if i > 0:
		sig[i] 	 = sig[i-1]*m.exp(vol*m.sqrt(float(dt))*Z[i]+mu*float(dt))
		time[i] = time[i-1] + dt*1000
	u = np.random.random_integers(0,1)
	strike[i] = strikes[np.random.random_integers(0,4)]
	direction[i] = directions[u]
	e[i] = broker_factors[u]
	price[i] = e[i]*epsilon[i]*black_scholes_call(X,strike[i],r,L,sig[i])

#Plot the data for inspection
plt.plot(time,sig)
plt.show()

import csv

# PART 3: WRITE DATA TO FILE
with open('data_broker_orders.csv','wb') as csvfile:
	for i in range(0,N):
		csvfile.write("S,"+str(int(time[i]))+",broker;"+str(strike[i])+";"
				  +str(round(price[i],2))+";"+str(direction[i])+"\n")
		csvfile.write("S,"+str(int(time[i]))+",volSignal;"+str(round(100*sig[i],2))+"\n")
