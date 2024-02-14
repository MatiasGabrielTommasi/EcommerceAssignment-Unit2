package com.ecommerce;

public class Customer {
	private int customerID;
	private String name;
	private String address;
	
	public Customer(int customerId, String name, String address) {
		this.customerID = customerId;
		this.name = name;
		this.address = address;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	
	public void setCurtomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String toString() {
		return name;
	}
}