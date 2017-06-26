package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import model.DBData;
import model.StaticData;
import model.UserInterest;
import model.UserType;

public class Util {

	public static final boolean IS_DEBUG = true;

	public static void print(String string) {
		if (IS_DEBUG)
			System.out.println(string);
	}

	/**
	 * determine if the word is a state, city, lake, or ... <br />
	 * Output will be a table name
	 * 
	 * @param inputNoun
	 * @param adjectives
	 * @return
	 */
	public static String determineTypeOfProperNoun(String properNoun, List<String> nouns, List<String> adjectives) {
		
		
		if (DBData.countryNameIds.containsKey(properNoun))
			return "country";

		else if (DBData.landelevationNameIds.containsKey(properNoun) && nouns.contains("elevation"))

			return "landelevation";

		else if (DBData.roadNameIds.containsKey(properNoun))
			return "road";

		else if (DBData.mountainNameIds.containsKey(properNoun))
			return "mountain";

		else if ((DBData.stateNameIds.containsKey(properNoun) && !nouns.contains("most") && nouns.contains("city"))
				&& !adjectives.contains("biggest") && !adjectives.contains("shortest")
				&& !adjectives.contains("largest") && !adjectives.contains("smallest")
				&& !adjectives.contains("shortest") && !adjectives.contains("richest")
				|| !DBData.stateNameIds.containsKey(properNoun) && nouns.contains("population")
						&& nouns.contains("city"))
			return "city";

		else if (DBData.stateNameIds.containsKey(properNoun) && nouns.contains("river")
				&& !adjectives.contains("largest") && !adjectives.contains("biggest") && !adjectives.contains("longest")
				&& !adjectives.contains("shortest") && !nouns.contains("pass") && !nouns.contains("passes")
				&& !nouns.contains("flow") && !nouns.contains("flows") && !nouns.contains("traverse"))
			return "river";
         
		else if (DBData.borderNameIds.containsKey(properNoun) && nouns.contains("neighbour")
				&& !nouns.contains("mountain"))
		
			return "border";
		else if ((DBData.stateNameIds.containsKey(properNoun) && !nouns.contains("city"))
				|| DBData.stateNameIds.containsKey(properNoun) && !nouns.contains("river")
				|| !DBData.stateNameIds.containsKey(properNoun) && nouns.contains("population")
						&& nouns.contains("state"))
		
			return "state";
			

		else if ((DBData.cityNameIds.containsKey(properNoun)))
			return "city";

		else if (DBData.riverNameIds.containsKey(properNoun))

			return "river";

		else if (DBData.lakeNameIds.containsKey(properNoun))
			return "lake";

		return "";

	}

	/**
	 * get possible candidates for the input word. <br />
	 * Example: <br />
	 * Input: largest <br />
	 * Output: area, population
	 * 
	 * @param word
	 * @return
	 */
	public static Set<String> getPossibleCandidateAttributesForWord(String word, UserType userType, List<String> nouns,
			boolean isSeen) {
		Set<String> candidates = new HashSet<>();
		Set<String> userInterestedAttributes = UserInterest.userInterestMapping.get(userType); // all
																								// the
																								// attributes
																								// that
																								// this
																								// user
																								// is
		// candidate attribute [gdp, citycount, roadcount] // interested
		System.out.println(userInterestedAttributes);
		if (!(userType == UserType.NONE) && isSeen == false) {
			for (Entry<String, Set<String>> entry : StaticData.attributeMappings.entrySet()) {
				Set<String> values = entry.getValue();
				if (values.contains(word)) {
					System.out.println(entry.getKey());
					if (userInterestedAttributes.contains(entry.getKey())) {
						System.out.println("Improper nouns - " + nouns);
						System.out.println(!(nouns.contains("cities") && entry.getKey().equalsIgnoreCase("CityCount"))
								&& (!(nouns.contains("city") && entry.getKey().equalsIgnoreCase("CityCount"))));
						if (word.equalsIgnoreCase("richest") || word.equalsIgnoreCase("poorest")) {
							if (userType == UserType.BUSINESSMAN) {
								if (!(entry.getKey().equalsIgnoreCase("TotalPopulation"))
										&& !(nouns.contains("cities") && entry.getKey().equalsIgnoreCase("CityCount"))
										&& (!(nouns.contains("city")
												&& entry.getKey().equalsIgnoreCase("CityCount")))) {
									candidates.add(entry.getKey());
								}
							} else {
								if (!(entry.getKey().equalsIgnoreCase("area"))
										&& !(entry.getKey().equalsIgnoreCase("TotalPopulation"))
										&& !(nouns.contains("cities") && entry.getKey().equalsIgnoreCase("CityCount"))
										&& (!(nouns.contains("city")
												&& entry.getKey().equalsIgnoreCase("CityCount")))) {
									candidates.add(entry.getKey());
								}
							}

						} else if (word.equalsIgnoreCase("largest") || (word.equalsIgnoreCase("smallest"))) {
							if (userType == UserType.BUSINESSMAN) {
								if (!(entry.getKey().equalsIgnoreCase("CityCount"))
										&& !(nouns.contains("states") && entry.getKey().equalsIgnoreCase("RoadCount"))
										&& (!(nouns.contains("state")
												&& entry.getKey().equalsIgnoreCase("RoadCount")))) {
									candidates.add(entry.getKey());
								}
							} else {
								if (entry.getKey().equalsIgnoreCase("area")
										|| entry.getKey().equalsIgnoreCase("TotalPopulation")) {
									candidates.add(entry.getKey());
								}
							}

						}

					}
				}
			}
		}

		else {
			if (!isSeen) {
				if (nouns.contains("state") && !nouns.contains("border") && !nouns.contains("population")
						|| nouns.contains("states")) {
					candidates.add("area");
					candidates.add("TotalPopulation");
					candidates.add("gdp");
					candidates.add("RiverCount");
					candidates.add("LakeCount");
					candidates.add("RoadCount");
					candidates.add("MountainCount");

				} else if (nouns.contains("population") || word.contains("highest")) {

					candidates.add("TotalPopulation");

				}

				else if (nouns.contains("state") || nouns.contains("border")) {

					candidates.add("area");

				}

				else if (nouns.contains("city") || nouns.contains("cities")) {
					candidates.add("gdp");
					candidates.add("area");
					candidates.add("TotalPopulation");
				} else if (nouns.contains("mountain") || word.equals("mountains")) {
					candidates.add("Mountainheight");
				}

				else if (nouns.contains("lake") || nouns.contains("lakes")) {
					candidates.add("LakeLength");
				} else if (nouns.contains("gdp") && word.equals("highest")) {
					candidates.add("GDP");
				}
			} else {
				if ((nouns.contains("state") || nouns.contains("city")) && word.equals("richest")) {
					if (userType == UserType.TOURIST && nouns.contains("state")) {
						candidates.add("MountainCount");
						candidates.add("RiverCount");
						candidates.add("CityCount");
						candidates.add("LakeCount");
					} else if (userType == UserType.TOURIST && nouns.contains("city")) {
						candidates.add("MountainCount");
						candidates.add("RiverCount");
						candidates.add("LakeCount");
					} else {
						candidates.add("gdp");
					}

				} else if ((nouns.contains("city") && word.equals("populous"))) {
					candidates.add("TotalPopulation");
				} else if ((nouns.contains("population") && word.equals("highest"))) {
					candidates.add("TotalPopulation");
				}

				else if ((nouns.contains("state") || nouns.contains("city")) || nouns.contains("cities")
						|| nouns.contains("capital") && word.equals("largest") || nouns.contains("biggest")) {
					if (userType == UserType.TOURIST) {
						candidates.add("area");
					} else if (userType == UserType.BUSINESSMAN) {
						candidates.add("TotalPopulation");
					} else {
						candidates.add("area");
						candidates.add("TotalPopulation");
					}
				} else if (nouns.contains("mountain")
						&& (word.equals("largest") || word.equals("highest") || word.equals("lowest"))) {
					candidates.add("Mountainheight");
				} else if (nouns.contains("lake") && (word.equals("largest") || word.equals("shortest")
						|| (word.equals("smallest")) && !nouns.contains("river")) && !nouns.contains("road")) {
					candidates.add("LakeLength");
				} else if (nouns.contains("river") && word.equals("largest") || word.equals("biggest")
						|| word.equals("longest") || word.equals("shortest") && !nouns.contains("road")) {
					candidates.add("RiverLength");
				} else if (nouns.contains("road") && word.equals("largest") || word.contains("smallest")
						|| word.contains("shortest")) {
					candidates.add("RoadLength");
				}

			}
		}
		return candidates;

	}

	public static boolean isPrepositionOf(String preposition) {
		return preposition.toLowerCase().equals("of");
	}

	public static boolean isWhere(String Where) {
		return Where.toLowerCase().equals("where");
	}

	public static boolean isMinQuestion(List<String> adjectives) {
		boolean isMinimum = false;

		for (String adjective : adjectives) {
			if (StaticData.minimumAdjectives.contains(adjective)) {
				isMinimum = true;
				break;
			}
		}

		return isMinimum;
	}

	public static boolean isMaxQuestion(List<String> adjectives) {
		boolean isMaximum = false;

		for (String adjective : adjectives) {
			if (StaticData.maximumAdjectives.contains(adjective)) {
				isMaximum = true;
				break;
			}
		}

		return isMaximum;
	}

	public static boolean isCountQuestion(String question) {
		boolean isCount = false;

		question = question.toLowerCase();

		for (String countPhrase : StaticData.countPhrases) {
			if (question.contains(countPhrase)) {
				isCount = true;
				break;
			}
		}

		return isCount;
	}

	/**
	 * get possible candidate query tables for the input words. <br />
	 * Example: <br />
	 * Input: lake <br />
	 * Output: lake
	 * 
	 * isSameTablePossible: should only be true if question contains the word
	 * 'of'
	 * 
	 * TODO: this function should return a set of string (table names) and not
	 * just one string (table name)
	 * 
	 * @param word
	 * @return
	 */
	public static ArrayList<String> getPossibleCandidateQueryTableForWord(List<String> words, String whereTypeTableName,
			boolean isSameTableQuestion) {
		String candidate = "";
		boolean isTableFound = isSameTableQuestion;
		ArrayList<String> candidates = new ArrayList<String>();
		if (!isTableFound) {
			for (String word : words) {
				for (Entry<String, Set<String>> entry : StaticData.queryTableMappings.entrySet()) {
					Set<String> values = entry.getValue();
					if (values.contains(word)) {
						candidate = entry.getKey();
						if (!candidate.isEmpty() && !candidates.contains(candidate)) {
							candidates.add(candidate);
						}
						isTableFound = true;

					}
				}

			}
		}
		if (!isTableFound) {
			for (String word : words) {
				for (Entry<String, Set<String>> entry : StaticData.sameTableAttributeMappings.entrySet()) {
					Set<String> values = entry.getValue();
					if (values.contains(word)) {
						candidate = entry.getKey();
						if (!candidate.isEmpty() && !candidates.contains(candidate)) {
							candidates.add(candidate);
						}
						isTableFound = true;

					}

				}
			}

		} else {
			candidate = whereTypeTableName;
			if (!candidate.isEmpty() && !candidates.contains(candidate)) {
				candidates.add(candidate);
			}
		}
		return candidates;
	}

	/**
	 * determine if the same table should be used to retrieve data. <br />
	 * Example: What is the capital of Kansas? <br />
	 * In normal scenario, city table will be used, but in this case, capital
	 * should be fetched from state table.
	 * 
	 * @param words
	 * @param whereTypeTableName
	 * @return
	 */
	private static boolean isSameTableQuestion(List<String> words, String whereTypeTableName) {
		boolean isSameTable = false;
		Set<String> possibleWords = StaticData.sameTableAttributeMappings.get(whereTypeTableName);

		for (String word : words) {
			if (possibleWords.contains(word)) {
				isSameTable = true;
				break;
			}
		}

		return isSameTable;
	}

	public static boolean isSameTableQuestion(boolean isSameTablePossible, List<String> words,
			String whereTypeTableName) {
		return isSameTablePossible && isSameTableQuestion(words, whereTypeTableName);
	}

	/**
	 * determines if the question belongs to Category 5. <br />
	 * Example Question: Give the largest state. <br />
	 * Or what are the richest state? Such questions do not have any
	 * preposition, and do not have any proper noun. In addition, they contain a
	 * word like largest, lowest, smallest, etc.
	 * 
	 * @param preposition
	 * @param properNoun
	 *            //any state name like Michigan
	 * @param isMinMaxQuestion
	 * @return
	 */
	public static boolean isSameTableMinMaxQuestion(String thirdPersonSin, String preposition, String properNoun,
			boolean isMinMaxQuestion) {
		return thirdPersonSin.isEmpty() && preposition.isEmpty() && properNoun.isEmpty() && isMinMaxQuestion;
	}

	/**
	 * isSameTableQuestion: if data is to be fetched from the same table, some
	 * other column might be the desired attribute. <br />
	 * For example: <i>Question: What is the capital of Kansas.</i> Now,
	 * StateCapital is the required column from the same table, State.<br />
	 * <br />
	 * if not sameTableQuestion, mainAttribute for the table will be fetched
	 * 
	 * @param isSameTableQuestion
	 * @param tableNames
	 * @param desiredAttribute
	 * @return
	 */

	public static ArrayList<String> getDesiredAttributeForTable(boolean isSameTableQuestion,
			ArrayList<String> tableNames, List<String> inproperNouns) {
		String attribute;
		ArrayList<String> attributes = new ArrayList<String>();
		try {

			for (int i = 0; i < tableNames.size(); i++) {
				String tableName = tableNames.get(i);
				System.out.println(tableName + "*************");

				String inproperNoun = inproperNouns.get(i);
				switch (tableName) {
				case "state":
					attribute = StaticData.stateTableAttributeMappings.containsKey(inproperNoun)
							? StaticData.stateTableAttributeMappings.get(inproperNoun) : "";
					break;

				case "border":
					attribute = StaticData.borderTableAttributeMappings.containsKey(inproperNoun)
							? StaticData.borderTableAttributeMappings.get(inproperNoun) : "";
					break;
				case "city":
					attribute = StaticData.cityTableAttributeMappings.containsKey(inproperNoun)
							? StaticData.cityTableAttributeMappings.get(inproperNoun) : "";
					break;
				case "country":
					attribute = StaticData.countryTableAttributeMappings.containsKey(inproperNoun)
							? StaticData.countryTableAttributeMappings.get(inproperNoun) : "";
					break;

				case "lake":
					attribute = StaticData.lakeTableAttributeMappings.containsKey(inproperNoun)
							? StaticData.lakeTableAttributeMappings.get(inproperNoun) : "";
					break;
				case "landelevation":
					attribute = StaticData.landelevationTableAttributeMappings.containsKey(inproperNoun)
							? StaticData.landelevationTableAttributeMappings.get(inproperNoun)
							: StaticData.landelevationTableAttributeMappings.containsKey(inproperNoun)
									? StaticData.landelevationTableAttributeMappings.get(inproperNoun) : "";

					break;
				case "mountain":
					attribute = StaticData.mountainTableAttributeMappings.containsKey(inproperNoun)
							? StaticData.mountainTableAttributeMappings.get(inproperNoun) : "";
					break;
				case "river":
					attribute = StaticData.riverTableAttributeMappings.containsKey(inproperNoun)
							? StaticData.riverTableAttributeMappings.get(inproperNoun) : "";
					break;
				case "road":
					attribute = StaticData.roadTableAttributeMappings.containsKey(inproperNoun)
							? StaticData.roadTableAttributeMappings.get(inproperNoun) : "";
					break;
				default:
					attribute = "";
				}
				System.out.println(tableName + " " + inproperNoun + " " + attribute);

				if (!attribute.isEmpty() && !attributes.contains(attribute)) {
					attributes.add(attribute);
					// break;
				}
				if (attribute.isEmpty()) {
					attribute = getMainAttributeForTable(tableName);
					if (!attribute.isEmpty() && !attributes.contains(attribute)) {
						attributes.add(attribute);
					}
				}
			}
		} catch (Exception e) {

		}

		return attributes;
	}

	public static String getMainAttributeForTable(String tableName) {
		String attribute = "";
		if (StaticData.mainAttributeForTable.containsKey(tableName)) {
			attribute = StaticData.mainAttributeForTable.get(tableName);
		}
		return attribute;
	}

	/**
	 * whereType is expected to be a table name
	 * 
	 * @param whereType
	 * @return
	 */
	public static String getWhereAttributeForWhereType(String whereType) {
		return StaticData.whereAttributeForWhereType.containsKey(whereType)
				? StaticData.whereAttributeForWhereType.get(whereType) : "";
	}

	public static String getIdForName(String properNoun, String tableName) {
		if (tableName.equals("state")) {
			return DBData.stateNameIds.containsKey(properNoun) ? DBData.stateNameIds.get(properNoun).toString() : "";
		}

		if (tableName.equals("city")) {
			return DBData.cityNameIds.containsKey(properNoun) ? DBData.cityNameIds.get(properNoun).toString() : "";
		}

		if (tableName.equals("lake")) {
			return DBData.lakeNameIds.containsKey(properNoun) ? DBData.lakeNameIds.get(properNoun).toString() : "";
		}

		if (tableName.equals("border")) {
			return DBData.borderNameIds.containsKey(properNoun) ? DBData.borderNameIds.get(properNoun).toString() : "";
		}

		if (tableName.equals("country")) {
			return DBData.countryNameIds.containsKey(properNoun) ? DBData.countryNameIds.get(properNoun).toString()
					: "";
		}

		if (tableName.equals("landelevation")) {
			return DBData.landelevationNameIds.containsKey(properNoun)
					? DBData.landelevationNameIds.get(properNoun).toString() : "";
		}

		if (tableName.equals("mountain")) {
			return DBData.mountainNameIds.containsKey(properNoun) ? DBData.mountainNameIds.get(properNoun).toString()
					: "";
		}

		if (tableName.equals("road")) {
			return DBData.roadNameIds.containsKey(properNoun) ? DBData.roadNameIds.get(properNoun).toString() : "";
		}

		if (tableName.equals("river")) {

			return DBData.riverNameIds.containsKey(properNoun) ? DBData.riverNameIds.get(properNoun).toString() : "";
		}

		return "";
	}

}
