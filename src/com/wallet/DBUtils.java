package com.wallet; /**
 * Created by ilya_saniukevich on 18/04/2017.
 */


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DBUtils {

    private static Connection connection= null;

    public static void connectToBD ()
    {
       if (connection != null) return ;




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


    public static boolean saveSms(String PhoneNumber, String TextMessage, String ReceiveTime) throws SQLException, ParseException {
        CallableStatement cstmt = null;
        ResultSet rs = null;

//2017-04-27 15:44:04
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        try {
            date = format.parse(ReceiveTime);
        }
        catch (ParseException ex) {
            System.out.println("Exception " + ex);
        }
        System.out.println("date " + date);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        System.out.println("timestamp " + timestamp);
        // Execute a stored procedure that returns some data.
            cstmt = connection.prepareCall("{call [dbo].[SaveSMS](?,?,?)}");

      /*   System.out.print("INSERT INTO [dbo].[SmsInbox]\n" +
                 "           ([PhoneNumber]\n" +
                 "           ,[TextMessage]\n" +
                 "           ,[ReceiveTime]\n" +
                 "     VALUES\n" +
                 "           (<"+PhoneNumber+", nchar(15),>\n" +
                 "           ,<"+TextMessage+", nchar(500),>\n" +
                 "           ,<"+date+", datetime,>)");*/

         /*   cstmt = connection.prepareCall("INSERT INTO [dbo].[SmsInbox]" +
                    "           ([PhoneNumber]\n" +
                    "           ,[TextMessage]\n" +
                    "           ,[ReceiveTime]\n" +
                    "    ) VALUES(?,?,?)");*/
        cstmt.setString(1, PhoneNumber);
        cstmt.setString(2, TextMessage);
        cstmt.setTimestamp(3, timestamp);


      cstmt.execute();
    return true;
    }
}

