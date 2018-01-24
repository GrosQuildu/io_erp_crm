package main.java.erp_crm;

import main.java.erp_crm.backend.SharedData;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Mail {
    private ArrayList<InternetAddress> toMail,toMailCC;
    private String subject;
    private String message;
    private ArrayList<String> files;
    
    
    /*---- wysyłanie maila -----*/
    /*Parametry: 
    * toMail -> lista adresów widocznych w nagłówku maila
    * toMailCC -> lista adresów ukrytych
    * subject -> temat wiadomości
    * message -> treść wiadomości
    * files -> lista ścieżek do załączników
    * */


    public Mail(ArrayList<InternetAddress> toMail,ArrayList<InternetAddress> toMailCC, String subject, String message, ArrayList<String> files){
        this.toMail=toMail;
        this.toMailCC=toMailCC;
        this.subject = subject;
        this.message = message;
        this.files = files;
    }
    public Boolean send() throws IOException, MessagingException {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mailField.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mailField.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mailField.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mailField.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mailField.handlers.message_rfc822");
        Properties prop = System.getProperties();


        prop.put("mailField.smtp.host", SharedData.getConfig().getMailServerAddress());
        prop.put("mailField.smtp.port", SharedData.getConfig().getMailPort());
        prop.put("mailField.smtp.socketFactory.port", SharedData.getConfig().getMailPort());
        prop.put("mailField.smtp.auth", "true");
        prop.put("mailField.smtp.starttls.enable", "true");
        prop.put("mailField.user", SharedData.getConfig().getMailAddress());
        prop.put("mailField.password", SharedData.getConfig().getMailPassword());

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SharedData.getConfig().getMailAddress(), SharedData.getConfig().getMailPassword());
            }});
        session.setDebug(true);

        MimeMessage msg = new MimeMessage(session);
        try {
            String from = SharedData.getConfig().getDefaultSenderName()+" <"+ SharedData.getConfig().getMailAddress()+">";
            //msg.setFrom(new InternetAddress(from));
            //msg.setFrom(MimeUtility.encodeText(from));
            msg.setFrom(new InternetAddress(SharedData.getConfig().getMailAddress(), SharedData.getConfig().getDefaultSenderName(), "UTF-8"));
            InternetAddress[] adresy = new InternetAddress[toMail.size()];
            adresy = toMail.toArray(adresy);
            msg.setRecipients(Message.RecipientType.TO, adresy);
            if(SharedData.getConfig().getDefaultCC()!=null && !SharedData.getConfig().getDefaultCC().isEmpty())
                msg.setRecipients(Message.RecipientType.CC, SharedData.getConfig().getDefaultCC());
            if(toMailCC.size()>0){
                adresy = new InternetAddress[toMailCC.size()];
                adresy = toMailCC.toArray(adresy);
                msg.setRecipients(Message.RecipientType.BCC, adresy);
            }
            msg.setSubject(subject);
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(message);
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            if(files.size()>0) {
                List<MimeBodyPart> attachments = new ArrayList<>();
                for (String i : files) {
                    MimeBodyPart tmp = new MimeBodyPart();
                    tmp.attachFile(new File(i));
                    attachments.add(tmp);
                }
                for (MimeBodyPart i : attachments) {
                    mp.addBodyPart(i);
                }
            }
            msg.setContent(mp);
            msg.setSentDate(new Date());
            msg.saveChanges();
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
