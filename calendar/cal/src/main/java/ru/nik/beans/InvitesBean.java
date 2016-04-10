package ru.nik.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ru.nik.dto.EventMembersDTO;
import ru.nik.dto.UsersDTO;
import ru.nik.services.servicesImpl.EventmembersServiceBean;
import ru.nik.services.servicesImpl.UserServiceBean;

/**
 * @author Nikita
 *
 */
@Named
@ViewScoped
public class InvitesBean
{
    @EJB
    private EventmembersServiceBean eventmembersService;
    
    @EJB
    private UserServiceBean usersService;
    
    public List<EventMembersDTO> getUnverifiedEvents()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext externalContext = fc.getExternalContext();
        UsersDTO user = usersService.getUserByLogin(externalContext.getUserPrincipal().getName());
        
        List<EventMembersDTO> ev = eventmembersService.getUnerifiedEvents(user.getUserId());
        return ev;
    }
    
    public void acceptInvite(Long evMemId)
    {
        EventMembersDTO evMem = eventmembersService.find(evMemId);
        evMem.setIsConfirmed(true);
        eventmembersService.update(evMem);
    }
    
    public void deleteInvite(Long evMemId)
    {
        eventmembersService.remove(evMemId);
    }
}
