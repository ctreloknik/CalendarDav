package ru.nik.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "USER_CALENDAR_EVENTS")
// @BatchSize(size=100)
public class UserCalendarEventsDTO
{
    private Long userCalendarEventsId;
    private UserCalendarDTO userCalendar;
    private String name;
    private String description;
    private Date startDatetime;
    private Date endDatetime;
    private Date startTime;
    private Date endTime;
    private Date notificationTime;
    private Boolean allDay;
    private Integer repeatTime;

    private List<EventMembersDTO> eventMembers = new ArrayList<EventMembersDTO>();
    private List<EventCategoriesDTO> eventCategories = new ArrayList<EventCategoriesDTO>();

    public UserCalendarEventsDTO()
    {
    }

    public UserCalendarEventsDTO(Long userCalendarEventsId,
            UserCalendarDTO userCalendar, String name, String description,
            Date startDatetime, Date endDatetime, Date startTime, Date endTime,
            Date notificationTime, Boolean allDay, Integer repeatTime)
    {
        this.userCalendarEventsId = userCalendarEventsId;
        this.userCalendar = userCalendar;
        this.name = name;
        this.description = description;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notificationTime = notificationTime;
        this.allDay = allDay;
        this.repeatTime = repeatTime;
    }

    @Id
    @Column(name = "USER_CALENDAR_EVENTS_ID", unique = true, nullable = false, insertable = true, updatable = true, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUserCalendarEventsId()
    {
        return userCalendarEventsId;
    }

    public void setUserCalendarEventsId(Long userCalendarEventsId)
    {
        this.userCalendarEventsId = userCalendarEventsId;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_CALENDAR_ID", unique = false, nullable = false, insertable = true, updatable = true)
    public UserCalendarDTO getUserCalendar()
    {
        return userCalendar;
    }

    public void setUserCalendar(UserCalendarDTO userCalendar)
    {
        this.userCalendar = userCalendar;
    }

    @Column(name = "NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "DESCRIPTION", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Temporal(value = TemporalType.DATE)
    @Column(name = "START_DATETIME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public Date getStartDatetime()
    {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime)
    {
        this.startDatetime = startDatetime;
    }

    @Temporal(value = TemporalType.DATE)
    @Column(name = "END_DATETIME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public Date getEndDatetime()
    {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime)
    {
        this.endDatetime = endDatetime;
    }

    @Temporal(value = TemporalType.TIME)
    @Column(name = "START_TIME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    @Temporal(value = TemporalType.TIME)
    @Column(name = "END_TIME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    @Column(name = "NOTIFICATION_TIME", unique = false, nullable = true, insertable = true, updatable = true)
    public Date getNotificationTime()
    {
        return notificationTime;
    }

    public void setNotificationTime(Date notificationTime)
    {
        this.notificationTime = notificationTime;
    }

    @Column(name = "ALL_DAY", unique = false, nullable = true, insertable = true, updatable = true)
    public Boolean getAllDay()
    {
        return allDay;
    }

    public void setAllDay(Boolean allDay)
    {
        this.allDay = allDay;
    }

    @Column(name = "REPEAT_TIME", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getRepeatTime()
    {
        return repeatTime;
    }

    public void setRepeatTime(Integer repeatTime)
    {
        this.repeatTime = repeatTime;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userCalendarEventsDTO")
    public List<EventMembersDTO> getEventMembers()
    {
        return eventMembers;
    }

    public void setEventMembers(List<EventMembersDTO> eventMembers)
    {
        this.eventMembers = eventMembers;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "event")
    public List<EventCategoriesDTO> getEventCategories()
    {
        return eventCategories;
    }

    public void setEventCategories(List<EventCategoriesDTO> eventCategories)
    {
        this.eventCategories = eventCategories;
    }

}
