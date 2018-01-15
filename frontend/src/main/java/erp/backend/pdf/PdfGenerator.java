package main.java.erp.backend.pdf;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import main.java.erp.backend.PriceToWords;
import main.java.erp.backend.SharedData;
import main.java.erp.backend.Utils;
import javafx.scene.control.ComboBox;
import main.java.erp.backend.model.common.Client;
import main.java.erp.backend.model.erp.Order;
import main.java.erp.backend.model.erp.OrderedArticle;
import main.java.erp.backend.model.erp.Proforma;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
//klasa do tworzenia wszystkich pdfów w programie
public class PdfGenerator {
    public static String filePath;
    private static final int STANDARD_CELLSPAN=1;
    private static Client client;
    private static Order order;
    private static Proforma proforma;

    private static PdfPCell generateCell(int border, String text, int colspan, boolean bold){
        BaseFont bf = null;
        try {
            if(bold)
                bf = BaseFont.createFont("calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
            else
                bf = BaseFont.createFont("calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        Font font = new Font(bf, 9);
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text, font);
        cell.setBorder(border);
        cell.setColspan(colspan);
        cell.setPadding(10);
        cell.addElement(p);
        return cell;
    }
    private static PdfPCell generateCell(int border, String text, int colspan, boolean bold, int fontSize){
        BaseFont bf = null;
        try {
            if(bold)
                bf = BaseFont.createFont("calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
            else
                bf = BaseFont.createFont("calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        Font font = new Font(bf, fontSize);
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text, font);
        cell.setBorder(border);
        cell.setColspan(colspan);
        cell.setPadding(10);
        cell.addElement(p);
        return cell;
    }
    private static PdfPCell generateCell(int border, String text, int colspan, boolean bold,Integer leading){
        BaseFont bf = null;
        try {
            if(bold)
                bf = BaseFont.createFont("calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
            else
                bf = BaseFont.createFont("calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        Font font = new Font(bf, 9);
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text, font);
        p.setLeading(leading);
        cell.setBorder(border);
        cell.setColspan(colspan);
        cell.setPadding(10);
        cell.addElement(p);
        return cell;
    }
    private static PdfPCell generateImageCell(String fileName) throws BadPdfFormatException {
        try {
            Image image = Image.getInstance(fileName);
            PdfPCell cell = new PdfPCell(image,true);
            cell.setPaddingTop(0);
            cell.setPaddingLeft(0);
            cell.setPaddingBottom(32);
            cell.setPaddingRight(35);
            cell.setBorder(0);
            cell.setColspan(1);
            //cell.setImage(image);
            return cell;
        } catch (BadElementException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean order(String numerZamowienia, String path) throws IOException, DocumentException {
        filePath=path;
        PdfPCell cell;
        Paragraph tmp;
        float lead;

        order = prepareZamowienie(numerZamowienia);
        client = order.getClient();

        Document document = new Document();
        BaseFont bf = BaseFont.createFont("calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(bf, 9);
        Font boldFont = new Font(BaseFont.createFont("calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED),9);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        Rectangle rect = new Rectangle(30, 30, 550, 800);
        writer.setBoxSize("art", rect);
        PdfFooterEvent event = new PdfFooterEvent("Formularz proszę przesłać na adres e-mailField: "+ SharedData.getMailAddress(),true);
        writer.setPageEvent(event);
        document.setPageSize(PageSize.A4);
        document.setMargins(36,36,36,36);
        document.open();

        /*----------NAGLOWEK----------*/
        PdfPTable naglowek = new PdfPTable(4);
        naglowek.setWidthPercentage(100);
        naglowek.addCell(generateImageCell("logo.jpg"));
        PdfPTable leftTable = new PdfPTable(1);
        PdfPTable rightTable = new PdfPTable(1);
        lead=12;
        tmp = new Paragraph("Numer zamówienia:", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        leftTable.addCell(cell);
        tmp = new Paragraph("Data złożenia zamówienia:", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        leftTable.addCell(cell);
        tmp = new Paragraph("Osoba kontaktowa:", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        leftTable.addCell(cell);
        cell = new PdfPCell(leftTable);
        cell.setColspan(2);
        cell.setBorder(0);
        cell.setPaddingLeft(120);
        naglowek.addCell(cell);


        tmp = new Paragraph(order.getOrderNumber(), boldFont);
        tmp.setLeading(lead);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(3);
        rightTable.addCell(cell);
        tmp = new Paragraph(order.getOrderDate().toString(), font);
        tmp.setLeading(lead);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        rightTable.addCell(cell);
        cell.setPadding(3);

        tmp = new Paragraph(SharedData.getImie()+" "+ SharedData.getNazwisko()+"\ntel. "+ SharedData.getTelefon()+"\nmailField: "+ SharedData.getMail(), font);
        tmp.setLeading(lead);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        rightTable.addCell(cell);
        cell.setPadding(3);


        cell = new PdfPCell(rightTable);
        cell.setColspan(1);
        cell.setBorder(0);
        naglowek.addCell(cell);
        document.add(naglowek);
        /*----------DANE DOSTAWCY---------------*/
        PdfPTable dostawca = new PdfPTable(1);
        dostawca.setHorizontalAlignment(Element.ALIGN_LEFT);
        tmp = new Paragraph("Dostawca", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        dostawca.addCell(cell);

        PdfPTable dostawcaZamawiajacy = new PdfPTable(3);
        dostawcaZamawiajacy.setWidthPercentage(100);

        tmp = new Paragraph(getDaneDostawcy(), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        dostawca.addCell(cell);
        cell = generateCell(0,"Nr konta bankowego Dostawcy:",STANDARD_CELLSPAN,true,9);
        cell.setPadding(3);
        dostawca.addCell(cell);

        tmp = new Paragraph(getBank(), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        dostawca.addCell(cell);
        cell = new PdfPCell(dostawca);
        cell.setBorder(0);
        dostawcaZamawiajacy.addCell(cell);

        /*----------DANE ZAMAWIAJĄCEGO----------*/
        PdfPTable zamawiajacy = new PdfPTable(2);
        zamawiajacy.setHorizontalAlignment(Element.ALIGN_RIGHT);

        tmp = new Paragraph("Dane do faktury", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        zamawiajacy.addCell(cell);

        tmp = new Paragraph("Adres dostawy", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        zamawiajacy.addCell(cell);

        tmp = new Paragraph(prepareDaneDoFaktury(client), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        zamawiajacy.addCell(cell);

        tmp = new Paragraph(prepareDeliveryAddress(client), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        zamawiajacy.addCell(cell);

        tmp = new Paragraph("Osoba kontaktowa", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        zamawiajacy.addCell(cell);


        zamawiajacy.addCell(generateCell(0,"",STANDARD_CELLSPAN,false));


        zamawiajacy.addCell(generateCell(0,"",STANDARD_CELLSPAN,false));

        cell=new PdfPCell(zamawiajacy);
        cell.setColspan(2);
        cell.setBorder(1);
        dostawcaZamawiajacy.addCell(cell);
        dostawcaZamawiajacy.setSpacingBefore(10);
        document.add(dostawcaZamawiajacy);

        /*----------PRZEDMIOTY----------*/
        PdfPTable przedmioty = new PdfPTable(7);
        przedmioty.setWidthPercentage(100);
        cell = generateCell(1, "Lp.", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        cell.setPaddingRight(0);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Opis produktu", 2, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Ilość [m2/szt]", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Cena/jed.\n", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Wartość netto [PLN]", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Waga [kg]", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        Paragraph p = new Paragraph("\nPrzedmiot zamówienia:\n", boldFont);
        p.setLeading(12);
        document.add(p);

        przedmioty.setSpacingBefore(5);
        document.add(przedmioty);
        document.add(prepareTowarTable());


        /*----------KWOTY---------*/
        document.add(prepareKwoty());



        /*----------TERMIN, UWAGI, WARUNKI---------*/
        p = new Paragraph("Termin realizacji", boldFont);
        p.setLeading(12);
        p.getFont().setSize(9);
        p.setSpacingBefore(10);
        document.add(p);
        
        p = new Paragraph(order.getRealizationDate().toString(), font);
        
        p.setLeading(12);
        p.getFont().setSize(9);
        document.add(p);
        p = new Paragraph("Warunki płatności", boldFont);
        p.getFont().setSize(9);
        p.setLeading(12);
        document.add(p);
        p = new Paragraph(order.getConditions(), new Font(bf,9));
        p.setLeading(12);
        document.add(p);

        p = new Paragraph("\nUwagi:\n"+ order.getComments(), new Font(bf, 8));
        p.setLeading(10);
        p.setSpacingBefore(5);
        document.add(p);



        /*----------PODPISY-----------*/
        generatePodpisy(lead, document, bf);
        /*PdfReader reader = new PdfReader("instrukcja.pdf");
        int n = reader.getNumberOfPages();
        PdfImportedPage page;
        PdfPTable tmpTable = new PdfPTable(1);
        tmpTable.setWidthPercentage(100);
        for(int i=1; i<=n; i++){
            page = writer.getImportedPage(reader,i);
            PdfPCell tmpCell = new PdfPCell();
            tmpCell.setBorder(0);
            tmpCell.addElement(Image.getInstance(page));
            tmpTable.addCell(tmpCell);
        }
        document.add(tmpTable);*/
        document.close();
        /*reader.close();*/
        return true;
    }

    private static void generatePodpisy(float lead, Document document, BaseFont bf) throws DocumentException {
        Paragraph tmp;
        PdfPCell cell;PdfPTable podpisy = new PdfPTable(2);
        podpisy.setSpacingBefore(50);
        podpisy.setWidthPercentage(80);
        tmp = new Paragraph("................................................\nZamawiający", new Font(bf, 7));
        tmp.setAlignment(Element.ALIGN_CENTER);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        cell.setPaddingRight(80);
        podpisy.addCell(cell);
        if(SharedData.getSkanPath().isEmpty()){
            tmp = new Paragraph("................................................\nDostawca", new Font(bf, 7));
            tmp.setAlignment(Element.ALIGN_CENTER);
            tmp.setLeading(lead);
            cell = generateCell(0,"",STANDARD_CELLSPAN,false);
            cell.addElement(tmp);
            cell.setPadding(3);
        } else cell=generateImageCell(SharedData.getSkanPath());
        cell.setPaddingLeft(70);
        podpisy.addCell(cell);
        document.add(podpisy);
    }

    public static Boolean order(String numerZamowienia, String path, Boolean show) throws IOException, DocumentException {
        Boolean res = order(numerZamowienia,path);
        if(res && show) {
            try {
                Desktop.getDesktop().open(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }


    public static Boolean proforma(String numerZamowienia, String path) throws IOException, DocumentException {
        filePath=path;
        PdfPCell cell;
        Paragraph tmp;
        float lead=12;

        order = prepareZamowienie(numerZamowienia);
        proforma = prepareProforma(numerZamowienia);
        client = order.getClient();

        Document document = new Document();
        BaseFont bf = BaseFont.createFont("calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(bf, 9);
        Font boldFont = new Font(BaseFont.createFont("calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED),9);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

        String stopka = "";
        writer.setPageEvent(new PdfFooterEvent(stopka,false));
        Rectangle rect = new Rectangle(30, 30, 550, 800);
        writer.setBoxSize("art", rect);
        document.setPageSize(PageSize.A4);
        document.setMargins(36,36,36,36);
        document.open();

        /*----------NAGLOWEK----------*/
        tmp = new Paragraph("FAKTURA PROFORMA NR "+proforma.getProformaNumber(),boldFont);
        tmp.getFont().setSize(16);
        tmp.getFont().setColor(new Color(200,200,200));
        tmp.setAlignment(Element.ALIGN_RIGHT);
        document.add(tmp);
        tmp.getFont().setSize(9);
        tmp.getFont().setColor(new Color(0,0,0));

        PdfPTable naglowek = new PdfPTable(4);
        naglowek.setWidthPercentage(100);
        naglowek.addCell(generateImageCell("logo.jpg"));
        naglowek.addCell(generateCell(0,"",3,false));
        document.add(naglowek);

        /*------------ SPRZEDAJĄCY -------------------*/

        PdfPTable dostawcaZamawiajacy = new PdfPTable(2);
        dostawcaZamawiajacy.setWidthPercentage(100);


        PdfPTable dostawca = new PdfPTable(1);
        dostawca.setHorizontalAlignment(Element.ALIGN_LEFT);
        tmp = new Paragraph("Sprzedający", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        dostawca.addCell(cell);


        tmp = new Paragraph(getDaneDostawcy(), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        dostawca.addCell(cell);
        cell = generateCell(0,"Nr konta bankowego Dostawcy:",STANDARD_CELLSPAN,true,9);
        cell.setPadding(3);
        dostawca.addCell(cell);

        tmp = new Paragraph(getBank(), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        dostawca.addCell(cell);
        cell = new PdfPCell(dostawca);
        cell.setBorder(0);
        dostawcaZamawiajacy.addCell(cell);

        /*----------DANE ZAMAWIAJĄCEGO----------*/
        PdfPTable zamawiajacy = new PdfPTable(2);
        zamawiajacy.setHorizontalAlignment(Element.ALIGN_RIGHT);

        tmp = new Paragraph("Nabywca", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        zamawiajacy.addCell(cell);

        zamawiajacy.addCell(generateCell(0,"",STANDARD_CELLSPAN,false));

        tmp = new Paragraph(prepareDaneDoFaktury(client), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        zamawiajacy.addCell(cell);

        zamawiajacy.addCell(generateCell(0,"",STANDARD_CELLSPAN,false));

        tmp = new Paragraph("NIP: "+ client.getNip(),boldFont);
        cell = new PdfPCell(tmp);
        cell.setPadding(3);
        cell.setBorder(0);
        zamawiajacy.addCell(cell);



        /*----------DATY-----------*/

        PdfPTable daty = new PdfPTable(1);
        daty.setWidthPercentage(100);

        tmp = new Paragraph("Data wystawienia: ",font);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        tmp.add(new Paragraph(proforma.getIssueDate().toString(),boldFont));
        cell.addElement(tmp);
        cell.setPadding(2);
        daty.addCell(cell);

        tmp = new Paragraph("Data sprzedaży: ",font);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        tmp.add(new Paragraph(proforma.getSaleDate().toString(),boldFont));
        cell.addElement(tmp);
        cell.setPadding(2);
        daty.addCell(cell);

        tmp = new Paragraph("Sposób zapłaty: ",font);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        tmp.add(new Paragraph(proforma.getPaymentMethod(),boldFont));
        cell.addElement(tmp);
        cell.setPadding(2);
        daty.addCell(cell);

        tmp = new Paragraph("Termin zapłaty: ",font);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        tmp.add(new Paragraph(proforma.getPaymentDate().toString(),boldFont));
        cell.addElement(tmp);
        cell.setPadding(2);
        daty.addCell(cell);

        cell=new PdfPCell(daty);
        cell.setBorder(0);
        cell.setPaddingTop(10);
        zamawiajacy.addCell(cell);

        cell=new PdfPCell(zamawiajacy);
        cell.setBorder(1);
        dostawcaZamawiajacy.addCell(cell);
        document.add(dostawcaZamawiajacy);
        /*----------PRZEDMIOTY----------*/

        PdfPTable przedmioty=new PdfPTable(new float[]{1,4,1,3,3,2,2,3});
        przedmioty.setWidthPercentage(100);
        cell = generateCell(1, "Lp.", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        cell.setPaddingRight(0);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Nazwa towaru lub usługi", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Ilość", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Cena jednostkowa\nbez podatku", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Wartość towaru\nbez podatku", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Stawka podatku", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Kwota podatku", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Wartość towaru\nwraz z podatkiem", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);

        przedmioty.setSpacingBefore(5);
        document.add(przedmioty);
        document.add(prepareTowarTableProforma());


        /*-----------PODSUMOWANIE----------*/

        PdfPTable podsumowanie = new PdfPTable(2);
        podsumowanie.setHorizontalAlignment(Element.ALIGN_RIGHT);
        podsumowanie.setWidthPercentage(50);
        podsumowanie.setSpacingBefore(20);

        PdfPTable lewa = new PdfPTable(1);
        PdfPTable prawa = new PdfPTable(1);


        cell = generateCell(1, "Należność ogółem:", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        lewa.addCell(cell);

        cell = generateCell(0, "Zapłacono:", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        lewa.addCell(cell);

        cell = generateCell(0, "Do zapłaty:", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        lewa.addCell(cell);

        Float doZaplaty = 0f;
        tmp = new Paragraph(Utils.formatPln(23.33f),boldFont);//TODO: ?????
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(1, "", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        cell.addElement(tmp);
        prawa.addCell(cell);
        if (order.getIsPaid().toLowerCase().contains("zaliczka")) {
            tmp = new Paragraph(Utils.formatPln(order.getAdvance().floatValue()), boldFont);
            doZaplaty = 1 - order.getAdvance().floatValue(); //toDo: zamiast 1: wyliczenie kwoty brutto
        } else if (order.getIsPaid().toLowerCase().contains("całość")) {
            tmp = new Paragraph(Utils.formatPln(1f), boldFont); //toDo: zamiast 1: wyliczenie kwoty brutto
            doZaplaty = 0f;
        } else if (order.getIsPaid().toLowerCase().contains("nie")) {
            tmp = new Paragraph("0.0 zł", boldFont);
            doZaplaty = 1f; //toDo: zamiast 1: wyliczenie kwoty brutto
        }
        doZaplaty = Math.round(doZaplaty*100)/100f;
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0, "", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        cell.addElement(tmp);
        prawa.addCell(cell);

        tmp = new Paragraph(Utils.formatPln(doZaplaty),boldFont);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0, "", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        cell.addElement(tmp);
        prawa.addCell(cell);

        cell=new PdfPCell(lewa);
        cell.setBorder(0);
        podsumowanie.addCell(cell);
        cell=new PdfPCell(prawa);
        cell.setBorder(0);
        podsumowanie.addCell(cell);

        document.add(podsumowanie);
        tmp = new Paragraph("Słownie: "+ PriceToWords.toWords(doZaplaty), font);
        tmp.setSpacingBefore(20);
        document.add(tmp);


        document.close();
        return true;
    }
    public static Boolean proforma(String numerZamowienia, String path, Boolean show) throws IOException, DocumentException {
        Boolean res = proforma(numerZamowienia, path);
        if(res && show){
            Desktop.getDesktop().open(new File(path));
        }
        return res;
    }
    /*------------FUNKCJE POMOCNICZE---------------*/


    private static PdfPTable prepareTowarTableProforma() {
        ArrayList<OrderedArticle> lista = getOrderedArticles();
        PdfPCell cell;
        PdfPTable przedmiotyTresc = new PdfPTable(new float[]{1, 4, 1, 3, 3, 2, 2, 3});
        przedmiotyTresc.setWidthPercentage(100);
        int lastLp=0;
        int lp=1;
        for(OrderedArticle i : lista) {
            cell = generateCell(0, (lp++)+".", STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            cell.setPaddingRight(0);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, i.getDescription().trim(), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, i.getAmount().toString(), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, Utils.formatPln(i.getUnitPrice().floatValue()), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, Utils.formatPln(i.getNetPrice().floatValue()), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, order.getVat().toString().replace(".00","").replace(".0","") + "%", STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            Float taxRate=order.getVat() * i.getNetPrice().floatValue() / 100;
            cell = generateCell(0, Utils.formatPln(taxRate), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, Utils.formatPln(taxRate+i.getNetPrice().floatValue()), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            lastLp++;
        }if(countNetCost(order).floatValue()-order.getDeliveryCost().floatValue()<5000 && order.getDeliveryCost().floatValue()>0){
            cell = generateCell(0, (lastLp+1)+".", STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            cell.setPaddingRight(0);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, "Dostawa", STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, "1", STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, Utils.formatPln(order.getDeliveryCost().floatValue()), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, Utils.formatPln(order.getDeliveryCost().floatValue()), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, order.getVat().toString().replace(".00","").replace(".0","") + "%", STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            Float taxRate=order.getVat() * order.getDeliveryCost().floatValue() / 100;
            cell = generateCell(0, Utils.formatPln(taxRate), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);
            cell = generateCell(0, Utils.formatPln(order.getDeliveryCost().floatValue()+taxRate), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            przedmiotyTresc.addCell(cell);

        }
        return przedmiotyTresc;
    }

    private static BigDecimal countNetCost(Order order) {
        BigDecimal cost = new BigDecimal("0.0");
        for(OrderedArticle orderedArticle : getOrderedArticles()){
            cost = cost.add(orderedArticle.getNetPrice());
        }
        return cost;
    }

    private static Proforma prepareProforma(String numerZamowienia) {
        //wyszukanie proformy do zamówienia
        return new Proforma();
    }



    private static PdfPTable prepareKwoty() {
        PdfPTable t = new PdfPTable(5);
        t.setWidthPercentage(100);
        PdfPCell cell;

        cell = generateCell(1,"Zaliczka" + order.getAdvance().toString(),STANDARD_CELLSPAN,true);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(1,"VAT",STANDARD_CELLSPAN,true);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(1,"Wartość netto",STANDARD_CELLSPAN,true);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(1,"Wartość brutto",STANDARD_CELLSPAN,true);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(1,"Waga łącznie",STANDARD_CELLSPAN,true);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(0, Utils.formatPln(order.getAdvance().floatValue()),STANDARD_CELLSPAN,false);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(0, Math.round(order.getVat().doubleValue())+"%",STANDARD_CELLSPAN,false);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(0, Utils.formatPln(1f),STANDARD_CELLSPAN,false); //toDo: cena netto
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(0, Utils.formatPln(1f),STANDARD_CELLSPAN,false); //toDo: cena brutto
        cell.setPadding(1);
        t.addCell(cell);
/*
        cell = generateCell(0, Utils.formatKg(order.getWaga()),STANDARD_CELLSPAN,false);
        cell.setPadding(1);
        t.addCell(cell);*/
        t.setSpacingBefore(10);

        return t;
    }

    private static String getDaneDostawcy() {
        File file = new File("dostawca.txt");
        Scanner sc;
        try {
            sc = new Scanner(file, "UTF-8");
            sc.useDelimiter("$^");
            String s = sc.next();
            sc.close();
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static String getBank() {
        File file = new File("bank.txt");
        Scanner sc;
        try {
            sc = new Scanner(file, "UTF-8");
            sc.useDelimiter("$^");
            String s = sc.next();
            sc.close();
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static Order prepareZamowienie(String numerZamowienia) {
        //pobranie zamówienia o danym numerze
        return new Order();
    }

    private static String prepareDaneDoFaktury(Client client) {
        return client.getName()+(client.getNip().trim().isEmpty()?"":"\nNIP: "+ client.getNip())+"\n"+ client.getStreet()+"\n"+ client.getPostCode()+" "+ client.getStreet();
    }

    private static String prepareDeliveryAddress(Client client) {
        return client.getNameDelivery()+"\n"+ client.getStreetDelivery()+"\n"+ client.getPostCodeDelivery()+" "+ client.getCityDelivery();
    }

    private static Client prepareKlient(int id) {
        //pobranie klienta o danym id
        return new Client();
    }
    private static ArrayList<OrderedArticle> getOrderedArticles(){
        //pobranie listy towarów
        return new ArrayList<>();
    }
    private static PdfPTable prepareTowarTable(){
        PdfPTable t = new PdfPTable(7);
        t.setWidthPercentage(100);
        PdfPCell cell;
        int lastLp=0;
        Integer lp=1;
        for(OrderedArticle i : getOrderedArticles()){
            cell = generateCell(0, (lp++).toString()+".",STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, i.getDescription().trim(),2,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, i.getAmount().toString(),STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, Utils.formatPln(i.getUnitPrice().floatValue()),STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, Utils.formatPln(i.getNetPrice().floatValue()),STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, Utils.formatKg(i.getWeight()),STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            lastLp=lp;
        }
        if(1f- order.getDeliveryCost().floatValue()<5000 && order.getDeliveryCost().floatValue()>0){ //toDo: zamiast 1f: cena netto
            cell = generateCell(0, (lastLp+1)+".",STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, "Dostawa",2,false);
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, "-",STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, "-",STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, Utils.formatPln(order.getDeliveryCost().floatValue()),STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, "-",STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, "-",STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
        }

        return t;
    }

    public static Boolean protokol(ArrayList<OrderedArticle> doProtokolu, ArrayList<ComboBox> rolkiArr, ArrayList<String> ilosciRolek, LocalDate dzienSporzadzenia, LocalDate dzienPrzekazania, String miejsce, String reprezentantVesstige, String nazwaKlienta, String reprezentantKlienta, String filePath) throws IOException, DocumentException {
        Document document = new Document();
        BaseFont bf = BaseFont.createFont("calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(bf, 10);
        Font boldFont = new Font(BaseFont.createFont("calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED),11);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        Rectangle rect = new Rectangle(30, 30, 550, 800);
        writer.setBoxSize("art", rect);
        document.setPageSize(PageSize.A4);
        document.setMargins(36,36,36,36);
        document.open();

        /*------------------NAGLOWEK---------------------*/
        PdfPTable naglowek = new PdfPTable(4);
        naglowek.setWidthPercentage(100);
        naglowek.addCell(generateImageCell("logo.jpg"));
        naglowek.addCell(generateCell(0, "",STANDARD_CELLSPAN,false, new Integer(10)));
        naglowek.addCell(generateCell(0, "",STANDARD_CELLSPAN,false, new Integer(10)));
        naglowek.addCell(generateCell(0, "",STANDARD_CELLSPAN,false, new Integer(10)));
        document.add(naglowek);

        /*-----------------TRESC-----------------------*/
        Paragraph p = new Paragraph();
        p.setFont(boldFont);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        p.setSpacingBefore(20);
        p.add("PROTOKÓŁ DOSTARCZENIA TOWARU");
        document.add(p);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String dzienPr = sdf.format(Date.valueOf(dzienPrzekazania));
        String dzienSp = sdf.format(Date.valueOf(dzienSporzadzenia));
        String sb = "Sporządzony dnia " +
                dzienSp +
                " r. w " +
                miejsce +
                ".\nDnia " +
                dzienPr +
                " r. dokonano przekazania niżej wymienionego towaru pomiędzy:\n" +
                "Sprzedawcą - <FIRMA>, reprezentowanym przez: " +
                reprezentantVesstige +
                "\na\n" +
                "Kupującym - " +
                nazwaKlienta +
                ", reprezentowanym przez: " +
                reprezentantKlienta +
                ".";
        p=new Paragraph();
        p.setFont(font);
        p.setAlignment(Element.ALIGN_LEFT);
        p.add(sb);
        document.add(p);


        /*-------------Articles---------------*/
        p=new Paragraph();
        p.setFont(boldFont);
        p.setSpacingAfter(10);
        p.setSpacingBefore(30);
        p.add("Specyfikacja przekazanego towaru:");
        document.add(p);

        PdfPTable tHead = new PdfPTable(8);

        PdfPTable t = new PdfPTable(8);
        t.setWidthPercentage(100);
        PdfPCell cell;


        cell = generateCell(1, "Lp.",STANDARD_CELLSPAN,true, new Integer(10));
        cell.setPadding(2);
        t.addCell(cell);
        cell = generateCell(1, "Opis towaru",3,true, new Integer(10));
        cell.setPadding(2);
        t.addCell(cell);
        cell = generateCell(1, "Ilość [m2]",2,true, new Integer(10));
        cell.setPadding(2);
        t.addCell(cell);
        cell = generateCell(1, "Ilość rolek",2,true, new Integer(10));
        cell.setPadding(2);
        t.addCell(cell);

        int lp=1;
        for(OrderedArticle i : doProtokolu){
            cell = generateCell(0, (lp++)+".",STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, i.getDescription().trim(),3,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, i.getAmount().toString(),2,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, ilosciRolek.get(doProtokolu.indexOf(i)),2,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
        }
        document.add(tHead);
        document.add(t);

        p = new Paragraph();
        p.setFont(font);
        p.setSpacingBefore(30);
        p.setSpacingAfter(20);
        p.add("Zleceniobiorca potwierdza, że przekazany towar został dostarczony w ww. ilości, bez widocznych uszkodzeń.");
        document.add(p);

        generatePodpisy(12, document, bf);
        document.close();
        Desktop.getDesktop().open(new File(filePath));
        return true;
    }
}
