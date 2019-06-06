import org.postgresql.core.SqlCommand;

import java.sql.*;

public class DbConnect {
     private String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
     private String USER = "postgres";
     private String PASS = "redtea";
     public static Connection connection = null;
        public DbConnect(){

        }
        public boolean connect() {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException|NullPointerException e) {
                System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
                return false;
            }
            if(connection==null)
            try {
                connection = DriverManager
                        .getConnection(DB_URL, USER, PASS);
                System.out.println("Connection was super to Db");
                return true;
            } catch (SQLException e) {
                System.out.println("Connection Failed to Db");
                return false;
            }
            return false;
        }
    public Result change(PreparedStatement preparedStatement, String action) throws SQLException{
//        try {
            if (connection != null) {
//                PreparedStatement preparedStatement=connection.prepareStatement(pgSQLRequest);
//                Statement tempStatement = connection.createStatement();
                switch (action) {
                    case "set":
//                        tempStatement.executeUpdate(pgSQLRequest);
//                        tempStatement.close();
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        return null;
                    case "get":
                        return new Result(preparedStatement.executeQuery(), preparedStatement);
                }
            } else {
                System.out.println("Cannt complete a reqeust: statement not initialized");
            }
//        } catch (SQLException e) {
//            System.out.println("Cannt complete a change: ");
//        }

        return null;
    }
    public PreparedStatement request(int a)throws SQLException{
                switch (a) {
                    case 1:
                        return connection.prepareStatement("SELECT * FROM \"Users\" WHERE \"Email\"=?");
                    case 2: return connection.prepareStatement("SELECT * FROM \"Users\" WHERE \"Name\"=?");
                    case 3: return connection.prepareStatement("SELECT * FROM \"Users\" WHERE \"Password\"=?");
                    case 4: return connection.prepareStatement("SELECT * FROM \"HumanTree\" WHERE \"nickname\"=?");
                }
                return null;
    }
    public boolean dbHas(int a, String parametr) throws NullPointerException,SQLException {
//        try {
            PreparedStatement preparedStatement=request(a);
            preparedStatement.setString(1,parametr);
            return change(preparedStatement,"get").getResultSet().next();
//            return change(String.format("SELECT * FROM %s WHERE %s", dbName, condition), "get").getResultSet().next();
//        } catch ( SQLException | NullPointerException e) {
//            return false;
//        }
    }
}
