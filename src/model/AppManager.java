package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppManager {

	private List<StaffMember> staff;
	private List<Ingredient> inventory;
	private List<Combo> combos;
	private List<Order> orders;
//	private List<Ingredient> inventoryCopy;

	
	public String STAFF_MEMBERS_DATA = "data/StaffMembersList.txt";
	public String INVENTORY_DATA = "data/InventoryList.txt";
	public String COMBO_DATA_BIN = "data/ComboListBinary.bin";
	public String ORDER_DATA_BIN = "data/OrderListBinary.bin";

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
	
//	public List<Ingredient> getInventoryCopy() {
//		return inventoryCopy;
//	}
//
//	public void setInventoryCopy(List<Ingredient> inventoryCopy) {
//		this.inventoryCopy = inventoryCopy;
//	}
	
	
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
	
	public List<Ingredient> ingredientsForCombo(String line) {

		List<Ingredient> list = new ArrayList<Ingredient>();

		String [] ingredients = line.split("\n");

		for(int i = 0; i < ingredients.length; i++) {

			String [] attributes = ingredients[i].split(";");

			int quantity = Integer.parseInt(attributes[1]);

			Ingredient ing = new Ingredient(attributes[0], quantity, attributes[2]);

			list.add(ing);
		}
		
		return list;
	}
	
	public List<String> comboNames() {
		
		List<String> names = new ArrayList<String>();
		
		for(int i = 0; i < combos.size(); i++) {
			
			String n = combos.get(i).getName();
			names.add(n);
		}
		
		return names;
	}
	
	public void saveComboData() throws FileNotFoundException, IOException {

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COMBO_DATA_BIN));
		oos.writeObject(combos);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public boolean loadComboData() throws FileNotFoundException, IOException, ClassNotFoundException {

		File f = new File(COMBO_DATA_BIN);

		boolean isLoaded = false;

		if(f.exists()) {

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			combos = (List<Combo>) ois.readObject();
			ois.close();
			isLoaded = true;
		}

		return isLoaded;
	}
	
	public boolean itemIsAvailable(String name, int quantity) {
		
		boolean exists = false;
		boolean available = false;
		
		for(int i = 0; i < inventory.size() && !exists; i++) {
			
			if(inventory.get(i).getName().equalsIgnoreCase(name)) {
				
				exists = true;

				if(inventory.get(i).getQuantity() - quantity >= 0) {
					
					available = true;
				}
			}
		}
		
		return available;
	}
	
	
	//_______________________________ORDERS________________________________
	
	public boolean addOrder(Order o) {
		
		if(orders.add(o)) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	public List<Ingredient> comboIngredientsList(String comboName) {
		
		List<Ingredient> list = new ArrayList<Ingredient>();
		boolean exists = false;
		
		for(int i = 0; i < combos.size() && !exists; i++) {
			
			if(combos.get(i).getName().equalsIgnoreCase(comboName)) {
				
				exists = true;
				
				list.addAll(combos.get(i).getIngredients());
			}
		}
		
		return list;
	}
	
	public Combo findThisCombo(String name) {
		
		boolean exists = false;
		
		Combo c = null;
		
		for(int i = 0; i < combos.size() && !exists; i++) {
			
			if(combos.get(i).getName().equalsIgnoreCase(name)) {
				
				c = combos.get(i);
				exists = true;
			}
		}
		
		return c;
	}
	
	public List<Combo> organizeCombosByPriceInsertionSort(List<Combo> list) {

		int j;
		Combo aux;
		
		for(int i = 1; i < list.size(); i++) {
			
			aux = list.get(i);
			j = i - 1;
			
			while(j >= 0 && aux.getPrice() < list.get(j).getPrice()) {
				
				list.set(j + 1, list.get(j));
				j--;
			}
			
			list.set(j + 1, aux);
		}

		return list;
	}
	
	public void changeOrderStatus(String uuid, String newStatus) {
		
		boolean exists = false;
		
		for(int i = 0; i < orders.size() && !exists; i++) {
			
			if(orders.get(i).getUuid().equalsIgnoreCase(uuid)) {
				
				orders.get(i).setStatus(newStatus);
				exists = true;
			}
		}
	}
	
	public void saveOrderData() throws FileNotFoundException, IOException {

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDER_DATA_BIN));
		oos.writeObject(orders);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public boolean loadOrderData() throws FileNotFoundException, IOException, ClassNotFoundException {

		File f = new File(ORDER_DATA_BIN);

		boolean isLoaded = false;

		if(f.exists()) {

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			orders = (List<Order>) ois.readObject();
			ois.close();
			isLoaded = true;
		}

		return isLoaded;
	}
	
	public List<String> combosOfAnOrder(String uuid) {
		
		List<String> list = new ArrayList<String>();
		
		boolean exists = false;
		
		for(int i = 0; i < orders.size() && !exists; i++) {
			
			if(orders.get(i).getUuid().equalsIgnoreCase(uuid)) {
				
				for(int j = 0; j < orders.get(i).getCombos().size(); j++) {
					
					list.add(orders.get(i).getCombos().get(j).getName());
				}
			}
		}
		
		return list;
	}
	
	public boolean allItemsAreAvailable(Combo c, List<Ingredient> inventoryCopy) throws IOException {
<<<<<<< HEAD
		
		boolean allAvailable = true;
		boolean overZero = true;
		boolean isAvailable = true;
		
		List<Ingredient> list = c.getIngredients();
		
		for(int i = 0; i < list.size() && isAvailable == true; i++) {
			
			
			for(int j = 0; j < inventoryCopy.size() && overZero == true; j++) {
				
				if(list.get(i).getName().equalsIgnoreCase(inventoryCopy.get(j).getName())) {
					
					int result = inventoryCopy.get(j).getQuantity() - list.get(i).getQuantity();
					
					System.out.println("---" + inventoryCopy.get(j).getQuantity() + " - " + list.get(i).getQuantity() + " = " + result); //APPROVED
					
					inventoryCopy.get(j).setQuantity(result);
					
					if(result > 0) {
						
						
						
					} else if(result <= 0) {
						
						System.out.println("---" + false + " STOP"); //ALLOWS EXTRAS
						
						overZero = false;
						allAvailable = false;
=======

		boolean allAvailable = true;

		List<Ingredient> list = c.getIngredients();
		
		System.out.println("Inventory size: " + inventory.size());
		System.out.println("List size: " + list.size());

		for(int i = 0; i < list.size() && allAvailable == true; i++) {

			boolean itemAvailable = true;

//			for(int j = 0; j < inventoryCopy.size() && itemAvailable; j++) {
//
//				if(list.get(i).getName().equalsIgnoreCase(inventoryCopy.get(j).getName())) {
//
//					if(inventoryCopy.get(j).getQuantity() > 0) {
//
//						int result = inventoryCopy.get(j).getQuantity() - list.get(i).getQuantity();
//
//						if(result >= 0) {
//
//							inventoryCopy.get(j).setQuantity(result);
//
//						} else if(result < 0) {
//
//							itemAvailable = false;
//						}
//					}
//				}
//			}
//
//			for(int k = 0; k < inventory.size() && itemAvailable; k++) {
//
//				if(list.get(i).getName().equalsIgnoreCase(inventory.get(k).getName())) {
//
//					System.out.println("Inventory: " + inventory.get(k).getQuantity());
//					System.out.println("List: " + list.get(i).getQuantity());
//
//					int result = inventory.get(k).getQuantity() - list.get(i).getQuantity();
//					System.out.println("SUBS: " + k + " " + result);
//
//					if(result >= 0) {
//
//						inventory.get(k).setQuantity(result);
//					}
//				}
//			}
//
//			if(itemAvailable == false) {
//
//				allAvailable = false;
//			}

			for(int k = 0; k < inventoryCopy.size() && allAvailable == true; k++) {

				if(list.get(i).getName().equalsIgnoreCase(inventoryCopy.get(k).getName())) {

					if(inventoryCopy.get(k).getQuantity() > 0) {

						int result = inventoryCopy.get(k).getQuantity() - list.get(i).getQuantity();

						System.out.println("SUBS: " + k + " " + result);

						if(result >= 0) {

							inventoryCopy.get(k).setQuantity(result);

						} else if(result < 0) {

							allAvailable = false;
						}
>>>>>>> master
					}
				}
			}
		}
		
<<<<<<< HEAD
//		if(overZero == false && isAvailable == false) {
//			
//			allAvailable = false;
//		}
		
		
//		boolean itemAvailable = true;
//
//		List<Ingredient> list = c.getIngredients();
//
//		for(int i = 0; i < list.size() && itemAvailable == true; i++) {
//
//			boolean overZero = true;
//
//			for(int  j = 0; j < inventoryCopy.size() && overZero == true; j++) {
//
//				if(list.get(i).getName().equalsIgnoreCase(inventoryCopy.get(j).getName())) { //APPROVED
//
//					int result = inventoryCopy.get(j).getQuantity() - list.get(i).getQuantity();
//
//					System.out.println("---" + inventoryCopy.get(j).getQuantity() + " - " + list.get(i).getQuantity() + " = " + result); //APPROVED
//
//					inventoryCopy.get(j).setQuantity(result); //APPROVED
//
//					if(result == 0) {
//
//						System.out.println("---" + false + " STOP"); //ALLOWS EXTRAS
//
//						overZero = false;
//					}
//				}
//
//				System.out.println("=====END OF J FOR");
//			}
//
//			for(int k = 0; k < inventoryCopy.size() && overZero == true; k++) {
//
//				if(list.get(i).getName().equalsIgnoreCase(inventoryCopy.get(k).getName()));
//
//				System.out.println(i + ", " + k + " " + true);
//			}
//		}
		
		
		
		
		
//		List<Ingredient> list = c.getIngredients();
//		
//		for(int i = 0; i < list.size() && allAvailable == true; i++) {
//		
//			for(int j = 0; j < inventoryCopy.size() && allAvailable == true; j++) {
//				
//				if(list.get(i).getName().equalsIgnoreCase(inventoryCopy.get(j).getName())) {
//					
//					int result = inventoryCopy.get(j).getQuantity() - list.get(i).getQuantity();
//
//					System.out.println("SUBS: " + j + " " + result);
//					
//					if(result >= 0) {
//						
//						inventory.get(j).setQuantity(result);
//						exportInventoryData();
//						
//					} else if(result < 0) {
//						
//						allAvailable = false;
//					}
//				}
//			}
//			
//		}
		
=======
>>>>>>> master
		System.out.println();
		
		System.out.println(inventoryCopy.toString());
		
		exportInventoryData();
		
		return allAvailable;
	}
	
	public void restoreIngredientValues(List<Combo> list) throws IOException {

		for(int i = 0; i < list.size(); i++) {

			for(int j = 0; j < inventory.size(); j++) {

				for(int k = 0; k < list.get(i).getIngredients().size(); k++) {
					
//					System.out.println(list.get(i).getIngredients().get(k).getName() + "\n");

					if(inventory.get(j).getName().equalsIgnoreCase(list.get(i).getIngredients().get(k).getName())) {
						
//						System.out.println("Inventory: " + inventory.get(j).getName());
//						System.out.println("List: " + list.get(i).getIngredients().get(k));

						int result = inventory.get(j).getQuantity() + list.get(i).getIngredients().get(k).getQuantity();
						
						System.out.println("ADD: " + k + " " + result);;
						
//						System.out.println(inventory.get(j).getQuantity() + " + " + list.get(i).getIngredients().get(k).getQuantity());
//						System.out.println(result + "\n");

						inventory.get(j).setQuantity(result);
						exportInventoryData();
					}
				}
			}
			
			System.out.println();
		}
	}
	
//	public boolean orderCanBeCreated(Order o) {
//		
//		boolean canCreateOrder = true;
//		
//		List<Combo> list = o.getCombos();
//		
//		for(int i = 0; i < list.size() && canCreateOrder; i++) {
//			
//			for(int j = 0; j < list.get(i).getIngredients().size() && canCreateOrder; j++) {
//				
//				for(int k = 0; k < inventory.size(); k++) {
//					
//					
//				}
//			}
//		}
//		
//		return false;
//	}
	
//	public List<Ingredient> getTheIngredientsForThisCombo(String name) {
//		
//		List<Ingredient> list = new ArrayList<Ingredient>();
//		
//		for(int i = 0; i < combos.size(); i++) {
//			
//			if(combos.get(i).getName().equalsIgnoreCase(name)) {
//				
//				list.addAll(combos.get(i).getIngredients());
//			}
//		}
//		
//		return list;
//	}
}
