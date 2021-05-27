import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class javaconnect {
    
    Connection conn=null;
    public static Connection ConnecrDb(){
    File workingDirectory = new File((new StringBuilder()).append(System.getProperty("user.dir").toString()).append("/Database").toString());
        try {
            Class.forName("org.sqlite.JDBC");
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Bretan\\Dropbox\\Car\\Database\\Car");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/bogdanfasie/IdeaProjects/Car/Database/Car");

      //  JOptionPane.showMessageDialog(null, "Connection established"); 
        return conn;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;}
    }
}
