

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnect {
    Connection conn=null;
    public static Connection ConnectDb(){
        try{
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/balastiere","root","");
            Class.forName("org.sqlite.JDBC");
//          Connection conn = DriverManager.getConnection("jdbc:sqlite:C://pdf//Car");
//            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Bretan\\Dropbox\\Car\\Database\\Car");
              Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/bogdanfasie/IdeaProjects/Car/Database/Car");
//            Connection conn=DriverManager.getConnection("jdbc:mysql://coffeeapp.club:3306/coffeeap_db","coffeeap_app","!yPTmfi_ysMz");
            return conn;
        }catch(Exception e){
        return null;
        }
    }
    
}
