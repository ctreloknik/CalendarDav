package ru.nik.services.servicesImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ru.nik.dto.UserCalendarDTO;
import ru.nik.dto.UsersDTO;
import ru.nik.services.UserCalendarService;


/**
 * @author Nikita
 *
 */
public class UserCalendarServiceImpl extends GenericCrudImpl<UserCalendarDTO, Long> implements UserCalendarService
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
	
    public UserCalendarServiceImpl()
    {
		super(UserCalendarDTO.class);
	}
	
    public UsersDTO getUserByCalendarId(Long userCalendarId)
	{
		Query q = getEntityManager().createQuery(
				"select u from UsersDTO u where " +
				"u.userId in (select uc from UserCalendarDTO uc " +
				"where uc.userCalendarId = :userCalendarId)");
		q.setParameter("userCalendarId", userCalendarId);
		return (UsersDTO) q.getSingleResult();
	}

}
