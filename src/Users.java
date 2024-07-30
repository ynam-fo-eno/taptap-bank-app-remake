import java.sql.*;

public class Users {
    public static void main(String[] args){
        try {
            Connection connection1 = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/timo_schema_2",
                    "root",
                    ""
            );

            Statement statement1 = connection1.createStatement();
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM bank_customers");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("password"));
            }
        }catch (SQLException sqlE){
            sqlE.printStackTrace();
        }
    }
}
