package ru.nik.console;

import ru.nik.dto.UsersDTO;
import ru.nik.services.servicesImpl.UserServiceBean;

public class Main
{
	public static void main( String[] args )
    {
		UsersDTO u = new UsersDTO();
		UserServiceBean userLocalServiceImpl = new UserServiceBean();
		
		u.setUserId(2L);
		userLocalServiceImpl.create(u);
    }
}
