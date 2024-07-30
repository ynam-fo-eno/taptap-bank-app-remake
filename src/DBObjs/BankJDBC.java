package DBObjs;

import java.math.BigDecimal;
import java.sql.*;

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

}
