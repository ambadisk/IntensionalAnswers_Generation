package model.dbObject;

public class Landelevation {
	private int id;
	private String name;
	private String highland;
	private String lowland;
	private int stateId;
	private double highLength;
	private double lowLength;
	
	public Landelevation(int id, String highland, String lowland, String name , int stateId, double highLength, double lowLength) {
		this.id = id;
		this.name=name;
		this.highland = highland;
		this.lowland = lowland;
		this.stateId=stateId;
		this.highLength = highLength;
		this.lowLength = lowLength;
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

	public String getHighland() {
		return highland;
	}

	public void setHighland(String highland) {
		this.highland = highland;
	}
	public String getLowland() {
		return lowland;
	}

	public void setLowland(String lowland) {
		this.lowland = lowland;
	}
	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	

	public double getHighLength() {
		return highLength;
	}

	public void setHighLength(double highLength) {
		this.highLength = highLength;
	}

	public double getLowLength() {
		return lowLength;
	}

	public void setLowLength(double lowLength) {
		this.lowLength = lowLength;
	}
	
	
}
	