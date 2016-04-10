package ru.nik.services;

import java.util.Date;
import java.util.List;

import ru.nik.dto.EventCategoriesDTO;
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
     * Получить события за указанную дату для выбранного календаря.
     * @param date дата
     * @return список событий
     */
    public List<UserCalendarEventsDTO> getEventsByDateAndUser(Date date, Long userId);
    
    /**
     * Получение ближайших событий.
     * @return
     */
    public List<UserCalendarEventsDTO> getNextEvents();
    
    /**
     * Получить категории события по ид события.
     * @param eventId ид события
     * @return категории.
     */
    public List<EventCategoriesDTO> getEventCategories(Long eventId);
    
}
