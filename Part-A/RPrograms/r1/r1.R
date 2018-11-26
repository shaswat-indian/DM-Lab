
#importing the 'iris' dataset
data <- iris	

#exporting to CSV file
write.csv(data,'iris.csv')	

#clearing the 'data' variable
rm(data)	

#reading from CSV file
data <- read.csv('iris.csv')	

#printing the read data
data	
