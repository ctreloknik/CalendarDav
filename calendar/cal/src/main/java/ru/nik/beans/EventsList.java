package ru.nik.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.dto.UsersDTO;
import ru.nik.enums.EventCategories;
import ru.nik.services.servicesImpl.UserCalendarEventsServiceBean;
import ru.nik.services.servicesImpl.UserServiceBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс для списков событий.
 * 
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
    
    @EJB
    private UserServiceBean usersService;

    private List<UserCalendarEventsDTO> calendarEvents = new ArrayList<UserCalendarEventsDTO>();

    private Date selectedDate;

    // для фильтра
    private List<String> selectedCategories = new ArrayList<String>();
    private List<String> categories = new ArrayList<String>();

    @PostConstruct
    void init()
    {
        for (EventCategories ec : EventCategories.values())
            categories.add(ec.getName());
    }

    public Date getSelectedDate()
    {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate)
    {
        this.selectedDate = selectedDate;
    }

    public List<String> getAllCategories()
    {
        return categories;
    }

    public List<String> getSelectedCategories()
    {
        return selectedCategories;
    }

    public void setSelectedCategories(List<String> selectedCategories)
    {
        this.selectedCategories = selectedCategories;
    }

    public List<UserCalendarEventsDTO> getCalendarEvents()
    {
        return calendarEvents;
    }

    public void setCalendarEvents(List<UserCalendarEventsDTO> calendarEvents)
    {
        this.calendarEvents = calendarEvents;
    }

    public List<UserCalendarEventsDTO> getAllEvents()
    {
        return eventsServiceBean.getAll();
    }

    public void getEventsByFilter()
    {
        getEventsByDate(selectedDate);
    }

    private void getEventsByDate(Date date)
    {
        calendarEvents = eventsServiceBean.getEventsByDateAndUser(date, getCurrentUser().getUserId());
    }

    public void onDateSelect(SelectEvent selectEvent)
    {
        this.setSelectedDate((Date) selectEvent.getObject());
        getEventsByDate(selectedDate);
    }

    private UsersDTO getCurrentUser()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext externalContext = fc.getExternalContext();
        return usersService.getUserByLogin(externalContext.getUserPrincipal().getName());
    }
    
}
