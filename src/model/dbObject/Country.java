package model.dbObject;

public class Country {
	
	private int id;
	private String name;
	private String region;
	private double Countryarea;
	private String capitalName;
	private double population;
	private String Countryabbreviation;
	private String highland;
	private String lowland;

	
	public Country(int id, String name, String region, double area, String capitalName, double population, String abbreviation, String highland, String lowland) {
		
		this.id = id;
		this.name = name;
		this.region = region;
		this.Countryarea = area;
		this.capitalName=capitalName;
		this.population=population;
		this.Countryabbreviation=abbreviation;
		this.highland=highland;
		this.lowland=lowland;
				
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
	
	public String getRegion() {
		return region;
	}


	public double getArea() {
		return Countryarea;
	}

	public void setArea(double area) {
		this.Countryarea = area;
	}

	public String getCapitalName() {
		return capitalName;
	}

	public void setCapitalName(String capitalName) {
		this.capitalName=capitalName;
	}
	
	public double getPopulation() {
		return population;
	}

	public void setPopulation(double population) {
		this.population = population;
	}

	public String getAbbreviation() {
		return Countryabbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.Countryabbreviation=abbreviation;
	}
	public String getHighland() {
		return highland;
	}

	public void setHighland(String highland) {
		this.highland=highland;
	}
	public String getLowland() {
		return lowland;
	}

	public void setLowland(String lowland) {
		this.lowland=lowland;
	}

	
}



