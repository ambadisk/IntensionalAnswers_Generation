 package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserInterest {
	public static Map<UserType, Set<String>> userInterestMapping;	// <"tourist", ["river", "lake", "mountain"]>
																	// <"businessman", ["city", "state", "country", "border", "road"]
	
	private static void prepareUserInterestMapping() {
		if (userInterestMapping == null || userInterestMapping.isEmpty()) {
			userInterestMapping = new HashMap<>();
			
			Set<String> touristInterestedAttributeSet = new HashSet<>();
			touristInterestedAttributeSet.add("RiverCount");
			touristInterestedAttributeSet.add("LakeCount");
			touristInterestedAttributeSet.add("MountainCount");
			touristInterestedAttributeSet.add("CityCount");
			touristInterestedAttributeSet.add("river");
			touristInterestedAttributeSet.add("lake");
			touristInterestedAttributeSet.add("state");
			touristInterestedAttributeSet.add("mountain");
			touristInterestedAttributeSet.add("TotalPopulation");
			touristInterestedAttributeSet.add("area");
			
			
			userInterestMapping.put(UserType.TOURIST, touristInterestedAttributeSet);
			
			Set<String> businessmanInterestedAttributeSet = new HashSet<>();
			businessmanInterestedAttributeSet.add("CityCount");
			businessmanInterestedAttributeSet.add("RoadCount");
			businessmanInterestedAttributeSet.add("StateName");
			businessmanInterestedAttributeSet.add("CountryName");
			businessmanInterestedAttributeSet.add("BorderName");
			businessmanInterestedAttributeSet.add("RoadName");
			businessmanInterestedAttributeSet.add("city");
			businessmanInterestedAttributeSet.add("state");
			businessmanInterestedAttributeSet.add("country");
			businessmanInterestedAttributeSet.add("border");
			businessmanInterestedAttributeSet.add("gdp");
			businessmanInterestedAttributeSet.add("road");
			businessmanInterestedAttributeSet.add("TotalPopulation");
			
			
			userInterestMapping.put(UserType.BUSINESSMAN, businessmanInterestedAttributeSet);
			
			Set<String> noneInterestedAttributeSet = new HashSet<>();
			userInterestMapping.put(UserType.NONE, noneInterestedAttributeSet);
		}
	}
	
	public static void prepareAllUserInterests() {
		prepareUserInterestMapping();
	}
}
