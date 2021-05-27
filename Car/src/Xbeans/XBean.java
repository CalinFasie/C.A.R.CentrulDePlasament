package Xbeans;


import org.w3c.dom.Document;
import org.xbeans.DOMEvent;
import org.xbeans.DOMListener;
import org.xbeans.XbeansException;
/**
 * XBean base class implements org.xbeans interfaces.
*
* extend XBean to create useful xbeans.
*/
 public class XBean implements org.xbeans.DOMListener,
org.xbeans.DOMSource{
protected DOMListener DOMListener;
protected Document processedXmlDoc = null;
public XBean(){
}
public void setDOMListener(DOMListener newDomListener) {
DOMListener = newDomListener;
}
public DOMListener getDOMListener(){
return DOMListener;
}
public void documentReady(DOMEvent evt) throws XbeansException {
processedXmlDoc = processDocument(evt.getDocument());
if(DOMListener!=null)
DOMListener.documentReady(new DOMEvent(this, processedXmlDoc));
}
public void processDocument() throws XbeansException {
}
public Document processDocument(Document doc) throws XbeansException {
return doc;
}
}
