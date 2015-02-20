import csv


#requires raw data, this program only process 

def processCSV(round):
	
	processedStrings = []
	#with open('case2round1raw.csv', 'rb') as csvfile:
	with open('case2round'+str(round)+'raw.csv', 'rb') as csvfile:
		spamreader = csv.reader(csvfile, delimiter=' ', quotechar='|')
		for row in spamreader:
			processedStrings.append(row)

	splitStrings = []
	for r in processedStrings:
		splitStrings.append(r[0].split(","))
	
	returnStrings = []
	for s in splitStrings:
		temp = []
		row = []
		for smalls in s:			
			temp.append(float(smalls)-0.5)
			temp.append(float(smalls)+0.5)
		returnStrings.append(temp)
	return returnStrings
	#return returnStrings
	


def generate(round):



	splitStrings = processCSV(round)

	rangeLimit = 1000
	tradeLimit = 0 #the absolute limit imposed on participants 
	tradableTicker = ""

	S1 = "S1"
	S2 = "S2"
	S3 = "S3"
	S4 = "S4"
	S5 = "S5"

	delim = ";"

	row2 = [] 
	row2.append("S")
	row2.append(900)

	if round == 1:
		tradableTicker = "symbol;" + S1 + delim + S2 + delim
		row2.append("symbol;"+ S1 + delim + S2)
		tradeLimit = 40
	elif round == 2:
		tradableTicker = "symbol;" + S1 + delim + S2 + delim + S3 + delim
		row2.append("symbol;"+ S1 + delim + S2 + delim + S3)
		tradeLimit = 60
	elif round == 3:
		tradableTicker = "symbol;" + S1 + delim + S2 + delim + S3 + delim + S4 + delim + S5 + delim
		row2.append("symbol;"+ S1 + delim + S2 + delim + S3 + delim + S4 + delim + S5)
		tradeLimit = 100
	else:
		print "error"
		return 

	rv = []
	
	#initiate control signals 
	row1 = []
	row1.append("S")
	row1.append(800)
	row1.append("limit;"+str(tradeLimit))


	rv.append(row1)
	rv.append(row2)

	#initiate order signals 
	for i in range(rangeLimit):
		row = []
		row.append("S")
		row.append(1000+i*500)

		s=splitStrings[i]
		if round == 1:
			tempHolder = S1 + delim + str(s[0]) + delim + str(s[1]) + delim + S2 + delim + str(s[2]) + delim + str(s[3])
		elif round == 2:
			tempHolder = S1 + delim + str(s[0]) + delim + str(s[1]) + delim + S2 + delim + str(s[2]) + delim + str(s[3]) + delim + S3 + delim + str(s[4]) + delim + str(s[5])
		elif round == 3: 
			tempHolder = S1 + delim + str(s[0]) + delim + str(s[1]) + delim + S2 + delim + str(s[2]) + delim + str(s[3]) + delim + S3 + delim + str(s[4]) + delim + str(s[5]) + delim + S4 + delim + str(s[6]) + delim + str(s[7]) + delim + S5 + delim + str(s[8]) + delim + str(s[9])
		else:
			print "erro"
			return 
		row.append(tempHolder)
		rv.append(row)

	row = [] 
	row.append("S")
	row.append(1000+rangeLimit*500)
	row.append("end")
	rv.append(row)

	filename = '/Users/hwang15/Desktop/MW Trading Competition/2015/Case 2/Data/SampleData/case2round'+str(round)+'SampleData.csv'
	ofile = open( filename , "wb" )
	writer = csv.writer(ofile,delimiter=',',quotechar='|', quoting=csv.QUOTE_MINIMAL)
	#writer.writerow( ["direction", "vol", "strike", "price"] )
	[ writer.writerow( x ) for x in rv ]
	ofile.close()

	return 
