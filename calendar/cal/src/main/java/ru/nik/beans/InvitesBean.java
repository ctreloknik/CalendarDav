package ru.nik.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.services.servicesImpl.EventmembersServiceBean;

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
    
    public List<UserCalendarEventsDTO> getUnverifiedEvents()
    {
        return eventmembersService.getUnerifiedEvents();
    }
}
