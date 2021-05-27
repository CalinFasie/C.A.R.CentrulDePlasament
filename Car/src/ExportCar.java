import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import com.itextpdf.text.Rectangle;

public class ExportCar {
    static PreparedStatement pst=null;
    static ResultSet rs=null;
    static Connection conn =MySQLConnect.ConnectDb();
    
//      public static final String RESULT= "C:/pdf/nu.pdf";
//      private static String FILE = "C:/pdf/test.pdf";
        private static String FILE = "/home/bogdanfasie/IdeaProjects/Car/Rezultate/Borderou.pdf";

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);

	static class TableHeader extends PdfPageEventHelper {
        /** The header text. */
        String header;
        /** The template with the total number of pages. */
        PdfTemplate total;
 
        /**
         * Allows us to change the content of the header.
         * @param header The new header String
         */
        public void setHeader(String header) {
            this.header = header;
        }
 
        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }
 
        /**
         * Adds a header to every page
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table = new PdfPTable(3);//3
            try {
  //              table.setWidths(new int[]{44, 24, 2});//24,24,2
            	 table.setWidths(new int[]{44, 24,2});//24,24,2
                table.setTotalWidth(52);//527
                table.setLockedWidth(true);
                table.getDefaultCell().setFixedHeight(2);//20
                table.getDefaultCell().setBorder(Rectangle.OUT_BOTTOM);
                table.addCell(header);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//                table.addCell(String.format("Page %d of", writer.getPageNumber()));
                table.addCell(writer.getPageNumber()+"");
                PdfPCell cell = new PdfPCell(Image.getInstance(total));
//                cell.setBorder(Rectangle.OUT_RIGHT);
                table.addCell(cell);
                table.writeSelectedRows(0, -1, 14, 40, writer.getDirectContent());//34,,803
            }
            catch(DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
 
        /**
         * Fills out the total number of pages before the document is closed.
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                    2, 2, 0);
        }
    }
	
	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document) {
		document.addTitle("My first PDF");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("Lars Vogel");
		document.addCreator("Lars Vogel");
	}

	private static void addTitlePage( PdfWriter write,Document document)throws DocumentException {
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("                  CAR Centrul de Plasament nr. 1 IFN                           Data____________ ",smallBold));
		preface.add(new Paragraph("                 BORDEROU nr._____________", catFont));
		addEmptyLine(preface, 1);
		preface.add(new Paragraph("              Pentru incasarile realizate de membrii CAR pe luna_______ 2015 " ,smallBold));
		addEmptyLine(preface, 1);
//		preface.add(new Paragraph("Page"+write.getCurrentPageNumber() ,smallBold));
		document.add(preface);
	}

	private static void addContent(Document document) throws DocumentException {

		// Second parameter is the number of the chapter
		Chapter catPart = new Chapter(1);

		Paragraph subPara = new Paragraph();
		Section subCatPart = catPart.addSection(subPara);

                // Now add all this to the document
		document.add(catPart);

	}

	private static void createTable(Document document) throws DocumentException {
		PdfPTable table = new PdfPTable(8);
		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(1);
		float[] columnWidths = new float[] {15f, 75f, 20f, 28f,29f, 30f, 32f,25f};
        try {
			table.setWidths(columnWidths);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		PdfPCell c1 = new PdfPCell(new Phrase("Nr. \ncrt"));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Numele si prenumele"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Nr \nmarca \nfisa"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Taxa de\n inscriere"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("Dobanda"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("Rata \nimprumut"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("Depunere \n la fondul \n social"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("Total"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
//		c1 = new PdfPCell(new Phrase("Suma\nretinuta"));
	//	c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		//table.addCell(c1);
		
		
		 try{
	          String sql="select nume, prenume, marca,taxa, dobanda,rata,fond, retinere from Print ";
	          pst=conn.prepareStatement(sql);
	          rs=pst.executeQuery();
	          int j = 1;
	          int total = 0;
	             while (rs.next()) {
	            	 table.addCell(j+"");
	            	// table.addCell(rs.getString("nume")+" si "+rs.getString("prenume"));
	            	 table.addCell(rs.getString("nume"));
	            	 table.addCell(rs.getString("marca"));
	            	 table.addCell(rs.getString("taxa"));
	            	 table.addCell(rs.getString("dobanda"));
	            	 table.addCell(rs.getString("rata"));
	            	 table.addCell(rs.getString("fond"));
//	            	 table.addCell(rs.getString("fond"));
	            	 table.addCell(rs.getString("retinere"));
	            	 
	            	 total = total + Integer.parseInt(rs.getString("retinere"));
	            	 j++;
	                }
	             table.addCell("");
	             table.addCell("Total");
	             table.addCell("");
	             table.addCell("");
	             table.addCell("");
	             table.addCell("");
	             table.addCell("");
//	             table.addCell("");
	             table.addCell(total+"");
	            }catch(Exception e){
	    }                       
//		subCatPart.add(table);
		 try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Paragraph preface = new Paragraph();addEmptyLine(preface, 1);
			preface.add(new Paragraph("Intocmit contabil Car                                         Operat Stat",smallBold));
			document.add(preface);
	}
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
        public static void t(){
            try {
			System.out.println("daaaa");
			  Document document = new Document(PageSize.A4, 10, 20, 20, 20);
//			  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
			  PdfWriter writer=	PdfWriter.getInstance(document, new FileOutputStream(FILE));
			  
			  TableHeader event = new TableHeader();
		        writer.setPageEvent(event);
		        
			document.open();
			addMetaData(document);
			addTitlePage(writer,document);
//			addContent(document);
			createTable(document);
//			int pageN = writer.PageNumber;
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        }
}