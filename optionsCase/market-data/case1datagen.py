import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as sp
import csv
import random
from numpy import arange, array, ones, linalg
from math import *


sk = [0.2,0.4,0.6,0.8,1] 

def makeSBM(dt, number_of_steps):
	x = 0
	t = 0
	SBM_series = [x]
	time_series = [t]
	for k in range(number_of_steps):
		t = t+dt
		x = x+np.sqrt(dt)*sp.norm.rvs()
		SBM_series.append(x)
		time_series.append(t)
	return time_series, SBM_series

def makeGBMHelper(SBM_series, time_series,mu, sigma2, start):
  GBM_series = []
  for i in range(0,len(SBM_series)):
  	s = start*np.exp((mu-sigma2/2.0)*time_series[i]+np.sqrt(sigma2/1.0)*SBM_series[i])
  	GBM_series.append(s)
  return time_series, GBM_series

def makeGBM(dt, number_of_steps, mu,sigma2,start):
	t,x = makeSBM(dt,number_of_steps)
	t,s = makeGBMHelper(x,t,mu,sigma2,start)
	return t,s

def CND(X):

	(a1,a2,a3,a4,a5) = (0.31938153, -0.356563782, 1.781477937, -1.821255978, 1.330274429)
	L = abs(X)
	K = 1.0 / (1.0 + 0.2316419 * L)
	w = 1.0 - 1.0 / sqrt(2*pi)*exp(-L*L/2.) * (a1*K + a2*K*K + a3*pow(K,3) + a4*pow(K,4) + a5*pow(K,5))

	if X<0:
		w = 1.0-w
	return w


def BlackSholes(X,v):

	S = 100.0
	T = 1.0
	r = 0.01

	d1 = (log(S/X)+(r+v*v/2.)*T)/(v*sqrt(T))

	d2 = d1-v*sqrt(T)

	return S*CND(d1)-X*exp(-r*T)*CND(d2)

def generate():

	s,t = makeGBM(1,100,0,0.05*0.05,0.3)
	rv = []
	limit = 100

	for i in range(limit):
		rand = random.random()

		row = []
		row.append("S")
		row.append(1000+i*5)
		signal = "broker;"

		if rand < 0.5:
			signal = signal + str(-1) + ";"
		else:
			signal = signal + str(1) + ";"

		if rand < sk[0]:
			signal = signal + str(80) + ";" + str(BlackSholes(80,t[i])) + ";"
			
		elif rand < sk[1]:
			signal = signal + str(90) + ";" + str(BlackSholes(90,t[i])) + ";"

		elif rand < sk[2]:
			signal = signal + str(100) + ";" + str(BlackSholes(100,t[i])) + ";"
		
		elif rand < sk[3]:
			signal = signal + str(110) + ";" + str(BlackSholes(110,t[i])) + ";"

		else:
			signal = signal + str(120) + ";" + str(BlackSholes(120,t[i])) + ";"
		
		signal = signal + str(t[i]);
		row.append(signal)
		rv.append(row)
	row = []
	row.append("S")
	row.append(1000+limit*5)
	row.append("end")
	rv.append(row)
	
	filename = '/Users/hwang15/Desktop/MW Trading Competition/2015/Case 1/Data/case1SampleData.csv'
	ofile = open( filename , "wb" )
	writer = csv.writer(ofile,delimiter=',',quotechar='|', quoting=csv.QUOTE_MINIMAL)
	#writer.writerow( ["direction", "vol", "strike", "price"] )
	[ writer.writerow( x ) for x in rv ]
	ofile.close()

	return rv
	











