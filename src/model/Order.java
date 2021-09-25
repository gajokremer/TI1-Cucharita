package model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	private String uuid;
	private List<Combo> combos;
	private String status;
	private String date;
	
	public Order(String uuid, List<Combo> combos, String status, String date) {
		super();
		this.uuid = uuid;
		this.combos = combos;
		this.status = status;
		this.date = date;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Combo> getCombos() {
		return combos;
	}

	public void setCombos(List<Combo> combos) {
		this.combos = combos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Order [uuid=" + uuid + ", combos=" + combos + ", status=" + status + ", date=" + date + "]";
	}
}