package ru.nik.services;

import ru.nik.dto.EventMembersDTO;
import ru.nik.dto.UsersDTO;

/**
 * @author Nikita
 *
 */
public interface EventMembersService extends GenericCrud<EventMembersDTO, Long>
{
	/**
     * Получение сущности участника по ИД события.
     * @param eventMemberId
     * @return
     */
    public UsersDTO getUserByEventMemberId(Long eventMemberId);
    
}
