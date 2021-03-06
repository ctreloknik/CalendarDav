package ru.nik.services.servicesImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ru.nik.dto.EventMembersDTO;
import ru.nik.dto.UsersDTO;
import ru.nik.services.EventMembersService;

/**
 * @author Nikita
 *
 */
public class EventMembersServiceImpl extends GenericCrudImpl<EventMembersDTO, Long> implements EventMembersService
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
    
    public EventMembersServiceImpl()
    {
		super(EventMembersDTO.class);
	}
    
    /**
     * Получение сущности участника по ИД события.
     * @param eventMemberId
     * @return
     */
    public UsersDTO getUserByEventMemberId(Long eventMemberId)
    {
        Query q = getEntityManager().createQuery(
                "select em.user from EventMembersDTO em " +
                "where em.eventMemberId=:eventMemberId");
        q.setParameter("eventMemberId", eventMemberId);
        return (UsersDTO) q.getSingleResult();
    }

    /**
     * Получить список участников по ИД события.
     * @return участники.
     */
    @SuppressWarnings("unchecked")
    public List<EventMembersDTO> getMembersByEventId(Long eventId)
    {
        Query q = getEntityManager().createQuery(
                "select em from EventMembersDTO em "
                + "where em.userCalendarEventsDTO.userCalendarEventsId =:eventId");
        q.setParameter("eventId", eventId);
        return q.getResultList();
    }

    /**
     * Получить список неподтвежденных событий.
     * @return события.
     */
    @SuppressWarnings("unchecked")
    public List<EventMembersDTO> getUnerifiedEvents(Long userId)
    {
        Query q = getEntityManager().createQuery(
                "select em from EventMembersDTO em "
                + "where em.user.userId=:userId and em.isConfirmed = false");
        q.setParameter("userId", userId);
        return q.getResultList();
    }
    
    /**
     * Получить сущность связи по ИД события и ИД участника.
     * @return сущность.
     */
    public EventMembersDTO getEventMemberByEventAndMemberIDs(Long eventId, Long userId)
    {
        Query q = getEntityManager().createQuery(
                "select em from EventMembersDTO em "
                + "where em.user.userId=:userId and "
                + "em.userCalendarEventsDTO.userCalendarEventsId=:eventId");
        q.setParameter("userId", userId);
        q.setParameter("eventId", eventId);
        return (EventMembersDTO) q.getSingleResult();
    }
}
