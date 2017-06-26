package model.dbObject;

public class Border {
	private int id;
	private String name;
	private int stateId;
	private int countryId;

	
	public Border(int id, String name, int stateId) {
		this.id = id;
		this.name = name;
		this.stateId = stateId;
		this.countryId = countryId;
		
		
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
	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	
	
}



