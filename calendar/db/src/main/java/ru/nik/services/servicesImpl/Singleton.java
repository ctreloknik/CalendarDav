package ru.nik.services.servicesImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Nikita
 *
 */
public class Singleton
{
    private static EntityManagerFactory emf;

    static
    {
        emf = Persistence.createEntityManagerFactory("PERSISTENCE");
    }

    public static EntityManager CreateEntityManager()
    {
        return emf.createEntityManager();
    }

    public static void close()
    {
        emf.close();
    }
}
