package com.mlb.estore.domain;

import java.sql.Timestamp;
import java.util.List;

public class Order {
	private String id;
	private String uid;
	private double totalprice;
	private String address;
	private int status;
	private Timestamp createtime;

	private List<OrderItem> list;

	/**
	 * @return the list
	 */
	public List<OrderItem> getList() {
		return list;
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<OrderItem> list) {
		this.list = list;
	}

	public Order() {
		super();

	}

	/**
	 * @param id
	 * @param uid
	 * @param totalprice
	 * @param address
	 * @param status
	 * @param createtime
	 * @param list
	 */
	public Order(String id, String uid, double totalprice, String address,
			int status, Timestamp createtime, List<OrderItem> list) {
		super();
		this.id = id;
		this.uid = uid;
		this.totalprice = totalprice;
		this.address = address;
		this.status = status;
		this.createtime = createtime;
		this.list = list;
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
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the totalprice
	 */
	public double getTotalprice() {
		return totalprice;
	}

	/**
	 * @param totalprice
	 *            the totalprice to set
	 */
	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the createtime
	 */
	public Timestamp getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime
	 *            the createtime to set
	 */
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order [id=" + id + ", uid=" + uid + ", totalprice="
				+ totalprice + ", address=" + address + ", status=" + status
				+ ", createtime=" + createtime + ", list=" + list + "]";
	}

}
