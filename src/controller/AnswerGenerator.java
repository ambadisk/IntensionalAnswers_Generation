package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import model.StaticData;
import model.DBData;

public class AnswerGenerator {
	public static String getAnswerSentence(String mainAttribute, List<String> adjectives, String Verb,
			List<String> nouns, String properNoun, List<String> results, boolean isSameTableQuestion) {
		String answer = "";

		if (results == null || results.isEmpty()) {
			answer = "There are no " + nouns.get(0) + (!properNoun.isEmpty() ? (" in " + properNoun) : "") + ".";
		}

		else if (Verb.contains("passing")) {

			String result = "";

			for (int i = 0; i < results.size(); i++) {
				if (i == results.size() - 1) {
					result += results.get(i) + " ";
				} else {
					result += results.get(i) + ", ";
				}
			}

			answer = result + "are " + " passing " + " through " + properNoun;
		}
		if(results.size() > 1){
			IntensionalAnswerGenerator iag = new IntensionalAnswerGenerator();
			String ians = iag.generateIntensionalAnswer(results);
			System.out.println("Intentional answer : All"+ results+"are from "+properNoun );
			//System.out.println("Intentional answer : Coming soon, " +ians);
		}
		 
		/*
		 * else if(improperNouns.contains("capital")){ answer =
		 * "every states has capital." ; }
		 */
		else {
			int resultSize = results.size();
			int maxAnswersToShow = 5;
			// a maximum of 5 answers should be displayed!
			int numberOfIterations = resultSize > maxAnswersToShow ? maxAnswersToShow : resultSize;

			if (resultSize > maxAnswersToShow) {
				answer = "Some of the";
			}
			
			if(nouns.contains("long")&&(nouns.contains("river"))){
				
				answer = properNoun + " " +nouns.get(1) + " is " + results.get(0) + " km " + nouns.get(0) + ".";
				return answer;
				
			}

			answer += (adjectives != null && !adjectives.isEmpty())
					? (answer.length() > 0 ? " " : "") + adjectives.get(0) : "";
			int adjectivesCount = adjectives.size();
			for (int i = 1; i < adjectivesCount; i++) {
				// the last adjective
				if (i == adjectivesCount - 1)
					answer += " and " + adjectives.get(i);
				else
					answer += ", " + adjectives.get(i);
			}
			if (adjectives.contains("big")) {
				answer = properNoun + " is" + " " + results.get(0);
			} else {
				answer += (answer.length() > 0 ? " " : "") + nouns.get(0)
						+ (!properNoun.isEmpty() ? ((isSameTableQuestion ? " of " : " in ") + properNoun) : "")
						+ (results.size() > 1 ? " are " : " is ");
				answer += results.get(0);
			}
			for (int i = 1; i < numberOfIterations; i++) {
				// the last element
				if (i == numberOfIterations - 1)
					answer += " and " + results.get(i);
				else
					answer += ", " + results.get(i);
			}
			
			
			if (nouns.contains("length") || mainAttribute.contains("RiverLength")) {
				answer += " km" + ".";
			}
			
			else if (nouns.contains("height")) {
				answer += " m" + ".";
			} else if (mainAttribute.contains("Area")) {
				answer += " km²" + ".";
			} else if (mainAttribute.contains("GDP")) {
				answer += " million usd" + ".";
			} else {
				answer += ".";
			}
		}

		return answer;
	}

	public static String getAnswerSentenceForUserInterest(List<String> nouns,
			Map<String, List<String>> attributeNameWithResults, Set<String> candidateAttributes,
			boolean isMinQuestion) {
		String answer = "";

		if (attributeNameWithResults == null || attributeNameWithResults.isEmpty()) {
			answer = "There are no " + nouns.get(0) + ".";
		} else {
			Iterator<String> iterator = candidateAttributes.iterator();
			int index = 0;
			Set<String> entryset = attributeNameWithResults.keySet();
			String key = entryset.iterator().next();
			ArrayList<List<String>> subLists = new ArrayList<List<String>>();
			int numRecords = attributeNameWithResults.get(key).size() / (candidateAttributes.size() + 1);
			for (int i = 0; i < numRecords; i++) {
				List<String> subList = attributeNameWithResults.get(key).subList(index,
						index + candidateAttributes.size() + 1);
				subLists.add(subList);
				index += (candidateAttributes.size() + 1);
			}
			int numResultsToShow = 5;
			int attributeID = 1;
			for (int i = 0; i < candidateAttributes.size(); i++) {
				String candidateAttribute = iterator.next();
				for (int j = 0; j < numResultsToShow; j++) {
					List<String> subList = subLists.get(new Random().nextInt(subLists.size()));
					String stateName = subList.get(0);
					int count = Integer.parseInt(subList.get(attributeID));
					String normalName = StaticData.attributeNameNormalNameMappings.get(candidateAttribute);

					if (candidateAttribute.contains("gdp")) {

						System.out.println(normalName + " " + "of " + stateName + " is " + count);
						answer += (normalName + " " + "of " + stateName + " is " + count + "\n");
					} else {
						System.out.println(stateName + " has " + count + " " + normalName);
						answer += (stateName + " has " + count + " " + normalName + "\n");

					}

				}
				attributeID++;
				answer += ("\n");
				System.out.println();

			}

		}

		return answer;
	}

	public static String getAnswerSentenceForMultipleProperNouns(ArrayList<String> tableNames, String properNoun,
			String preposition, List<String> results, List<String> nouns, String adjectives) {
		String answer = "";
		String tableName = tableNames.get(1);

		// Which states does Road 65 pass through

		int rz = results.size();
		for (int i = 0; i < results.size(); i++) {

			if (i < results.size() - 1)

			{
				answer += results.get(i) + ",";

			}

			else if (rz == 1) {
				answer += results.get(0);

			}

			else

			{
				answer += (results.get(i));

			}

		}
		if(nouns.contains("bottom")){
			answer = results.get(0) + " is the " + nouns.get(0)+ " " +nouns.get(1); 
		}

		if (nouns.contains("roads") && nouns.contains("pass")) {

			answer = properNoun + " pass " + preposition + " " + answer;
		}

		if (nouns.contains("passes") || (nouns.contains("rivers") || nouns.contains("river"))
				&& !nouns.contains("traverse") && !nouns.contains("flows") && !nouns.contains("flow")) {

			answer += " pass " + preposition + " " + properNoun;
		}

		if (tableName.contains("road")) {

			answer = properNoun + " passes " + preposition + " " + answer;
		}

		// Which road passes through Tennessee

		if (nouns.contains("border") && !adjectives.contains("largest") && !adjectives.contains("smallest")) {

			answer = properNoun + " has " + "border " + preposition + " " + answer;
		}

		else if (nouns.contains("flow") || nouns.contains("flows")) {

			answer += " flows " + preposition + " " + properNoun; // Tell me the
																	// rivers
																	// flows
																	// through
																	// Montana.

		}

		else if (nouns.contains("traverse")) {

			answer += " traverse " + preposition + " " + properNoun; // Tell me
																		// the
																		// rivers
																		// flows
																		// through
																		// Montana.

		}
		else if (nouns.contains("neighbour")) {

			answer = nouns.get(0) + " " + nouns.get(1) + " for" + " " + properNoun + " are " + answer;
		}
		// the rivers that traverse through Montana.

		else if (nouns.contains("cities") || (nouns.contains("city"))) {

			answer += " has " + tableName + " named " + " " + properNoun;
		} // which states have cities named Birmingham

		else if (adjectives.contains("largest")) {

			answer += "is " + "the " + adjectives + " state" + " that " + tableName + " " + preposition + " "
					+ properNoun;

		} else if (adjectives.contains("smallest")) {

			answer += "is " + "the " + adjectives + " state" + " that " + tableName + " " + preposition + " "
					+ properNoun;

		}

		else {

		}
		
		
		if(results.size() > 1){
			IntensionalAnswerGenerator iag = new IntensionalAnswerGenerator();
			String ians = iag.generateIntensionalAnswer(results);
			System.out.println("Intentional answer : All"+ results+"are from "+properNoun );
		}
		 
		
		return answer;

	}

	public static String getAnswerSentenceForSingularVerbQuestion(List<String> nouns,
			Map<String, List<String>> attributeNameWithResults, boolean isMinQuestion, List<String> adjectives) {
		String answer = "";

		if (attributeNameWithResults == null || attributeNameWithResults.isEmpty()) {
			answer = "There are no " + nouns.get(0) + ".";
		} else {
			for (Entry<String, List<String>> entry : attributeNameWithResults.entrySet()) {
				String attributeNormalName = entry.getKey();
				List<String> results = entry.getValue();

				if (results.get(0) != null) {

					{
						if (attributeNormalName.contains("gdp"))
							answer += adjectives.get(0) + " " + nouns.get(0) + " is " + results.get(0) + " and "
									+ attributeNormalName + " is " + results.get(1) + " million usd" + ". ";
						else if (attributeNormalName.contains("area")) {
							answer += adjectives.get(0) + " " + nouns.get(0) + " is " + results.get(0) + " and "
									+ attributeNormalName + " is " + results.get(1) + " km²" + ". ";
						} else if (attributeNormalName.contains("RiverLength")) {
							answer += adjectives.get(0) + " " + nouns.get(0) + " is " + results.get(0) + " and" + " "
									+ attributeNormalName + " is " + results.get(1) + " km" + "." + "\n\n";

						} else if (attributeNormalName.contains("Mountainheight")) {
							answer += adjectives.get(0) + " " + nouns.get(0) + " is " + results.get(0) + " and" + " "
									+ attributeNormalName + " is " + results.get(1) + " m" + "." + "\n\n";

						} else if (attributeNormalName.contains("LakeLength")) {
							answer += adjectives.get(0) + " " + nouns.get(0) + " is " + results.get(0) + " and" + " "
									+ attributeNormalName + " is " + results.get(1) + " km²" + "." + "\n\n";

						} else if (attributeNormalName.contains("RoadLength")) {
							answer += adjectives.get(0) + " " + nouns.get(0) + " is " + results.get(0) + " and" + " "
									+ attributeNormalName + " is " + results.get(1) + " km" + "." + "\n\n";
						}
						
						else if (nouns.contains("population") && nouns.contains("state") && adjectives.contains("largest")||adjectives.contains("biggest")) {
							answer = nouns.get(0) + " of the "+ adjectives.get(0) + nouns.get(1) + " is " + results.get(0) +".";

						}
						else if (nouns.contains("population") && nouns.contains("city") && adjectives.contains("largest")||adjectives.contains("biggest")) {
							answer = nouns.get(0) + " of the "+ adjectives.get(0) + " " + nouns.get(1) + " is " + results.get(0) +".";

						}
						 else if (nouns.contains("population") && nouns.contains("city")) {
							answer = results.get(0) + " has the " + adjectives.get(0) + " population " + "and"
									+ " population is " + results.get(1) + "." + "\n\n";
						}
						else if (nouns.contains("population") && nouns.contains("state")) {
							answer = results.get(0) + " has the " + adjectives.get(0) + " population " + "and"
									+ " population is " + results.get(1) + "." + "\n\n";

						}

						else if (nouns.contains("population") && nouns.contains("city")) {

							answer = "Population of " + adjectives.get(0) + " city" + " is " + results.get(0) + "."
									+ "\n\n";

						} else {

							answer += adjectives.get(0) + " " + nouns.get(0) + " is " + results.get(0) + " and" + " "
									+ "number of " + attributeNormalName + " are " + results.get(1) + "." + "\n\n";
						}
					}

				}

			}
		}
		return answer;
	}

	public static String getAnswerSentenceForCountQuestions(List<String> results, List<String> nouns,
			String properNoun) {
		String answer = "";

		answer = "There are " + results.get(0) + " " + nouns.get(0) + " in " + properNoun + ".";

		return answer;
	}

	public static String getAnswerSentenceForWhereQuestions(List<String> results, List<String> improperNouns,
			String properNoun, String WhereType) {
		String answer = "";

		if (WhereType.contains("state")) {
			answer = properNoun + " is a" +" "+WhereType + " of" + " USA" +"." + " its" + " area" + " is" + " "
					+ results.get(0) + " km²" + " and" + " Population" + " is" + " " + results.get(1) + ".";

		}

		else {
			answer = properNoun + " is a" + " " + WhereType + " of" + " USA" + "." + " its" + " area" + " is" + " "
					+ results.get(0) + " km²" + " and" + " Population" + " is" + " " + results.get(1) + ".";
		}

		return answer;
	}
}
