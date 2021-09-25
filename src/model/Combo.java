package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Combo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private List<Ingredient> ingredients;
	private double price;
	
	public Combo(String name, List<Ingredient> ingredients, double price) {
		super();
		this.name = name;
//		this.ingredients = ingredients;
		ingredients = new ArrayList<Ingredient>();
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Combo [name=" + name + ", ingredients=" + ingredients + ", price=" + price + "]";
	}
}
