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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class DBUtils {

    private static Connection connection= null;
    private static double sizeOfJackPot = 0.5;
    private static double oneLot = 1;

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
          //  System.out.println("Processing SMS: "+NN);
            String IssaPhoneNumber = rs.getString("PhoneNumber").trim();
            String Sms = rs.getString("TextMessage").trim();
            String summaFromSms=SmsBlock.getNumberFromSMS(Sms);
            if ((SmsBlock.isSmsCorrect(Sms)&&(SmsBlock.IssaPhoneNumber.equals(IssaPhoneNumber)))) {

                //add record to deposit table
                for (int i = 0; i<SmsBlock.getSummaFromSMS(Sms); i++){
                    cstmt = connection.prepareCall("{call [dbo].[AddDeposit](?,?)}");
                    cstmt.setString(1, NN);
                    cstmt.setString(2, summaFromSms);
                    cstmt.execute();
                }
                addSummToJackPot((int) (SmsBlock.getSummaFromSMS(Sms)));
            }else{
                cstmt = connection.prepareCall("{call [dbo].[FailSms](?)}");
                cstmt.setString(1, NN);
                cstmt.execute();

            }
        }
    }

public static String getWinner() throws SQLException {
System.out.println("Try to get winner");
    CallableStatement cstmt = null;
    ResultSet rs = null;
    cstmt = connection.prepareCall("SELECT phone FROM [HelpWallet].[dbo].[Deposit] where winners_nn is null;");
    rs= cstmt.executeQuery();
   ArrayList<String> depositNumbers = new ArrayList<String>();
    while(rs.next()){
        depositNumbers.add(rs.getString("phone").trim());
    }

    int randomNum = ThreadLocalRandom.current().nextInt(0, depositNumbers.size());

    System.out.println("get "+randomNum+" from "+depositNumbers.size());


    String winnersNumber =  depositNumbers.get(randomNum);
       System.out.println("Random Winner's number " + winnersNumber +". Select random "+randomNum+" from "+ depositNumbers.size());
    return winnersNumber.trim();
    }
private static void addSummToJackPot(int summ) throws SQLException {

    CallableStatement cstmt = null;

    cstmt = connection.prepareCall("{call [dbo].[AddToPot](?)}");
    cstmt.setInt(1, summ);
    cstmt.execute();
}

    public static void reduceFromJackPot(int summ) throws SQLException {

        CallableStatement cstmt = null;
        cstmt = connection.prepareCall("{call [dbo].[ReduceFromPot](?)}");
        cstmt.setInt(1, summ);
        cstmt.execute();
    }

    public static int getJackPot() throws SQLException {
        CallableStatement cstmt = null;
        cstmt = connection.prepareCall("Select * from [HelpWallet].[dbo].[settings]");
        ResultSet rs = null;
        rs= cstmt.executeQuery();
        int result=0;
        if (rs.next()) {
        result = (rs.getInt(2));
        }
       System.out.println("All JackPot "+result);
        result = (int)(rs.getInt(2)*sizeOfJackPot);
        System.out.println("Reduce JackPot to "+result);
        result = ThreadLocalRandom.current().nextInt(0, result);

        System.out.println("size of jackPoot after random " + result);
        return result;
    }

    public static int saveWinner(String phoneNumber, double summ) throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        System.out.println("Save winner in current date "+date.getTime());
        CallableStatement cstmt = null;
        cstmt = connection.prepareCall("{call [dbo].[SaveWinner](?,?,?)}");

        cstmt.setTimestamp(1, timestamp);
        cstmt.setString(2,phoneNumber);
        cstmt.setDouble(3,summ);
        cstmt.execute();

        ResultSet rs = null;
        cstmt = connection.prepareCall("select max(Winner.nn)max_nn from Winner");

        rs=cstmt.executeQuery();
        int winnerNN=0;
        if (rs.next()){
        winnerNN = rs.getInt("max_nn");}
        return winnerNN;
    }

    public static  void deleteFromDeposit(String phoneNumber, int summ, int winnerNN) throws SQLException {
        CallableStatement cstmt = null;
        cstmt = connection.prepareCall("{call [dbo].[DeleteDeposit](?,?,?)}");


        cstmt.setString(1,phoneNumber);
        int count = (int)(summ/oneLot);
        cstmt.setInt(2,summ);
        cstmt.setInt(3,winnerNN);
        cstmt.execute();

    }

}

