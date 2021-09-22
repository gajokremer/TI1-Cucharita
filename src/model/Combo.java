package model;

import java.io.Serializable;

public class Combo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Ingredient [] ingredients;
	private double price;
	
	public Combo(String name, Ingredient[] ingredients, double price) {
		super();
		this.name = name;
		this.ingredients = ingredients;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ingredient[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(Ingredient[] ingredients) {
		this.ingredients = ingredients;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
