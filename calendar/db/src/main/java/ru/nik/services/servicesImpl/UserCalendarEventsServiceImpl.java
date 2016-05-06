package ru.nik.services.servicesImpl;

import java.util.ArrayList;
import java.util.Date;
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
        GenericCrudImpl<UserCalendarEventsDTO, Long> implements UserCalendarEventsService
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
    public void saveCategories(UserCalendarEventsDTO event, List<String> categories)
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

    private boolean isExist(String categoryName, List<EventCategoriesDTO> categories)
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
     * Получение событий, которые относятся к определенному календарю по ссылке.
     * @param url ссылка на календарь
     * @return список событий
     */
    @SuppressWarnings("unchecked")
    public List<UserCalendarEventsDTO> getEventsByCalendarURL(String url)
    {
        String query = "SELECT e FROM UserCalendarEventsDTO e WHERE "
                + "e.userCalendar.url=:url";
        Query q = getEntityManager().createQuery(query);
        q.setParameter("url", url);
        return q.getResultList();
    }
    
    /**
     * Получить события за указанную дату для выбранного пользователя.
     * 
     * @param date
     *            дата
     * @return список событий
     */
    @Override
    public List<UserCalendarEventsDTO> getEventsByDateAndUser(Date date, Long userId)
    {
        List<UserCalendarEventsDTO> result = new ArrayList<UserCalendarEventsDTO>();
        String select = "SELECT distinct e FROM UserCalendarEventsDTO e ";
        String where = "WHERE e.userCalendar.user.userId=:userId ";
        StringBuilder builder = new StringBuilder(select + where);
        builder.append("and (:date BETWEEN e.startDatetime AND e.endDatetime or e.repeatTime=1");
        builder.append(" or (e.repeatTime=2 and (e.startDatetime = e.endDatetime) and (:date - e.startDatetime)%7=0))");
        result.addAll(getResultListFromQuery(builder.toString(), date, userId));

        builder = new StringBuilder(select + where);
        builder.append("and (e.startDatetime < e.endDatetime) and e.repeatTime=2 and :date not BETWEEN e.startDatetime AND e.endDatetime");
        result.addAll(getEventsByWeekRepeat(builder.toString(), date, userId));
        
        return result;
    }

    @SuppressWarnings("unchecked")
    private List<UserCalendarEventsDTO> getResultListFromQuery(String query, Date date,
            Long userId)
    {
        Query q = getEntityManager().createQuery(query);
        q.setParameter("userId", userId);
        q.setParameter("date", date);
        return q.getResultList();
    }

    private List<UserCalendarEventsDTO> getEventsByWeekRepeat(String query, Date date,
            Long userId)
    {
        List<UserCalendarEventsDTO> resultList = new ArrayList<UserCalendarEventsDTO>();
        List<UserCalendarEventsDTO> tmp = getResultListFromQuery(query, date, userId);
        for (UserCalendarEventsDTO event : tmp)
        {
            Long weeks = (date.getTime() - event.getStartDatetime().getTime()) / 604800000;
            System.out.println(weeks);
            Date dateForCheck = new Date(date.getTime() - weeks * 604800000);
            if (dateForCheck.getTime() >= event.getStartDatetime().getTime()
                    && dateForCheck.getTime() <= event.getEndDatetime().getTime())
            {
                resultList.add(event);
            }
        }
        return resultList;
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
