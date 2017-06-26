package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.dbObject.State;

public class AttributesStates {
	public static Map<String, String> StateName= new HashMap<>();
	public static Map<String, Double> Area= new HashMap<>();
    public static Map<String, Integer> StateId =new HashMap<>(); ;
	public static Map<String, String> StateAbbrevation =new HashMap<>();
	public static Map<String, String> StateCapital=new HashMap<>();
	public static Map<String, Integer> StateGMT=new HashMap<>();
	public static Map<String, Double> TotalPopulation=new HashMap<>();
	public static Map<String, Double> GDP=new HashMap<>();
	public static Map<String, Integer> CityCount = new HashMap<>();
	public static Map<String, Integer> MountainCount=new HashMap<>();
	public static Map<String, Integer> LakeCount=new HashMap<>();
	public static Map<String, Integer> RiverCount=new HashMap<>();
	public static Map<String, Integer> RoadCount=new HashMap<>();
	public static Map<String, Integer> CityId=new HashMap<>();
	public static Map<String, Integer> RiverId=new HashMap<>();
	public static Map<String, Integer> RoadId=new HashMap<>();
	public static Map<String, String> Location=new HashMap<>();
	
	public static ArrayList<HashMap> getItemList(){
		
		return null;
				
	}
}
