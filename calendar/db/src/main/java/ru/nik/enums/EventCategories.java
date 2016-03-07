package ru.nik.enums;

/**
 * @author Nikita
 *
 */
public enum EventCategories
{
    BUSINESS(1, "Бизнес"),
    MEETINGS(2, "Встречи"),
    ANNIVERSARY(3, "Годовщины"),
    BIRTHDAY(4, "Дни рождения"),
    INCOMES(5,"Доходы"),
    CUSTOMERS(6, "Заказчики"),
    CALLS(7, "Звонки"),
    IDEAS(8, "Идеи"),
    FAVORITES(9, "Избранное"),
    HOLIDAY(10, "Каникулы"),
    CLIENTS(11, "Клиенты"),
    COMPETITIONS(12, "Соревнования"),
    PRIVATE(13, "Личное"),
    ANSWERS(14, "Ответы"),
    VACATION(15, "Отпуск"),
    GIFTS(16, "Подарки"),
    VENDORS(17, "Поставщики"),
    CELEBRATIONS(18, "Праздники"),
    PROJECTS(19, "Проекты"),
    TRAVELS(20, "Путешествия"),
    OTHER(21, "Разное");
    
    private Integer id;
    private String name;
    
    private EventCategories(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
    
    /**
     * Перевод в строку.
     */
    public String toString()
    {
        return name;
    }
    
    /**
     * Получает имя по идентификатору.
     * @param id идентификатор
     * @return
     */
    public static String getNameById(Integer id)
    {
        for (EventCategories category : values())
            if (category.id.equals(id))
                return category.name;
        return "";
    }
    
    /**
     * Получает ИД по названию.
     * @param имя идентификатор
     * @return
     */
    public static Integer getIdByName(String name)
    {
        for (EventCategories category : values())
            if (category.name.equals(name))
                return category.id;
        return -1;
    }
}
