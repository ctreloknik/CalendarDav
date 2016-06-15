package ru.nik.services;

import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class EmailSenderUtil {

    @EJB
    private EmailService emailService;
    
    public void sendInvite(String email, String title, String text) {
        emailService.send(email, title, "Вы были приглашены в событие " +  title + ". Описание: " + text);
    }

}
