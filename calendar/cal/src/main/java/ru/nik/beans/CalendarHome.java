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
	
	private Date calendarDate;
	
	@PostConstruct
    public void init()
	{
	}

    public Date getCalendarDate()
    {
        return calendarDate;
    }

    public void setCalendarDate(Date calendarDate)
    {
        this.calendarDate = calendarDate;
    }
}
