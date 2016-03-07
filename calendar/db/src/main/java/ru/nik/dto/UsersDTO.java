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
    private String userLogin;
    private String userPass;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    
    private List<EventMembersDTO> eventMembers = new ArrayList<EventMembersDTO>();

    public UsersDTO()
    {
    }
    
    public UsersDTO(Long userId, String userLogin, String userPass)
    {
        this.userId = userId;
        this.setUserLogin(userLogin);
        this.setUserPass(userPass);
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
    
    @Column(name = "USER_LOGIN", unique = true, nullable = false, insertable = true, updatable = true)
    public String getUserLogin()
    {
        return userLogin;
    }

    public void setUserLogin(String userLogin)
    {
        this.userLogin = userLogin;
    }

    @Column(name = "USER_PASS", unique = true, nullable = false, insertable = true, updatable = true)
    public String getUserPass()
    {
        return userPass;
    }

    public void setUserPass(String userPass)
    {
        this.userPass = userPass;
    }

    @Column(name = "LAST_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Column(name = "FIRST_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Column(name = "MIDDLE_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    @Column(name = "EMALE", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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
