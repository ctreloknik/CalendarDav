package ru.nik.services.servicesImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ru.nik.dto.EventCategoriesDTO;
import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.dto.UsersDTO;
import ru.nik.enums.EventCategories;
import ru.nik.services.UserCalendarEventsService;

/**
 * @author Nikita
 *
 */
public class UserCalendarEventsServiceImpl extends
        GenericCrudImpl<UserCalendarEventsDTO, Long> implements
        UserCalendarEventsService
{
    @PersistenceContext(unitName = "PERSISTENCEUNIT")
    protected EntityManager eman;

    @Override
    protected void closeEntityManager()
    {
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return eman;
    }

    public UserCalendarEventsServiceImpl()
    {
        super(UserCalendarEventsDTO.class);
    }

    @Override
    public UsersDTO getUserEventById(Long userEventId)
    {
        Query q = getEntityManager().createQuery(
                "select ue.userCalendar.user from UserCalendarEventsDTO ue "
                        + "where ue.userCalendarEventsId=:userEventId");
        q.setParameter("userEventId", userEventId);
        return (UsersDTO) q.getSingleResult();
    }

    @Override
    public void saveCategories(UserCalendarEventsDTO event,
            List<String> categories)
    {
        List<EventCategoriesDTO> currentCategories = getEventCategories(event
                .getUserCalendarEventsId());
        EventCategoriesDTO ec;
        for (String cat : categories)
        {
            if (!isExist(cat, currentCategories))
            {
                ec = new EventCategoriesDTO();
                ec.setEvent(event);
                ec.setCategoryId(EventCategories.getIdByName(cat).longValue());
                eman.merge(ec);
            }
        }
        for (EventCategoriesDTO cat : currentCategories)
        {
            if (!isExist(cat.getCategoryId().intValue(), categories))
            {
                eman.remove(cat);
            }
        }
    }

    // //////// переделать на получение данных из бд !!!!!!!
    private boolean isExist(String categoryName,
            List<EventCategoriesDTO> categories)
    {
        for (EventCategoriesDTO cat : categories)
        {
            if (cat.getCategoryId().equals(
                    EventCategories.getIdByName(categoryName).longValue()))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isExist(Integer categiryId, List<String> categories)
    {
        for (String cat : categories)
        {
            if (cat.equals(EventCategories.getNameById(categiryId)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Получить события за указанную дату для выбранного календаря.
     * 
     * @param date
     *            дата
     * @return список событий
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserCalendarEventsDTO> getEventsByDateAndUser(Date date, Long userId)
    {
        List<UserCalendarEventsDTO> result = new ArrayList<UserCalendarEventsDTO>();
        String select = "SELECT distinct e FROM UserCalendarEventsDTO e WHERE e.userCalendar.user.userId=:userId ";
        StringBuilder builder = new StringBuilder(select);
        builder.append("and ((:date BETWEEN e.startDatetime AND e.endDatetime) or e.repeatTime=1 or ");
        builder.append("(e.repeatTime=2 and (e.startDatetime = e.endDatetime) and (:date - e.startDatetime)%7=0))");
        
        result.addAll(getResultListFromQuery(builder.toString(), date, userId));
        
        List<UserCalendarEventsDTO> tmp = new ArrayList<UserCalendarEventsDTO>();
        builder = new StringBuilder(select);
        builder.append("and e.repeatTime=2 and (e.startDatetime < e.endDatetime)");
        tmp.addAll(getResultListFromQuery(builder.toString(), date, userId));
        
        for (UserCalendarEventsDTO event : tmp)
        {
            Calendar startEvent = new GregorianCalendar();
            startEvent.setTime(event.getStartDatetime());
            Calendar endEvent = new GregorianCalendar();
            endEvent.setTime(event.getEndDatetime());
            Calendar calendarCurr = new GregorianCalendar();
            calendarCurr.setTime(date);
            if (date.after(event.getStartDatetime()))
            {
                while(date.after(event.getStartDatetime()))
                {
                    calendarCurr.add(Calendar.DAY_OF_YEAR, -7);
                }
                calendarCurr.add(Calendar.DAY_OF_YEAR, 7);
                if (calendarCurr.after(startEvent) && calendarCurr.before(endEvent))
                {
                    result.add(event);
                }
            }
        }
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private List<UserCalendarEventsDTO> getResultListFromQuery(String query, Date date, Long userId)
    {
        Query q = getEntityManager().createQuery(query);
        q.setParameter("userId", userId);
        q.setParameter("date", date);
        return q.getResultList();
    }
    
    private List<UserCalendarEventsDTO> getEventsByWeekRepeat(List<UserCalendarEventsDTO>, Date date)
    {
        
    }

    /**
     * Получение ближайших событий.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserCalendarEventsDTO> getNextEvents()
    {
        Query q = getEntityManager()
                .createQuery(
                        "SELECT DISTINCT e from UserCalendarEventsDTO e "
                                + "WHERE e.startDatetime > current_date ORDER BY e.startDatetime");
        return q.getResultList();
    }

    /**
     * Получить категории события по ид события.
     * 
     * @param eventId
     *            ид события
     * @return категории.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<EventCategoriesDTO> getEventCategories(Long eventId)
    {
        Query q = getEntityManager().createQuery(
                "SELECT ec from EventCategoriesDTO ec "
                        + "WHERE ec.event.userCalendarEventsId =:eventId");
        q.setParameter("eventId", eventId);
        return q.getResultList();
    }

}
