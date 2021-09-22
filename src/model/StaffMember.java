package model;

import java.io.Serializable;

public class StaffMember implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String id;
	private String password;
	private String birthdate;
	
	public StaffMember(String name, String id, String password, String birthdate) {
		super();
		this.name = name;
		this.id = id;
		this.password = password;
		this.birthdate = birthdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
}
