package model.dbObject;

public class Road {
	private int id;
	private String name;
	private int stateId;
	private double length;
	

	public Road(int id, String name, int stateId, double length) {
		
		this.id=id;
		this.name=name;
		this.stateId=stateId;
		this.length=length;
		
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

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

}





