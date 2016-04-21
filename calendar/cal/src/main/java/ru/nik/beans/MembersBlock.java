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

    // private List<EventMembersDTO> currentMembers;
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

    /*
     * public List<EventMembersDTO> getCurrentMembers() { return currentMembers;
     * } public void setCurrentMembers(List<EventMembersDTO> currentMembers) {
     * this.currentMembers = currentMembers; }
     */
    // ////Методы для работы с участниками //////

    // исправить в дальнейшем передачу, чтобы не было явно видно ИД
    public void addMemder(Long userId)
    {
        int i = getMemberPosition(deletedUsers, userId);
        if (i != -1)
        {
            deletedUsers.remove(i);
        }
        else
        {
            addedUsers.add(userId);
        }
    }

    public void deleteMember(Long eventMemberId)
    {
        int i = getMemberPosition(addedUsers, eventMemberId);
        if (i != -1)
        {
            addedUsers.remove(i);
        }
        else
        {
            deletedUsers.add(eventMemberId);
        }
    }

    public List<EventMembersDTO> getEventMembers()
    {
        List<EventMembersDTO> allMembers;
        if (eventHome.getEvent().getUserCalendarEventsId() != null)
        {
            allMembers = eventHome.getMembersService().getMembersByEventId(
                    eventHome.getEvent().getUserCalendarEventsId());
        }
        else
        {
            allMembers = new ArrayList<EventMembersDTO>();
        }

        for (Long delUserId : deletedUsers)
        {
            int i = getMemberPosition(delUserId, allMembers);
            if (i != -1)
            {
                allMembers.remove(i);
            }
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

    private int getMemberPosition(Long userId, List<EventMembersDTO> members)
    {
        int result = 0;
        for (EventMembersDTO evMem : members)
        {
            if (userId == evMem.getUser().getUserId())
            {
                return result;
            }
            result++;
        }
        return -1;
    }

    private int getMemberPosition(List<Long> memIds, Long userId)
    {
        int result = 0;
        for (Long id : memIds)
        {
            if (userId == id)
            {
                return result;
            }
            result++;
        }
        return -1;
    }

    public List<UsersDTO> getAllUsers()
    {
        return eventHome.getUsersService().getAll();
    }

    public void saveMembers()
    {
        for (Long userId : addedUsers)
        {
            createInvite(userId, false);
        }
        for (Long userId : deletedUsers)
        {
            Long eventId = eventHome.getEvent().getUserCalendarEventsId(); 
            Long evMemId = eventHome.getMembersService().getEventMemberByEventAndMemberIDs(eventId, userId).getEventMemberId();
            eventHome.getMembersService().remove(evMemId);
        }
    }
    
    public void createInvite(Long userId, Boolean isConfirmed)
    {
        EventMembersDTO evm;
        evm = new EventMembersDTO();
        evm.setUser(eventHome.getUsersService().find(userId));
        evm.setUserCalendarEventsDTO(eventHome.getEvent());
        evm.setIsConfirmed(isConfirmed);
        eventHome.getMembersService().create(evm);
    }

    public void initiate()
    {
        // currentMembers = new ArrayList<EventMembersDTO>();
        addedUsers = new ArrayList<Long>();
        deletedUsers = new ArrayList<Long>();
    }

    public void deleteAll()
    {
        // currentMembers = null;
        addedUsers = null;
        deletedUsers = null;
    }
}
