package com.mlb.estore.domain;

public class Goods {
	private String id;
	private String name;
	private double marketprice;
	private double estoreprice;
	private String category;
	private int num;
	private String imgurl;
	private String description;

	private int salesnum;

	public int getSalesnum() {
		return salesnum;
	}

	public void setSalesnum(int salesNum) {
		this.salesnum = salesNum;
	}

	public Goods() {
		super();

	}

	public Goods(String id, String name, double marketprice,
			double estoreprice, String category, int num, String imgurl,
			String description) {
		super();
		this.id = id;
		this.name = name;
		this.marketprice = marketprice;
		this.estoreprice = estoreprice;
		this.category = category;
		this.num = num;
		this.imgurl = imgurl;
		this.description = description;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the marketprice
	 */
	public double getMarketprice() {
		return marketprice;
	}

	/**
	 * @param marketprice
	 *            the marketprice to set
	 */
	public void setMarketprice(double marketprice) {
		this.marketprice = marketprice;
	}

	/**
	 * @return the estoreprice
	 */
	public double getEstoreprice() {
		return estoreprice;
	}

	/**
	 * @param estoreprice
	 *            the estoreprice to set
	 */
	public void setEstoreprice(double estoreprice) {
		this.estoreprice = estoreprice;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the imgurl
	 */
	public String getImgurl() {
		return imgurl;
	}

	/**
	 * @param imgurl
	 *            the imgurl to set
	 */
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", marketprice="
				+ marketprice + ", estoreprice=" + estoreprice + ", category="
				+ category + ", num=" + num + ", imgurl=" + imgurl
				+ ", description=" + description + ", salesnum=" + salesnum
				+ "]";
	}

}
