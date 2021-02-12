import java.sql.*;
import java.util.*;
//import reduceSomeCostsFunction;

/**
 * The class implements methods of the Restaurant database interface.
 *
 * All methods of the class receive a Connection object through which all
 * communication to the database should be performed. Note: the Connection
 * object should not be closed by any method.
 *
 * Also, no method should throw any exceptions. In particular, in case an error
 * occurs in the database, then the method should print an error message and
 * call System.exit(-1);
 */

public class RestaurantApplication {

    private Connection connection;

    /*
     * Constructor
     */
    public RestaurantApplication(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * getFrequentlyOrderedMenuItems has an integer argument called
     * numMenuItemsOrdered, and returns the menuItemID for each menuItem where the
     * total quantity (adding up quantity across all billEntry tuples) of that
     * menuItem is greater than or equal to numMenuItemsOrdered. A value of
     * numMenuItemsOrdered that’s not positive is an error.
     */

    public List<Integer> getFrequentlyOrderedMenuItems(int numMenuItemsOrdered) {
        List<Integer> result = new ArrayList<Integer>();
        // your code here
        try {
            // case if numMenuItemsOrdered is POSITIVE
            if(numMenuItemsOrdered > 0){
                String sql = "SELECT B.menuItemID FROM billEntry B " +
                "GROUP BY B.menuItemID " + 
                "HAVING SUM(B.quantity) >= ?";

                //System.out.println(sql);
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, numMenuItemsOrdered);

                ResultSet res = stmt.executeQuery();
                while (res.next()) {
                    result.add(res.getInt(1));

                }
            }
            // case if numMenuItemsOrdered is NEGATIVE
            else {
                System.err.println("Error: Parameter numMenuItemsOrdered should be > 0 ");
                return null;
            }
        }
        catch (SQLException sqle) {
            System.out.println("SQLException : " + sqle);
        }
        // end of your code

        return result;
    }

    /**
     * updateServerName method has two arguments, an integer argument, theServerID,
     * and a string argument, newServerName. For the tuple in the server table (if
     * any) whose serverID equals theServerID, updateServerName should update name
     * to be newServerName. (Note that there may not be any tuples whose serverID
     * matches theServerID.) updateServerName should return the number of tuples
     * that were updated, which will always be 0 or 1.
     */

    public int updateServerName(int theServerID, String newServerName) {
        // your code here; return 0 appears for now to allow this skeleton to compile.
        int rowsUpdated  = 0 ; 

        
        try{

            String sql = "UPDATE server " + 
            "SET name = ? " + 
            " WHERE serverID = ?" ;

            //System.out.printf(" %s \n ",sql);
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, newServerName);
            stmt.setInt(2, theServerID);

            rowsUpdated = stmt.executeUpdate();
         
            //System.out.printf("Updated number of rows %d \n", rowsUpdated);

            // will return 1 or 0 depending on if tuple is updated
            return rowsUpdated;
        }
        catch(SQLException sqle) {
            System.out.println("SQLException : " + sqle);
            sqle.printStackTrace();
        }
    
        
        return rowsUpdated;
        // end of your code
    }

    /**
     * reduceSomeVisitCosts has an integer parameters, maxVisitCount. It invokes a
     * stored function reduceSomeCostsFunction that you will need to implement and
     * store in the database according to the description in Section 5.
     * reduceSomeCostsFunction should have the same parameters, maxVisitCount. A
     * value of maxVisitCount that’s not positive is an error.
     *
     * The visits table has a cost attribute. reduceSomeCostsFunction will reduce
     * the cost for some (but not necessarily all) visits; Section 5 explains which
     * visits should have their cost reduced, and also tells you how much they
     * should be reduced. The reduceSomeVisitCosts method should return the same
     * integer result that the reduceSomeCostsFunction stored function returns.
     *
     * The reduceSomeVisitCosts method must only invoke the stored function
     * reduceSomeCostsFunction, which does all of the assignment work; do not
     * implement the reduceSomeVisitCosts method using a bunch of SQL statements
     * through JDBC.
     */

    public int reduceSomeVisitCosts(int maxVisitCount) {
        // There's nothing special about the name storedFunctionResult
        int storedFunctionResult = 0;

        // your code here
        String sql = " select * from reduceSomeCostsFunction(?) ";

        try {
            // case if maxVisitCount is POSITIVE, returns num tuples updated
            if(maxVisitCount > 0){
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, maxVisitCount);
                ResultSet res = stmt.executeQuery();
                while (res.next()) {
                    //System.out.printf("%d tuples cost Reduced by stored function \n", res.getInt(1));
                    storedFunctionResult = res.getInt(1);
                }
            }
             // case if maxVisitCount is NEGATIVE, returns 0 
            else{
                System.err.println("Error: Parameter reduceSomeVisitCosts should be > 0 ");
                return storedFunctionResult;
            }
        } catch (SQLException sqle) {
            System.out.println("SQLException : " + sqle);
        }

        // end of your code
        return storedFunctionResult;

    }

};
