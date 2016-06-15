package ru.nik.services;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import ru.nik.dto.UsersDTO;
import ru.nik.services.servicesImpl.UserServiceBean;


@Stateful
public class SignUpService{

    @EJB
    private UserServiceBean personService;
    
    //@EJB
    //CodeSendService codeSendService;

    private UsersDTO person = null;


    public void userStep(String login, String password){
        if (person == null){
            person = new UsersDTO();
            //person.setConfirmed(false);
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
        //codeSendService.sendCode(person.getEmail(),"Registration on ConferenceManagmentSystem");
    }

   /* public Boolean confirmationStep(String code){
        return codeSendService.confirmation(code);
    }*/

    /*public void sendCode(){
        codeSendService.sendCode(person.getEmail(),"Регистрация на ConferenceManagmentSystem");
    }*/

    public void completionStep(){
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
