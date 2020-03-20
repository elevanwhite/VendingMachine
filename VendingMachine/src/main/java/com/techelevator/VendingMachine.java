package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {
	public static int stockingQuantity = 5;
	public static File sourceFile = new File("VendingMachine.txt");
	public static File salesReportFile = new File("salesReport.txt");
	public static LocalDateTime timeStamp = LocalDateTime.now();
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss a");
	public static String formatStamp = timeStamp.format(formatter);

	private Map<String, VendableItem> inventory = new LinkedHashMap<>();
	private CashBox cashBox = new CashBox();
	private LogWriter logWriter = new LogWriter();

	public VendingMachine() {
		//Reads the sourceFile and uses VendableItem interface to determine which objects to create in the machine.
		try (Scanner layoutScanner = new Scanner(sourceFile)) {
			while (layoutScanner.hasNextLine()) {
				String currentLine = layoutScanner.nextLine();
				String[] inventoryUpload = currentLine.split("\\|");
				String slot = inventoryUpload[0];
				String name = inventoryUpload[1];
				BigDecimal price = new BigDecimal(inventoryUpload[2]);
				String type = inventoryUpload[3];
				if (type.equals("Chip")) {
					VendableItem v = new Chips(slot, name, price, stockingQuantity);
					inventory.put(slot, v);
				} else if (type.equals("Gum")) {
					VendableItem v = new Gum(slot, name, price, stockingQuantity);
					inventory.put(slot, v);
					} else if (type.equals("Drink")) {
					VendableItem v = new Beverage(slot, name, price, stockingQuantity);
					inventory.put(slot, v);
				} else if (type.equals("Candy")) {
					VendableItem v = new Candy(slot, name, price, stockingQuantity);
					inventory.put(slot, v);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("VendingMachine.txt file not found");
		}
	}
	//Displays the item menu. Populated by the sourceFile at runtime. Also notes when items are sold out.
	public String displayItems() {
		String result = "";
		String soldOutNote = "";
		System.out.println("\n__________________Menu__________________");
		for (String s : inventory.keySet()) {
			VendableItem currentItem = inventory.get(s);
			int itemQuantity = currentItem.getQuantity();
			if (itemQuantity == 0) {
				soldOutNote = " *SOLD OUT*";
			} else {
				soldOutNote = "";
			}
			result += String.format("%-10s%-25s$%-15.02f%-10s\n", currentItem.slot(), currentItem.getName(),
					currentItem.getPrice(), soldOutNote);
		}
		return result;

	}
	//Handles customer input to determine if selection is valid and funds are sufficient. If so, the purchase is made.
	public String purchase(String userSelection) {
		String result = "Item not available";
		if (inventory.containsKey(userSelection)) {
			VendableItem selectedItem = inventory.get(userSelection);
			if (selectedItem.getQuantity() > 0 && (cashBox.getBalance().compareTo(selectedItem.getPrice()) > 0)) {
				result = selectedItem.dispense();
				cashBox.makePurchase(selectedItem);
				logWriter.logTransaction(formatStamp, selectedItem.getName(), selectedItem.slot(),
						selectedItem.getPrice(), cashBox.getBalance());
			} else if (selectedItem.getQuantity() > 0 && (cashBox.getBalance().compareTo(selectedItem.getPrice()) < 0)) {
				result = "Insufficient funds";
			}
		} else {
			result = "Invalid selection";
		}
		return result;
	}

	public BigDecimal getBalance() {
		return cashBox.getBalance();
	}
	//Sets balance to 0 once the customer gets their change.
	public void zeroOutBalance() {
		cashBox.zeroOutBalance();
	}
	//Deposits input whole dollars from user.
	public void makeDeposit(int depositAmt) {
		cashBox.makeDeposit(depositAmt);
		logWriter.logDeposit(formatStamp, depositAmt, cashBox.getBalance());
	}
	//Returns the change owed to customer. Logs when change was given in the log.txt file.
	public String getChange() {
		BigDecimal change = getBalance();
		String message = cashBox.getChange();
		logWriter.logChange(formatStamp, change, cashBox.getBalance());
		return message;
	}
	//Generates a sales report displaying quantity of items sold and the total amount in sales
	public void salesReport() {
		try (PrintWriter salesWriter = new PrintWriter(salesReportFile)) {
			for (String sr : inventory.keySet()) {
				VendableItem currentItem = inventory.get(sr);
				salesWriter.println(currentItem.getName() + " | " + (stockingQuantity - currentItem.getQuantity()));
			}
			salesWriter.println("");
			salesWriter.println("**TOTAL SALES** $" + cashBox.getSpent());
		} catch (FileNotFoundException e) {
			System.out.println("Could not export the sales report.");
		}
	}
}
