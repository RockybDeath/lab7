import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {
    private String user;
    private String password;
    private Properties properties;
    public Mail(String user,String password){
        this.user=user;
        this.password=password;
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
    }
    public void send(String what,String email) throws MessagingException{
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Мяв, я из лабы");
            message.setText("Это ваш пароль для авторизации, непонятный , который вы не сможете нормально запомнить.\nДобро пожаловать в увлекательный мир опасности и приключений.\n" +
                    "На повестке дня сдача лабы Александру и Ивану, это сложный квест для настоящих героев, следует собрать рейд.\n" +
                    "Другие важные новости на СтудентовУбивают.com\n"+"ПАРОЛЬ - "+what);
            Transport.send(message);
    }
}
