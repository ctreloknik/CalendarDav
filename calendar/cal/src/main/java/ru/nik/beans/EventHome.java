package ru.nik.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.enums.EventCategories;
import ru.nik.enums.RepeatTime;
import ru.nik.services.servicesImpl.UserCalendarEventsServiceBean;
import ru.nik.services.servicesImpl.UserCalendarServiceBean;

@Named(value = "eventHome")
@ManagedBean
@RequestScoped
public class EventHome implements Serializable
{
	private static final long serialVersionUID = -3226059638923060660L;

	@EJB
	private UserCalendarEventsServiceBean eventsServiceBean;
	
	@EJB
	private UserCalendarServiceBean userCalendarService;
	
	private UserCalendarEventsDTO event;
	
	private List<String> selectedCategories = new ArrayList<String>();
	private List<String> categories = new ArrayList<String>();
	
	private List<String> repeatTimeList = new ArrayList<String>();
	private String selectedRepeatTime;
	
	@PostConstruct
    public void init()
    {
        event = new UserCalendarEventsDTO();
        for (EventCategories ec : EventCategories.values())
            categories.add(ec.getName());
        
        selectedRepeatTime = "";
        for (RepeatTime rt : RepeatTime.values())
            repeatTimeList.add(rt.getName());
    }
	
    public UserCalendarEventsDTO getEvent()
    {
        return event;
    }

    public void setEvent(UserCalendarEventsDTO event)
    {
        this.event = event;
    }
    
    public List<String> getSelectedCategories()
    {
        return selectedCategories;
    }

    public void setSelectedCategories(List<String> selectedCategories)
    {
        this.selectedCategories = selectedCategories;
    }

    public List<String> getAllCategories()
    {
        return categories;
    }

    public List<String> getRepeatTimeList()
    {
        return repeatTimeList;
    }

    public String getSelectedRepeatTime()
    {
        return selectedRepeatTime;
    }

    public void setSelectedRepeatTime(String selectedRepeatTime)
    {
        this.selectedRepeatTime = selectedRepeatTime;
    }

    public void saveEvent()
	{
        event.setRepeatTime(RepeatTime.getIdByName(selectedRepeatTime));
        event.setUserCalendar(userCalendarService.find(1L));
        event = eventsServiceBean.create(event);
	    eventsServiceBean.saveCategories(event, selectedCategories);
	    update();
	}
    
    public void updateEvent()
    {
        eventsServiceBean.update(event);
        update();
    }
    
    private void update()
    {
        event = null;
        categories.clear();
        selectedCategories.clear();
        selectedRepeatTime = "";
        repeatTimeList.clear();
        init();
    }

}
