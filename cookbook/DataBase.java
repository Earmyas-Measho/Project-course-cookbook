package cookbook;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class DataBase {
    Connection conn;

    String uName = "Select userName from users";
    //String pass = "Select pass from users where users.userName = '" + usernameTextField.getText() + "'";


    public Connection connect (){
        Connection c = null;
        String url = "jdbc:mysql://localhost:3306/cook_bookdb";
        String userName = "root";
        String password = "root"; 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url,userName,password);
                System.out.println("Connected");
            }
            return conn;
        } catch (Exception e) {
            System.out.println("Not connected");
        }
        return c;
    }
    
    public ResultSet uNameQuery(){
        ResultSet a = null;
        try {
            Statement st = connect().createStatement();
            ResultSet rs = st.executeQuery(uName);
            return rs;
            
        } catch (Exception e) {
            // TODO: handle exception
            return a;
        }
    }
   /* public ResultSet passQuery(){
        ResultSet a = null;
        try {
            Statement st = connect().createStatement();
            ResultSet rs = st.executeQuery(pass);
            return rs;
            
        } catch (Exception e) {
            // TODO: handle exception
            return a;
        }
    }*/
  
    
}
