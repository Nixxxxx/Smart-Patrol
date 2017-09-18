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
@Table(name = "member")
public class Member implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String number;
	private String name;
	private String mobile;
	private String email;
	private String extra;
	private Date addTime = new Date();
	private int status = 1;

	public Member() {
		super();
	}

	public Member(String number, String name, String mobile, String email, String extra) {
		super();
		this.number = number;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.extra = extra;
	}

	public Member(int id, String number, String name, String mobile, String email, String extra) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
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

	@Column(name = "number", nullable = true, length = 30)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "name", nullable = true, length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "mobile", nullable = true, length = 30)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "email", nullable = true, length = 30)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "extra", nullable = true, length = 30)
	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Column(name = "add_time", nullable = true, length = 30)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name = "status", nullable = true, length = 30)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
