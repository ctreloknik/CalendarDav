package ru.nik.services;

import javax.ejb.Local;

import ru.nik.dto.UserCalendarDTO;
import ru.nik.dto.UsersDTO;

/**
 * @author Nikita
 *
 */
@Local
public interface UserCalendarService extends GenericCrud<UserCalendarDTO, Long>
{
    /**
     * Получение владельца календаря по ИД календаря.
     * @param userCalendarId ИД
     * @return
     */
    public UsersDTO getUserByCalendarId(Long userCalendarId);
}
