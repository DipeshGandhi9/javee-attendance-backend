package com.javee.attendance.repositories;

import com.javee.attendance.entities.Organization;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface OrganizationRepository extends CrudRepository<Organization, Long>
{
	List<Organization> findAll();
}

