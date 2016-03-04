package ru.nik.services;

import java.util.List;

import ru.nik.dto.UsersDTO;

/**
 * Интерфейс для доступа к сервисам управления пользователями.
 * @author Nikita
 */
public interface UserService extends GenericCrud<UsersDTO, Long>
{
    /**
     * Получение пользователя по идентифкатору.
     * @param userId - ид пользователя
     * @return
     */
    //public UsersDTO findUserById(Long userId);
    
    /**
     * Метод выполняет запрос получения списка пользователей для SuggestBox.
     * @param inputSuggestion - параметр, по которому ищутся пользователи.
     * @return
     */
    //public List<UsersDTO> getUsersBySuggestion(String inputSuggestion);
}
