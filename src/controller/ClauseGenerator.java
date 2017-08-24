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

import org.jgrapht.DirectedGraph;

import model.AttributesStates;
import model.DBData;
import model.dbObject.State;

import java.lang.String;

import util.DBConnector;
import weka.core.stopwords.Rainbow;

import org.jgrapht.*;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedMultigraph;
public class ClauseGenerator {




	
	public  List<ClauseData> constructClause( String result){
		
		List <ClauseData> raw_clausedata= null;
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
		
	