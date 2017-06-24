package com.mlb.estore.domain;

import java.sql.Timestamp;

public class User {
	/*
	 * `id` varchar(32) NOT NULL COMMENT '用户ID', `nickname` VARCHAR(10) DEFAULT
	 * NULL COMMENT '昵称', `email` VARCHAR(32) UNIQUE NOT NULL COMMENT '用户账号',
	 * `password` VARCHAR(32) DEFAULT NULL COMMENT '密码', `activecode`VARCHAR(32)
	 * DEFAULT NULL COMMENT '注册激活码', `status` INT DEFAULT 0 COMMENT
	 * '是否激活0表示注册未激活1表示激活', `registertime` TIMESTAMP COMMENT '注册时间', `role`
	 * VARCHAR(10) DEFAULT NULL COMMENT '角色', PRIMARY KEY (`id`)
	 */
	private String id;
	private String nickname;
	private String email;
	private String password;
	private String activecode;
	private int status;
	private Timestamp registertime;
	// user:用户 ;admin:管理员
	private String role;

	public User() {
		super();

	}

	public User(String id, String nickname, String email, String password,
			String activecode, int status, Timestamp registertime, String role) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.activecode = activecode;
		this.status = status;
		this.registertime = registertime;
		this.role = role;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the activecode
	 */
	public String getActivecode() {
		return activecode;
	}

	/**
	 * @param activecode
	 *            the activecode to set
	 */
	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the registertime
	 */
	public Timestamp getRegistertime() {
		return registertime;
	}

	/**
	 * @param registertime
	 *            the registertime to set
	 */
	public void setRegistertime(Timestamp registertime) {
		this.registertime = registertime;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Users [id=" + id + ", nickname=" + nickname + ", email="
				+ email + ", password=" + password + ", activecode="
				+ activecode + ", status=" + status + ", registertime="
				+ registertime + ", role=" + role + "]";
	}

}
