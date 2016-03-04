package ru.nik.services.servicesImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import ru.nik.dto.UserCalendarDTO;
import ru.nik.dto.UsersDTO;

/**
 * @author Nikita
 *
 */
@Stateless
public class UserCalendarServiceBean extends UserCalendarServiceImpl
{
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public UsersDTO getUserByCalendarId(Long userCalendarId)
    {
        return super.getUserByCalendarId(userCalendarId);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UserCalendarDTO create(UserCalendarDTO t)
    {
        return super.create(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public UserCalendarDTO find(Long id)
    {
        return super.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<UserCalendarDTO> getAll()
    {
        return super.getAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UserCalendarDTO update(UserCalendarDTO t)
    {
        return super.update(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(Long id)
    {
        super.remove(id);
    }

}
