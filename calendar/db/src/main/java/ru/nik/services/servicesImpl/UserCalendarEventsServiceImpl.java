package ru.nik.services.servicesImpl;

import java.util.Calendar;
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
public class UserCalendarEventsServiceImpl extends GenericCrudImpl<UserCalendarEventsDTO, Long> implements UserCalendarEventsService
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
                "select ue.userCalendar.user from UserCalendarEventsDTO ue " +
                "where ue.userCalendarEventsId=:userEventId");
        q.setParameter("userEventId", userEventId);
        return (UsersDTO) q.getSingleResult();
    }

    @Override
    public void saveCategories(UserCalendarEventsDTO event, List<String> categories)
    {
        create(event);
        EventCategoriesDTO ec;
        for (String string : categories)
        {
            ec = new EventCategoriesDTO();
            ec.setEvent(event);
            ec.setCategoryId(EventCategories.getIdByName(string).longValue());
            eman.merge(ec);
        }
    }
    
    /**
     * Получить события за указанную дату.
     * @param date дата
     * @return список событий
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserCalendarEventsDTO> getEventsByDate(Date date)
    {
        Query q = getEntityManager().createQuery(
                "SELECT e FROM UserCalendarEventsDTO e " +
                "WHERE :date BETWEEN e.startDatetime AND e.endDatetime");
        q.setParameter("date", date);
        return q.getResultList();
    }
    
    /**
     * Получение ближайших событий.
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserCalendarEventsDTO> getNextEvents()
    {
        Query q = getEntityManager().createQuery(
                "SELECT DISTINCT e from UserCalendarEventsDTO e "
                + "WHERE e.startDatetime > current_date ORDER BY e.startDatetime");
        return q.getResultList();
    }
    
    /**
     * Получить категории события по ид события.
     * @param eventId ид события
     * @return категории.
     */
    public List<EventCategoriesDTO> getEventCategories(Long eventId)
    {
        Query q = getEntityManager().createQuery(
                "SELECT ec from EventCategoriesDTO ec "
                + "WHERE ec.event.userCalendarEventsId =:eventId");
        q.setParameter("eventId", eventId);
        return q.getResultList();
    }
    
}
