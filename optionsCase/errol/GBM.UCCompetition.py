####################################################
# Optionscity
# UChicago Midwest Trading Competition
# 1D Geometric Brownian Motion Simulation
####################################################

import numpy as np
import math as m
import matplotlib.pyplot as plt

T  	= 2		# Number of seconds
dt    	= .01         	# Stepsize (as a fraction of a second)
N 	= int(round(T/dt))
S   	= [0]*N				# Create a vector of N points, initialized to 0
Z  	= np.random.normal(0,1,N) 	# Standard normal Guassian rvs
time 	= [0]*N    

# GBM params for midpoint
mu  	= .01   	# Mean
vol     = .02   	# Stdev
S[0]    = 1950		# Set starting point     			
time[0] = 0
 
# Generate the data
for i in range(1,N): 
    S[i] = S[i-1]*m.exp(vol*m.sqrt(float(dt))*Z[i]+mu*float(dt))
    time[i] = time[i-1] + dt

# Plot the data
plt.plot(time,S)
plt.show()
