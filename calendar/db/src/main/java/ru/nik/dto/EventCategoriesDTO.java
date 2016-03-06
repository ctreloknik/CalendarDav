/**
 * 
 */
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

/**
 * @author Nikita
 *
 */

@Entity
@Table(name = "EVENT_CATEGORIES")
public class EventCategoriesDTO
{
    private Long eventCategoryId;
    private UserCalendarEventsDTO event;
    private Integer categoryId;
    
    public EventCategoriesDTO()
    {
    }
    
    public EventCategoriesDTO(Long eventCategoryId,
            UserCalendarEventsDTO event, Integer categoryId)
    {
        this.eventCategoryId = eventCategoryId;
        this.event = event;
        this.categoryId = categoryId;
    }

    @Id
    @Column(name = "EVENT_CATEGORY_ID", unique = true, 
        nullable = false, insertable = true, updatable = true, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getEventCategoryId()
    {
        return eventCategoryId;
    }

    public void setEventCategoryId(Long eventCategoryId)
    {
        this.eventCategoryId = eventCategoryId;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "EVENT_ID", unique = false, nullable = true, insertable = true, updatable = true)
    public UserCalendarEventsDTO getEvent()
    {
        return event;
    }

    public void setEvent(UserCalendarEventsDTO event)
    {
        this.event = event;
    }

    @Column(name = "CATEGORY_NAME", unique = false, nullable = false, insertable = true, updatable = true, length = 64)
    public Integer getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId)
    {
        this.categoryId = categoryId;
    }
}
