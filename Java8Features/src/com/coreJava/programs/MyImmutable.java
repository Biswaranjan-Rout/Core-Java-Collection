package com.coreJava.programs;

import java.util.Date;

public final class MyImmutable {
	private final int accountNo;
	private final String bankName;
	private final Date mutableDate;

	private MyImmutable(int accountNo, String bankName, Date date) {
		this.accountNo = accountNo;
		this.bankName = bankName;
		this.mutableDate = new Date(date.getTime());
	}

	public static MyImmutable getInstance(int accountNo, String bankName, Date date) {
		return new MyImmutable(accountNo, bankName, date);
	}

	public int getAccountNo() {
		return accountNo;
	}

	public String getBankName() {
		return bankName;
	}

	public Date getMutableDate() {
		return new Date(mutableDate.getTime());
	}

	@Override
	public String toString() {
		return "MyImmutable [accountNo=" + accountNo + ", bankName=" + bankName + ", mutableDate=" + mutableDate + "]";
	}

}
