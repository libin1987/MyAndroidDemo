package com.lib.mydemo.model;

public class PagerJson {
	private int count;
	private int totalPages;

	private int currentPage;

	private int totalCount;
	
	private String balance;

	private String arrays;
	private String array;
	
	private int total;
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalPages() {
		return this.totalPages;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public String getArrays() {
		return arrays;
	}

	public void setArrays(String arrays) {
		this.arrays = arrays;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getArray() {
		return array;
	}

	public void setArray(String array) {
		this.array = array;
	}

}
