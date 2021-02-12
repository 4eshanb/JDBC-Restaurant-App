# JDBC Restaurant App

This implements part of an application front-end to a Restaurant database.

# Restaurant Application Class
RestaurantApplication.java contains a skeleton for the RestaurantApplication class, which has
methods that interact with the database using JDBC.  
  
The methods in the RestaurantApplication class are the following:  
  
1. <ins>getFrequentlyOrderedMenuItems</ins>: This method has an integer argument called
numMenuItemsOrdered, and returns the menuItemID for each menuItem where the total
quantity (adding up quantity across all billEntry tuples) of that menuItem is greater than or
equal to numMenuItemsOrdered. A value of numMenuItemsOrdered that’s not positive is
an error.  
  
2. <ins>updateServerName</ins>: Sometimes a server wants to change their name. The
updateServerName method has two arguments, an integer argument, theServerID, and a
string argument, newServerName. For the tuple in the server table (if any) whose serverID
equals theServerID, updateServerName updates name to be newServerName. (Note
that there may not be any tuples whose serverID matches theServerID.) updateServerName
 returns the number of tuples that were updated, which will always be 0 or 1.  
  
3. <ins>reduceSomeVisitCosts</ins>: This method has an integer parameters, maxVisitCount. It invokes
a stored function reduceSomeCostsFunction; reduceSomeCostsFunction 
has the same parameters, maxVisitCount. A value of maxVisitCount that’s not positive is
an error.  
  
The visits table has a cost attribute. reduceSomeCostsFunction will reduce the cost for
some (but not necessarily all) visits; The reduceSomeVisitCosts
method should return the same integer result that the reduceSomeCostsFunction stored
function returns.  
  
The reduceSomeVisitCosts method must only invoke the stored function
reduceSomeCostsFunction, which does all of the assignment work.