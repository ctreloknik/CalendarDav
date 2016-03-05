package ru.nik.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.services.servicesImpl.UserCalendarEventsServiceBean;
import ru.nik.services.servicesImpl.UserCalendarServiceBean;

@Named(value = "eventHome")
@RequestScoped
public class EventHome implements Serializable
{
	private static final long serialVersionUID = -3226059638923060660L;

	@EJB
	private UserCalendarEventsServiceBean eventsServiceBean;
	
	@EJB
	private UserCalendarServiceBean userCalendarService;
	
	private UserCalendarEventsDTO event;
	
	@PostConstruct
    public void init()
    {
        event = new UserCalendarEventsDTO();
    }
	
    public UserCalendarEventsDTO getEvent()
    {
        return event;
    }

    public void setEvent(UserCalendarEventsDTO event)
    {
        this.event = event;
    }

    public void saveEvent()
	{
        event.setUserCalendar(userCalendarService.find(1L));
	    eventsServiceBean.create(event);
	    update();
	}
    
    private void update()
    {
        event = null;
        init();
    }
	
}
