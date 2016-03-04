package ru.nik.services.servicesImpl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import ru.nik.dto.UsersDTO;

/**
 * @author Nikita
 *
 */
@Stateless
public class UserCalendarEventsServiceBean extends UserCalendarEventsServiceImpl
{
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UsersDTO getUserEventById(Long userEventId)
	{
		return super.getUserEventById(userEventId);
	}
	
}
