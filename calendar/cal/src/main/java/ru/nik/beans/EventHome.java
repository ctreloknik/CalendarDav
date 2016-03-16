package ru.nik.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import ru.nik.dto.EventCategoriesDTO;
import ru.nik.dto.EventMembersDTO;
import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.dto.UsersDTO;
import ru.nik.enums.EventCategories;
import ru.nik.enums.RepeatTime;
import ru.nik.services.servicesImpl.EventmembersServiceBean;
import ru.nik.services.servicesImpl.UserCalendarEventsServiceBean;
import ru.nik.services.servicesImpl.UserCalendarServiceBean;
import ru.nik.services.servicesImpl.UserServiceBean;

@Named(value = "eventHome")
@ManagedBean
@SessionScoped
public class EventHome implements Serializable
{
    private static final long serialVersionUID = -3226059638923060660L;

    @EJB
    private UserCalendarEventsServiceBean eventsServiceBean;
    
    @EJB
    private UserServiceBean usersService;
    
    @EJB
    private EventmembersServiceBean membersService;

    @EJB
    private UserCalendarServiceBean userCalendarService;
    
    //private MembersBlock membersBlock;

    private UserCalendarEventsDTO event;
    private List<UserCalendarEventsDTO> calendarEvents = new ArrayList<UserCalendarEventsDTO>();

    private List<String> selectedCategories = new ArrayList<String>();
    private List<String> categories = new ArrayList<String>();

    private List<String> repeatTimeList = new ArrayList<String>();
    private String selectedRepeatTime = "";

    private Boolean managed = false;

    //////// По возможности вынести работу с участниками в отдельный класс
    private List<UsersDTO> addedUsers = new ArrayList<UsersDTO>();
    private List<EventMembersDTO> deletedUsers = new ArrayList<EventMembersDTO>();
    
    
    @PostConstruct
    public void init()
    {
        event = new UserCalendarEventsDTO();
        for (EventCategories ec : EventCategories.values())
            categories.add(ec.getName());

        for (RepeatTime rt : RepeatTime.values())
            repeatTimeList.add(rt.getName());
    }
    
    /*private void initBlocks()
    {
        MembersBlock membersBlock = new MembersBlock();
        this.setMembersBlock(membersBlock);
    }*/
    
    ////// Getters and setters for blocks //////

    /*
    public MembersBlock getMembersBlock()
    {
        return membersBlock;
    }

    public void setMembersBlock(MembersBlock membersBlock)
    {
        this.membersBlock = membersBlock;
    }
    */

    ////// Getters and setters //////
    
    public List<UsersDTO> getAddedUsers()
    {
        return addedUsers;
    }

    public void setAddedUsers(List<UsersDTO> addedUsers)
    {
        this.addedUsers = addedUsers;
    }

    public List<EventMembersDTO> getDeletedUsers()
    {
        return deletedUsers;
    }

    public void setDeletedUsers(List<EventMembersDTO> deletedUsers)
    {
        this.deletedUsers = deletedUsers;
    }

    public UserCalendarEventsDTO getEvent()
    {
        return event;
    }

    public void setEvent(UserCalendarEventsDTO event)
    {
        this.event = event;
    }

    public List<UserCalendarEventsDTO> getCalendarEvents()
    {
        return calendarEvents;
    }

    public void setCalendarEvents(List<UserCalendarEventsDTO> calendarEvents)
    {
        this.calendarEvents = calendarEvents;
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

    public List<EventMembersDTO> getEventMembers()
    {
        return membersService.getMembersByEventId(event.getUserCalendarEventsId());
    }
    
    public List<UsersDTO> getAllUsers()
    {
        return usersService.getAll();
    }
    
    ////// Методы для работы с событиями //////
    
    public void onDateSelect(SelectEvent selectEvent)
    {
        calendarEvents = eventsServiceBean.getEventsByDate((Date) selectEvent
                .getObject());
    }

    public List<UserCalendarEventsDTO> getAllEvents()
    {
        return eventsServiceBean.getAll();
    }

    public void preCreateEvent()
    {
        managed = false;
        event = new UserCalendarEventsDTO();
        Date currentDate = new Date();
        event.setName("Новое событие");
        event.setStartDatetime(currentDate);
        event.setEndDatetime(currentDate);
        Long curTime = System.currentTimeMillis();
        event.setStartTime(new Date(curTime));
        event.setEndTime(new Date(curTime));
        update();
    }

    public void loadEvent(Long eventId)
    {
        managed = true;
        event = eventsServiceBean.find(eventId);
        setSelectedRepeatTime(RepeatTime.getNameById(event.getRepeatTime()));
        List<EventCategoriesDTO> cat = eventsServiceBean.getEventCategories(event.getUserCalendarEventsId());
        for (EventCategoriesDTO ec : cat)
            selectedCategories.add(EventCategories.getNameById(ec.getCategoryId().intValue()));
    }

    public void saveEvent()
    {
        if (checkDate())
            return;

        if (!managed)
        {
            event.setRepeatTime(RepeatTime.getIdByName(selectedRepeatTime));
            event.setUserCalendar(userCalendarService.find(1L));
            event = eventsServiceBean.create(event);
            eventsServiceBean.saveCategories(event, selectedCategories);
        }
        else
        {
            event = eventsServiceBean.update(event);
        }
        update();
    }

    public Boolean checkDate()
    {
        if (event.getStartDatetime().equals(event.getEndDatetime()) && 
                (event.getStartTime().after(event.getEndTime())))
        {
            makeMessAboutWrongDateOrTime();
            return false;
        }
        if (event.getStartDatetime().after(event.getEndDatetime()))
        {
            makeMessAboutWrongDateOrTime();
            return false;
        }
               
        return true;
    }
    
    public void makeMessAboutWrongDateOrTime()
    {
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка!", "Введено неверное время или дата."));
    }

    private void update()
    {
        event = new UserCalendarEventsDTO();
        // categories.clear();
        selectedCategories.clear();
        selectedRepeatTime = "";
        // repeatTimeList.clear();
        // init();
    }

}
