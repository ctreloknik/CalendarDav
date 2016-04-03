package ru.nik.services.servicesImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import ru.nik.dto.UsersDTO;

/**
 * @author Nikita
 */
@Stateless
public class UserServiceBean extends UserServiceImpl
{
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UsersDTO create(UsersDTO t)
    {
		return super.create(t);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UsersDTO find(Long id)
	{
		return super.find(id);
	}

	@Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public UsersDTO getUserByLogin(String login)
    {
        return super.getUserByLogin(login);
    }

    @Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UsersDTO> getAll()
	{
		return super.getAll();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UsersDTO update(UsersDTO t)
	{
		return super.update(t);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(Long id)
	{
		super.remove(id);
	}
}
