package ru.nik.services.servicesImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;
import java.util.Map;

/**
 * @author Nikita
 */
public abstract class AbstructImpl<T, PK>
{
    protected EntityManager em;
    protected abstract EntityManager getEntityManager();
    protected abstract void closeEntityManager();

    @SuppressWarnings("unchecked")
	protected List<T> getResultList(String jpql, Map<String,Object> parametres)
	{
        em = getEntityManager();
        Query query = em.createQuery(jpql);
        for(Map.Entry<String,Object> entry : parametres.entrySet())
        {
            query.setParameter(entry.getKey(),entry.getValue());
        }
        List<T> result = query.getResultList();
        closeEntityManager();
        return result;
    }

    @SuppressWarnings("unchecked")
	protected List<T> getResultList(String jpql)
	{
        em = getEntityManager();
        List<T> result = em.createQuery(jpql).getResultList();
        closeEntityManager();
        return result;
    }

}
