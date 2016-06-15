package ru.nik.enums;

public enum FilterOnFuture
{
    CURRENT_DAY(0, "Текущая дата"),
    TOMORROW(1, "На завтра"),
    ON_WEEK(2, "На неделю вперед"),
    ON_MONTH(3, "На месяц вперед");
    
    private Integer id;
    private String name;
    
    private FilterOnFuture(Integer id, String name)
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
        for (FilterOnFuture time : values())
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
        for (FilterOnFuture time : values())
            if (time.name.equals(name))
                return time.id;
        return -1;
    }
}
