package com.techelevator;
import java.math.BigDecimal;

public class Beverage implements VendableItem {
	private String name;
	private BigDecimal price;
	private int quantity;
	private String slot;

	public Beverage (String slot, String name, BigDecimal price, int quantity) {
		this.slot = slot;
		this.name = name;
		this.price = price;
		this.quantity = quantity;			
	}
	
	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}
	@Override
	public String dispense() {
		String vendMsg= "Glug Glug, Yum!";
		quantity= getQuantity()-1;
		return vendMsg;
		
	}

	@Override
	public String slot() {
		return slot;
	}
	
}