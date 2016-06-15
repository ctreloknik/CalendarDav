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
     * 
     * @param url
     *            ссылка на календарь
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
        builder.append("and (:date BETWEEN e.startDatetime AND e.endDatetime or e.repeatTime=1 and e.repeatTime<>6");
        builder.append(" or (e.repeatTime=2 and (e.startDatetime = e.endDatetime) and (:date - e.startDatetime)%7=0))");
        builder.append(" or (e.repeatTime=5 and (e.startDatetime = e.endDatetime))");
        result.addAll(getResultListFromQuery(builder.toString(), date, userId));

        builder = new StringBuilder(select + where);
        builder.append("and (e.startDatetime < e.endDatetime) and e.repeatTime=2 and :date not BETWEEN e.startDatetime AND e.endDatetime");
        result.addAll(getEventsByWeekRepeat(builder.toString(), date, userId));

        builder = new StringBuilder(select + where);
        builder.append("and (e.startDatetime < e.endDatetime) and e.repeatTime=5 and :date not BETWEEN e.startDatetime AND e.endDatetime");
        result.addAll(getEventsByMonthRepeat(builder.toString(), date, userId));

        builder = new StringBuilder(select + where);
        builder.append("and e.repeatTime=6");
        result.addAll(getEventsByYearRepeat(builder.toString(), date, userId));

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
            Date dateForCheck = new Date(date.getTime() - weeks * 604800000);
            if (dateForCheck.getTime() >= event.getStartDatetime().getTime()
                    && dateForCheck.getTime() <= event.getEndDatetime().getTime())
            {
                resultList.add(event);
            }
        }
        return resultList;
    }

    private List<UserCalendarEventsDTO> getEventsByMonthRepeat(String query, Date date,
            Long userId)
    {
        List<UserCalendarEventsDTO> resultList = new ArrayList<UserCalendarEventsDTO>();
        List<UserCalendarEventsDTO> tmp = getResultListFromQuery(query, date, userId);
        for (UserCalendarEventsDTO event : tmp)
        {
            int startDayforCheck = event.getStartDatetime().getDate();
            int endDayforCheck = event.getEndDatetime().getDate();
            if (date.getDate() >= startDayforCheck && date.getDate() <= endDayforCheck)
            {
                resultList.add(event);
            }
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
    private List<UserCalendarEventsDTO> getEventsByYearRepeat(String query, Date date,
            Long userId)
    {
        Query q = getEntityManager().createQuery(query);
        q.setParameter("userId", userId);
        List<UserCalendarEventsDTO> resultList = new ArrayList<UserCalendarEventsDTO>();
        List<UserCalendarEventsDTO> tmp = q.getResultList();
        for (UserCalendarEventsDTO event : tmp)
        {
            if (date.getMonth() >= event.getStartDatetime().getMonth()
                    && date.getMonth() <= event.getEndDatetime().getMonth()
                    && date.getDate() >= event.getStartDatetime().getDate()
                    && date.getDate() <= event.getEndDatetime().getDate())
            {
                resultList.add(event);
            }
        }
        return resultList;
    }

    /**
     * Получение ближайших событий по фильтру.
     * 
     * @return список событий
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserCalendarEventsDTO> getNextEvents(int filter, Long userId)
    {
        String select = "SELECT distinct e FROM UserCalendarEventsDTO e ";
        String where = "WHERE e.userCalendar.user.userId=:userId ";
        StringBuilder builder = new StringBuilder(select + where);
        builder.append(" and e.startDatetime > current_date");
        Calendar calendar = new GregorianCalendar();
        switch(filter) {
            case 1: 
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                break;
            case 2: 
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                break;
            case 3: 
                calendar.add(Calendar.DAY_OF_YEAR, 30);
                break;
        }
        builder.append(" and e.endDatetime <= :date");
        Query q = getEntityManager().createQuery(builder.toString());
        q.setParameter("userId", userId);
        q.setParameter("date", calendar.getTime());
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
