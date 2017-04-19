package main; /**
 * Created by ilya_saniukevich on 18/04/2017.
 */

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class BDUtils {

    private static Connection connection= null;

    public static Connection connectToBD ()
    {
       if (connection != null) return connection;




        try {
            // Establish the connection.
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setIntegratedSecurity(false);
            ds.setUser("HelpWallet");
            ds.setPassword("123qwerty");
            ds.setServerName("192.168.1.13");
            ds.setPortNumber(1433);
            ds.setDatabaseName("HelpWallet");
            connection = ds.getConnection();
            return connection;
          /*  // Execute a stored procedure that returns some data.
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
            }*/
        }

        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
         //   if (rs != null) try { rs.close(); } catch(Exception e) {}

           // if (con != null) try { con.close(); } catch(Exception e) {}
        }
    }


    public boolean saveSms(String smsText, String fromNumber, Date date) throws SQLException {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        // Execute a stored procedure that returns some data.
        //    cstmt = con.prepareCall("{call dbo.uspGetEmployeeManagers(?)}");
            cstmt = connection.prepareCall("INSERT INTO [dbo].[SmsInbox]\n" +
                    "           ([PhoneNumber]\n" +
                    "           ,[TextMessage]\n" +
                    "           ,[ReceiveTime]\n" +
                    "           ,[Processed])\n" +
                    "     VALUES\n" +
                    "           (<PhoneNumber, nchar(15),>\n" +
                    "           ,<TextMessage, nchar(500),>\n" +
                    "           ,<ReceiveTime, datetime,>\n" +
                    "           ,<Processed, bit,>)");
            //cstmt.setInt(1, 50);
            rs = cstmt.executeQuery();
            }
}

