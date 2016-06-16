package ru.nik.services;

import java.security.MessageDigest;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import ru.nik.dto.UserCalendarDTO;
import ru.nik.dto.UsersDTO;
import ru.nik.services.servicesImpl.UserCalendarServiceBean;
import ru.nik.services.servicesImpl.UserServiceBean;


@Stateful
public class SignUpService{

    @EJB
    private UserServiceBean personService;
    
    @EJB
    private UserCalendarServiceBean calendarService;
    
        private UsersDTO person = null;


    public void userStep(String login, String password){
        if (person == null){
            person = new UsersDTO();
        }
        person.setUserLogin(login);
        person.setUserPass(password);
        person = personService.create(person);
    }

    public void personStep(String firstName, String secondName, String middleName, String email){
        person.setFirstName(firstName);
        person.setLastName(secondName);
        person.setMiddleName(middleName);
        person.setEmail(email);
    }

    public void completionStep() throws Exception {
        UserCalendarDTO calendar = new UserCalendarDTO();
        MessageDigest md = MessageDigest.getInstance("MD5");
        String forHash = person.getEmail() + person.getFirstName() + person.getLastName(); 
        byte[] hash = md.digest(forHash.getBytes());
        calendar.setUser(person);
        calendar.setUrl(hash.toString());
        person.setRole("USER");
        personService.update(person);
    }

    public UsersDTO getPerson() {
        return person;
    }

    public void setPerson(UsersDTO person) {
        this.person = person;
    }
}
