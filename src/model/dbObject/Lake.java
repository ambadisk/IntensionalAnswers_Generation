package model.dbObject;

public class Lake {
	private int id;
	private String name;
	private double length;
	private int stateId;
	
	public Lake(int id, String name, double length, int stateId) {
		this.id = id;
		this.name = name;
		this.length = length;
		this.stateId = stateId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	
	
}
