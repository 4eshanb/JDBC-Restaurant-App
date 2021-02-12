# JDBC Restaurant App

This implements part of an application front-end to a Restaurant database.

## Restaurant Application Class
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


## Stored Function

The stored function is called reduceSomeCostsFunction, which 
has an integer parameters, maxVisitCount; reduceSomeCostsFunction will change the cost
attribute for some (but not necessarily all) visit tuples. But it never reduces the costs of more than
maxVisitCount visit tuples.  
  
The customer table has attributes including customerID and status. A customer has
High, Medium, Low status or NULL status based on whether that customer’s status is ‘H’, ‘M’, ‘L”
or NULL.   
  
The visit table has attributes including customerID and cost. The
cost is reduced for visits of customers based on their status (but only if that cost is NOT NULL).  
• Reduce the cost of visits by High status customers by 10%  
• Reduce the cost of visits by Medium status customers by 5%.  
• Reduce the cost of visits by Low status customers by 1%.  
• Do nothing for the cost of visits for customers who have NULL status.  
  
But the cost is not reduced in all visit tuples, rather for at most
maxVisitCount visit tuples.  
  
How is it decided which costs to reduce?  
First, we process the visits by High status customers, ordered by increasing joinDate. Then we
process the visits by Medium status customers, ordered by increasing joinDate. Then we process
the visits by Low status customers, order by increasing joinDate. (“Processing” involves doing the
reductions that are described above.) But as soon as we have processed maxVisitCount visit tuples,
we are done. The value that reduceSomeCostsFunction returns is the number of visit tuples that
have been updated.  

Won’t that value always equal maxVisitCount?  
No. Suppose that there are 3 visits by High status
customers, 5 visits by Medium status customers, and 9 visits by Low status customers.  
• If maxVisitCount is 17 or more, then all 17 of these visits will have their costs reduced, and
reduceSomeCostsFunction returns the value 17 (even if maxVisitCount was 20).
• If maxVisitCount is 8, then the costs for the visits by the High and Medium status
customers will be reduced, and reduceSomeCostsFunction returns the value 8.
• If maxVisitCount is 7, then the costs for the 3 visits by the High status customers will be
reduced, and the costs for 4 of the 5 Medium status customer visits will be reduced, and
reduceSomeCostsFunction returns the value 7. Which Medium status customers receive
the reduction? The ones with the earlies joinDate.  

The stored function is saved to a text file named
reduceSomeCostsFunction.pgsql. To create the stored function reduceSomeCostsFunction, issue
the psql command at the server prompt:
\i reduceSomeCostsFunction.pgsql


## Testing
There are tests in RunRestaurantApplication.java.
• One test is writtens of the getFrequentlyOrderedMenuItems method with the
numMenuItemsOrdered argument set to 65. The code should print the result returned as
output. 

• Two tests are written for the updateServerName method. The first test is for theServerID
3 and newServerName ‘Phileas Fogg’. The second test is for theServerID 10 and
newServerName ‘John Smith’. 

• Two tests are for the reduceSomeVisitCosts method. The first test has
maxVisitCount value 10. The second test has maxVisitCount value 95. In
RunRestaurantApplication, the code prints the result (total number of customers
whose status was updated) returned by each of the two tests, with a description of what
each test was, just as for each of the previous methods.  
  
The tests must be run in the specified order, running with maxVisitCount 10, and
then with maxVisitCount 95. The order affects the results.  
  
All of these method tests must be ran in order, starting with the database provided by
our create and load scripts. Some of these methods change the database, so using the database provided and executing methods in order is required.

This assignment was done during CSE 180 with Professor Sheldon Finkelstein at UCSC.