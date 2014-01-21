package utils.notice.mail;

import javax.mail.MessagingException;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-6
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public interface MailService {

    public void sendMail(String msg,MessageWrapper messageWrapper) throws MessagingException;

}
