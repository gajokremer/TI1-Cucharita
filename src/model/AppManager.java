package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppManager {

	private List<StaffMember> staff;
	private List<Ingredient> inventory;
	private List<Combo> combos;
	private List<Order> orders;
	
	public String STAFF_MEMBERS_DATA = "data/StaffMembersList.txt";
	public String INVENTORY_DATA = "data/InventoryList.txt";
//	public String STAFF_MEMBERS_DATA = "data/StaffMembersList.txt";
//	public String STAFF_MEMBERS_DATA = "data/StaffMembersList.txt";

	
	
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
	
	//_______________________________STAFF________________________________
	
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
	
	public void importStaffData() throws IOException {

//		StaffMembers.removeAll(StaffMembers);

		BufferedReader br = new BufferedReader(new FileReader(STAFF_MEMBERS_DATA));
		String line = br.readLine();

		while(line != null) {

			String [] parts = line.split(";");
			StaffMember  m = new StaffMember(parts[0], parts[1], parts[2], parts[3]);
			addStaffMember(m);
			line = br.readLine();
		}

		br.close();
	}

	public void exportStaffData() throws IOException {

		FileWriter fw = new FileWriter(STAFF_MEMBERS_DATA, false);

		for(int i = 0; i < staff.size(); i++) {

			StaffMember m = staff.get(i);
			fw.write(m.getName() + ";" + m.getId() + ";" + 
					m.getPassword() + ";" + m.getBirthdate() + "\n");
		}

		fw.close();
	}
	
	
	//_______________________________INVENTORY________________________________
	
	public boolean addIngredient(Ingredient i) {
		
		if(inventory.add(i)) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	public List<String> inventoryNamesInList() {
		
		List<String> ingredientNames = new ArrayList<String>();
		
		if(inventory != null) {
			
			for(int i = 0; i < inventory.size(); i++) {
				
				String n = inventory.get(i).getName();
				ingredientNames.add(n);
			}
		}
		
		return ingredientNames;
	}
	
	public void sortInventoryByName() {
		
		Comparator<Ingredient> c1 = new InventoryNameComparator();
		Collections.sort(inventory, c1);
	}
	
	public void importInventoryData() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(INVENTORY_DATA));
		String line = br.readLine();

		while(line != null) {

			String [] parts = line.split(";");
			
			int quantity = Integer.parseInt(parts[1]);
			
			Ingredient  i = new Ingredient(parts[0], quantity, parts[2]);
			addIngredient(i);
			line = br.readLine();
		}
		
		sortInventoryByName();
		
		br.close();
	}

	public void exportInventoryData() throws IOException {

		FileWriter fw = new FileWriter(INVENTORY_DATA, false);

		for(int i = 0; i < inventory.size(); i++) {

			Ingredient ing = inventory.get(i);
			fw.write(ing.getName() + ";" + ing.getQuantity() + ";" + 
					ing.getUnit() + "\n");
		}

		fw.close();
	}
	
	//_______________________________COMBOS________________________________
	
	public boolean addCombo(Combo c) {
		
		if(combos.add(c)) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	//_______________________________ORDERS________________________________
	
	public boolean addOrder(Order o) {
		
		if(orders.add(o)) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
}
