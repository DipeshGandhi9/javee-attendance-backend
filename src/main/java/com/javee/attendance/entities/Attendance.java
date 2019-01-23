package com.javee.attendance.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Attendance
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	@ManyToOne
	private Employee employee;
	private Date timeIn;
	private Date timeOut;

	protected Attendance()
	{
	}

	public Attendance( Employee employee, Date timeIn, Date timeOut )
	{
		this.employee = employee;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
	}

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

	public Date getTimeInDate()
	{
		return timeIn;
	}

	public void setTimeInDate( Date timeIn )
	{
		this.timeIn = timeIn;
	}

	public Date geTimeOuttDate()
	{
		return timeOut;
	}

	public void setTimeOutDate( Date timeOut )
	{
		this.timeOut = timeOut;
	}
}
