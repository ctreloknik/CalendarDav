package ru.nik.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * @author Nikita
 */
@Entity
@Table(name = "USERS")
//@BatchSize(size=100)
public class UsersDTO
{
    private Long userId;
    
    private List<EventMembersDTO> eventMembers = new ArrayList<EventMembersDTO>();

    public UsersDTO()
    {
    }
    
    public UsersDTO(Long userId)
    {
        this.userId = userId;
    }

    @Id
    @Column(name = "USER_ID", unique = true, nullable = false, insertable = true, updatable = true, precision = 22, scale = 0)
    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "user")
    public List<EventMembersDTO> getEventMembers()
    {
        return eventMembers;
    }

    public void setEventMembers(List<EventMembersDTO> eventMembers)
    {
        this.eventMembers = eventMembers;
    }
    
}
