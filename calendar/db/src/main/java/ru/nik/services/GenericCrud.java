package ru.nik.services;

import java.util.List;

/**
 * Created by Nikita.
 */
public interface GenericCrud<T, PK>
{
    public T create(T t);
    public T find(PK id);
    public T update(T t);
    public void remove(PK id);
    public List<T> getAll();
}
