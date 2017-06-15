package com.mlb.estore.domain;

public class Cart {
	private String uid;
	private String gid;
	private int buynum;
	// 每一条购物车信息都匹配一个商品
	private Goods goods;

	public Cart() {
		super();

	}

	public Cart(String uid, String gid, int buynum, Goods goods) {
		super();
		this.uid = uid;
		this.gid = gid;
		this.buynum = buynum;
		this.goods = goods;
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

	/**
	 * @return the goods
	 */
	public Goods getGoods() {
		return goods;
	}

	/**
	 * @param goods
	 *            the goods to set
	 */
	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cart [uid=" + uid + ", gid=" + gid + ", buynum=" + buynum
				+ ", goods=" + goods + "]";
	}

}
