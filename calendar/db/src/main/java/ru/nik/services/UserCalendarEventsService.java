package ru.nik.services;

import java.util.Date;
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
     * Сохранить категории для события.
     * @param event событие
     * @param categories категории
     */
    public void saveCategories(UserCalendarEventsDTO event, List<String> categories);
    
    /**
     * Получить события за указанную дату.
     * @param date дата
     * @return список событий
     */
    public List<UserCalendarEventsDTO> getEventsByDate(Date date);
    
}
