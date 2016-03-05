package ru.nik.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;

import ru.nik.dto.UserCalendarDTO;
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
	private UserCalendarEventsServiceBean eventsServiceBean; 

	@EJB
	private UserCalendarServiceBean calendarService; 
	
	private UserCalendarDTO calendar;
	private ScheduleModel scheduleModel;
	
/*	@PostConstruct
    public void init()
	{
		this.calendar = calendarService.
	}*/

	
    public ScheduleModel getCalendar()
    {
    	
    	//Заглушка для тестов работы календаря
    	List<UserCalendarEventsDTO> events = new ArrayList<UserCalendarEventsDTO>();
    	return new LazyScheduleModel();
    	
    	/*List<UserCalendarEventsDTO> events = eventsServiceBean.getAll();

        // MAGIC !!!
        LazyScheduleModel lazyEventModel = new LazyScheduleModel()
        {
            public void loadEvents(Date start, Date end)
            {
                for ( course : )
                {
                    addEvent(new DefaultScheduleEvent(.getName(),
                            *Service.getStartTimeByCourse( ), .getEndDate()));
                }
            }
        };*/
    }
}
