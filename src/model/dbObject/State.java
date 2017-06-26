package model.dbObject;

public class State {
	private int id;
	private String name;
	private String stateAbbreviation;
	private int countryId;
	private String capital;
	private String gmt;
	private double population;
	private double area;
	private double gdp;
	private double riverCount;
	private double cityCount;
	private double mountainCount;
	private double lakeCount;
	private double roadCount;
	private String Location;

	public State(int id, String name, String abbreviation, int countryId, String capital, String gmt, double population,
			double area, double gdp, double riverCount, double cityCount, double mountainCount, double lakeCount, double roadCount ,String Location) {
		this.id = id;
		this.name = name;
		this.stateAbbreviation = abbreviation;
		this.countryId = countryId;
		this.capital = capital;
		this.gmt = gmt;
		this.population = population;
		this.area = area;
		this.gdp = gdp;
		this.riverCount = riverCount;
		this.cityCount = cityCount;
		this.mountainCount = mountainCount;
		this.lakeCount = lakeCount;
		this.roadCount=roadCount;
		this.Location= Location;
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

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getGmt() {
		return gmt;
	}

	public void setGdp(double gdp) {
		this.gdp = gdp;
	}

	public double getGdp() {
		return gdp;
	}

	public void setGmt(String gmt) {
		this.gmt = gmt;
	}

	public double getPopulation() {
		return population;
	}

	public void setPopulation(double population) {
		this.population = population;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getRiverCount() {
		return riverCount;
	}

	public void setRiverCount(double riverCount) {
		this.riverCount = riverCount;
	}

	public double getCityCount() {
		return cityCount;
	}

	public void setCityCount(double cityCount) {
		this.cityCount = cityCount;
	}

	public double getMountainCount() {
		return mountainCount;
	}

	public void setMountainCount(double mountainCount) {
		this.mountainCount = mountainCount;
	}

	public double getLakeCount() {
		return lakeCount;
	}

	public void setLakeCount(double lakeCount) {
		this.lakeCount = lakeCount;
	}
	
	public double getRoadCount() {
		return roadCount;
	}

	public void setRoadCount(double roadCount) {
		this.roadCount = roadCount;
	}
	public void setLocation(String Location){
		this.Location=Location;
	}
	public String getLocation(){
		return Location;
	}
}

