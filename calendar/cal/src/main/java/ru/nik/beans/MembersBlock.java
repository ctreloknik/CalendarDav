package ru.nik.beans;

import java.util.ArrayList;
import java.util.List;

import ru.nik.dto.EventMembersDTO;
import ru.nik.dto.UsersDTO;

/**
 * @author Nikita
 *
 */
public class MembersBlock
{
    private EventHome eventHome;

    private List<EventMembersDTO> currentMembers;
    private List<Long> addedUsers = new ArrayList<Long>();
    private List<Long> deletedUsers = new ArrayList<Long>();

    public MembersBlock(EventHome eventHome)
    {
        this.eventHome = eventHome;
    }

    /*
     * public List<UsersDTO> getUsers() { return users; } public void
     * setUsers(List<UsersDTO> users) { this.users = users; }
     */

    public List<Long> getAddedUsers()
    {
        return addedUsers;
    }

    public void setAddedUsers(List<Long> addedUsers)
    {
        this.addedUsers = addedUsers;
    }

    public List<Long> getDeletedUsers()
    {
        return deletedUsers;
    }

    public void setDeletedUsers(List<Long> deletedUsers)
    {
        this.deletedUsers = deletedUsers;
    }

    public List<EventMembersDTO> getCurrentMembers()
    {
        return currentMembers;
    }

    public void setCurrentMembers(List<EventMembersDTO> currentMembers)
    {
        this.currentMembers = currentMembers;
    }

    // ////Методы для работы с участниками //////

    // исправить в дальнейшем передачу, чтобы не было явно видно ИД
    public void addMemder(Long userId)
    {
        addedUsers.add(userId);
    }

    public void deleteMember(Long eventMemberId)
    {
        deletedUsers.add(eventMemberId);
    }

    public List<EventMembersDTO> getEventMembers()
    {
        List<EventMembersDTO> allMembers = new ArrayList<EventMembersDTO>(
                currentMembers);
        int i = 0;
        for (Long delUserId : deletedUsers)
        {
            if (delUserId == null)
                break;
            if (allMembers.get(i).getUser().getUserId().equals(delUserId))
            {
                allMembers.remove(i);
            }
            i++;
        }
        for (Long userId : addedUsers)
        {
            if (userId == null)
                break;
            EventMembersDTO eventMem = new EventMembersDTO();
            eventMem.setUser(eventHome.getUsersService().find(userId));
            eventMem.setIsConfirmed(false);
            eventMem.setUserCalendarEventsDTO(eventHome.getEvent());
            allMembers.add(eventMem);
        }
        return allMembers;
    }

    public List<UsersDTO> getAllUsers()
    {
        return eventHome.getUsersService().getAll();
    }

    public void saveMembers()
    {
        EventMembersDTO evm;
        for (Long userId : addedUsers)
        {
            evm = new EventMembersDTO();
            evm.setUser(eventHome.getUsersService().find(userId));
            evm.setUserCalendarEventsDTO(eventHome.getEvent());
            eventHome.getMembersService().create(evm);
        }
    }
}
