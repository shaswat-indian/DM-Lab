library(rpart)	#for decision tree1
library(rpart.plot)	#graph for decision tree1
library(e1071)	#for naive Bayes classifier
library(party)	#for decision tree2
library(caret)	#for confusionMatrix

data <- iris

#split data for training and testing
index <- sample(2,nrow(data),replace=TRUE,p=c(0.7,0.3))
trainData <- data[index==1,]
testData <- data[index==2,]

#Y vs X
features <- Species ~ Sepal.Length + Sepal.Width + Petal.Length + Petal.Width

#Decision Tree
tree1 <- rpart(features,trainData,method="class")
rpart.plot(tree1)

tree2 <- ctree(features,trainData)
plot(tree2)

#Decision Tree Prediction
res1 <- predict(tree2,testData)
confusionMatrix(res1,testData$Species)


#Naive Bayes
model1 <- naiveBayes(features,trainData)
print(model1)

#Naive Bayes Prediction
res2 <- predict(model1,testData)
confusionMatrix(res2,testData$Species)
