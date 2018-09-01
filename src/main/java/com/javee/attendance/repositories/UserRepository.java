package com.javee.attendance.repositories;

import com.javee.attendance.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
	User findByUserNameAndPassword(String userName, String password);
}
