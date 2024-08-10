package DBObjs;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class BankJDBC {
    private static final String DB_URL= "jdbc:mysql://127.0.0.1:3306/bank_app";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD= "";
    
    public static People loginValidation(String username, String password){
        try{
            Connection connectToDB = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            PreparedStatement sqlStatement = connectToDB.prepareStatement(
                    "SELECT *FROM bankcustomers WHERE username = ? AND password = ?"
            );

            sqlStatement.setString(1,username);
            sqlStatement.setString(2, password);

            ResultSet resultSet = sqlStatement.executeQuery();
            if(resultSet.next() ) {
                int userID = resultSet.getInt("id");
                BigDecimal usersBalance = resultSet.getBigDecimal("balance");
                return new People(userID,username,password,usersBalance);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean registrationValidation(String username, String password){
        try {

            if(!userValidation(username)) {
                Connection connectToDB = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
                PreparedStatement sqlStatement = connectToDB.prepareStatement(
                        "INSERT INTO  bankcustomers (username,password,balance) VALUES(?,?,?)"
                );
                sqlStatement.setString(1,username);
                sqlStatement.setString(2, password);
                sqlStatement.setBigDecimal(3, new BigDecimal(0));
                sqlStatement.executeUpdate();
                return true;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean userValidation(String username){
        try {
            Connection connectToDB = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            PreparedStatement sqlStatement = connectToDB.prepareStatement(
                    "SELECT *FROM bankcustomers WHERE username = ?"
            );
            sqlStatement.setString(1,username);
            ResultSet resultSet = sqlStatement.executeQuery();


            if( ! resultSet.next() ) {
               return false;
            }
            /*
                This if statement checks for the name's existence in the DB.
                If it isn't there, it returns false to denote that that username is available.
            */

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean addTranssactiontoDB(Transaction transaction) {
        try {
            Connection connectToDB = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            PreparedStatement transactionEntry = connectToDB.prepareStatement(
                    "INSERT INTO banktransactions (t_amount,t_type,t_date,u_id) VALUES (?, ?, NOW(), ?)"
            );
            transactionEntry.setBigDecimal(1,transaction.getTransactionAmount());
            transactionEntry.setString(2,transaction.getTransactionType());
            transactionEntry.setInt(3,transaction.getUserID());
            transactionEntry.executeUpdate();
            return true;

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateUserBalance(People personne) {
        try {
            Connection connectToDB = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            PreparedStatement moneyUpdate = connectToDB.prepareStatement(
                    "UPDATE bankcustomers SET balance = ? WHERE id=?"
            );
            moneyUpdate.setBigDecimal(1,personne.getBalance());
            moneyUpdate.setInt(2,personne.getId());
            moneyUpdate.executeUpdate();
            return true;

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createTransfer(People personne, String transferredUser, double amount) {
        try {
            Connection connectToDB = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            PreparedStatement makeTransfer = connectToDB.prepareStatement(
                    "SELECT * FROM bankcustomers WHERE username=?"
            );
            makeTransfer.setString(1,transferredUser);
            ResultSet resultSet = makeTransfer.executeQuery();
            while(resultSet.next()) {
                //Do transfer from sender...
                People transferringHuman = new People(
                        resultSet.getInt("id"),
                        transferredUser,
                        resultSet.getString("password"),
                        resultSet.getBigDecimal("balance")
                );
                //...to receiver...
                Transaction transferTransaction = new Transaction(
                        personne.getId(),
                        "Transfer",
                        new BigDecimal(-amount),
                        null
                );

                //Should ensure it all goes to the reciver from here...
                Transaction receivedTransaction = new Transaction(
                        transferringHuman.getId(),
                        "Transfer",
                        new BigDecimal(amount),
                        null
                );

                transferringHuman.setBalance(transferringHuman.getBalance().add(BigDecimal.valueOf(amount)));
                updateUserBalance(transferringHuman);

                personne.setBalance(personne.getBalance().subtract(BigDecimal.valueOf(amount)));
                updateUserBalance(personne);

                addTranssactiontoDB(transferTransaction);
                addTranssactiontoDB(receivedTransaction);

                return true;
            }
        }

        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static ArrayList<Transaction>showPreviousTransactions(People personne) {
        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try {
            Connection connectToDB = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            PreparedStatement onyeshaTransactionsZote = connectToDB.prepareStatement(
                    "SELECT * FROM banktransactions WHERE u_id=?"
            );
            onyeshaTransactionsZote.setInt(1,personne.getId());
            ResultSet resultSet = onyeshaTransactionsZote.executeQuery();
            while(resultSet.next() ) {
                Transaction transactionHistory = new Transaction(
                        personne.getId(),
                        resultSet.getString("t_type"),
                        resultSet.getBigDecimal("t_amount"),
                        resultSet.getDate("t_date")
                );
                pastTransactions.add(transactionHistory);

            }


        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return pastTransactions;
    }
}
