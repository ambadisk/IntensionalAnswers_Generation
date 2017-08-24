package controller;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import edu.stanford.nlp.graph.DirectedMultiGraph;
import util.DBConnector;

public class QueryEvaluation {
	
	HashMap<String, HashMap<String, ArrayList<String>>> clause =null;
	
	public QueryEvaluation(HashMap<String, HashMap<String, ArrayList<String>>>tobequeried){
		this.clause= tobequeried;
		
		}
	String sql = null;
	sql= "select statename from state where StateName in (SELECT StateName from mythesis.border where BorderName = 'Nevada' )
 UNION (select StateName from mythesis.river where rivername = 'Colorado') union(select state.StateName from state  inner join landelevation on state.StateId=landelevation.StateId where landelevation.landelevationName = 'mount hood and pacific ocean') union(select StateName from road where roadname= 'road 80');";
	public 	String queryfire (){
		String rs=null;
		  try {
				Statement sta = DBConnector.conn.createStatement();
				rs = sta.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
	
	
	
	
	
	
	
		
	/*HashMap<String, List<String>> clause_toverify = null;

	DirectedMultiGraph<String, String> clause = null;

	public QueryEvaluation(DirectedMultiGraph<String, String> after_LGG) {
		this.clause = after_LGG;
        this.clause_toverify= (HashMap<String, List<String>>) after_LGG.toMap();
        System.out.println(clause_toverify);
        
        
	}

	private HashMap<String, List<String>> queryGenerate(HashMap<String, List<String>> clause_toverify) {
		// TODO Auto-generated method stub
		for(Entry <String, List<String>> entry : clause_toverify.entrySet() ){
			Set<String> vals  = new HashSet<String>();
			for(String s: entry.getValue()){
				vals.add(s);
			}
			System.out.println(vals);
			clause_toverify.put(entry.getKey(), new ArrayList<String>(vals));
		}
		
		return clause_toverify;
	}*/

}
