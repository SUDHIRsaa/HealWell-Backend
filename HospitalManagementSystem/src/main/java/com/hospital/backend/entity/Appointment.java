package com.hospital.backend.entity;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
    private int apId;
	private Integer pId;
    private Integer drId;
    private String descript;
    private Integer cancelConfirm;
    private Date appointmentDate;
    private Time appointmentTime;
    private String status;
    

	public int getApId() {
		return apId;
	}
	public void setApId(int apId) {
		this.apId = apId;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public Integer getDrId() {
		return drId;
	}
	public void setDrId(Integer drId) {
		this.drId = drId;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public Integer getCancelConfirm() {
		return cancelConfirm;
	}
	public void setCancelConfirm(Integer cancelConfirm) {
		this.cancelConfirm = cancelConfirm;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public Time getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(Time appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    // Default constructor
    public Appointment() {
        this.cancelConfirm = 0;
        this.status = "PENDING";
    }
}