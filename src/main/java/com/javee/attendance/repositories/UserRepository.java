package com.javee.attendance.repositories;

import com.javee.attendance.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    /*--------------------------------------------
    |   P U B L I C    A P I    M E T H O D S   |
    ============================================*/

	User findByUserNameAndPassword(String userName, String password);
}
