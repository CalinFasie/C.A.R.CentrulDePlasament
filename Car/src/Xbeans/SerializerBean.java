package Xbeans;


import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

import java.io.OutputStream;
import java.io.Writer;
/**
* Serialize a document to a stream or writer
*/
//public class SerializerBean extends JavaDatabaseBible.ch17.xbeans.XBean{
public class SerializerBean extends XBean{    
    
protected OutputStream os = System.out;
protected Writer writer = null;
protected XMLSerializer serializer;
public SerializerBean(){
}
public void setOutputStream(OutputStream os){
this.os = os;
}
public void setWriter(Writer writer){
this.writer = writer;
}
public Document processDocument(Document doc) {
OutputFormat fmt = new OutputFormat("xml",null,true);
if(writer!=null){
serializer = new XMLSerializer(writer,fmt);
}else{
serializer = new XMLSerializer(os,fmt);
}
if(doc!=null){
try{
serializer.asDOMSerializer().serialize(doc);

}catch (Exception e){
e.printStackTrace();
}
}
return doc;
}
}
