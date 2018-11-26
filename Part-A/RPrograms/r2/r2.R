
#load the Iris dataset
data <- iris

#exploring the dataset
#structure of the dataset
str(data)

#summary of the dataset
summary(data)

#rows from beginning of the dataset
head(data)

#rows from end of the dataset
tail(data)

#mean of Sepal.Length
mean(data$Sepal.Length)

#median of Sepal.Width
median(data$Sepal.Width)

#range of Petal.Length
range(data$Petal.Length)

#variance of Petal.Width
var(data$Petal.Width)

#finding quantiles for Sepal.Length
quantile(data$Sepal.Length)

#tabular representation of number of species
table(data$Species)

#covariance between Sepal.Length and Petal.Length
cov(data$Sepal.Length,data$Petal.Length)

#correlation between Sepal.Length and Petal.Length
cor(data$Sepal.Length,data$Petal.Length)



#visulaizing the dataset
#histogram of Sepal.Lentgh
hist(data$Sepal.Length)

#curve indicating Petal.Width density
plot(density(data$Petal.Width))

#pie-chart of number of species
pie(table(data$Species), main="Number of Species Pie-Chart")

#bar-graph of Sepal Length
barplot(table(data$Sepal.Length), xlab="Sepal Length", ylab="Frequency")

#scatter-plot of Petal.Length vs Row Index
plot(data$Petal.Length)

#graphs are saved in a PDF file

