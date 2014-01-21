package utils.notice.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-6
 * Time: 上午10:45
 * To change this template use File | Settings | File Templates.
 */
public class MailWrappedSender implements MailService {

    @Autowired
    JavaMailSenderImpl mailSender;
    private Byte[] lock = new Byte[0];

    public void sendMail(String msg, MessageWrapper messageWrapper) throws MessagingException {
        synchronized (lock) {
            mailSender.setUsername(messageWrapper.getMailForm());
            mailSender.setPassword(messageWrapper.getPassword());
            validateSenderSession(messageWrapper.getMailForm(), messageWrapper.getPassword());
            MimeMessage mailMessage = MimeMessageCreator.createMessge(messageWrapper.getMailTo());
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
            messageHelper.setFrom(messageWrapper.getMailForm());
            messageHelper.setSubject(messageWrapper.getMailSubject());
            messageHelper.setText(wrapHtml(msg), true);
            mailSender.send(mailMessage);
        }
    }

    public String wrapHtml(String msg){
        String result= String.format("<html><body>%s</body></html>",msg);
        return result;
    }

    /**
     * 验证邮箱是否可以成功发送，重置session使其始终可用
     */
    public synchronized void validateSenderSession(String username, String password) {
        MyAuthenticator myauth = new MyAuthenticator(username, password);
        Session currentSession = mailSender.getSession();
        Session session = Session.getDefaultInstance(currentSession.getProperties(), myauth);
        mailSender.setSession(session);
    }

    public static void main(String[] args) throws MessagingException {
        ApplicationContext appContext= new ClassPathXmlApplicationContext("classpath*:spring/*.xml");
        MailWrappedSender sendMail = (MailWrappedSender)appContext.getBean("mailService");
        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.setMailForm("liujian@cd.tuan800.com");
        messageWrapper.setMailSubject("日志");
        messageWrapper.setMailTo("liujian@cd.tuan800.com");
        messageWrapper.setPassword("yjwvfp520");
        sendMail.sendMail("<table><tr><td>123</td><td>ds</td><td>zaas</td></tr></table>",messageWrapper);
    }

}
