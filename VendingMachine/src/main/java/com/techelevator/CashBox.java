package com.techelevator;

import java.math.BigDecimal;

public class CashBox {

	private BigDecimal balance;
	private BigDecimal spent;

	public CashBox() {
		this.balance = new BigDecimal("0.00");
		this.spent = new BigDecimal("0.00");
	}

	public BigDecimal getBalance() {
		return balance;
	}
	//Returns string of what change is owed to customer
	public String getChange() {
		int dollar = 0;
		int quarter = 0;
		int dime = 0;
		int nickel = 0;
		String dollarLabel ="dollar(s)";
		String quarterLabel ="quarter(s)";
		String dimeLabel="dime(s)";
		String nickelLabel="nickel(s)";
		
		BigDecimal pennies = new BigDecimal("100");
		BigDecimal centsBalance = balance.multiply(pennies);
		int c = centsBalance.intValue();
		while (c > 0) {
			 while(c > 100) {
				c -= 100;
				dollar++;
			}
			while (c < 100 && c >= 25) {
				c -= 25;
				quarter++;
			}
			while (c < 25 && c >= 10) {
				c -= 10;
				dime++;
			}
			while ( c >0 && c <=5) {
				c -=5;
				nickel++;
			}
		}		
		String result = "\nYour change is: \n"+ dollar +" "+ dollarLabel+ ", "+ quarter +" "+ quarterLabel+", "
		+ dime +" "+ dimeLabel +", " + nickel + " "+ nickelLabel;
		
		return result;
		
		}
	
	//Makes a deposit if deposit amount is > 0.
	public void makeDeposit(Integer depositAmt) {
		if (depositAmt>0){
		balance = balance.add(BigDecimal.valueOf(depositAmt));
		}
	}
	//Subtracts selected item price from customer balance.
	public void makePurchase(VendableItem selectedItem) {
		balance = balance.subtract(selectedItem.getPrice());
		spent = spent.add(selectedItem.getPrice());
	}
	//returns the amount spent for the sales report method.
	public BigDecimal getSpent() {
		return spent;
	}
	//Sets balance to 0 once the customer gets their change.
	public void zeroOutBalance() {
		balance = new BigDecimal("0.00");
	}
}