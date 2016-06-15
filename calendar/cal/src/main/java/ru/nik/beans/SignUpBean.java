package ru.nik.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import ru.nik.services.SignUpService;

@Named
@SessionScoped
public class SignUpBean implements Serializable {

    @EJB
    private SignUpService signUpService;

    private String firstname;
    private String lastname;
    private String middlename;
    private String login;
    private String password;
    private String email;
    //private String code;
    private int step;

    @PostConstruct
    public void init() {
        if(signUpService.getPerson()!=null){
            login = signUpService.getPerson().getUserLogin();
            email = signUpService.getPerson().getEmail();
            firstname = signUpService.getPerson().getFirstName();
            lastname = signUpService.getPerson().getLastName();
            setMiddlename(signUpService.getPerson().getMiddleName());
        }
    }
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename()
    {
        return middlename;
    }
    public void setMiddlename(String middlename)
    {
        this.middlename = middlename;
    }
    public void save() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Welcome " + firstname + " " + lastname));
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    /*public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }*/

    public void setPassword(String password) {
        this.password = password;
    }

    public void nextStep() {
        switch (step) {
            case 0: signUpService.userStep(login,password); break;
            case 1: signUpService.personStep(firstname,lastname,middlename,email);break;
            //case 2: if (!signUpService.confirmationStep(code)){
              //  step--;
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Code not equal!", "Contact admin."));
            //} break;
        }
        if(step<=2){
            step++;
        }
    }

    public String complite(){
        signUpService.completionStep();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(login, password);
        } catch (ServletException e) {
            return "error";
        }
        return "success";
    }

    /*public void getNewCode(){
        signUpService.sendCode();
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
