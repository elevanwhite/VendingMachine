package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LogWriter {

	File logFile = new File("log.txt");
	//Writes out to log.txt file to track deposits,transactions, and change returned.
	public void addToFile(String line) {
		try(PrintWriter logWrite = new PrintWriter(new FileOutputStream(logFile, true))){
			logWrite.println(line);
		} catch (FileNotFoundException e) {
			System.out.println("Could not to write to file");
		}
	}

	public void logDeposit(String formatStamp, int depositAmt, BigDecimal balance) {
		String logDeposit = String.format("%s FEED MONEY: $%d $%.2f",formatStamp,depositAmt, balance);
		addToFile(logDeposit);
	}
	public void logChange(String formatStamp, BigDecimal change, BigDecimal balance) {
		String logChange = String.format("%s GIVE CHANGE: $%.2f $0.00",formatStamp,change);
		addToFile(logChange);
	}
	public void logTransaction(String formatStamp ,String name, String slot, BigDecimal price, BigDecimal balance) {
		String logTrans= formatStamp+" "+ name + " " + slot + " " +"$"+ price + " " +"$"+ balance;
		addToFile(logTrans);
	}
}
