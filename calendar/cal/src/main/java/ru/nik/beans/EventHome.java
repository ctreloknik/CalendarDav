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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ru.nik.dto.EventCategoriesDTO;
import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.dto.UsersDTO;
import ru.nik.enums.EventCategories;
import ru.nik.enums.Importancy;
import ru.nik.enums.RepeatTime;
import ru.nik.services.servicesImpl.EventmembersServiceBean;
import ru.nik.services.servicesImpl.UserCalendarEventsServiceBean;
import ru.nik.services.servicesImpl.UserCalendarServiceBean;
import ru.nik.services.servicesImpl.UserServiceBean;

/**
 * Класс для работы с событием.
 * @author Nikita
 *
 */
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

    private MembersBlock membersBlock;

    private UserCalendarEventsDTO event;

    private List<String> selectedCategories = new ArrayList<String>();
    private List<String> categories = new ArrayList<String>();

    private List<String> repeatTimeList = new ArrayList<String>();
    private String selectedRepeatTime = "";

    private Boolean managed = false;

    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "success";
    }
    
    @PostConstruct
    public void init()
    {
        initBlocks();
        
        event = new UserCalendarEventsDTO();
        for (EventCategories ec : EventCategories.values())
            categories.add(ec.getName());

        for (RepeatTime rt : RepeatTime.values())
            repeatTimeList.add(rt.getName());
    }

    private void initBlocks()
    {
        MembersBlock membersBlock = new MembersBlock(this);
        this.setMembersBlock(membersBlock);
    }
    
    // //// Getters and setters for services //////

    public UserServiceBean getUsersService()
    {
        return usersService;
    }

    public void setUsersService(UserServiceBean usersService)
    {
        this.usersService = usersService;
    }

    public EventmembersServiceBean getMembersService()
    {
        return membersService;
    }

    public void setMembersService(EventmembersServiceBean membersService)
    {
        this.membersService = membersService;
    }

    // //// Getters and setters for blocks //////

    public MembersBlock getMembersBlock()
    {
        return membersBlock;
    }

    public void setMembersBlock(MembersBlock membersBlock)
    {
        this.membersBlock = membersBlock;
    }
    
    // ////Getters and setters //////

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
    
    public String getImportancy()
    {
        return Importancy.getNameById(event.getImportancy());
    }
    
    public void setImportancy(String selectedImportancy)
    {
        event.setImportancy(Importancy.getIdByName(selectedImportancy));
    }
    
    public List<String> getImportancyCategories()
    {
        List<String> imp = new ArrayList<String>();
        for (Importancy importancy : Importancy.values())
        {
            imp.add(importancy.getName());
        }
        return imp;
    }

    // //// Методы для работы с событиями //////
    public void preCreateEvent()
    {
        managed = false;
        deleteAll();
        initiate();
    }

    public void loadEvent(Long eventId)
    {
        initiate();
        managed = true;
        event = eventsServiceBean.find(eventId);
        setSelectedRepeatTime(RepeatTime.getNameById(event.getRepeatTime()));
        List<EventCategoriesDTO> cat = eventsServiceBean
                .getEventCategories(event.getUserCalendarEventsId());
        for (EventCategoriesDTO ec : cat)
            selectedCategories.add(EventCategories.getNameById(ec
                    .getCategoryId().intValue()));
    }

    public void saveEvent()
    {
        if (!checkDate())
            return;

        event.setRepeatTime(RepeatTime.getIdByName(selectedRepeatTime));
        //event.setNotificationTime(notificationTime);
        if (!managed)
        {
            event.setUserCalendar(userCalendarService.getCalendarByUserId(getCurrentUser().getUserId()));
            event = eventsServiceBean.create(event);
            membersBlock.createInvite(getCurrentUser().getUserId(), true);
        }
        else
        {
            event = eventsServiceBean.update(event);
        }
        eventsServiceBean.saveCategories(event, selectedCategories);
        membersBlock.saveMembers();
        deleteAll();
        initiate();
    }
    
    public void deleteEvent(Long eventId)
    {
        eventsServiceBean.remove(eventId);
    }

    public Boolean checkDate()
    {
        if (event.getStartDatetime().equals(event.getEndDatetime())
                && (event.getStartTime().after(event.getEndTime())))
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
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ошибка!",
                        "Введено неверное время или дата."));
    }

    private UsersDTO getCurrentUser()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext externalContext = fc.getExternalContext();
        return usersService.getUserByLogin(externalContext.getUserPrincipal().getName());
    }

    private void initiate()
    {
        membersBlock.initiate();
        event = new UserCalendarEventsDTO();
        Date currentDate = new Date();
        event.setName("Новое событие");
        event.setImportancy(Importancy.MEDIUM.getId());
        event.setStartDatetime(currentDate);
        event.setEndDatetime(currentDate);
        Long curTime = System.currentTimeMillis();
        event.setStartTime(new Date(curTime));
        event.setEndTime(new Date(curTime));
        selectedCategories = new ArrayList<String>();
        selectedRepeatTime = "";
    }
    
    public void deleteAll()
    {
        event = null;
        membersBlock.deleteAll();
        selectedCategories = null;
        selectedRepeatTime = "";
    }

}
