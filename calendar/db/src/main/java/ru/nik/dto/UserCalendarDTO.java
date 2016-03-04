package ru.nik.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_CALENDAR")
//@BatchSize(size=100)
public class UserCalendarDTO
{
    private Long userCalendarId;
    private UsersDTO user;
    private String url;
    
    private List<UserCalendarEventsDTO> userCalendarEvents = new ArrayList<UserCalendarEventsDTO>();
	
	public UserCalendarDTO()
	{
	}
	
	public UserCalendarDTO(Long userCalendarId, UsersDTO user, String url)
	{
		this.userCalendarId = userCalendarId;
		this.user = user;
		this.url = url;
	}

	@Id
    @Column(name = "USER_CALENDAR_ID", unique = true, nullable = false, 
    	insertable = true, updatable = true, precision = 22, scale = 0)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getUserCalendarId()
	{
		return userCalendarId;
	}

	public void setUserCalendarId(Long userCalendarId)
	{
		this.userCalendarId = userCalendarId;
	}

	@OneToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", unique = false, nullable = false, insertable = true, updatable = true)
    public UsersDTO getUser()
	{
		return user;
	}

	public void setUser(UsersDTO user)
	{
		this.user = user;
	}

	@Column(name = "URL", unique = true, nullable = false, insertable = true, updatable = true)
    public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "userCalendar")
    public List<UserCalendarEventsDTO> getUserCalendarEvents()
    {
        return userCalendarEvents;
    }

    public void setUserCalendarEvents(
            List<UserCalendarEventsDTO> userCalendarEvents)
    {
        this.userCalendarEvents = userCalendarEvents;
    }
	
}
