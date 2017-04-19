package main; /**
 * Created by ilya_saniukevich on 18/04/2017.
 */

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class BDUtils {

/*
    public boolean connectToBD(){
Connection
    }
*/


    public static void connectToBD ()
    {// Declare the JDBC objects.
        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            // Establish the connection.
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setIntegratedSecurity(false);
            ds.setUser("HelpWallet");
            ds.setPassword("123qwerty");
            ds.setServerName("192.168.1.13");
            ds.setPortNumber(1433);
            ds.setDatabaseName("HelpWallet");
            con = ds.getConnection();

            // Execute a stored procedure that returns some data.
        //    cstmt = con.prepareCall("{call dbo.uspGetEmployeeManagers(?)}");
            cstmt = con.prepareCall("select * from sms");
            //cstmt.setInt(1, 50);
            rs = cstmt.executeQuery();

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println("PhoneNumber: " + rs.getString("PhoneNumber") +
                        ", " + rs.getString("SmsText"));
                //System.out.println("MANAGER: " + rs.getString("ManagerLastName") +
                 //       ", " + rs.getString("ManagerFirstName"));
                System.out.println();
            }
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (cstmt != null) try { cstmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }


}

