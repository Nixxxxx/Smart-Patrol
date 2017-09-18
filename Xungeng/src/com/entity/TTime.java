package com.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "time")
public class TTime implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Time startTime;
	private Time endTime;
	private String extra;
	private Date addTime = new Date();
	private int status = 1;

	public TTime() {
		super();
	}

	public TTime(Time startTime, Time endTime, String extra) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.extra = extra;
	}

	public TTime(int id, Time startTime, Time endTime, String extra) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.extra = extra;
	}

	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GenericGenerator(name = "generator", strategy = "native")
	@GeneratedValue(generator = "generator")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "start_time", nullable = false, length = 30)
	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", nullable = false, length = 30)
	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	@Column(name = "extra", nullable = false, length = 30)
	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Column(name = "add_time", nullable = false, length = 30)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name = "status", nullable = false, length = 30)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
