package ru.nik.services.servicesImpl;

import java.util.HashMap;

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

    /**
     * Получение пользователя по логину.
     */
    @Override
    public UsersDTO getUserByLogin(String login)
    {
        String jpa = "SELECT u FROM UsersDTO u WHERE u.userLogin = :login";
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("login",login);
        return (UsersDTO) this.getResultList(jpa, parameters).get(0);
    }
}
