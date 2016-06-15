package ru.nik.services;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Stateful;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Stateful
public class EmailService {

    private Properties props;
    private Session session;

    @PostConstruct
    public void init() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        session = Session.getDefaultInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ConferenceManagmentSystem@gmail.com", "Iconference");
                    }
                });
    }

    @Asynchronous
    public void send(String email, String title, String text) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ConferenceManagmentSystem@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(title);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println(e);
        }
    }
}
