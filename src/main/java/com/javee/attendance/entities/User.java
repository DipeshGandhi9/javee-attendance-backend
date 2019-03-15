package com.javee.attendance.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
public class User
{
	public enum ROLE
	{
		ADMIN, HR_MANAGER, EMPLOYEE
	}

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@OneToOne
	private Employee employee;

	private String userName;

	@JsonProperty( access = JsonProperty.Access.WRITE_ONLY )
	private String password;

	private ROLE role = ROLE.EMPLOYEE;

	public Long getId()
	{
		return id;
	}

	public void setId( Long id )
	{
		this.id = id;
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public void setEmployee( Employee employee )
	{
		this.employee = employee;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName( String userName )
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public ROLE getRole()
	{
		return role;
	}

	public void setRole( ROLE role )
	{
		this.role = role;
	}
}

