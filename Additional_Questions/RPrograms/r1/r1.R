#Market Basket Transaction - Handle Missing Values

#import the created csv file
file <- read.csv('market.csv')
print("Input Data")
file

#selecting the columns
name <- file[['name']]
quantity <- as.integer(file[['quantity']])
price <- file[['price']]

#defining a function to find mode(useful for categorical attributes)
find_mode <- function(x){
	ux <- unique(x)
	ux[which.max(tabulate(match(x,ux)))]
}

#replace missing values by mode
name[is.na(name)] <- find_mode(name)

#replace missing values by mean
quantity[is.na(quantity)] <- as.integer(mean(quantity, na.rm=TRUE))

#replace missing values by median
price[is.na(price)] <- median(price, na.rm=TRUE)


#export the new data after handling missing values
print("Output Data")
newFile <- data.frame(name,quantity,price)
newFile

write.csv(newFile,'newMarket.csv')


