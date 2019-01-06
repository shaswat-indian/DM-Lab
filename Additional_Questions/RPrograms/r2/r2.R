#Hospital - Data Exploration and Visualization

#import the created csv file
data <- read.csv('hospital.csv')

#data exploration
str(data)
summary(data)
mean(data$height)
median(data$weight)

#data visualization
pie(table(data$gender))
hist(data$height)
