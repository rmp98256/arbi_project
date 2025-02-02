import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.util.ByteArrayDataSource;
import javax.mail.internet.*;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

public class SendMailWithGeneratedPDF {
    public static void sendMail(byte[] pdfData, String selectedDate ) {
        // Email recipient and sender details
        String to = "jaibalramarbi@gmail.com";
        
        String from = "jaibalramarbi@gmail.com"; // Replace with your email
        String host = "smtp.gmail.com";
        String password = "cgyd wdlb mcad tuhg"; // Replace with your email password or App Password

        // Set up the properties for the mail session
        Properties props = new Properties();
        props.put("mail.smtp.user","username"); 
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "25"); 
        props.put("mail.debug", "true"); 
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.starttls.enable","true"); 
        props.put("mail.smtp.EnableSSL.enable","true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
        props.setProperty("mail.smtp.socketFactory.fallback", "false");   
        props.setProperty("mail.smtp.port", "465");   
        props.setProperty("mail.smtp.socketFactory.port", "465"); 

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jaibalramarbi@gmail.com", "cgyd wdlb mcad tuhg");
            }
        });
        session.setDebug(true);

        try {
            

            // Step 2: Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set the sender, recipient, and subject
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Report for "+selectedDate );

            // Step 3: Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Please find the attached PDF document.");

            // Step 4: Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Add the message body part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Step 5: Attach the PDF document
            messageBodyPart = new MimeBodyPart();
           
            DataSource dataSource = new ByteArrayDataSource(pdfData, "application/pdf");
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setFileName(selectedDate + ".pdf");
            multipart.addBodyPart(messageBodyPart);

            // Step 6: Set the complete message parts
            message.setContent(multipart);

            // Step 7: Send the email
            Transport.send(message);
            System.out.println("Email sent successfully with PDF attachment.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
