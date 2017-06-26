package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.dbObject.Border;
import model.dbObject.City;
import model.dbObject.Country;
import model.dbObject.Lake;
import model.dbObject.Landelevation;
import model.dbObject.Mountain;
import model.dbObject.River;
import model.dbObject.Road;
import model.dbObject.State;
import util.DBConnector;

public class DBData {
	
	//public static Map<UserType, Set<String>> userInterestMapping;
	public static Map<Integer, State> states;
	public static Map<String, Integer> stateNameIds; // state name -> state id

	public static Map<Integer, City> cities;
	public static Map<String, Integer> cityNameIds; // city name -> city id

	public static Map<Integer, Lake> lakes;
	public static Map<String, Integer> lakeNameIds; // lake name -> lake id

	public static Map<Integer, Border> borders;
	public static Map<String, Integer> borderNameIds;

	public static Map<Integer, Country> countries;
	public static Map<String, Integer> countryNameIds;

	public static Map<Integer, Landelevation> landelevations;
	public static Map<String, Integer> landelevationNameIds;

	public static Map<Integer, Mountain> mountains;
	public static Map<String, Integer> mountainNameIds;

	public static Map<Integer, River> rivers;
	public static Map<String, Integer> riverNameIds;
	

	public static Map<Integer, Road> roads;
	public static Map<String, Integer> roadNameIds;
	
	public static List<HashMap> allnames;

	private static void loadStates() throws SQLException {
if (states == null || states.isEmpty()) {
			states = new HashMap<>();
			stateNameIds = new HashMap<>();

			Statement sta = DBConnector.conn.createStatement();
			String Sql = "select * from state";
			ResultSet rs = sta.executeQuery(Sql);

			
			State state;
			while (rs.next()) {
				int id = rs.getInt("StateId");
				String name = rs.getString("StateName");
				String stateAbbreviation = rs.getString("StateAbbreviation");
				int countryId = rs.getInt("CountryId");
				String capital = rs.getString("StateCapital");
				String gmt = rs.getString("StateGMT");
				double population = rs.getDouble("TotalPopulation");
				double area = rs.getDouble("Area");
				double gdp = rs.getDouble("GDP");
				double riverCount = rs.getDouble("RiverCount");
				double cityCount = rs.getDouble("CityCount");
				double mountainCount = rs.getDouble("MountainCount");
				double lakeCount = rs.getDouble("LakeCount");
				double roadCount = rs.getDouble("RoadCount");
				String Location= rs.getString("Location");
				state = new State(id, name, stateAbbreviation, countryId, capital, gmt, population, area, gdp, riverCount, cityCount, mountainCount, lakeCount, roadCount,Location);
				states.put(id, state);
				stateNameIds.put(name, id);
				
			}
		}
	}

	private static void loadRoads() throws SQLException {
		if (roads == null || roads.isEmpty()) {
			roads = new HashMap<>();
			roadNameIds = new HashMap<>();

			Statement sta = DBConnector.conn.createStatement();
			String Sql = "select * from road";
			ResultSet rs = sta.executeQuery(Sql);

			Road road;
			while (rs.next()) {
				int id = rs.getInt("RoadId");
				String name = rs.getString("RoadName");
				int stateId = rs.getInt("StateId");
				double length = rs.getDouble("RoadLength");

				road = new Road(id, name, stateId, length);
				roads.put(id, road);
				roadNameIds.put(name, id);
			}
		}
	}

	private static void loadRivers() throws SQLException {
		if (rivers == null || rivers.isEmpty()) {
			rivers = new HashMap<>();
			riverNameIds = new HashMap<>();
            
			Statement sta = DBConnector.conn.createStatement();
			String Sql = "select * from river";
			ResultSet rs = sta.executeQuery(Sql);

			River river;
			while (rs.next()) {
				int id = rs.getInt("RiverId");
				String name = rs.getString("RiverName");
				int stateId = rs.getInt("StateId");
				double length = rs.getDouble("RiverLength");
				int countryId = rs.getInt("CountryId");
				String statename = rs.getString("StateName");

				river = new River(id, name, stateId, length, countryId, statename);
				rivers.put(id, river);
				riverNameIds.put(name, id);
				
				
			}
		}
	}

	private static void loadMountains() throws SQLException {
		if (mountains == null || mountains.isEmpty()) {
			mountains = new HashMap<>();
			mountainNameIds = new HashMap<>();

			Statement sta = DBConnector.conn.createStatement();
			String Sql = "select * from mountain";
			ResultSet rs = sta.executeQuery(Sql);

			Mountain mountain;
			while (rs.next()) {
				int id = rs.getInt("MountainId");
				String name = rs.getString("MountainName");
				int stateId = rs.getInt("StateId");
				double length = rs.getDouble("MountainHeight");

				int countryId = rs.getInt("CountryId");

				mountain = new Mountain(id, name, stateId, length, countryId);
				mountains.put(id, mountain);
				mountainNameIds.put(name, id);
			}
		}
	}

	private static void loadlandelevations() throws SQLException {
		if (landelevations == null || landelevations.isEmpty()) {
			landelevations = new HashMap<>();
			landelevationNameIds = new HashMap<>();

			Statement sta = DBConnector.conn.createStatement();
			String Sql = "select * from landelevation";
			ResultSet rs = sta.executeQuery(Sql);

			Landelevation landElevation;
			while (rs.next()) {
				int id = rs.getInt("LandelevationId");
				String name = rs.getString("landelevationName");
				String highland = rs.getString("Highland");
				String lowland = rs.getString("Lowland");
				int stateId = rs.getInt("StateId");
				double highLength = rs.getDouble("HighLength");
				double lowLength = rs.getDouble("LowLength");

				landElevation = new Landelevation(id, name, highland, lowland, stateId, highLength, lowLength);
				landelevations.put(id, landElevation);
				landelevationNameIds.put(name, id);
				
			}
		}
	}

	private static void loadCountries() throws SQLException {
		if (countries == null || countries.isEmpty()) {
			countries = new HashMap<>();
			countryNameIds = new HashMap<>();

			Statement sta = DBConnector.conn.createStatement();
			String Sql = "select * from country";
			ResultSet rs = sta.executeQuery(Sql);

			Country country;
			while (rs.next()) {
				int id = rs.getInt("CountryId");
				String name = rs.getString("CountryName");
				String highland = rs.getString("Highland");
				String region = rs.getString("Region");
				String lowland = rs.getString("Lowland");
				String Countryabbreviation = rs.getString("CountryAbbreviation");

				String capitalName = rs.getString("CountryCapital");

				double population = rs.getDouble("TotalPopulation");
				double Countryarea = rs.getDouble("Area");

				country = new Country(id, name, region, Countryarea, capitalName, population, Countryabbreviation,
						highland, lowland);
				countries.put(id, country);
				countryNameIds.put(name, id);
			}
		}
	}

	private static void loadBorders() throws SQLException {
		if (borders == null || borders.isEmpty()) {
			borders = new HashMap<>();
			borderNameIds = new HashMap<>();

			Statement sta = DBConnector.conn.createStatement();
			String Sql = "select * from border";
			ResultSet rs = sta.executeQuery(Sql);

			Border border;
			while (rs.next()) {
				int id = rs.getInt("BorderId");
				String name = rs.getString("BorderName");
				int stateId = rs.getInt("StateID");
				//int countryId = rs.getInt("CountryID");

				border = new Border(id, name, stateId);
				borders.put(id, border);
				borderNameIds.put(name, id);
			}
		}
	}

	private static void loadLakes() throws SQLException {
		if (lakes == null || lakes.isEmpty()) {
			lakes = new HashMap<>();
			lakeNameIds = new HashMap<>();

			Statement sta = DBConnector.conn.createStatement();
			String Sql = "select * from lake";
			ResultSet rs = sta.executeQuery(Sql);

			Lake lake;
			while (rs.next()) {
				int id = rs.getInt("LakeId");
				String name = rs.getString("LakeName");
				double length = rs.getDouble("LakeLength");
				int stateId = rs.getInt("StateId");

				lake = new Lake(id, name, length, stateId);
				lakes.put(id, lake);
				lakeNameIds.put(name, id);
			}
		}
	}

	private static void loadCities() throws SQLException {
		if (cities == null || cities.isEmpty()) {
			cities = new HashMap<>();
			cityNameIds = new HashMap<>();

			Statement sta = DBConnector.conn.createStatement();
			String Sql = "select * from city";
			ResultSet rs = sta.executeQuery(Sql);

			City city;
			while (rs.next()) {
				int id = rs.getInt("CityId");
				String name = rs.getString("CityName");
				int stateId = rs.getInt("StateID");
				double area = rs.getDouble("Area");
				double population = rs.getDouble("TotalPopulation");

				city = new City(id, name, stateId, area, population);
				cities.put(id, city);
				cityNameIds.put(name, id);
			}
		}
	}
	
	public static void loadAllDBData() throws ClassNotFoundException, SQLException {
		DBConnector.connectToDB();
		loadStates();
		loadCities();
		loadLakes();
		loadCountries();
		loadlandelevations();
		loadRivers();
		loadRoads();
		loadBorders();
		loadMountains();
		loadAllNames();
	}
	
	
	public static void loadAllNames() {
		allnames =  new ArrayList<HashMap>();
		
		allnames.add((HashMap) stateNameIds);
		allnames.add((HashMap) cityNameIds);
		allnames.add((HashMap) lakeNameIds);
		allnames.add((HashMap) countryNameIds);
		allnames.add((HashMap) landelevationNameIds);
		allnames.add((HashMap) riverNameIds);
		allnames.add((HashMap) roadNameIds);
		allnames.add((HashMap) borderNameIds);
		allnames.add((HashMap) mountainNameIds);
		
		
	}
	
}


