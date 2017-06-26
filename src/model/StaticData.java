package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StaticData {
	public static Map<String, Set<String>> attributeMappings;
	public static Map<String, Set<String>> queryTableMappings;
	public static Map<String, String> mainAttributeForTable;
	public static Map<String, String> whereAttributeForWhereType; // if
																	// condition
																	// is on
																	// state,
																	// "StateId"
																	// should be
																	// checked
	public static Map<String, Set<String>> sameTableAttributeMappings; // table
																		// name,
																		// attributes
																		// that,
																		// if
																		// are
																		// present,
																		// same
																		// table
																		// should
																		// be
																		// used
																		// to
																		// fetch
																		// results
																		// eg.
																		// of
																		// question:
																		// what
																		// is
																		// the
																		// capital
																		// of
																		// Kansas?
																		// state
																		// should
																		// be
																		// used
																		// as
																		// table
																		// instead
																		// of
																		// city
																		// in
																		// this
																		// case

	public static Set<String> minimumAdjectives; // if any of these adjectives
													// exist in the question,
													// this is a minimum
													// question
													// eg. Which is the smallest
													// city in California?

	public static Set<String> maximumAdjectives; // if any of these adjectives
													// exist in the question,
													// this is a maximum
													// question
													// eg. Which is the largest
													// city in California?

	public static Set<String> countPhrases; // if any of these phrases exist in
											// the question,
											// this is a count question.
											// eg. how many cities are there in
											// Kansas?

	public static Set<String> isPhrases;
	public static Set<String> arePhrases;

	public static Map<String, String> stateTableAttributeMappings; // use only
																	// when we
																	// know that
																	// desired
																	// table is
																	// state. To
																	// know that
																	// for
																	// capital,
																	// StateCapital
																	// column is
																	// to be
																	// used, for
																	// population,
																	// TotalPopulation
																	// column is
																	// used, and
																	// so on.
	public static Map<String, String> cityTableAttributeMappings;
	public static Map<String, String> countryTableAttributeMappings;
	public static Map<String, String> borderTableAttributeMappings;
	public static Map<String, String> riverTableAttributeMappings;
	public static Map<String, String> roadTableAttributeMappings;
	public static Map<String, String> mountainTableAttributeMappings;
	public static Map<String, String> lakeTableAttributeMappings;
	public static Map<String, String> landelevationTableAttributeMappings;

	public static Map<String, String> attributeNameNormalNameMappings; // normal
																		// name
																		// to be
																		// used
																		// in
																		// answer
																		// for
																		// the
																		// attribute
																		// name.
																		// For
																		// eg.
																		// RiverCount
																		// ->
																		// river.
																		// It
																		// will
																		// only
																		// be
																		// used
																		// for
																		// category
																		// 5
																		// (user
																		// interest)
																		// questions

	private static void prepareAttributeNameNormalNameMappings() {
		if (attributeNameNormalNameMappings == null || attributeNameNormalNameMappings.isEmpty()) {
			attributeNameNormalNameMappings = new HashMap<>();

			attributeNameNormalNameMappings.put("RiverCount", "rivers");
			attributeNameNormalNameMappings.put("MountainCount", "mountains");
			attributeNameNormalNameMappings.put("LakeCount", "lakes");
			attributeNameNormalNameMappings.put("CityCount", "cities");
			attributeNameNormalNameMappings.put("RoadCount", "roads");
			attributeNameNormalNameMappings.put("area", "area");
			attributeNameNormalNameMappings.put("gdp", "gdp");
			attributeNameNormalNameMappings.put("TotalPopulation", "TotalPopulation");

		}
	}

	/**
	 * function for preparing mappings that would help in question modelling
	 */
	private static void prepareAttributeMappings() {
		if (attributeMappings == null || attributeMappings.isEmpty()) {
			attributeMappings = new HashMap<>();

			Set<String> areaSet = new HashSet<>();
			areaSet.add("area");

			areaSet.add("largest");
			areaSet.add("smallest");
			areaSet.add("city");
			areaSet.add("biggest");
			areaSet.add("richer");
			areaSet.add("richest");
			areaSet.add("poorest");

			attributeMappings.put("area", areaSet);

			Set<String> populationSet = new HashSet<>();
			populationSet.add("population");
			populationSet.add("cityzens");
			populationSet.add("city");
			populationSet.add("state");
			populationSet.add("people");
			populationSet.add("largest");

			populationSet.add("smallest");
			populationSet.add("highest");

			populationSet.add("richest");
			populationSet.add("richer");
			populationSet.add("poorest");

			attributeMappings.put("TotalPopulation", populationSet);

			Set<String> gdpSet = new HashSet<>();
			gdpSet.add("gdp");
			gdpSet.add("richest");
			gdpSet.add("poorer");
			gdpSet.add("poorest");
			gdpSet.add("richer");
			gdpSet.add("rich");
			gdpSet.add("money");
			gdpSet.add("largest");
			gdpSet.add("highest");
			gdpSet.add("maximam");
			gdpSet.add("smallest");

			attributeMappings.put("gdp", gdpSet);

			Set<String> riverCountSet = new HashSet<>();
			riverCountSet.add("richest");
			riverCountSet.add("richer");
			riverCountSet.add("poorer");
			riverCountSet.add("largest");
			riverCountSet.add("poorest");
			riverCountSet.add("smallest");

			attributeMappings.put("RiverCount", riverCountSet);

			Set<String> roadCountSet = new HashSet<>();
			roadCountSet.add("richest");
			roadCountSet.add("richer");
			roadCountSet.add("largest");
			roadCountSet.add("poorest");
			roadCountSet.add("smallest");
			attributeMappings.put("RoadCount", roadCountSet);

			Set<String> cityCountSet = new HashSet<>();
			cityCountSet.add("richest");
			cityCountSet.add("richer");
			cityCountSet.add("poorer");
			cityCountSet.add("poorestr");
			cityCountSet.add("largest");
			cityCountSet.add("smallest");
			attributeMappings.put("CityCount", cityCountSet);

			Set<String> mountainCountSet = new HashSet<>();
			mountainCountSet.add("richest");
			mountainCountSet.add("largest");
			mountainCountSet.add("poorest");
			mountainCountSet.add("smallest");
			attributeMappings.put("MountainCount", mountainCountSet);

			Set<String> lakeCountSet = new HashSet<>();
			lakeCountSet.add("richest");
			lakeCountSet.add("largest");
			lakeCountSet.add("smallest");
			lakeCountSet.add("richer");
			lakeCountSet.add("poorer");
			attributeMappings.put("LakeCount", lakeCountSet);

			Set<String> riverSet = new HashSet<>();
			riverSet.add("shortest");
			riverSet.add("long");
			riverSet.add("largest");
			riverSet.add("biggest");
			riverSet.add("longest");
			riverSet.add("smallest");
			attributeMappings.put("RiverLength", riverSet);

			Set<String> roadSet = new HashSet<>();
			roadSet.add("length");
			roadSet.add("largest");
			roadSet.add("shortest");
			roadSet.add("smallest");

			attributeMappings.put("RoadLength", roadSet);

			Set<String> lakeSet = new HashSet<>();
			lakeSet.add("lake");
			lakeSet.add("largest");
			lakeSet.add("longest");
			lakeSet.add("shortest");
			lakeSet.add("smallest");
			attributeMappings.put("LakeLength", lakeSet);

			Set<String> mountainSet = new HashSet<>();

			mountainSet.add("largest");
			mountainSet.add("lowest");
			mountainSet.add("smallest");

			attributeMappings.put("Mountainheight", mountainSet);

			Set<String> landelevationSet = new HashSet<>();
			landelevationSet.add("point");
			landelevationSet.add("highland");
			landelevationSet.add("lowland");
			landelevationSet.add("HighLength");
			landelevationSet.add("LowLength");

			attributeMappings.put("landelevation", landelevationSet);

			Set<String> lengthSet = new HashSet<>();
			lengthSet.add("lake");
			lengthSet.add("river");
			lengthSet.add("mountain");
			lengthSet.add("country");

			lengthSet.add("longest");
			lengthSet.add("smallest");
			lengthSet.add("deepest");

			attributeMappings.put("length", lengthSet);

			Set<String> borderSet = new HashSet<>();
			borderSet.add("border");

			attributeMappings.put("border", borderSet);

		}
	}

	// it gives the other column of the table
	// it gives the table name

	private static void prepareSameTableAttributeMappings() {
		if (sameTableAttributeMappings == null || sameTableAttributeMappings.isEmpty()) {
			sameTableAttributeMappings = new HashMap<>();

			Set<String> stateSet = new HashSet<>();
			stateSet.add("capital");
			stateSet.add("area");
			stateSet.add("population");
			stateSet.add("citizens");
			stateSet.add("people");
			stateSet.add("gmt");
			stateSet.add("gdp");
			stateSet.add("river");
			stateSet.add("city");
			stateSet.add("mountain");
			stateSet.add("lake");
			stateSet.add("abbreviation");
			stateSet.add("big");

			sameTableAttributeMappings.put("state", stateSet);

			Set<String> citySet = new HashSet<>();
			citySet.add("area");
			citySet.add("big");

			sameTableAttributeMappings.put("city", citySet);

			Set<String> countrySet = new HashSet<>();
			countrySet.add("region");
			countrySet.add("area");
			countrySet.add("capital");

			countrySet.add("top");
			countrySet.add("bottom");
			countrySet.add("lowland");
			countrySet.add("highland");
			countrySet.add("USA");
			countrySet.add("abbreviation");

			sameTableAttributeMappings.put("country", countrySet);

			Set<String> borderSet = new HashSet<>();
			borderSet.add("border");

			sameTableAttributeMappings.put("border", borderSet);

			Set<String> roadSet = new HashSet<>();
			roadSet.add("road");

			sameTableAttributeMappings.put("road", roadSet);

			Set<String> riverSet = new HashSet<>();
			riverSet.add("river");

			sameTableAttributeMappings.put("river", riverSet);

			Set<String> mountainSet = new HashSet<>();
			mountainSet.add("MountainName");
			mountainSet.add("high");
			mountainSet.add("height");
			mountainSet.add("Mountain");
			mountainSet.add("elevation");
			sameTableAttributeMappings.put("mountain", mountainSet);

			Set<String> lakeSet = new HashSet<>();
			lakeSet.add("lake");
			lakeSet.add("road name");

			sameTableAttributeMappings.put("lake", lakeSet);

			Set<String> landelevationSet = new HashSet<>();
			landelevationSet.add("top");
			landelevationSet.add("point");
			landelevationSet.add("highest");
			landelevationSet.add("bottom");
			landelevationSet.add("highland");
			landelevationSet.add("lowland");
			landelevationSet.add("length");
			landelevationSet.add("elevation");

			sameTableAttributeMappings.put("landelevation", landelevationSet);

		}
	}

	private static void prepareStateTableAttributeMappings() {
		if (stateTableAttributeMappings == null || stateTableAttributeMappings.isEmpty()) {
			stateTableAttributeMappings = new HashMap<>();

			stateTableAttributeMappings.put("capital", "StateCapital");
			stateTableAttributeMappings.put("area", "Area");
			stateTableAttributeMappings.put("population", "TotalPopulation");
			stateTableAttributeMappings.put("citizens", "TotalPopulation");
			stateTableAttributeMappings.put("people", "TotalPopulation");
			stateTableAttributeMappings.put("state", "StateName");
			stateTableAttributeMappings.put("gmt", "StateGMT");
			stateTableAttributeMappings.put("gdp", "GDP");
			stateTableAttributeMappings.put("river", "RiverCount");
			stateTableAttributeMappings.put("City", "CityCount");
			stateTableAttributeMappings.put("mountain", "MountainCount");
			stateTableAttributeMappings.put("lake", "LakeCount");
			stateTableAttributeMappings.put("road", "RoadCount");
			stateTableAttributeMappings.put("abbreviation", "StateAbbreviation");
			stateTableAttributeMappings.put("big", "Area");

		}
	}

	private static void prepareCityTableAttributeMappings() {
		if (cityTableAttributeMappings == null || cityTableAttributeMappings.isEmpty()) {
			cityTableAttributeMappings = new HashMap<>();
			cityTableAttributeMappings.put("area", "Area");
			cityTableAttributeMappings.put("population", "TotalPopulation");
			cityTableAttributeMappings.put("city", "CityName");
			cityTableAttributeMappings.put("big", "Area");

		}
	}

	private static void prepareCountryTableAttributeMapping() {
		if (countryTableAttributeMappings == null || countryTableAttributeMappings.isEmpty()) {
			countryTableAttributeMappings = new HashMap<>();
			countryTableAttributeMappings.put("region", "Region");
			countryTableAttributeMappings.put("area", "CountryArea");
			countryTableAttributeMappings.put("capital", "CountryCapital");
			countryTableAttributeMappings.put("population", "CountryTotalPopulation");
			countryTableAttributeMappings.put("top", "Highland");
			countryTableAttributeMappings.put("Highland", "Highland");
			countryTableAttributeMappings.put("bottom", "Lowland");
			countryTableAttributeMappings.put("lowland", "Lowland");
			countryTableAttributeMappings.put("abbreviation", "CountryAbbreviation");

		}
	}

	private static void prepareMountainTableAttributeMapping() {
		if (mountainTableAttributeMappings == null || mountainTableAttributeMappings.isEmpty()) {
			mountainTableAttributeMappings = new HashMap<>();
			mountainTableAttributeMappings.put("mountain", "MountainName");
			mountainTableAttributeMappings.put("height", "MountainHeight");
			mountainTableAttributeMappings.put("elevation", "MountainHeight");
			mountainTableAttributeMappings.put("high", "MountainHeight");

		}
	}

	private static void prepareRiverTableAttributeMapping() {
		if (riverTableAttributeMappings == null || riverTableAttributeMappings.isEmpty()) {
			riverTableAttributeMappings = new HashMap<>();
			riverTableAttributeMappings.put("long", "RiverLength");
			riverTableAttributeMappings.put("river", "RiverName");
			riverTableAttributeMappings.put("rivers", "RiverName");
			riverTableAttributeMappings.put("length", "RiverLength");

		}
	}

	private static void prepareRoadTableAttributeMapping() {
		if (roadTableAttributeMappings == null || roadTableAttributeMappings.isEmpty()) {
			roadTableAttributeMappings = new HashMap<>();
			roadTableAttributeMappings.put("road", "RoadName");
			roadTableAttributeMappings.put("length", "RoadLength");
			roadTableAttributeMappings.put("long", "RoadLength");

		}
	}

	private static void prepareLandElevationTableAttributeMapping() {
		if (landelevationTableAttributeMappings == null || landelevationTableAttributeMappings.isEmpty()) {
			landelevationTableAttributeMappings = new HashMap<>();
			landelevationTableAttributeMappings.put("highland", "Highland");
			landelevationTableAttributeMappings.put("top", "Highland");
			landelevationTableAttributeMappings.put("point", "Highland");
			landelevationTableAttributeMappings.put("lowest point", "Lowland");
			landelevationTableAttributeMappings.put("highest", "Highland");
			landelevationTableAttributeMappings.put("lowland", "Lowland");
			landelevationTableAttributeMappings.put("bottom", "Lowland");
			landelevationTableAttributeMappings.put("peak", "HighLength");
			landelevationTableAttributeMappings.put("low", "LowLength");
			landelevationTableAttributeMappings.put("length", "LowLength");
			landelevationTableAttributeMappings.put("elevation", "highLength");

		}
	}

	private static void prepareLakeTableAttributeMapping() {
		if (lakeTableAttributeMappings == null || lakeTableAttributeMappings.isEmpty()) {
			lakeTableAttributeMappings = new HashMap<>();
			lakeTableAttributeMappings.put("lake", "LakeName");
			lakeTableAttributeMappings.put("length", "LakeLength");

		}
	}

	private static void prepareBorderTableAttributeMapping() {
		if (borderTableAttributeMappings == null || borderTableAttributeMappings.isEmpty()) {
			borderTableAttributeMappings = new HashMap<>();
			borderTableAttributeMappings.put("border", "BorderName");

		}
	}

	/**
	 * function for preparing mappings that would help in question modelling it
	 * takes all improper noun
	 * 
	 */
	private static void prepareQueryTableMappings() {
		if (queryTableMappings == null || queryTableMappings.isEmpty()) {
			queryTableMappings = new HashMap<>();

			Set<String> lakeSet = new HashSet<>();
			lakeSet.add("lake");
			lakeSet.add("lakes");

			lakeSet.add("water");
			queryTableMappings.put("lake", lakeSet);

			Set<String> mountainSet = new HashSet<>();
			mountainSet.add("mountain");
			mountainSet.add("mountains");
			mountainSet.add("Mountain");

			queryTableMappings.put("mountain", mountainSet);

			Set<String> citySet = new HashSet<>();
			citySet.add("city");
			citySet.add("cities");
			queryTableMappings.put("city", citySet);

			Set<String> roadSet = new HashSet<>();
			roadSet.add("road");
			roadSet.add("roads");

			queryTableMappings.put("road", roadSet);

			Set<String> borderSet = new HashSet<>();
			borderSet.add("border");
			borderSet.add("borders");
			queryTableMappings.put("border", borderSet);

			Set<String> landelevationSet = new HashSet<>();

			landelevationSet.add("landelevation");
			landelevationSet.add("landelevations");
			queryTableMappings.put("landelevation", landelevationSet);

			Set<String> riverSet = new HashSet<>();
			riverSet.add("river");
			riverSet.add("rivers");

			queryTableMappings.put("river", riverSet);

			Set<String> countrySet = new HashSet<>();
			countrySet.add("country");
			countrySet.add("countries");
			countrySet.add("America");
			countrySet.add("usa");
			countrySet.add("USA");

			queryTableMappings.put("country", countrySet);

			Set<String> stateSet = new HashSet<>();
			stateSet.add("state");
			stateSet.add("states");

			queryTableMappings.put("state", stateSet);
		}
	}

	private static void prepareMainAttributesForTables() {
		if (mainAttributeForTable == null || mainAttributeForTable.isEmpty()) {
			mainAttributeForTable = new HashMap<>();

			mainAttributeForTable.put("state", "StateName");
			mainAttributeForTable.put("city", "CityName");
			mainAttributeForTable.put("lake", "LakeName");
			mainAttributeForTable.put("river", "RiverName");
			mainAttributeForTable.put("country", "CountryName");
			mainAttributeForTable.put("landelevation", "landelevationName");
			mainAttributeForTable.put("country", "CountryName");
			mainAttributeForTable.put("river", "StateName");
			mainAttributeForTable.put("road", "RoadName");
			mainAttributeForTable.put("border", "BorderName");
			mainAttributeForTable.put("mountain", "MountainName");
			mainAttributeForTable.put("capital", "capitalName");

		}
	}

	private static void prepareWhereAttributeForWhereType() {
		if (whereAttributeForWhereType == null || whereAttributeForWhereType.isEmpty()) {
			whereAttributeForWhereType = new HashMap<>();

			whereAttributeForWhereType.put("state", "StateId");
			whereAttributeForWhereType.put("StateCapital", "StateId");
			whereAttributeForWhereType.put("city", "CityId");
			whereAttributeForWhereType.put("river", "RiverId");
			whereAttributeForWhereType.put("country", "CountryId");
			whereAttributeForWhereType.put("landelevation", "LandelEvationId");
			whereAttributeForWhereType.put("country", "CountryId");
			whereAttributeForWhereType.put("river", "RiverId");
			whereAttributeForWhereType.put("road", "RoadId");
			whereAttributeForWhereType.put("border", "BorderId");
			whereAttributeForWhereType.put("mountain", "MountainId");
			whereAttributeForWhereType.put("lake", "lakeId");

		}
	}

	private static void prepareAdjectiveSets() {
		if (minimumAdjectives == null || minimumAdjectives.isEmpty()) {
			minimumAdjectives = new HashSet<>();
			minimumAdjectives.add("minimum");
			minimumAdjectives.add("lowest");
			minimumAdjectives.add("smallest");
			minimumAdjectives.add("shallowest");
			minimumAdjectives.add("poorest");
			minimumAdjectives.add("poorer");
			minimumAdjectives.add("shortest");
			minimumAdjectives.add("point");
		}

		if (maximumAdjectives == null || maximumAdjectives.isEmpty()) {
			maximumAdjectives = new HashSet<>();
			maximumAdjectives.add("maximum");
			maximumAdjectives.add("largest");
			maximumAdjectives.add("highest");
			maximumAdjectives.add("biggest");
			maximumAdjectives.add("deepest");
			maximumAdjectives.add("richest");
			maximumAdjectives.add("richer");
			maximumAdjectives.add("longest");

		}

		if (countPhrases == null || countPhrases.isEmpty()) {
			countPhrases = new HashSet<>();
			countPhrases.add("how many");
			countPhrases.add("count");
			countPhrases.add("quantity");
		}

		if (isPhrases == null || isPhrases.isEmpty()) {
			isPhrases = new HashSet<>();
			isPhrases.add("is");

		}

		if (arePhrases == null || arePhrases.isEmpty()) {
			arePhrases = new HashSet<>();
			arePhrases.add("are");

		}

	}

	public static void prepareAllStaticData() {
		prepareAttributeMappings();
		prepareQueryTableMappings();
		prepareMainAttributesForTables();
		prepareWhereAttributeForWhereType();
		prepareAdjectiveSets();
		prepareSameTableAttributeMappings();
		prepareStateTableAttributeMappings();
		prepareCityTableAttributeMappings();
		prepareCountryTableAttributeMapping();
		prepareAttributeNameNormalNameMappings();
		prepareRiverTableAttributeMapping();
		prepareRoadTableAttributeMapping();
		prepareLakeTableAttributeMapping();
		prepareLandElevationTableAttributeMapping();
		prepareBorderTableAttributeMapping();
		prepareMountainTableAttributeMapping();

	}
}
