package controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import model.DBData;
import model.StaticData;
import model.UserType;
import util.DBConnector;
import util.Util;

public class QueryGenerator {

	public static String getAnswerForQuestion(String question, UserType userType) throws Exception {
		String answerSentence = "";
		if (question.contains("next to")) {
			question = question.replace("next to", "border with");
		}
		if (question.contains("neighbouring")) {
			question = question.replace("neighbouring", "neighbour");
		}

		if (question.contains("USA")) {
			question = question.replace("USA", "America");
		}
		if (question.contains("?")) {
			question = question.replace("?", "");
		}

		// tokenize - get all the words
		// process all the words and get their POS type
		// adjectives and nouns are of interest for us
		// in the above question, adjective: longest (which are the richest
		// state)
		// nouns:
		// NN or NNS: lake
		// NNP or NNPS: Michigan
		StringTokenizer st = new StringTokenizer(question);

		MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");

		List<String> nouns = new ArrayList<>(); // fill this set after parsing

		String properNoun = ""; // will be used in the where clause
		String JJS = ""; // adjective
		String JJR = ""; // adjective
		String RB = ""; // adverb
		String Verb = ""; // Verb
		String Where = "";
		List<String> nounsPlural = new ArrayList<>();
		String properNounPlural = "";
		String preposition = "";
		String thirdPersonSin = "";
		List<String> adjectives = new ArrayList<>(); // fill this hashset after
														// parsing using
		String prevWordTag = "";

		String prevWord = "";// tokenizer
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			String tag = tagger.tagString(word);
			Util.print(word + " - " + tag);

			if (tag.contains("_CD") && prevWordTag.contains("_NNP")) {
				properNoun += (" " + word);
			}

			if (prevWordTag.contains("lowest_JJS") && tag.contains("point_NN")) {
				// word = adjectives + improperNouns;
				nouns.add(prevWord + " " + word);
			}

			if (tag.contains("_NNP")) {
				if (properNoun.isEmpty()) {
					properNoun = word;
				} else if (prevWordTag.contains("_NNP")) {
					properNoun += (" " + word);
				}
			} else if (tag.contains("_NNPS")) {
				properNounPlural = word;
			} else if (tag.contains("_NN") || (tag.contains("_RB"))) {
				nouns.add(word);
			} else if (tag.contains("_NNS")) {
				nounsPlural.add(word);
			} else if (tag.contains("_JJS") || (tag.contains("_JJR") || (tag.contains("_JJ")))) {
				JJS = word;
				adjectives.add(word);
			} else if (tag.contains("_VBG")) {
				Verb = word;
			} else if (tag.contains("_VBZ")) {
	thirdPersonSin = word;
			} else if (tag.contains("_IN")) {
				preposition = word;
			} else if (tag.contains("_WRB")) {
				Where = word;
			}
			prevWordTag = tag;
			prevWord = word;
		}

		Util.print("proper noun - " + properNoun);
		Util.print("proper noun plural -" + properNounPlural);
		Util.print("Verb - " + Verb);
		System.out.println("nouns -" + nouns);
		// Util.print("improper noun plural -"+ improperNounsPlural); //cities,
		// states, rivers
		System.out.println("adjective -" + adjectives);
		Util.print("preposition -" + preposition);

		// ----------------------------------------------------------------------//
		// WHENEVER A NEW TYPE OF QUESTION COMES, CHANGES WILL START FROM HERE
		// ----------------------------------------------------------------------//
		//System.out.println(DBData.borderNameIds);
		if (DBData.borderNameIds.containsKey(properNoun)){
			//System.out.println("in border");
			if (nouns.contains("neighbour")){
				//System.out.println("in neighbour");
			}
		}

		String whereType = Util.determineTypeOfProperNoun(properNoun, nouns, adjectives);// table
																							// name
		// now i'd know that Michigan is a State. This will help us in
		// formulating sql query (where clause) -> where State='Michigan'

		Util.print("Where type - " + whereType);

		nouns.addAll(nounsPlural);
		if (nouns.size() == 0) {
			nouns = adjectives;
		}
		boolean isPrepositionOf = Util.isPrepositionOf(preposition);
		boolean isMinimumQuestion = Util.isMinQuestion(adjectives);
		boolean isMaximumQuestion = Util.isMaxQuestion(adjectives);
		boolean isCountQuestion = Util.isCountQuestion(question);
		boolean isWhereQuestion = Util.isWhere(Where);

		boolean isSeen = false;
		boolean isHas = false;

		st = new StringTokenizer(question);
		while (st.hasMoreTokens()) {
			String word = st.nextToken();

			if (word.equals("is")) {
				isSeen = true;

			}

			else if (word.equals("has")) {
				isHas = true;
			}

		}

		boolean isSameTableQuestion = Util.isSameTableQuestion(isPrepositionOf, nouns, whereType); // category
																									// 1
																									// //
																									// eg.
																									// give
																									// the
																									// capital
																									// of
																									// kansas

		boolean isSameTableMinMaxQuestion = Util.isSameTableMinMaxQuestion(thirdPersonSin, preposition, properNoun,
				isMinimumQuestion || isMaximumQuestion); // category 5 // eg.
		// give the largest
		// state. or which are the richest state
		// this is the
		// category for
		// which we have to
		// get alternative
		// answers

		// Change the userType to none i.e. default if this question does not
		// belong to category 5.
		// Answers for such questions will always be same for all types of
		// users.
		// For example: What is the capital of Kansas?
		// It's answer will be same, no matter what the user type is
		String userTypeForIs = "";
		if (userType == UserType.TOURIST) {
			userTypeForIs = "TOURIST";
		} else if (userType == UserType.BUSINESSMAN) {
			userTypeForIs = "BUSINESSMAN";
		}

		userType = isSameTableMinMaxQuestion ? userType : userType.NONE;

		Set<String> candidateAttributes = new HashSet<>();
		for (String adj : adjectives) {

			candidateAttributes.addAll(Util.getPossibleCandidateAttributesForWord(adj, userType, nouns, isSeen)); // for
			// longest,
			// area
			// and
			// populationBUSINESSMAN
			// will
			// be
			// returned.
			// System.out.println();
			// this will help in formulating sql query -> max(area) or
			// max(population)
			// especially the where clause
		}

		// candidateAttributes now contains all the possible attributes that
		// need to be considered.
		// in the given question, candidateAttiributes will contain: area,
		// population
		// sample query:
		// SELECT StateName
		// FROM [State]
		// where Area = (select max(Area) from [State])
		// or TotalPopulation = (select max(TotalPopulation)
		// from [State]);

		ArrayList<String> queryTables = Util.getPossibleCandidateQueryTableForWord(nouns, whereType,
				isSameTableQuestion && !isSameTableMinMaxQuestion); // table
																	// name//
																	// now
		// we
		// know
		// the
		// query
		// table
		// as
		// well
		// sql:
		// from
		// <table
		// name>
		// queryTable = "landelevation";
		// String mainAttribute = Util.getMainAttributeForTable(queryTable);

		ArrayList<String> mainAttributes = Util
				.getDesiredAttributeForTable(isSameTableQuestion || isSameTableMinMaxQuestion, queryTables, nouns); // column
																													// name
		String whereAttributeName = Util.getWhereAttributeForWhereType(whereType);// sql
																					// where
																					// name
		String whereAttributeValue = Util.getIdForName(properNoun, whereType);// sql
																				// where
																				// name
																				// value
		System.out.println("\n\n\n");
		System.out.println("question: " + question);
		Util.print("noun: " + nouns.toString());
		Util.print("proper noun: " + properNoun);
		Util.print("where type: " + whereType);
		Util.print("where type attribute name: " + whereAttributeName);
		Util.print("where type value: " + whereAttributeValue);
		Util.print("candidate attributes: " + candidateAttributes.toString());
		Util.print("main attribute: " + mainAttributes); // gives the column
															// name
		Util.print("table: " + queryTables);
		Util.print("adjectives: " + adjectives.toString());
		Util.print("preposition: " + preposition);

		// just made a new boolean for readability

		boolean isSingularVerbQuestion = false;
		boolean isUserInterestQuestion = isSameTableMinMaxQuestion; // String
																	// preposition,
																	// String
																	// properNoun,
																	// boolean
																	// isMinMaxQuestion
		st = new StringTokenizer(question);
		isSeen = false;

		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			if (word.equals("is")) {
				isSeen = true;
			} else if (word.equals("has")) {
				isHas = true;
			}

			for (String candidateAttribute : candidateAttributes) {
				if ((isSeen || isHas) && StaticData.attributeMappings.get(candidateAttribute).contains(word)) {
					isSingularVerbQuestion = true;
					break;
				}
			}
		}

		if (isSingularVerbQuestion) {
			List<String> kh = nouns;
			List<String> kh1 = adjectives;

			if ((!(kh1.contains("largest") && (!kh1.contains("lowest"))
					&& (kh.contains("city") || kh.contains("state")))
					|| !(kh1.contains("richest") && (kh.contains("city") || kh.contains("state")))
					|| kh1.contains("highest")) && kh.contains("lake") || kh.contains("mountain")
					|| kh.contains("river") || kh.contains("population") || kh.contains("road")) {

				userType = UserType.NONE;

				System.out.println("User Type:" + userType);
			} else if ((kh1.contains("largest") || (kh1.contains("richest")))
					&& (kh.contains("city") || kh.contains("state"))) {
				if (userTypeForIs.equals("TOURIST")) {
					userType = UserType.TOURIST;
				} else if (userTypeForIs.equals("BUSINESSMAN")) {
					userType = UserType.BUSINESSMAN;
				}

			}

			if (userTypeForIs.equals("TOURIST") && kh1.contains("richest")) {
				userType = UserType.TOURIST;
			}

			switch (userType) {
			case TOURIST:
				if (kh.contains("city")) {
					userType = UserType.TOURIST;
				}
				break;
			case BUSINESSMAN:
				if (kh.contains("city")) {
					userType = UserType.BUSINESSMAN;
				}
				break;
			default:
				userType = UserType.NONE;
				break;
			}

			System.out.println("usertype is none:" + userType);
			Set<String> candidateAttributes1 = new HashSet<>();
			for (String adj : adjectives) {
				candidateAttributes1.addAll(Util.getPossibleCandidateAttributesForWord(adj, userType, nouns, isSeen)); // for
				// longest,
				// area
				// and
				// population
				// will
				// be
				// returned.
				// System.out.println();
				// this will help in formulating sql query -> max(area) or
				// max(population)
				// especially the where clause
			}

			String desiredAttribute = mainAttributes.get(0);
			String queryTable = queryTables.get(0);
			Map<String, String> sqlQueryWithAttributeNormalName = getSqlQueriesForSingularVerbQuestion(desiredAttribute,
					queryTable, candidateAttributes1, isMinimumQuestion, isMaximumQuestion, whereAttributeName,
					whereAttributeValue, properNoun, isSameTableMinMaxQuestion);

			Map<String, List<String>> attributeNormalNameQueryResults = new HashMap<>();
			List<String> results = null;
			for (Entry<String, String> entry : sqlQueryWithAttributeNormalName.entrySet()) {
				String attributeNormalName = entry.getValue();
				String sqlQuery = entry.getKey();
				results = getResultForSqlQuery(sqlQuery);
				attributeNormalNameQueryResults.put(attributeNormalName, results);
				System.out.println("\n\nsql(isSingularVerbQuestion): " + sqlQuery);
				answerSentence = AnswerGenerator.getAnswerSentenceForSingularVerbQuestion(nouns,
						attributeNormalNameQueryResults, isMinimumQuestion, adjectives);

			}

		}

		else if (isUserInterestQuestion) {
			String desiredAttribute = mainAttributes.get(0);
			String queryTable = queryTables.get(0);
			Map<String, String> sqlQueryWithAttributeNormalName = getSqlQueriesForUserInterest(desiredAttribute,
					queryTable, candidateAttributes, isMinimumQuestion, isMaximumQuestion);

			Map<String, List<String>> attributeNormalNameQueryResults = new HashMap<>();
			for (Entry<String, String> entry : sqlQueryWithAttributeNormalName.entrySet()) {
				String attributeNormalName = entry.getKey();
				String sqlQuery = entry.getValue();
				System.out.println("Query - " + sqlQuery);
				List<String> results = getResultForSqlQuery(sqlQuery);

				Iterator<String> iterator = results.iterator();
				for (int i = 0; i < results.size(); i = i + 2) {

					if (attributeNormalName.contains("gdp")) {

						// System.out.println(attributeNormalName + " " + "of "
						// + results.get(i) + " is " + results.get(i+1));
						answerSentence += (attributeNormalName + " " + "of " + results.get(i) + " is "
								+ results.get(i + 1) + " million usd " + "\n");
					}

					else if (attributeNormalName.contains("TotalPopulation")) {
						answerSentence += (results.get(i) + " " + "has " + results.get(i + 1) + " population " + "\n");
					} else {
						// System.out.println(results.get(i) + " has " +
						// results.get(i+1) + " " + attributeNormalName);
						answerSentence += (results.get(i) + " has " + results.get(i + 1) + " " + attributeNormalName
								+ "\n");

					}

				}
				// attributeNormalNameQueryResults.put(attributeNormalName,
				// results);
				// System.out.println("\n\n\n sql(user interest question query):
				// " + sqlQuery);
				answerSentence += "\n";
			}

			// answerSentence =
			// IntensionalAnswerGenerator.getAnswerSentenceForUserInterest(improperNouns,
			// attributeNormalNameQueryResults, candidateAttributes,
			// isMinimumQuestion);
			// Util.print("Sql Queries: " + sqlQueries.toString());

		}

		else if (isCountQuestion) {
			String queryTable = queryTables.get(0);
			String sqlQuery = getSqlQueryForCountQuestions(nouns, mainAttributes, queryTable, whereAttributeName,
					whereAttributeValue);
			System.out.println("\n\nsql(is count question): " + sqlQuery); // aksdhashdahsdjhasjdhjashdjashdhadhajshdjashdjashdhadha
			List<String> results = getResultForSqlQuery(sqlQuery);
			answerSentence = AnswerGenerator.getAnswerSentenceForCountQuestions(results, nouns, properNoun);
		}

		else if (mainAttributes.size() == 1 || queryTables.size() == 1) {
			String desiredAttribute = mainAttributes.get(0);
			String queryTable = queryTables.get(0);
			String sqlQuery = getSqlQuery(desiredAttribute, queryTable, Verb, candidateAttributes, whereAttributeName,
					whereAttributeValue, isMinimumQuestion, isMaximumQuestion, isSameTableMinMaxQuestion, queryTables,
					nouns, properNoun);
			System.out.println("\n\nsql(capital): " + sqlQuery); // Capital type
																	// of
																	// question
			List<String> results = getResultForSqlQuery(sqlQuery);
			answerSentence = AnswerGenerator.getAnswerSentence(desiredAttribute, adjectives, Verb, nouns, properNoun,
					results, isSameTableQuestion);
		} else if (isWhereQuestion) {

			String sqlQuery = getWhereQuestions(whereAttributeName, whereAttributeValue, whereType, Where);
			System.out.println("\n\nsql isWhere: " + sqlQuery); // Capital type
																// of question
			List<String> results = getResultForSqlQuery(sqlQuery);
			answerSentence = AnswerGenerator.getAnswerSentenceForWhereQuestions(results, nouns, properNoun, whereType);
		}

		else {
			String sqlQuery = getSqlQueryForMultipleNouns(mainAttributes, queryTables, whereAttributeName,
					isMinimumQuestion, whereAttributeValue, JJS, properNoun, nouns, candidateAttributes);
			System.out.println("multiple query:" + sqlQuery);

			List<String> result = getResultForSqlQuery(sqlQuery);
			answerSentence = AnswerGenerator.getAnswerSentenceForMultipleProperNouns(queryTables, properNoun,
					preposition, result, nouns, JJS);
		}

		System.out.println("\nAnswer(userInterestAnswer): " + answerSentence + "\n\n");// User
																						// Interest
																						// Answer
		return answerSentence;
	}

	private static String getSqlQueryForMultipleNouns(ArrayList<String> mainAttributes, ArrayList<String> tableNames,
			String whereAttributeName, boolean isMinQuestion, String whereAttributeValue, String adjectives,
			String properNoun, List<String> nouns, Set<String> candidateAttributes) throws SQLException {
		String sqlQuery = "";
		for (int i = 0; i < tableNames.size(); i++) {
			String tableName = tableNames.get(0);
			String tableName1 = tableNames.get(1);
			
			String mainAttribute = mainAttributes.get(0);
			String mainAttribute1 = mainAttributes.get(1);
			KnowledgeBase.mainatts_KB = mainAttribute;
			

			if (nouns.contains("states") || (nouns.contains("state")) || nouns.contains("STATES")
					|| nouns.contains("STATE")) {
				sqlQuery += "select " + mainAttribute + " from " + tableName1;
			} else if (nouns.contains("road") || (nouns.contains("roads")) || nouns.contains("river")
					|| nouns.contains("rivers") || nouns.contains("RIVER") || nouns.contains("RIVERS")
					|| nouns.contains("pass")) {
				sqlQuery += "select " + mainAttribute + " from " + tableName;

			} else if (nouns.contains("name") && ((nouns.contains("mountains")) || nouns.contains("mountain"))) {
				sqlQuery += "select " + mainAttribute + " from " + tableName + " where " + whereAttributeName + " = "
						+ whereAttributeValue;
				return sqlQuery;
			} else if (nouns.contains("name") && ((nouns.contains("lakes")) || nouns.contains("lake"))) {
				sqlQuery += "select " + mainAttribute + " from " + tableName + " where " + whereAttributeName + " = "
						+ whereAttributeValue;
				return sqlQuery;
			} else if (nouns.contains("name") && ((nouns.contains("roads")) || nouns.contains("road"))) {
				sqlQuery += "select " + mainAttribute + " from " + tableName + " where " + whereAttributeName + " = "
						+ whereAttributeValue;
				return sqlQuery;
			} else if (nouns.contains("bottom") && (nouns.contains("point"))) {
				sqlQuery += "select " + mainAttribute + " from " + tableName1 + " where " + whereAttributeName + " = "
						+ whereAttributeValue;
				return sqlQuery;
			}

			if ((nouns.contains("state") && nouns.contains("border")
					|| (nouns.contains("states")) && nouns.contains("border"))
					|| (nouns.contains("state") && !nouns.contains("pass") && !nouns.contains("city")
							&& adjectives.contains("largest"))) {
				sqlQuery = " select " + mainAttribute1 + " from " + tableName1 + " where " + whereAttributeName + " = "
						+ whereAttributeValue;
				if (adjectives.contains("largest")) {
					for (String candidateAttribute : candidateAttributes) {

						sqlQuery += " and " + "( " + candidateAttribute + " = " + "(" + "select " + "max" + "("
								+ candidateAttribute + ")" + " from " + tableName1 + " where " + whereAttributeName
								+ " = " + whereAttributeValue + ")" + ")";

						System.out.println(sqlQuery);

					}

				} else {
					for (String candidateAttribute : candidateAttributes) {

						sqlQuery += " and " + "( " + candidateAttribute + " = " + "(" + "select " + "min" + "("
								+ candidateAttribute + ")" + " from " + tableName1 + " where " + whereAttributeName
								+ " = " + whereAttributeValue + ")" + ")";

						System.out.println(sqlQuery);

					}

				}

			}

			else {
				sqlQuery += " where " + mainAttribute1 + " = " + "\'" + properNoun + "\'"; // adjectives
																							// should
																							// be
																							// replaced
																							// with
																							// properNoun
																							// and
																							// properNoun
																							// should
																							// be
																							// replaced
																							// with
																							// adjectives
			}

			sqlQuery += ";";

			return sqlQuery;
		}
		return sqlQuery;
	}

	/**
	 * Example: <br />
	 * <br />
	 * <b>Input:</b> <br />
	 * mainAttribute: "LakeName" <br />
	 * tableName: "lake" <br />
	 * candidateAttributes: ["length"] // not taken into consideration for now -
	 * TODO <br />
	 * whereAttributeName: StateId <br />
	 * whereAttributeValue: "1035" <br />
	 * <b>Output:<b> <br />
	 * sqlQuery: "select LakeName from lake where StateId = 1035;" <br />
	 * <br />
	 * 
	 * isMinQuestion -> if the question contains words like smallest, minimum,
	 * lowest isMaxQuestion -> if the question contains words like largest,
	 * maximum, highest
	 * 
	 * @param mainAttribute
	 * @param tableName
	 * @param candidateAttributes
	 * @param whereAttributeName
	 * @param whereAttributeValue
	 * @return
	 */

	public static String getSqlQuery(String mainAttribute, String tableName, String Verb,
			Set<String> candidateAttributes, String whereAttributeName, String whereAttributeValue,
			boolean isMinQuestion, boolean isMaxQuestion, boolean isSameTableMinMaxQuestion,
			ArrayList<String> tableNames, List<String> nouns, String properNoun) throws SQLException {
		KnowledgeBase.mainatts_KB=mainAttribute;
		for (int i = tableNames.size() - 1; i >= 0; i--) {

			tableName = tableNames.get(i);
			String sqlQuery = "";
			String tableName1 = tableNames.get(0);
			System.out.println(Arrays.toString(nouns.toArray()));
			System.out.println(whereAttributeName);
			if (nouns.contains("big") && whereAttributeName.contains("StateId")) {
				sqlQuery = "select " + mainAttribute + " from " + tableName + " where " + whereAttributeName + " = "
						+ whereAttributeValue + "; ";
				System.out.println(sqlQuery);
				return sqlQuery;
			}

			else if (nouns.contains("big") && whereAttributeName.contains("CityId")) {
				sqlQuery = "select " + mainAttribute + " from " + tableName1 + " where " + whereAttributeName + " = "
						+ whereAttributeValue + "; ";
				return sqlQuery;
			} else if

			(Verb.contains("passing")) {

				sqlQuery += "select " + mainAttribute + " from " + tableName1 + " where " + "StateName " + " = " + "\'"
						+ properNoun + "\'";
				return sqlQuery;
			} else if (nouns.contains("length") && (nouns.contains("rivers") || (nouns.contains("river")))) {
				sqlQuery += "select " + "RiverLength" + " from " + tableName1 + " where " + whereAttributeName + " = "
						+ whereAttributeValue + "; ";
				return sqlQuery;
			} else if (nouns.contains("name") && (nouns.contains("lakes"))) {
				sqlQuery += "select " + "LakeName" + " from " + tableName1 + " where " + whereAttributeName + " = "
						+ whereAttributeValue + "; ";
				return sqlQuery;
			} else if (nouns.contains("name") && (nouns.contains("rivers"))) {
				sqlQuery += "select " + "RiverName" + " from " + tableName1 + " where " + whereAttributeName + " = "
						+ whereAttributeValue + "; ";
				return sqlQuery;
			} else if (nouns.contains("point") && !nouns.contains("bottom") && !nouns.contains("top")) {
				sqlQuery += "select " + "Highland" + " from " + tableName + " where " + whereAttributeName + " = "
						+ whereAttributeValue + "; ";
				return sqlQuery;

			}
			else if (nouns.contains("lowest point") && !nouns.contains("top")) {
				sqlQuery += "select " + "Lowland" + " from " + tableName + " where " + whereAttributeName + " = "
						+ whereAttributeValue + "; ";
				return sqlQuery;

			}
		}

		String sqlQuery = "";
		
	/*	  if(nouns.contains("highland") && whereAttributeName.contains("CountryID")){
		  sqlQuery = "select " + "highland" + " from " + "country" +" where " + whereAttributeName + " = " + whereAttributeValue;;
		  return sqlQuery;}
		 
		  else if(nouns.contains("lowland") && whereAttributeName.contains("CountryID")){
			  sqlQuery = "select " + mainAttribute + " from " + "landelevation" +" where " + whereAttributeName + " = " + whereAttributeValue;; }
			  
		  
		  else{*/
		
		if (nouns.contains("highland")){
			sqlQuery = "select highland" + " from " + tableName +" where " + whereAttributeName + " = " + whereAttributeValue;
			return sqlQuery;
		}
		
		sqlQuery = "select " + mainAttribute + " from " + tableName;

		boolean isWhereClauseAdded = false;

		if (!whereAttributeName.isEmpty() && !whereAttributeValue.isEmpty()) {
			sqlQuery += " where " + whereAttributeName + " = " + whereAttributeValue;
			isWhereClauseAdded = true;
			System.out.println("shuvro:" + sqlQuery);

			return sqlQuery;

		}

		// have to apply group by and additional conditions in where clause!
		if (isMaxQuestion || isMinQuestion) {

			sqlQuery += isWhereClauseAdded ? " and (" : " where (";

			boolean isFirst = true;

			for (String candidateAttribute : candidateAttributes) {
				// 'or' will only be added if this is not the first condition
				if (!isFirst) {
					sqlQuery += " or ";
				} else {
					isFirst = false;
				}

				sqlQuery += (candidateAttribute + " = (");
				sqlQuery += isSameTableMinMaxQuestion ? getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion)
						: getGroupBySubQuery(candidateAttribute, tableName, whereAttributeName, whereAttributeValue,
								isMinQuestion);
				sqlQuery += ")";
			}

			sqlQuery += ")";
		}

		sqlQuery += ";";

		return sqlQuery;}
	
	
	// return sqlQuery;}

	public static Map<String, String> getSqlQueriesForUserInterest(String mainAttribute, String tableName,
			Set<String> candidateAttributes, boolean isMinQuestion, boolean isMaxQuestion) {
		KnowledgeBase.mainatts_KB=mainAttribute;
		Map<String, String> sqlQueries = new HashMap<>();
		String startOfQuery = " select " + mainAttribute + ",";

		String sqlQuery = "";

		for (String candidateAttribute : candidateAttributes) {
			String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(candidateAttribute)
					? StaticData.attributeNameNormalNameMappings.get(candidateAttribute) : candidateAttribute;

			if (isMaxQuestion)
				// sqlQuery = startOfQuery + candidateAttribute + " from "+
				// tableName + " order by "+ candidateAttribute + " DESC LIMIT
				// 5;";
				sqlQuery = "SELECT statename, mountaincount" + " from " + "( SELECT @row := @row +1 AS rownum,"
						+ mainAttribute + " AS statename, " + candidateAttribute
						+ " AS mountaincount FROM (SELECT @row :=0) r, " + tableName + " order by " + candidateAttribute
						+ " DESC ) AS statetable WHERE rownum % 10 = 1 LIMIT 5;";
			else {
				// sqlQuery = startOfQuery + candidateAttribute + " from "+
				// tableName + " order by "+ candidateAttribute + " ASC LIMIT
				// 5;";
				sqlQuery = "SELECT statename, mountaincount" + " from " + "( SELECT @row := @row +1 AS rownum,"
						+ mainAttribute + " AS statename, " + candidateAttribute
						+ " AS mountaincount FROM (SELECT @row :=0) r, " + tableName + " order by " + candidateAttribute
						+ " ASC ) AS statetable WHERE rownum % 10 = 1 LIMIT 5;";
			}

			sqlQueries.put(attributeNameForAnswer, sqlQuery);
		}

		return sqlQueries;

	}

	public static String getSqlQueryForCountQuestions(List<String> nouns, List<String> mainAttributes, String tableName,
			String whereAttributeName, String whereAttributeValue) {
		String sqlQuery = "";
		
		String mainAttribute = mainAttributes.get(0);
		KnowledgeBase.mainatts_KB=mainAttribute;
		String noun = nouns.get(0);

		if (nouns.contains("population") || nouns.contains("people")
				|| noun.contains("citizens") && (!whereAttributeName.contains("CityId"))) {

			sqlQuery = "select " + mainAttribute + " from " + tableName + " where " + whereAttributeName + " = "
					+ whereAttributeValue + ";";
		}

		else if (whereAttributeName.contains("CityId")) {
			sqlQuery = "select " + mainAttribute + " from " + "city" + " where " + whereAttributeName + " = "
					+ whereAttributeValue + ";";

		}

		else {

			sqlQuery = "select count(*) from " + tableName + " where " + whereAttributeName + " = "
					+ whereAttributeValue + ";";

		}

		return sqlQuery;
	}

	public static String getWhereQuestions(String whereAttributeName, String whereAttributeValue, String whereType,
			String Where) {
		String sqlQuery = "";
		
		KnowledgeBase.mainatts_KB=whereAttributeName;
		if (whereAttributeName.contains("StateId")) {
			sqlQuery = "select" + " Area" + ", " + "TotalPopulation" +", "+"Location"+ " from" + " " + whereType + " where" + " "
					+ whereAttributeName + " = " + whereAttributeValue + ";";
			return sqlQuery;

		}
		if (whereAttributeName.contains("CityId")) {
			sqlQuery = "select" + " Area" + ", " + "TotalPopulation" + " from" + " " + whereType + " where" + " "
					+ whereAttributeName + " = " + whereAttributeValue + ";";
			return sqlQuery;

		}

		return sqlQuery;

	}
	

	public static Map<String, String> getSqlQueriesForSingularVerbQuestion(String mainAttribute, String tableName,
			Set<String> candidateAttributes, boolean isMinQuestion, boolean isMaxQuestion, String whereAttributeName,
			String whereAttributeValue, String properNoun, boolean isSameTableMinMaxQuestion) {
		KnowledgeBase.mainatts_KB=mainAttribute;
		Map<String, String> sqlQueries = new HashMap<>();
		if (properNoun.isEmpty()) {
			String[] candidateAttributeArray = null;
			int count = 0;
			String startOfQuery = null;
			for (String candidateAttribute : candidateAttributes) {
				System.out.println("candidateAttribute: " + candidateAttribute);

				startOfQuery = "select " + mainAttribute + "," + candidateAttribute + " from " + tableName + " where (";

				String sqlQuery = startOfQuery + candidateAttribute + " = ("
						+ getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion) + "));";
				String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(
						candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute)
								: candidateAttribute;
				sqlQueries.put(sqlQuery, attributeNameForAnswer);
			}

		}

		else

		{
			Map<String, String> sqlQueries1 = new HashMap<>();

			String[] candidateAttributeArray = null;
			int count = 0;
			String startOfQuery = null;
			for (String candidateAttribute : candidateAttributes) {
				System.out.println("candidateAttribute: " + candidateAttribute);
				startOfQuery = "select " + mainAttribute + "," + candidateAttribute + " from " + tableName;

				System.out.println(startOfQuery);

				boolean isWhereClauseAdded = false;
				if (!whereAttributeName.isEmpty() && !whereAttributeValue.isEmpty()) {
					startOfQuery += " where " + whereAttributeName + " = " + whereAttributeValue + " and ";
					System.out.println(startOfQuery);
					isWhereClauseAdded = true;
				}

				String sqlQuery = startOfQuery + candidateAttribute + " = ("
						+ getMinMaxSubQuery(candidateAttribute, tableName, isMinQuestion);
				String attributeNameForAnswer = StaticData.attributeNameNormalNameMappings.containsKey(
						candidateAttribute) ? StaticData.attributeNameNormalNameMappings.get(candidateAttribute)
								: candidateAttribute;
				sqlQuery += " where " + whereAttributeName + " = " + whereAttributeValue + " ); ";
				System.out.println("final: " + sqlQuery);
				sqlQueries.put(sqlQuery, attributeNameForAnswer);
			}
		}
		return sqlQueries;
	}

	/**
	 * isMinimum: if true, then min() else max() <br />
	 * 
	 * returns queries like: select max(Area) from city where StateID = 1028
	 * group by StateID
	 * 
	 * @param candidateAttribute
	 * @param tableName
	 * @param whereAttributeName
	 * @param whereAttributeValue
	 * @param isMinQuestion
	 * @return
	 */
	public static String getGroupBySubQuery(String candidateAttribute, String tableName, String whereAttributeName,
			String whereAttributeValue, boolean isMinQuestion) {
		// select max(Area) from city
		// where
		// StateID = 1028
		// group by
		// StateID
		String sqlQuery = "select " + (isMinQuestion ? "min(" : "max(") + candidateAttribute + ") from " + tableName
				+ " where " + whereAttributeName + " = " + whereAttributeValue + " group by " + whereAttributeName;

		return sqlQuery;
	}

	/**
	 * isMinimum: if true, then min() else max() <br />
	 * 
	 * returns queries like: select max(Area) from States
	 * 
	 * @param candidateAttribute
	 * @param tableName
	 * @param isMinQuestion
	 * @return
	 */
	public static String getMinMaxSubQuery(String candidateAttribute, String tableName, boolean isMinQuestion) {
		// select max(Area) from States
		String sqlQuery = "select " + (isMinQuestion ? "min(" : "max(") + candidateAttribute + ") from " + tableName;

		return sqlQuery;
	}

	public static List<String> getResultForSqlQuery(String sql) throws SQLException {
		List<String> results = new ArrayList<>();
		Statement sta = DBConnector.conn.createStatement();
		ResultSet rs = sta.executeQuery(sql);

		while (rs.next()) {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				results.add(rs.getString(i));
			}
		}
		
		for (String s:results)
			System.out.println('\n'+s);
		
		return 	results;
	}
}
