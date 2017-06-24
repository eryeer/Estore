package com.mlb.estore.domain;

public class OrderItem {
	private String oid;
	private String gid;
	private int buynum;
	private String name;
	private double marketprice;
	private double estoreprice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMarketprice() {
		return marketprice;
	}

	public void setMarketprice(double marketprice) {
		this.marketprice = marketprice;
	}

	public double getEstoreprice() {
		return estoreprice;
	}

	public void setEstoreprice(double estoreprice) {
		this.estoreprice = estoreprice;
	}

	public OrderItem() {
		super();

	}

	/**
	 * @param oid
	 * @param gid
	 * @param buynum
	 */
	public OrderItem(String oid, String gid, int buynum) {
		super();
		this.oid = oid;
		this.gid = gid;
		this.buynum = buynum;
	}

	/**
	 * @param oid
	 * @param gid
	 * @param buynum
	 * @param name
	 * @param marketprice
	 * @param estoreprice
	 */
	public OrderItem(String oid, String gid, int buynum, String name,
			double marketprice, double estoreprice) {
		super();
		this.oid = oid;
		this.gid = gid;
		this.buynum = buynum;
		this.name = name;
		this.marketprice = marketprice;
		this.estoreprice = estoreprice;
	}

	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid
	 *            the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * @return the gid
	 */
	public String getGid() {
		return gid;
	}

	/**
	 * @param gid
	 *            the gid to set
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}

	/**
	 * @return the buynum
	 */
	public int getBuynum() {
		return buynum;
	}

	/**
	 * @param buynum
	 *            the buynum to set
	 */
	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}

	@Override
	public String toString() {
		return "OrderItem [oid=" + oid + ", gid=" + gid + ", buynum=" + buynum
				+ ", name=" + name + ", marketprice=" + marketprice
				+ ", estoreprice=" + estoreprice + "]";
	}

}
