package simple;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import com.opensymphony.xwork2.ActionSupport;

public class Emailer extends ActionSupport {

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String from;
   private String password;
   private String to;
   private String subject;
   private String body;
   private  String fileAttachment = "D:\\Effort Tracker - Aug-2013.xls";

   static Properties properties = new Properties();
   static
   {
	   System.out.println("---1");
      properties.put("mail.smtp.host", "smtp.gmail.com");
      properties.put("mail.smtp.socketFactory.port", "465");
      properties.put("mail.smtp.socketFactory.class",
                     "javax.net.ssl.SSLSocketFactory");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.port", "465");
   }


   public String execute() 
   {
	   System.out.println("---2");
      String ret = SUCCESS;
      try
      {
         Session session = Session.getDefaultInstance(properties,  
            new javax.mail.Authenticator() {
            protected PasswordAuthentication 
            getPasswordAuthentication() {
            return new 
            PasswordAuthentication(from, password);
            }});

         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(from));
         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
         message.setSubject(subject);
//         message.setText(body);
// **********************************************************        
         MimeBodyPart messageBodyPart = new MimeBodyPart();  
         messageBodyPart.setText(body);  
         Multipart multipart = new MimeMultipart();  
         multipart.addBodyPart(messageBodyPart);  
      // Part two is attachment  
         messageBodyPart = new MimeBodyPart();  
         FileDataSource source = new FileDataSource(fileAttachment);  
         messageBodyPart.setDataHandler(new DataHandler(source));  
         messageBodyPart.setFileName(fileAttachment);  
         multipart.addBodyPart(messageBodyPart);  
     // Put parts in message  
         message.setContent(multipart);  
         System.out.println("---3");
         Transport.send(message);  
         System.out.println("success....................................");  
      }
      catch(Exception e)
      {
         ret = ERROR;
         e.printStackTrace();
      }
      return ret;
   }

   public String getFrom() {
      return from;
   }

   public void setFrom(String from) {
      this.from = from;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getTo() {
      return to;
   }

   public void setTo(String to) {
      this.to = to;
   }

   public String getSubject() {
      return subject;
   }

   public void setSubject(String subject) {
      this.subject = subject;
   }

   public String getBody() {
      return body;
   }

   public void setBody(String body) {
      this.body = body;
   }

   public static Properties getProperties() {
      return properties;
   }

   public static void setProperties(Properties properties) {
      Emailer.properties = properties;
   }
}