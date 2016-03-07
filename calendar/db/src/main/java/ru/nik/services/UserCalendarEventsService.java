package ru.nik.services;

import java.util.List;

import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.dto.UsersDTO;


/**
 * @author Nikita
 *
 */
public interface UserCalendarEventsService extends GenericCrud<UserCalendarEventsDTO, Long>
{
	/**
     * Поиск автора события.
     * @param userEventId ИД.
     * @return
     */
    public UsersDTO getUserEventById(Long userEventId);
    
    /**
     * Сохранить категории для события
     */
    public void saveCategories(UserCalendarEventsDTO event, List<String> categories);
    
}
