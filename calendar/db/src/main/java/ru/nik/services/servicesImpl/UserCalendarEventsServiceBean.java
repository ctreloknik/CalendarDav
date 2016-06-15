package ru.nik.services.servicesImpl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import ru.nik.dto.EventCategoriesDTO;
import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.dto.UsersDTO;

/**
 * @author Nikita
 *
 */
@Stateless
public class UserCalendarEventsServiceBean extends UserCalendarEventsServiceImpl
{
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UserCalendarEventsDTO create(UserCalendarEventsDTO t)
    {
        return super.create(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public UserCalendarEventsDTO find(Long id)
    {
        return super.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<UserCalendarEventsDTO> getAll()
    {
        return super.getAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UserCalendarEventsDTO update(UserCalendarEventsDTO t)
    {
        return super.update(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(Long id)
    {
        super.remove(id);
    }

    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UsersDTO getUserEventById(Long userEventId)
	{
		return super.getUserEventById(userEventId);
	}

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveCategories(UserCalendarEventsDTO event, List<String> categories)
    {
        super.saveCategories(event, categories);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<UserCalendarEventsDTO> getEventsByDateAndUser(Date date, Long userId)
    {
        return super.getEventsByDateAndUser(date, userId);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<UserCalendarEventsDTO> getNextEvents(int filter, Long userId)
    {
        return super.getNextEvents(filter, userId);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EventCategoriesDTO> getEventCategories(Long eventId)
    {
        return super.getEventCategories(eventId);
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<UserCalendarEventsDTO> getEventsByCalendarURL(String url)
    {
        return super.getEventsByCalendarURL(url);
    }
    
}
