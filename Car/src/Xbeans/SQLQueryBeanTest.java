package Xbeans;


import java.beans.Beans;
import java.io.FileOutputStream;
public class SQLQueryBeanTest {
static public void emain() {
//String databaseName = "C:\\Users\\Bretan\\Dropbox\\Car\\Database\\Car";
//String databaseName = "D:\\Car\\Database\\Car";
    String databaseName = "/home/bogdanfasie/IdeaProjects/Car/Database/Car";
String tableName = "Manevra";
//String SQLQuery = "SELECT * FROM CUSTOMERS WHERE STATE = 'NY'";
String SQLQuery = "SELECT nume,id,an,luna,marca,retinere FROM Manevra";
try{
   
//SQLQueryBean queryBean = (SQLQueryBean)Beans.instantiate(null,"JavaDatabaseBible.ch17.Xbeans.SQLQueryBean");
SQLQueryBean queryBean = (SQLQueryBean)Beans.instantiate(null,"Xbeans.SQLQueryBean");
SerializerBean serializer =
(SerializerBean)Beans.instantiate(null,"Xbeans.SerializerBean");
queryBean.setDatabaseName(databaseName);
queryBean.setTableName(tableName);
queryBean.setSQLQuery(SQLQuery);
queryBean.setDOMListener(serializer);
//serializer.setOutputStream(new FileOutputStream("C:\\Users\\Bretan\\Dropbox\\Car\\Rezultate\\Membri.xml"));
serializer.setOutputStream(new FileOutputStream("/home/bogdanfasie/IdeaProjects/Car/Rezultate/Membri.xml"));

queryBean.processDocument();

}catch(Exception e){
System.err.println(e);
}
}
}