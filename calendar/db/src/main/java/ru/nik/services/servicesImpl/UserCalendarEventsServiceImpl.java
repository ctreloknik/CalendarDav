package ru.nik.services.servicesImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.dto.UsersDTO;
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
    	eman.close();
	}

    public UserCalendarEventsServiceImpl()
    {
		super(UserCalendarEventsDTO.class);
	}

	@Override
    protected EntityManager getEntityManager()
    {
        return eman;
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

}
