package ru.nik.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("confirmPasswordValidator")
public class PasswordValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        String password = (String) o;
        UIInput uiInput = (UIInput) uiComponent.getAttributes().get("confirm");
        String confirm = uiInput.getSubmittedValue().toString();

        if (!password.equals(confirm)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords are not equal.",
                    password+" "+confirm + " не равны."));
        }
    }
}
