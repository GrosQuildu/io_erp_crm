package main.java.erp_crm.backend.pdf;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import main.java.erp_crm.backend.PriceToWords;
import main.java.erp_crm.backend.SharedData;
import main.java.erp_crm.backend.Utils;
import main.java.erp_crm.backend.model.DBData;
import main.java.erp_crm.backend.model.common.Client;
import main.java.erp_crm.backend.model.erp.Order;
import main.java.erp_crm.backend.model.erp.OrderedArticle;
import main.java.erp_crm.backend.model.erp.Proforma;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
                bf = BaseFont.createFont("/calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
            else
                bf = BaseFont.createFont("/calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);

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
                bf = BaseFont.createFont("/calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
            else
                bf = BaseFont.createFont("/calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);

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
                bf = BaseFont.createFont("/calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
            else
                bf = BaseFont.createFont("/calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);

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
        if(!fileName.isEmpty()) {
            try {
                Image image = Image.getInstance(fileName);
                PdfPCell cell = new PdfPCell(image, true);
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
        }

        PdfPCell cell = new PdfPCell();
        cell.setPaddingTop(0);
        cell.setPaddingLeft(0);
        cell.setPaddingBottom(32);
        cell.setPaddingRight(35);
        cell.setBorder(0);
        cell.setColspan(1);
        return cell;
    }

    public static Boolean order(Order o, String path) throws IOException, DocumentException {
        filePath=path;
        order = o;
        PdfPCell cell;
        Paragraph tmp;
        float lead;

        client = order.getClient();

        Document document = new Document();
        BaseFont bf = BaseFont.createFont("/calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(bf, 9);
        Font boldFont = new Font(BaseFont.createFont("/calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED),9);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        writer.setPageEmpty(false);

        Rectangle rect = new Rectangle(30, 30, 550, 800);
        writer.setBoxSize("art", rect);
        document.setPageSize(PageSize.A4);
        document.setMargins(36,36,36,36);
        document.open();

        /*----------NAGLOWEK----------*/
        PdfPTable header = new PdfPTable(4);
        header.setWidthPercentage(100);
        header.addCell(generateImageCell(SharedData.getConfig().getCompanyLogoPath()));
        PdfPTable leftTable = new PdfPTable(1);
        PdfPTable rightTable = new PdfPTable(1);
        lead=12;
        tmp = new Paragraph("Order No.:", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        leftTable.addCell(cell);
        tmp = new Paragraph("Order date:", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        leftTable.addCell(cell);
        tmp = new Paragraph("Client:", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        leftTable.addCell(cell);
        cell = new PdfPCell(leftTable);
        cell.setColspan(2);
        cell.setBorder(0);
        cell.setPaddingLeft(120);
        header.addCell(cell);


        tmp = new Paragraph((order.getOrderNumber() != null ? order.getOrderNumber() : ""), boldFont);
        tmp.setLeading(lead);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(3);
        rightTable.addCell(cell);
        tmp = new Paragraph((order.getOrderDate() != null ? order.getOrderDate().toString() : ""), font);
        tmp.setLeading(lead);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        rightTable.addCell(cell);
        cell.setPadding(3);

        tmp = new Paragraph(
                    (SharedData.getConfig().getEmployeeFirstName() != null ? SharedData.getConfig().getEmployeeFirstName() : "")+" " +
                        (SharedData.getConfig().getEmployeeName() != null ? SharedData.getConfig().getEmployeeName() : "")+"\ntel. "+
                        (SharedData.getConfig().getEmployeeTelephone() != null ? SharedData.getConfig().getEmployeeTelephone() : "")+"\nmail: "+
                        (SharedData.getConfig().getEmployeeMail() != null ? SharedData.getConfig().getEmployeeMail() : ""), font);
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
        header.addCell(cell);
        document.add(header);
        /*----------DANE DOSTAWCY---------------*/
        PdfPTable provider = new PdfPTable(1);
        provider.setHorizontalAlignment(Element.ALIGN_LEFT);
        tmp = new Paragraph("Provider", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        provider.addCell(cell);

        PdfPTable providerPurchaser = new PdfPTable(3);
        providerPurchaser.setWidthPercentage(100);

        tmp = new Paragraph(getProviderData(), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        provider.addCell(cell);
        cell = generateCell(0,"Provider's bank account number:",STANDARD_CELLSPAN,true,9);
        cell.setPadding(3);
        provider.addCell(cell);

        tmp = new Paragraph(getBank(), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        provider.addCell(cell);
        cell = new PdfPCell(provider);
        cell.setBorder(0);
        providerPurchaser.addCell(cell);

        /*----------DANE ZAMAWIAJĄCEGO----------*/
        PdfPTable purchaser = new PdfPTable(2);
        purchaser.setHorizontalAlignment(Element.ALIGN_RIGHT);

        tmp = new Paragraph("Invoice data", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        purchaser.addCell(cell);

        tmp = new Paragraph("Delivery address", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        purchaser.addCell(cell);

        tmp = new Paragraph(prepareInvoiceData(client), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        purchaser.addCell(cell);

        tmp = new Paragraph(prepareDeliveryAddress(client), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        purchaser.addCell(cell);

        tmp = new Paragraph("Client", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        purchaser.addCell(cell);


        purchaser.addCell(generateCell(0,"",STANDARD_CELLSPAN,false));


        purchaser.addCell(generateCell(0,"",STANDARD_CELLSPAN,false));

        cell=new PdfPCell(purchaser);
        cell.setColspan(2);
        cell.setBorder(1);
        providerPurchaser.addCell(cell);
        providerPurchaser.setSpacingBefore(10);
        document.add(providerPurchaser);

        /*----------PRZEDMIOTY----------*/
        PdfPTable articles = new PdfPTable(7);
        articles.setWidthPercentage(100);
        cell = generateCell(1, "No.", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        cell.setPaddingRight(0);
        articles.addCell(cell);
        cell = generateCell(1, "Item description", 2, true);
        cell.setPadding(2);
        articles.addCell(cell);
        cell = generateCell(1, "Quantity", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        articles.addCell(cell);
        cell = generateCell(1, "Price/pcs.\n", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        articles.addCell(cell);
        cell = generateCell(1, "Net value", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        articles.addCell(cell);
        cell = generateCell(1, "Weight [kg]", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        articles.addCell(cell);
        Paragraph p = new Paragraph("\nOrdered:\n", boldFont);
        p.setLeading(12);
        document.add(p);

        articles.setSpacingBefore(5);
        document.add(articles);
        document.add(prepareArticlesTable());


        /*----------KWOTY---------*/
        document.add(prepareAmounts());


        /*----------TERMIN, UWAGI, WARUNKI---------*/
        p = new Paragraph("Realization date", boldFont);
        p.setLeading(12);
        p.getFont().setSize(9);
        p.setSpacingBefore(10);
        document.add(p);
        
        p = new Paragraph((order.getRealizationDate() != null ? order.getRealizationDate().toString() : ""), font);
        
        p.setLeading(12);
        p.getFont().setSize(9);
        document.add(p);
        p = new Paragraph("Payment conditions", boldFont);
        p.getFont().setSize(9);
        p.setLeading(12);
        document.add(p);
        p = new Paragraph((order.getConditions() != null ? order.getConditions() : ""), new Font(bf,9));
        p.setLeading(12);
        document.add(p);

        p = new Paragraph("\nComments:\n"+ (order.getComments() != null ? order.getComments() : ""), new Font(bf, 8));
        p.setLeading(10);
        p.setSpacingBefore(5);
        document.add(p);



        /*----------PODPISY-----------*/
        generateSignatures(lead, document, bf);

        document.close();

        return true;
    }

    private static void generateSignatures(float lead, Document document, BaseFont bf) throws DocumentException {
        Paragraph tmp;
        PdfPCell cell;PdfPTable signatures = new PdfPTable(2);
        signatures.setSpacingBefore(50);
        signatures.setWidthPercentage(80);
        tmp = new Paragraph("................................................\nPurchaser", new Font(bf, 7));
        tmp.setAlignment(Element.ALIGN_CENTER);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        cell.setPaddingRight(80);
        signatures.addCell(cell);
        if(SharedData.getConfig().getSignatureScanPath() == null || SharedData.getConfig().getSignatureScanPath().isEmpty()){
            tmp = new Paragraph("................................................\nProvider", new Font(bf, 7));
            tmp.setAlignment(Element.ALIGN_CENTER);
            tmp.setLeading(lead);
            cell = generateCell(0,"",STANDARD_CELLSPAN,false);
            cell.addElement(tmp);
            cell.setPadding(3);
        } else cell=generateImageCell(SharedData.getConfig().getSignatureScanPath());
        cell.setPaddingLeft(70);
        signatures.addCell(cell);
        document.add(signatures);
    }

    public static Boolean order(Order order, String path, Boolean show) throws IOException, DocumentException {
        Boolean res = order(order,path);
        if(res && show) {
            try {
                Desktop.getDesktop().open(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }


    public static Boolean proforma(Order o, String path) throws IOException, DocumentException {
        filePath=path;
        order = o;
        PdfPCell cell;
        Paragraph tmp;
        float lead=12;

        proforma = prepareProforma(order);
        client = order.getClient();

        Document document = new Document();
        BaseFont bf = BaseFont.createFont("/calibri.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(bf, 9);
        Font boldFont = new Font(BaseFont.createFont("/calibrib.ttf", BaseFont.CP1250, BaseFont.EMBEDDED),9);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

        String stopka = "";
        writer.setPageEvent(new PdfFooterEvent(stopka,false));
        Rectangle rect = new Rectangle(30, 30, 550, 800);
        writer.setBoxSize("art", rect);
        document.setPageSize(PageSize.A4);
        document.setMargins(36,36,36,36);
        document.open();

        /*----------NAGLOWEK----------*/
        tmp = new Paragraph("PROFORMA INVOICE NO. "+proforma.getProformaNumber(),boldFont);
        tmp.getFont().setSize(16);
        tmp.getFont().setColor(new Color(200,200,200));
        tmp.setAlignment(Element.ALIGN_RIGHT);
        document.add(tmp);
        tmp.getFont().setSize(9);
        tmp.getFont().setColor(new Color(0,0,0));

        PdfPTable naglowek = new PdfPTable(4);
        naglowek.setWidthPercentage(100);
        naglowek.addCell(generateImageCell(SharedData.getConfig().getCompanyLogoPath()));
        naglowek.addCell(generateCell(0,"",3,false));
        document.add(naglowek);

        /*------------ SPRZEDAJĄCY -------------------*/

        PdfPTable dostawcaZamawiajacy = new PdfPTable(2);
        dostawcaZamawiajacy.setWidthPercentage(100);


        PdfPTable dostawca = new PdfPTable(1);
        dostawca.setHorizontalAlignment(Element.ALIGN_LEFT);
        tmp = new Paragraph("Seller", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        dostawca.addCell(cell);


        tmp = new Paragraph(getProviderData(), font);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        dostawca.addCell(cell);
        cell = generateCell(0,"Provider's bank account number:",STANDARD_CELLSPAN,true,9);
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

        tmp = new Paragraph("Purchaser", boldFont);
        tmp.setLeading(lead);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        cell.addElement(tmp);
        cell.setPadding(3);
        zamawiajacy.addCell(cell);

        zamawiajacy.addCell(generateCell(0,"",STANDARD_CELLSPAN,false));

        tmp = new Paragraph(prepareInvoiceData(client), font);
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

        tmp = new Paragraph("Date of invoice: ",font);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        tmp.add(new Paragraph(proforma.getIssueDate().toString(),boldFont));
        cell.addElement(tmp);
        cell.setPadding(2);
        daty.addCell(cell);

        tmp = new Paragraph("Sale date: ",font);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        tmp.add(new Paragraph(proforma.getSaleDate().toString(),boldFont));
        cell.addElement(tmp);
        cell.setPadding(2);
        daty.addCell(cell);

        tmp = new Paragraph("Payment method: ",font);
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(0,"",STANDARD_CELLSPAN,false);
        tmp.add(new Paragraph(proforma.getPaymentMethod(),boldFont));
        cell.addElement(tmp);
        cell.setPadding(2);
        daty.addCell(cell);

        tmp = new Paragraph("Date of payment: ",font);
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
        cell = generateCell(1, "No.", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        cell.setPaddingRight(0);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Item or service name", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Quantity", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Unit price without taxes", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Value of the goods without taxes", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Tax rate", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Amount of tax", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);
        cell = generateCell(1, "Value of the goods with taxes", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        przedmioty.addCell(cell);

        przedmioty.setSpacingBefore(5);
        document.add(przedmioty);
        document.add(prepareArticleTableProforma());


        /*-----------PODSUMOWANIE----------*/

        PdfPTable podsumowanie = new PdfPTable(2);
        podsumowanie.setHorizontalAlignment(Element.ALIGN_RIGHT);
        podsumowanie.setWidthPercentage(50);
        podsumowanie.setSpacingBefore(20);

        PdfPTable lewa = new PdfPTable(1);
        PdfPTable prawa = new PdfPTable(1);


        cell = generateCell(1, "Total:", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        lewa.addCell(cell);

        cell = generateCell(0, "Paid:", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        lewa.addCell(cell);

        cell = generateCell(0, "To pay:", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        lewa.addCell(cell);

        Float doZaplaty = 0f;
        tmp = new Paragraph(Utils.formatPln(23.33f),boldFont);//TODO: ?????
        tmp.setAlignment(Element.ALIGN_RIGHT);
        cell = generateCell(1, "", STANDARD_CELLSPAN, true);
        cell.setPadding(2);
        cell.addElement(tmp);
        prawa.addCell(cell);
        if (order.getIsPaid().contains("advance")) {
            tmp = new Paragraph(Utils.formatPln(order.getAdvance().floatValue()), boldFont);
            doZaplaty = 1 - order.getAdvance().floatValue(); //toDo: zamiast 1: wyliczenie kwoty brutto
        } else if (order.getIsPaid().toLowerCase().contains("całość")) {
            tmp = new Paragraph(Utils.formatPln(1f), boldFont); //toDo: zamiast 1: wyliczenie kwoty brutto
            doZaplaty = 0f;
        } else if (order.getIsPaid().toLowerCase().contains("no")) {
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
        tmp = new Paragraph("In words: "+ PriceToWords.toWords(doZaplaty), font);
        tmp.setSpacingBefore(20);
        document.add(tmp);


        document.close();
        return true;
    }
    public static Boolean proforma(Order order, String path, Boolean show) throws IOException, DocumentException {
        Boolean res = proforma(order, path);
        if(res && show){
            Desktop.getDesktop().open(new File(path));
        }
        return res;
    }
    /*------------FUNKCJE POMOCNICZE---------------*/


    private static PdfPTable prepareArticleTableProforma() {

        PdfPCell cell;
        PdfPTable articlesContent = new PdfPTable(new float[]{1, 4, 1, 3, 3, 2, 2, 3});
        articlesContent.setWidthPercentage(100);
        int lastLp=0;
        int lp=1;
        for(OrderedArticle i : order.getOrderedArticles()) {
            if (i != null) {
                cell = generateCell(0, (lp++) + ".", STANDARD_CELLSPAN, false);
                cell.setPadding(2);
                cell.setPaddingRight(0);
                articlesContent.addCell(cell);
                cell = generateCell(0, (i.getArticle() != null && i.getArticle().getName() != null ?
                        i.getArticle().getName().trim() : ""), STANDARD_CELLSPAN, false);
                cell.setPadding(2);
                articlesContent.addCell(cell);
                cell = generateCell(0, (i.getAmount() != null ? i.getAmount().toString() : ""), STANDARD_CELLSPAN, false);
                cell.setPadding(2);
                articlesContent.addCell(cell);
                cell = generateCell(0, Utils.formatPln(i.getArticle() != null && i.getArticle().getUnitPrice() != null ?
                        i.getArticle().getUnitPrice().floatValue() : 0f), STANDARD_CELLSPAN, false);
                cell.setPadding(2);
                articlesContent.addCell(cell);
                cell = generateCell(0, Utils.formatPln(i.getNetPrice() != null ? i.getNetPrice().floatValue() : 0f), STANDARD_CELLSPAN, false);
                cell.setPadding(2);
                articlesContent.addCell(cell);
                cell = generateCell(0, percentFormat(order.getVat()), STANDARD_CELLSPAN, false);
                cell.setPadding(2);
                articlesContent.addCell(cell);
                Float vat = order.getVat();

                vat = vat < 1 && vat > 0 ? vat * 100 : vat;
                Float taxRate = vat * i.getNetPrice().floatValue()/100;
                cell = generateCell(0, Utils.formatPln(taxRate), STANDARD_CELLSPAN, false);
                cell.setPadding(2);
                articlesContent.addCell(cell);
                cell = generateCell(0, Utils.formatPln(taxRate + i.getNetPrice().floatValue()), STANDARD_CELLSPAN, false);
                cell.setPadding(2);
                articlesContent.addCell(cell);
                lastLp++;
            }
        }

        if (order.getDeliveryCost().floatValue() > 0) {
            cell = generateCell(0, (lastLp + 1) + ".", STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            cell.setPaddingRight(0);
            articlesContent.addCell(cell);
            cell = generateCell(0, "Delivery", STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            articlesContent.addCell(cell);
            cell = generateCell(0, "1", STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            articlesContent.addCell(cell);
            cell = generateCell(0, Utils.formatPln(order.getDeliveryCost().floatValue()), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            articlesContent.addCell(cell);
            cell = generateCell(0, Utils.formatPln(order.getDeliveryCost().floatValue()), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            articlesContent.addCell(cell);
            cell = generateCell(0, percentFormat(order.getVat()), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            articlesContent.addCell(cell);
            Float vat = order.getVat();
            vat = vat < 1 && vat > 0 ? vat * 100 : vat;
            Float taxRate= vat * order.getDeliveryCost().floatValue() / 100;
            cell = generateCell(0, Utils.formatPln(taxRate), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            articlesContent.addCell(cell);
            cell = generateCell(0, Utils.formatPln(order.getDeliveryCost().floatValue() + taxRate), STANDARD_CELLSPAN, false);
            cell.setPadding(2);
            articlesContent.addCell(cell);

        }


        return articlesContent;
    }

    private static String percentFormat(Float vat) {
        vat = vat < 1 && vat > 0 ? vat * 100 : vat;
        return vat.toString().replace(".00", "").replace(".0", "") + "%";
    }


    private static Proforma prepareProforma(Order order) {
        for(Proforma proforma : DBData.getProformas()){
            if(proforma.getOrder().getOrderNumber().equals(order.getOrderNumber()))
                return proforma;
        }
        return null;
    }



    private static PdfPTable prepareAmounts() {
        PdfPTable t = new PdfPTable(5);
        t.setWidthPercentage(100);
        PdfPCell cell;

        cell = generateCell(1,"Advance",STANDARD_CELLSPAN,true);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(1,"VAT",STANDARD_CELLSPAN,true);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(1,"Net",STANDARD_CELLSPAN,true);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(1,"Gross",STANDARD_CELLSPAN,true);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(1,"Total weight",STANDARD_CELLSPAN,true);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(0, Utils.formatPln(order.getAdvance() != null ? order.getAdvance().floatValue() : 0f),STANDARD_CELLSPAN,false);
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(0, Math.round(order.getVat()!=null? order.getVat().doubleValue() : 0f)+"%",STANDARD_CELLSPAN,false);
        cell.setPadding(1);
        t.addCell(cell);
        float net = calculateNetPrice();
        cell = generateCell(0, Utils.formatPln(net),STANDARD_CELLSPAN,false); //toDo: cena netto
        cell.setPadding(1);
        t.addCell(cell);

        cell = generateCell(0, Utils.formatPln(net * (order.getVat() != null ? order.getVat() : 1)),STANDARD_CELLSPAN,false); //toDo: cena brutto
        cell.setPadding(1);
        t.addCell(cell);
        cell = generateCell(0, Utils.formatKg(calculateWeight(order.getOrderedArticles())),STANDARD_CELLSPAN,false);
        cell.setPadding(1);
        t.addCell(cell);
        t.setSpacingBefore(10);

        return t;
    }

    private static Float calculateNetPrice() {
        float price = 0;
        for(OrderedArticle article : order.getOrderedArticles()){
            if(article.getNetPrice()!=null) {
                price += article.getNetPrice().floatValue();
            }
        }
        return price;
    }

    private static float calculateWeight(List<OrderedArticle> articles){
        float weight = 0;
        for(OrderedArticle article : articles){
            if(article.getWeight()!=null) {
                weight += article.getWeight();
            }
        }
        return weight;
    }

    private static String getProviderData() {
        return SharedData.getConfig().getProviderData() != null ? SharedData.getConfig().getProviderData() : "";
    }
    private static String getBank() {
        return SharedData.getConfig().getBank() != null ? SharedData.getConfig().getBank() : "";
    }

    private static String prepareInvoiceData(Client client) {
        return (client.getName() != null ? client.getName() : "")+
                (client.getNip() != null && client.getNip().trim().isEmpty()?"":"\nNIP: "+ client.getNip())
                +"\n"+
                (client.getStreet() != null ? client.getStreet() : "")+"\n"+
                (client.getPostCode() != null ? client.getPostCode() : "")+" "+
                (client.getCity() != null ? client.getCity() : "");
    }

    private static String prepareDeliveryAddress(Client client) {
        return (client.getNameDelivery() != null ? client.getNameDelivery() : "")+"\n"+
                (client.getStreetDelivery() != null ? client.getStreetDelivery() : "")+"\n"+
                (client.getPostCodeDelivery() != null ? client.getPostCodeDelivery() : "")+" "+
                (client.getCityDelivery() != null ? client.getCityDelivery() : "");
    }

    private static PdfPTable prepareArticlesTable(){
        PdfPTable t = new PdfPTable(7);
        t.setWidthPercentage(100);
        PdfPCell cell;
        int lastLp=0;
        Integer lp=1;
        for(OrderedArticle i : order.getOrderedArticles()){
            if(i!=null){


                cell = generateCell(0, (lp++).toString()+".",STANDARD_CELLSPAN,false, new Integer(10));
                cell.setPadding(2);
                t.addCell(cell);
                cell = generateCell(0, (i.getArticle() != null && i.getArticle().getName() != null ?
                        i.getArticle().getName().trim() : ""),2,false, new Integer(10));
                cell.setPadding(2);
                t.addCell(cell);
                cell = generateCell(0, (i.getAmount() != null ? i.getAmount().toString() : ""),STANDARD_CELLSPAN,false, new Integer(10));
                cell.setPadding(2);
                t.addCell(cell);
                cell = generateCell(0, Utils.formatPln(i.getArticle() != null && i.getArticle().getUnitPrice()!=null?
                        i.getArticle().getUnitPrice().floatValue() : 0f),STANDARD_CELLSPAN,false, new Integer(10));
                cell.setPadding(2);
                t.addCell(cell);
                cell = generateCell(0, Utils.formatPln(i.getNetPrice()!=null ? i.getNetPrice().floatValue() : 0f),STANDARD_CELLSPAN,false, new Integer(10));
                cell.setPadding(2);
                t.addCell(cell);
                cell = generateCell(0, Utils.formatKg(i.getWeight() != null ? i.getWeight() : 0f),STANDARD_CELLSPAN,false, new Integer(10));
                cell.setPadding(2);
                t.addCell(cell);
                lastLp=lp;
            }
        }
        if(1f- order.getDeliveryCost().floatValue()<5000 && order.getDeliveryCost().floatValue()>0){ //toDo: zamiast 1f: cena netto
            cell = generateCell(0, (lastLp+1)+".",STANDARD_CELLSPAN,false, new Integer(10));
            cell.setPadding(2);
            t.addCell(cell);
            cell = generateCell(0, "Delivery",2,false);
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

}
