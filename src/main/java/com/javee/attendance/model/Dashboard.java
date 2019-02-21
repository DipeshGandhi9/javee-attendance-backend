package com.javee.attendance.model;

import com.javee.attendance.entities.Attendance;
import java.util.ArrayList;
import java.util.Date;

public class Dashboard
{
	int totalEmployee, leaveCount, presentCount;
	ArrayList<Attendance> attendances = new ArrayList<>();
	Date date;

	Dashboard()
	{
		super();
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate( Date date )
	{
		this.date = date;
	}

	public int getTotalEmployee()
	{
		return totalEmployee;
	}

	public void setTotalEmployee( int totalEmployee )
	{
		this.totalEmployee = totalEmployee;
	}

	public int getLeaveCount()
	{
		return leaveCount;
	}

	public void setLeaveCount( int leaveCount )
	{
		this.leaveCount = leaveCount;
	}

	public int getPresentCount()
	{
		return presentCount;
	}

	public void setPresentCount( int presentCount )
	{
		this.presentCount = presentCount;
	}

	public ArrayList<Attendance> getAttendances()
	{
		return attendances;
	}

	public void setAttendances( ArrayList<Attendance> attendances )
	{
		this.attendances = attendances;
	}
}

