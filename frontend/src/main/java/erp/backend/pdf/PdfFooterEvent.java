package main.java.erp.backend.pdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import java.io.IOException;

/**
 * Created by marcin on 15.09.16.
 */
public class PdfFooterEvent extends PdfPageEventHelper {
    private String s;
    private Boolean bold;
    public PdfFooterEvent(String s, Boolean bold){
        this.s=s;
        this.bold=bold;
    }
    public void onEndPage(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");
        try {
            Font font = new Font(BaseFont.createFont(bold?"calibrib.ttf":"calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED),9);
            Phrase text = new Phrase();
            text.setFont(font);
            text.add(s);
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, text, (rect.getLeft()+rect.getRight())/2, rect.getBottom(), 0);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
