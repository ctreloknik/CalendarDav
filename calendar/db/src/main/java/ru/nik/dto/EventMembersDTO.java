package ru.nik.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT_MEMBERS")
//@BatchSize(size=100)
public class EventMembersDTO
{
    private Long eventMemberId;
    private UserCalendarEventsDTO userCalendarEventsDTO;
    private UsersDTO user;
    private Boolean isConfirmed;
    
    public EventMembersDTO()
    {
    }

    public EventMembersDTO(Long eventMemberId,
            UserCalendarEventsDTO userCalendarEventsDTO, UsersDTO user,
            Boolean isConfirmed)
    {
        this.eventMemberId = eventMemberId;
        this.userCalendarEventsDTO = userCalendarEventsDTO;
        this.user = user;
        this.isConfirmed = isConfirmed;
    }

    @Id
    @Column(name = "EVENT_MEMBERS_ID", unique = true, nullable = false, 
    	insertable = true, updatable = true, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getEventMemberId()
    {
        return eventMemberId;
    }

    public void setEventMemberId(Long eventMemberId)
    {
        this.eventMemberId = eventMemberId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_CALENDAR_EVENTS_ID", unique = false, nullable = true, insertable = true, updatable = true)
    public UserCalendarEventsDTO getUserCalendarEventsDTO()
    {
        return userCalendarEventsDTO;
    }

    public void setUserCalendarEventsDTO(
            UserCalendarEventsDTO userCalendarEventsDTO)
    {
        this.userCalendarEventsDTO = userCalendarEventsDTO;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", unique = false, nullable = true, insertable = true, updatable = true)
    public UsersDTO getUser()
    {
        return user;
    }

    public void setUser(UsersDTO user)
    {
        this.user = user;
    }

    @Column(name = "IS_CONFIRMED", unique = false, nullable = false, insertable = true, updatable = true)
    public Boolean getIsConfirmed()
    {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed)
    {
        this.isConfirmed = isConfirmed;
    }
    
}
