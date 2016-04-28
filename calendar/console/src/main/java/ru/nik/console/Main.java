package ru.nik.console;

import ru.nik.dto.UsersDTO;
import ru.nik.services.servicesImpl.UserServiceBean;
import ru.nik.services.servicesImpl.UserServiceImpl;

public class Main
{
	public static void main( String[] args )
    {
		UsersDTO u = new UsersDTO();
		UserServiceImpl userLocalServiceImpl = new UserServiceImpl();
		
		System.out.println(userLocalServiceImpl.find(1L).getUserLogin());
		//u.setUserId(2L);
		//userLocalServiceImpl.create(u);
    }
}
