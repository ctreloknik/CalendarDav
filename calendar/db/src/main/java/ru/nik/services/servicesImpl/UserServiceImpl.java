package ru.nik.services.servicesImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.nik.dto.UsersDTO;
import ru.nik.services.UserService;


/**
 * @author Nikita
 *
 */
public class UserServiceImpl extends GenericCrudImpl<UsersDTO, Long> implements UserService
{
    @PersistenceContext(unitName = "PERSISTENCEUNIT")
    protected EntityManager eman;
    
    @Override
    protected void closeEntityManager()
	{
	}

    @Override
    protected EntityManager getEntityManager()
    {
        return eman;
    }
	
    public UserServiceImpl()
    {
		super(UsersDTO.class);
	}

 /*   public UsersDTO findUserById(Long userId)
    {
        String jpa = "SELECT p FROM UsersDTO p WHERE p.userId = :userId";
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("userId",userId);
        return (UsersDTO) this.getResultList(jpa, parameters).get(0);
    }

    @Transactional
    public UsersDTO createUser(UsersDTO user)
    {
        eman.persist(user);
        return user;
    }*/
}
