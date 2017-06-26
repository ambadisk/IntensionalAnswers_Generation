package controller;

import java.util.HashMap;
import java.util.Map;

public class ClauseRepresentation {
	String clause_name;
	String	data[];
	public ClauseRepresentation(String clause_name, String[] array) {
		super();
		this.clause_name = clause_name;
		this.data = array;
	}
	
	Map< String, String[]> cl = new HashMap<String, String[]>();

}
