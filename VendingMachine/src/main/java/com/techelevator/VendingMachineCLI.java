package com.techelevator;

import java.math.BigDecimal;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_QUIT = "Exit";
	private static final String MAIN_MENU_OPTION_REPORT = "Sales Report";//hidden option that can be used after purchase made
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_QUIT, MAIN_MENU_OPTION_REPORT};
	public static Scanner userInput = new Scanner(System.in);
	private Menu menu;
	private VendingMachine vendingMachine;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		vendingMachine = new VendingMachine();
	}

	public void run() {
		boolean done = false;
		System.out.println("    _    __               ___             ");     
		System.out.println("   | |  / /__  ____  ____/ (_)___  ____ _ ");     
		System.out.println("   | | / / _ \\/ __ \\/ __  / / __ \\/ __ `/ ");     
		System.out.println("   | |/ /  __/ / / / /_/ / / / / / /_/ /  ");     
		System.out.println("   |___/\\___/_/ /_/\\__,_/_/_/ /_/\\__, /   ");     
		System.out.println("                                /____/    ");     
		System.out.println("         __  ___           __    "
				+ "_     ");     
		System.out.println("        /  |/  /___ ______/ /_  (_)___  ___ ");
		System.out.println("       / /|_/ / __ `/ ___/ __ \\/ / __ \\/ _ \\");
		System.out.println("      / /  / / /_/ / /__/ / / / / / / /  __/");
		System.out.println("     /_/  /_/\\__,_/\\___/_/ /_/_/_/ /_/\\___/");
	  
		while (done == false) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				System.out.println(vendingMachine.displayItems());
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				boolean subDone = false;
				while (subDone == false) {
					System.out.println("\n");
					System.out.println("------------------");
					System.out.println("  Balance: $" + vendingMachine.getBalance());
					System.out.println("------------------");
					choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
					if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						System.out.println("Please enter the amount you'd like to add (in whole dollars) >>> ");
						String userDeposit = userInput.nextLine();
						try {
							int depositAmt  = Integer.parseInt(userDeposit);
								vendingMachine.makeDeposit(depositAmt);
						} catch (NumberFormatException e) {
							System.out.println("Please deposit whole dollar amount");
						}
					} else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						System.out.println("Please make your selection >>>");
						String userSelection = userInput.nextLine();
						String response = vendingMachine.purchase(userSelection.toUpperCase());
						System.out.println(response);

					} else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
						String change = vendingMachine.getChange();
						System.out.println(change);
						vendingMachine.zeroOutBalance();
						subDone = true;
					} 
					
				}
			} else if (choice.equals(MAIN_MENU_OPTION_QUIT)) {
				done = true;
			} else if (choice.equals(MAIN_MENU_OPTION_REPORT)) {
				vendingMachine.salesReport();
				System.out.println("Sales report has been generated (salesReport.txt)");
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
