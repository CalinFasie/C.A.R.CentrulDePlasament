package Xbeans;


import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xbeans.DOMEvent;
import org.xbeans.XbeansException;

import java.sql.*;
/**
* Query a database and return the ResultSet as an XML document
*/
public class SQLQueryBean extends XBean{
//public class SQLQueryBean extends JavaDatabaseBible.ch17.xbeans.XBean{
private String databaseName = "";
private String tableName = "";
private String SQLQuery = null;
Document document;
public SQLQueryBean(){
}
public void setDatabaseName(String databaseName){
this.databaseName = databaseName;}
public void setTableName(String tableName){
this.tableName = tableName;
}
public void setSQLQuery(String SQLQuery){
this.SQLQuery = SQLQuery;
}
public void processDocument() throws XbeansException{
try{
document = new DocumentImpl();
if(databaseName.length()>0&&tableName.length()>0){
   // String d = databaseName.toUpperCase();
     String d = "C.A.R. C.P.1";
    //String t = tableName.toUpperCase();
    String t = "Membri";
    Element root = (Element)document.createElement(t);
    document.appendChild (root);
    root.setAttribute("Retineri",d);
    appendDataNodes();
    }
else{
    throw new XbeansException("SQLQueryBean",null,"DBName/TableName Undefined",null);
    }
DOMEvent domEvt = new DOMEvent(this,document);
DOMListener.documentReady(domEvt);
}catch (Exception e){
e.printStackTrace();
}
}
public void appendDataNodes() {
//String url = "jdbc:odbc:"+databaseName;
 String url ="jdbc:sqlite:"+databaseName;
// Connection conn = DriverManager.getConnection("jdbc:sqlite:D:\\Car\\Database\\Car");
if(SQLQuery==null)SQLQuery="SELECT * FROM "+tableName;
try {
    Class.forName("org.sqlite.JDBC");
Connection con = DriverManager.getConnection(url);
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(SQLQuery);
ResultSetMetaData md = rs.getMetaData();
int nColumns = md.getColumnCount();
Element root = document.getDocumentElement();
while(rs.next()){
Element record=(Element)document.createElement("Membru");
root.appendChild (record);
for(int i=1;i<=nColumns;i++){
String fName = md.getColumnLabel(i);
String data = rs.getString(i);
record.setAttribute(fName,String.valueOf(data));
//if(fName.equals("nume")){
//record.setAttribute("nume",String.valueOf(data));
//}else{
//    if(fName.equals("id")){
//    record.setAttribute("id",String.valueOf(data));    
//    }else{    
//    Element fld = (Element)document.createElement(fName);
//    record.appendChild(fld);
//    fld.appendChild(document.createTextNode(data));} 
//}
}
}
con.close();
}catch (Exception e){
e.printStackTrace();
}
}

    //void setDOMListener(SerializerBean serializer) {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
}
