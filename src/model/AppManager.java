package model;

import java.util.ArrayList;
import java.util.List;

public class AppManager {

	private List<StaffMember> staff;
	private List<Ingredient> inventory;
	private List<Combo> combos;
	private List<Order> orders;
	
	
	public AppManager() {
		
		staff = new ArrayList<StaffMember>();
		inventory = new ArrayList<Ingredient>();
		combos = new ArrayList<Combo>();
		orders = new ArrayList<Order>();
	}
	
	public List<StaffMember> getStaff() {
		return staff;
	}
	public void setStaff(List<StaffMember> staff) {
		this.staff = staff;
	}
	public List<Ingredient> getInventory() {
		return inventory;
	}
	public void setInventory(List<Ingredient> inventory) {
		this.inventory = inventory;
	}
	public List<Combo> getCombos() {
		return combos;
	}
	public void setCombos(List<Combo> combos) {
		this.combos = combos;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
}
