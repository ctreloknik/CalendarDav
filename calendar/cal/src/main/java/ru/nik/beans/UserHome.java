package ru.nik.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ru.nik.dto.UsersDTO;
import ru.nik.services.servicesImpl.UserServiceBean;

@Named
@SessionScoped
public class UserHome implements Serializable
{
	private static final long serialVersionUID = 5756454302984019329L;

	@EJB
    UserServiceBean userServiceBean;

    @PostConstruct
    public void initPersonsBean() { }

    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "success";
    }

    public UsersDTO getUser()
    {
    	return userServiceBean.find(1L);
    }
}
