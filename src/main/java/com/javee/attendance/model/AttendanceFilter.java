package com.javee.attendance.model;

import java.util.Date;

public class AttendanceFilter
{
	private Date startDate;

	private Date endDate;

	private Long employeeId;

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate( Date startDate )
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate( Date endDate )
	{
		this.endDate = endDate;
	}

	public Long getEmployeeId()
	{
		return employeeId;
	}

	public void setEmployeeId( Long employeeId )
	{
		this.employeeId = employeeId;
	}
}

