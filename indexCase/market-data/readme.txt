In the folder for each round, you will find the following files:
- capWeights.csv
	30x1: The true portfolio weights of the index. These sum to 1.

- prices.csv
	10000x31: The prices for each of the 30 assets for 10,000 ticks, as well
			  as the value of the index.

- tradable_init.csv
	30x1: Whether each asset is tradable at the start of the round.

- tradable_changes.csv
	Cx3: An example of the regulatory changes that occur over the course of
	     the round. The first column is the time, the second column is the 
	     asset, and the third is it's new tradable/untradable status.