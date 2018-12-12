#loading the data
data <- Orange
summary(data)

#selecting the first 10 rows to plot the graph
age <- data[1:10,2]
circumference <- data[1:10,3]

#Y vs X
features <- circumference ~ age

#create the linear model
my_data <- data.frame(age,circumference)
linearModel <- lm(features,my_data)
summary(linearModel)

#plot the linear model
plot(features)
abline(linearModel)

#predict value for some given data
testAge <- data.frame(age=c(145,500))
predValue <- predict(linearModel,testAge)
print(predValue)
