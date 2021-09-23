package model;

import java.util.Comparator;

public class InventoryNameComparator implements Comparator<Ingredient> {

	@Override
	public int compare(Ingredient ing1, Ingredient ing2) {

		return ing1.getName().compareTo(ing2.getName());
	}
}