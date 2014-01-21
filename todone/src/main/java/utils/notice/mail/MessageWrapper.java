package utils.notice.mail;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-6-6
 * Time: 上午10:40
 * To change this template use File | Settings | File Templates.
 */
public class MessageWrapper {
    private String mailForm;
    private String mailSubject;
    private String mailTo;
    private String password;

    public String getMailForm() {
        return mailForm;
    }

    public void setMailForm(String mailForm) {
        this.mailForm = mailForm;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
