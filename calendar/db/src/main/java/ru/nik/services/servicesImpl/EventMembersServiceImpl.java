package ru.nik.services.servicesImpl;

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
    	eman.close();
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
    
    public UsersDTO getUserByEventMemberId(Long eventMemberId)
    {
        Query q = getEntityManager().createQuery(
                "select em.user from EventMembersDTO em " +
                "where em.eventMemberId=:eventMemberId");
        q.setParameter("eventMemberId", eventMemberId);
        return (UsersDTO) q.getSingleResult();
    }

}
