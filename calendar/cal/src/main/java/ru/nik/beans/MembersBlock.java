package ru.nik.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import ru.nik.dto.EventMembersDTO;
import ru.nik.dto.UserCalendarEventsDTO;
import ru.nik.dto.UsersDTO;
import ru.nik.services.servicesImpl.EventmembersServiceBean;
import ru.nik.services.servicesImpl.UserServiceBean;

/**
 * @author Nikita
 *
 */
public class MembersBlock
{
    private EventHome eventHome;
    
    private List<UsersDTO> users = new ArrayList<UsersDTO>();
    private List<EventMembersDTO> currentUsers = new ArrayList<EventMembersDTO>();
    private List<UsersDTO> addedUsers = new ArrayList<UsersDTO>();
    private List<EventMembersDTO> deletedUsers = new ArrayList<EventMembersDTO>();
    
    public MembersBlock(EventHome eventHome)
    {
        this.eventHome = eventHome;
    }
    
    /*public List<UsersDTO> getUsers()
    {
        return users;
    }
    public void setUsers(List<UsersDTO> users)
    {
        this.users = users;
    }
    */
    
    public List<EventMembersDTO> getCurrentUsers()
    {
        return currentUsers;
    }
    public void setCurrentUsers(List<EventMembersDTO> currentUsers)
    {
        this.currentUsers = currentUsers;
    }
    public List<UsersDTO> getAddedUsers()
    {
        return addedUsers;
    }
    public void setAddedUsers(List<UsersDTO> addedUsers)
    {
        this.addedUsers = addedUsers;
    }
    public List<EventMembersDTO> getDeletedUsers()
    {
        return deletedUsers;
    }
    public void setDeletedUsers(List<EventMembersDTO> deletedUsers)
    {
        this.deletedUsers = deletedUsers;
    }

    public List<UsersDTO> getAllUsers()
    {
        return users;
    }
    
    public void getCurrentMembersForEvent(Long eventId)
    {
        // заглушка для тестов
        // требуется получать участников для конкретного события
        //currentUsers = membersService.getAll();
    }
    
    public void saveMembers(UserCalendarEventsDTO event)
    {
        EventMembersDTO evm;
        for (UsersDTO user : addedUsers)
        {
            evm = new EventMembersDTO();
            evm.setUser(user);
            evm.setUserCalendarEventsDTO(event);
            //membersService.create(evm);
        }
    }
}
