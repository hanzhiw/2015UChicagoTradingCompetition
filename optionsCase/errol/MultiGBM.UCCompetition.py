#########################################################
# Optionscity
# UChicago Midwest Trading Competition
# Multi-Dimensional Geometric Brownian Motion Simulation
#########################################################

import numpy as np
import math as m
import matplotlib.pyplot as plt

T  	= 2		# Number of seconds
dt    	= .01         	# Stepsize (as a fraction of a second)
N 	= int(round(T/dt))
S1   	= [0]*N				# Create a vector of N points for process 1
Z1  	= np.random.normal(0,1,N) 	# Standard normal Guassian rvs for process 1
S2   	= [0]*N				# Create a vector of N points for process 2
Z2  	= np.random.normal(0,1,N) 	# Standard normal Guassian rvs for process 2
time 	= [0]*N    
X1	= [0]*N
X2 	= [0]*N

# GBM params  
mu1  	= .004 		# Mean for process 1
mu2	= -0.002	# Mean for process 2
sig1 	= .02		# Variance of process 1
sig2	= .04		# Variance of process 2
rho	= .01		# Correlation of processes 1 and 2

# Cov matrix
sigma 	= np.matrix([[sig1**2,sig1*sig2*rho],[sig2**2,sig1*sig2*rho]])
sigma.rt= np.sqrt(sigma) 

X 	 = m.sqrt(float(dt))*sigma.rt*np.array([[Z1[0]],[Z2[0]]])+np.array([[mu1],[mu2]])*float(dt)
X1[0] 	 = X[0,0]
X2[0] 	 = X[1,0]
S1[0]    = 1945		# Set starting point for process 1     			
S2[0]    = 1967		# Set starting point for process 2
time[0]  = 0
 
# Generate the data
for i in range(1,N): 
    X = np.array([[X1[i-1]],[X2[i-1]]]) + m.sqrt(float(dt))*sigma.rt*np.array([[Z1[i]],[Z2[i]]])+np.array([[mu1],[mu2]])*float(dt)
    X1[i] 	 = X[0,0]	# Get BM for process 1
    X2[i] 	 = X[1,0]	# Get BM for process 2
    S1[i] = S1[0]*m.exp(X1[i])  # Get GBM for process 1
    S2[i] = S2[0]*m.exp(X2[i])  # Get GBM for process 2
    time[i] = time[i-1] + dt

# Plot the data
plt.plot(time,S1,'r',time,S2,'g')
plt.show()
