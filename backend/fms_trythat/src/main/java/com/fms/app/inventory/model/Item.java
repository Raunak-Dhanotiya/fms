package com.fms.app.inventory.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "item")
@Table(name = "item")
public class Item {

	@Id
	@Column(name="item_id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itemId;
	
    @Column(name = "name" ,unique = true)
	private String name;

    @Column(name = "stock")
	private Integer stock=0;
    
    @Column(name = "unit")
	private String unit="Nos";

    @Column(name = "rate")
	private Double rate=0.0;
    
    @Column(name = "min_stock_req")
	private Integer minStockReq=0;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getMinStockReq() {
		return minStockReq;
	}

	public void setMinStockReq(Integer MinStockReq) {
		this.minStockReq = MinStockReq;
	}

	public Item(Integer itemId, String name, Integer stock, String unit, Double rate, Integer minStockReq) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.stock = stock;
		this.unit = unit;
		this.rate = rate;
		this.minStockReq = minStockReq;
	}

	public Item() {
		super();
	}
    
	
}
