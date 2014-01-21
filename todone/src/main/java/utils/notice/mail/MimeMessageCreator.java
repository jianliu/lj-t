package utils.notice.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-6
 * Time: 上午10:59
 * To change this template use File | Settings | File Templates.
 */
public class MimeMessageCreator {

    public static MimeMessage createMessge(String user) throws MessagingException {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        MimeMessage mailMessage = senderImpl.createMimeMessage();
        String toList = user;
        InternetAddress[] iaToList= InternetAddress.parse(toList);
        mailMessage.setRecipients(Message.RecipientType.TO, iaToList);
       return mailMessage;
    }

}
