package ru.nik.validators;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import ru.nik.services.servicesImpl.UserServiceBean;

@Named
@RequestScoped
public class LoginValidator implements Validator {

    @EJB
    UserServiceBean personService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (personService.getUserByLogin(o.toString()) != null){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login already exists",
                    o + " пользователь с таким логином уже существут."));
        }
    }

}
