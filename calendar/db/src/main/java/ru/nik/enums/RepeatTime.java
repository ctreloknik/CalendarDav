package ru.nik.enums;

/**
 * @author Nikita
 *
 */
public enum RepeatTime
{
    NO_REPEAT(0, "Не повторять"),
    EVERY_DAY(1, "Каждый день"),
    EVERY_WEEK(2, "Каждую неделю"),
    //EVERY_TWO_WEEK(3, "Каждые две недели"),
    //EVERY_WEEKDAY(4, "Каждый будний день"),
    EVERY_MONTH(5,"Кадый месяц"),
    EVERY_YEAR(6, "Каждый год");
    
    private Integer id;
    private String name;
    
    private RepeatTime(Integer id, String name)
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
        for (RepeatTime time : values())
            if (time.id.equals(id))
                return time.name;
        return "";
    }
    
    /**
     * Получает ИД по названию.
     * @param имя идентификатор
     * @return
     */
    public static Integer getIdByName(String name)
    {
        for (RepeatTime time : values())
            if (time.name.equals(name))
                return time.id;
        return -1;
    }
}
