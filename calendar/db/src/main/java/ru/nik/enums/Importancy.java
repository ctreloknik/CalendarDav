package ru.nik.enums;

/**
 * @author Nikita
 *
 */
public enum Importancy
{
    LOW(1,"Несущественное"),
    MEDIUM(2, "Обычное"),
    HIGH(3,"Важное");
    
    private Integer id;
    private String name;
    
    private Importancy(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Получает имя по идентификатору.
     * @param id идентификатор
     * @return
     */
    public static String getNameById(Integer id)
    {
        for (Importancy importancy : values())
            if (importancy.id.equals(id))
                return importancy.name;
        return "";
    }
    
    /**
     * Получает ИД по названию.
     * @param имя идентификатор
     * @return
     */
    public static Integer getIdByName(String name)
    {
        for (Importancy importancy : values())
            if (importancy.name.equals(name))
                return importancy.id;
        return -1;
    }
}
