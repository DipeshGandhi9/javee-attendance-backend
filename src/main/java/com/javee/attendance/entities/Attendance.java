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
	private Date date;
	private Date timeInDate;
	private Date timeOutDate;

	protected Attendance()
	{
	}

	public Attendance( Employee employee) {
		this.employee = employee;
		this.date = new Date();
		this.timeInDate = date;
	}

	public Attendance( Employee employee,Date date, Date timeInDate, Date timeOutDate )
	{
		this.employee = employee;
		this.date = date;
		this.timeInDate = timeInDate;
		this.timeOutDate = timeOutDate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public Date getTimeInDate() {
		return timeInDate;
	}

	public void setTimeInDate(Date timeInDate) {
		this.timeInDate = timeInDate;
	}

	public Date getTimeOutDate() {
		return timeOutDate;
	}

	public void setTimeOutDate(Date timeOutDate) {
		this.timeOutDate = timeOutDate;
	}

	
}
