package ru.nik.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;

import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.services.servicesImpl.UserCalendarEventsServiceBean;
import ru.nik.services.servicesImpl.UserCalendarServiceBean;

/**
 * @author Nikita
 *
 */
@Named
@ViewScoped
public class CalendarHome implements Serializable
{
	private static final long serialVersionUID = -6706342668540710218L;
	
	@EJB
	private UserCalendarEventsServiceBean eventsService; 

	@EJB
	private UserCalendarServiceBean calendarService; 
	
	private ScheduleModel eventModel;
	List<UserCalendarEventsDTO> events;
	
	@PostConstruct
    public void init()
	{
	    //Заглушка для тестов работы календаря
        //List<UserCalendarEventsDTO> events = eventsService.getAll();
        //return new LazyScheduleModel();
        
        events = eventsService.getAll();

        setEventModel(new LazyScheduleModel()
        {
            public void loadEvents(Date start, Date end)
            {
                for (UserCalendarEventsDTO ev: events)
                {
                    addEvent(new DefaultScheduleEvent(ev.getName(),
                            ev.getStartDatetime(), ev.getEndDatetime()));
                }
            }
        });
	}

    public ScheduleModel getEventModel()
    {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel)
    {
        this.eventModel = eventModel;
    }
}
