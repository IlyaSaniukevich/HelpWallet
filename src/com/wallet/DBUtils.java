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
      //  ResultSet rs = null;

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

        cstmt.setString(1, PhoneNumber);
        cstmt.setString(2, TextMessage);
        cstmt.setTimestamp(3, timestamp);
      cstmt.execute();
    return true;
    }

    public static void processNewSms() throws SQLException {
      //  String NN;
       // String PhoneNumber;
       // String Sms;
        System.out.println("Process new SMS");
        CallableStatement cstmt = null;
        ResultSet rs = null;
        cstmt = connection.prepareCall("{call [dbo].[SelectNewSms]}");
        rs= cstmt.executeQuery();
        while (rs.next())
        {
            String NN = rs.getString("NN");
            System.out.println("Processing SMS: "+NN);
            String IssaPhoneNumber = rs.getString("PhoneNumber").trim();
            String Sms = rs.getString("TextMessage").trim();
            if ((SmsBlock.isSmsCorrect(Sms)&&(SmsBlock.IssaPhoneNumber.equals(IssaPhoneNumber)))) {

                //add record to deposit table
                for (int i = 0; i<SmsBlock.getSummaFromSMS(Sms); i++){
                    cstmt = connection.prepareCall("{call [dbo].[AddDeposit](?,?)}");
                    cstmt.setString(1, NN);
                    cstmt.setString(2, SmsBlock.getNumberFromSMS(Sms));
                    cstmt.execute();
                }

            }else{
                cstmt = connection.prepareCall("{call [dbo].[FailSms](?)}");
                cstmt.setString(1, NN);
                cstmt.execute();

            }
        }
    }




}

