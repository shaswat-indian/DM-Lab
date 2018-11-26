
#uncomment the following two lines to install the packages if not present
#install.packages("arules")
#install.packages("arulesViz")

#attach the packages
library(arules)
library(arulesViz)

#load the Groceries dataset
data(Groceries)

#display the first 10 transactions
inspect(Groceries[1:10])


#bar graph showing the top 10 frequent items
itemFrequencyPlot(Groceries,topN=10,type="absolute")

#generate and display frequent itemsets in decreasing order of support
itemSets <- apriori(Groceries, parameter=list(minlen=1, maxlen=10,sup=0.02, target="frequent itemsets"))
inspect(sort(itemSets,by="support"))

#generate and display strong rules in decreasing order of confidence
itemRules <- apriori(Groceries, parameter=list(minlen=1, maxlen=10, sup=0.02, conf=0.4, target="rules"))
inspect(sort(itemRules,by="confidence"))

#graph showing the strong association rules
plot(itemRules, method="graph")


#graphs are saved in a PDF file
