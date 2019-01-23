package com.javee.attendance.repositories;

import com.javee.attendance.entities.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>
{
	User findByUserNameAndPassword( String userName, String password );

	User findByUserName( String userName );

	List<User> findAll();
}
