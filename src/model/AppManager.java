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
		
//		staff.add(new StaffMember("Gabriel Kremer", "1000372548", "123456", "2002-08-02"));
		staff.add(new StaffMember("Gabriel Kremer", "1", "123", "2002-08-02"));
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
	
	
	public boolean correctPassword(String id, String password) {
		
		boolean correct = false;
		
		for(int i = 0; i < staff.size() && !correct; i++) {
			
			if(staff.get(i).getId().equals(id)) {
				
				if(staff.get(i).getPassword().equals(password)) {
					
					correct = true;
				}
			}
		}
		
		return correct;
	}
	
	public boolean addStaffMember(StaffMember m) {

		if(staff.add(m)) {

//			totalBillboards++;
			
			return true;

		} else {

			return false;
		}
	}
	
	public boolean addIngredient(Ingredient i) {
		
		if(inventory.add(i)) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	public boolean addCombo(Combo c) {
		
		if(combos.add(c)) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	public boolean addOrder(Order o) {
		
		if(orders.add(o)) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
}
