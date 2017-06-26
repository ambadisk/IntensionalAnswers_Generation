package controller;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.stanford.nlp.graph.Graph;
import model.AttributesStates;
import model.DBData;
import model.dbObject.State;

import java.lang.String;

import util.DBConnector;
import weka.core.stopwords.Rainbow;

public class ClauseGenerator {




	
	public  Graph<String, String> constructClause( String result){
		
		Graph<String, String> raw_clausedata= null;
		System.out.println("main Attribute = "+KnowledgeBase.mainatts_KB);
		KnowledgeBase kb= new KnowledgeBase();
		
         
	    try {
	    	 raw_clausedata= kb.getid_table(result);
	    	
	    	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return raw_clausedata;
		}
	
	
	

	
		
		                 
		
	
		
	
		public void filterResults(){
			
			
		}
		
		
		
		
	

	
		
		
	}
		
	