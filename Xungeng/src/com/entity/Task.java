package com.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="task")
public class Task implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int memberId;
	private int deviceId;
	private int locationId;
	private Date startTime;
	private Date endTime;
	private String extra;
	private Date addTime=new Date();
	private int orgStage=1;
	private int nowStage=1;
	private int recordId=0;
	private int status=1;
	
	
	public Task() {
		super();
	}

	public Task(int memberId, int deviceId, int locationId, Date startTime, Date endTime, String extra) {
		super();
		this.memberId = memberId;
		this.deviceId = deviceId;
		this.locationId = locationId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.extra = extra;
	}
	
	@Id
	@Column(name="id",nullable=false,unique=true)
	@GenericGenerator(name="generator",strategy="native")
	@GeneratedValue(generator="generator")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name="member_id",nullable=false,length=10)
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	@Column(name="device_id",nullable=false,length=10)
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	
	@Column(name="location_id",nullable=false,length=10)
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	@Column(name="start_datetime",nullable=false,length=10)
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name="end_datetime",nullable=false,length=10)
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name="extra",nullable=false,length=30)
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Column(name="add_time",nullable=false,length=30)
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name="org_stage",nullable=false,length=4)
	public int getOrgStage() {
		return orgStage;
	}
	public void setOrgStage(int orgStage) {
		this.orgStage = orgStage;
	}

	@Column(name="now_stage",nullable=false,length=4)
	public int getNowStage() {
		return nowStage;
	}
	public void setNowStage(int nowStage) {
		this.nowStage = nowStage;
	}

	@Column(name="record_id",nullable=false,length=10)
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	@Column(name="status",nullable=false,length=4)
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


	
}
