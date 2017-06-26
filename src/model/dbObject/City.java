package model.dbObject;

public class City {
	private int id;
	private String name;
	private int stateId;
	private double area;
	private double population;
	
	public City(int id, String name, int stateId, double area, double population) {
		this.id = id;
		this.name = name;
		this.stateId = stateId;
		this.area = area;
		this.population = population;
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

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getPopulation() {
		return population;
	}

	public void setPopulation(double population) {
		this.population = population;
	}
}
