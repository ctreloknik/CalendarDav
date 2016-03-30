package ru.nik.beans;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.services.servicesImpl.UserCalendarEventsServiceBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ����� ��� ������� �������.
 * @author Nikita
 *
 */
@Named(value = "eventsList")
@ManagedBean
@SessionScoped
public class EventsList implements Serializable
{
    private static final long serialVersionUID = 1122455087740410829L;

    @EJB
    private UserCalendarEventsServiceBean eventsServiceBean;
    
    private List<UserCalendarEventsDTO> calendarEvents = new ArrayList<UserCalendarEventsDTO>();

    private Date selectedDate;

    public List<UserCalendarEventsDTO> getCalendarEvents()
    {
        return calendarEvents;
    }

    public void setCalendarEvents(List<UserCalendarEventsDTO> calendarEvents)
    {
        this.calendarEvents = calendarEvents;
    }

    public void onDateSelect(SelectEvent selectEvent)
    {
        this.selectedDate = (Date) selectEvent.getObject();
        calendarEvents = eventsServiceBean.getEventsByDate((Date) selectEvent
                .getObject());
    }

    public List<UserCalendarEventsDTO> getAllEvents()
    {
        return eventsServiceBean.getAll();
    }
    
    public List<UserCalendarEventsDTO> getEventsByFilter()
    {
        // MOCK
        return new ArrayList<UserCalendarEventsDTO>();
    }

}
