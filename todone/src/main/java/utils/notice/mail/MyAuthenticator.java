package utils.notice.mail;

import javax.mail.PasswordAuthentication;

/**
 * Created by IntelliJ IDEA.
 * User: liu
 * Date: 13-4-18
 * Time: 下午12:43
 */
public class MyAuthenticator extends javax.mail.Authenticator {

    private String strUser;
    private String strPwd;

    public MyAuthenticator(String user, String password) {
        this.strUser = user;
        this.strPwd = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(strUser, strPwd);
    }
}