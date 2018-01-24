package main.java.erp_crm.backend.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
/*-- klasa potrzebna do dodania stopki w pdfach --*/
public class TableFooter extends PdfPageEventHelper {
    protected PdfPTable footer;
    public TableFooter(PdfPTable footer) {
        this.footer = footer;
    }
    public void onEndPage(PdfWriter writer, Document document) {
        footer.writeSelectedRows(0, -1, 36, 64, writer.getDirectContent());
    }

}
