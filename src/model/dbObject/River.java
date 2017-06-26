package model.dbObject;

public class River {
	private int id;
	private String name;
	private int stateId;
	private double length;
	private int countryId;
	private String Statename;
	
	public River(int id, String name, int stateId, double length, int countryId, String statename) {
		this.id = id;
		this.name = name;
		this.stateId = stateId;
		this.length = length;
		this.countryId = countryId;
		this.Statename=statename;
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

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getStatenameId() {
		return Statename;
	}

	public void setStatenameId(String statename) {
		this.Statename=statename;
}
	
}





