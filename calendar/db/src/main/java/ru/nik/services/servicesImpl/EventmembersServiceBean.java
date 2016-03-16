package ru.nik.services.servicesImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import ru.nik.dto.EventMembersDTO;
import ru.nik.dto.UsersDTO;

/**
 * @author Nikita
 *
 */
@Stateless
public class EventmembersServiceBean extends EventMembersServiceImpl
{

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public UsersDTO getUserByEventMemberId(Long eventMemberId)
    {
        return super.getUserByEventMemberId(eventMemberId);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EventMembersDTO create(EventMembersDTO t)
    {
        return super.create(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public EventMembersDTO find(Long id)
    {
        return super.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<EventMembersDTO> getAll()
    {
        return super.getAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EventMembersDTO update(EventMembersDTO t)
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
    public List<EventMembersDTO> getMembersByEventId(Long eventId)
    {
        return super.getMembersByEventId(eventId);
    }
    
}
