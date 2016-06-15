package ru.nik.services.servicesImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.nik.services.GenericCrud;

/**
 * @author Nikita
 */
public class GenericCrudImpl<T, PK> extends AbstructImpl<T, PK> implements GenericCrud<T, PK>
{
    private Class<T> instance;

    public GenericCrudImpl(Class<T> instance)
    {
        this.instance = instance;
    }

    /**
     * Создание.
     */
    public T create(T t)
    {
        em = getEntityManager();
        t = em.merge(t);
        closeEntityManager();
        return t;
    }

    /**
     * Поиск.
     */
    public T find(PK id)
    {
        em  = getEntityManager();
        T t = em.find(instance, id);
        closeEntityManager();
        return t;
    }

    /**
     * Получение всех элементов.
     */
    public List<T> getAll()
    {
        em  = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(instance);
        Root<T> rootEntry = cq.from(instance);
        CriteriaQuery<T> all = cq.select(rootEntry);
        List<T> t = em.createQuery(all).getResultList();
        closeEntityManager();
        return t;
    }


    /**
     * Обновление записи.
     */
    public T update(T t)
    {
        em  = getEntityManager();
        t = em.merge(t);
        closeEntityManager();
        return t;
    }

    /**
     * Удаление записи.
     */
    public void remove(PK id)
    {
        em  = getEntityManager();
        T t = em.find(instance, id);
        em.remove(t);
        closeEntityManager();
    }

    @Override
    protected EntityManager getEntityManager()
    {
        em = Singleton.CreateEntityManager();
        em.getTransaction().begin();
        return em;
    }

    @Override
    protected void closeEntityManager()
    {
        if (em.getTransaction().isActive())
        	em.getTransaction().commit();
        if (em != null && em.isOpen())
        	em.close();
    }
}
