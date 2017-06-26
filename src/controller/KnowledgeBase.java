package controller;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mysql.jdbc.ResultSetMetaData;

import edu.stanford.nlp.graph.DirectedMultiGraph;
import edu.stanford.nlp.graph.Graph;
import graph.Node;
import model.DBData;
import util.DBConnector;


public class KnowledgeBase {
	static String mainatts_KB;
   	public  List<String> all_table_names = new ArrayList<String>();

	
   	public KnowledgeBase(){
	all_table_names.add("border");
    all_table_names.add("city");
	all_table_names.add("lake");
	all_table_names.add("landelevation");
	all_table_names.add("mountain");
	all_table_names.add("river");
	all_table_names.add("road");
	all_table_names.add("state");
   	}
	
	public  Graph<String, String> getid_table(String result) throws SQLException {
		//Map<String, ArrayList<String>> clausedata = new HashMap <String, ArrayList<String>>();
		Graph <String, String> clausedata = new DirectedMultiGraph<>();			
				
				
		if(mainatts_KB.endsWith("Name")){
			mainatts_KB = mainatts_KB.replace("Name", "Id");
		System.out.println("new main atts " + mainatts_KB);
		}	
	
		
	    	for (Map item : DBData.allnames){
	    		if(item.containsKey(result)){ 			
	    			int id_tofetch= (int) item.get(result);
	    			//System.out.println(id_tofetch);
	    			
	    	        clausedata = loadClauseData(id_tofetch, result);
	    	        break;
	    	        //System.out.println(clausedata);
	    		 
	    	
	    		}
	    		
	    	}
	     
	    
	    
	    //System.out.println(clausedata);
	    	//System.out.println(clausedata);
		return clausedata;
	    
	}
	
	
	public String querytypes(String tn,int id_tofetch){
		String sql= null;
		  
		if(tn.equals("border")){
			sql= "select BorderId,BorderName,StateId,StateName from mythesis."+tn+" where "+mainatts_KB+" =  "+id_tofetch ;
			 //sql =  "select * from information_schema.columns where table_schema ='mythesis' and table_name= '"+tn+"' and column_name= '"+mainatts_KB+"'";
		}
			else if(tn.equals("state")){
				sql="select StateId,StateName,StateCapital,Totalpopulation,Area,GDP,Location from mythesis."+tn+" where "+mainatts_KB+" = "+id_tofetch; // sql="IF EXISTS(Select StateId,StateName,StateCapital,Totalpopulation,Area,GDP,Location from "+ tn +" Where "+mainatts_KB+" = "+id_tofetch+")";
			}
			else if(tn.equals("city")){
				sql="select StateId,CityName,CityId,TotalPopulation,GDP from mythesis."+tn+" where "+mainatts_KB+" = "+id_tofetch; // and exists (select * from information_schema.columns where table_schema ='mythesis' and table_name='"+tn+ "' and column_name= '"+mainatts_KB+"')"; 
				//sql="IF EXISTS(Select StateId,CityName,CityId,TotalPopulation,GDP from "+ tn +" Where "+mainatts_KB+" = "+id_tofetch+")";
			}
			else if(tn.equals("lake")){
				sql="select StateId,LakeId,LakeName,LakeLength from mythesis."+tn+" where "+mainatts_KB+" = "+id_tofetch;
					//sql="IF EXISTS(Select StateId,LakeId,LakeName,LakeLength from "+ tn +" Where "+mainatts_KB+" = "+id_tofetch+")";
			}
			else if(tn.equals("river")){
				sql="select StateId,RiverId,RiverLength,RiverName from mythesis."+tn+" where "+mainatts_KB+" = "+id_tofetch;
			}
			else if(tn.equals("mountain")){
				sql="select StateId,MountainId,MountainName,MountainHeight from mythesis."+tn+" where "+mainatts_KB+" = "+id_tofetch; 
				//sql="IF EXISTS(Select StateId,MountainId,MountainName,MountainHeight from "+ tn +" Where "+mainatts_KB+" = "+id_tofetch+")";
			}
			else if(tn.equals("road")){
				sql="select StateId,RoadId,RoadName,RoadLength from mythesis."+tn+" where "+mainatts_KB+" = "+id_tofetch; 
				//sql="IF EXISTS(Select StateId,RoadId,RoadName,RoadLength from "+ tn +" Where "+mainatts_KB+" = "+id_tofetch+")";
			}
			else if(tn.equals("landelevation")){
				
				sql="select StateId,LandElevationId,landelevationName,HighLand,LowLand,HighLength,LowLength from mythesis."+tn+" where "+mainatts_KB+" = "+id_tofetch;				//sql="IF EXISTS(Select StateId,LandElevationId,landelevationName,HighLand,LowLand,HighLength,LowLength from "+ tn +" Where "+mainatts_KB+" = "+id_tofetch+")";
			}
		return sql;
		
		}
	
	
	
     

	   
	    
	  public static Graph<String, String> loadClauseData(int id_tofetch, String result_name ){ 
		  Graph<String, String> data = new DirectedMultiGraph<String, String>() ;
		  KnowledgeBase kb = new KnowledgeBase();		  
		  int flag=0;
		try {	
		
		  for (String tn : kb.all_table_names){
			 //System.out.println(mainatts_KB);
			 String check_query= "SELECT CASE WHEN EXISTS (select * from information_schema.columns where table_schema ='mythesis' and table_name= '"+tn+"' and column_name= '"+mainatts_KB+"') THEN 'yes'  else 'no' end ";
			 ResultSet rs_check = kb.query_executed(check_query);
			 flag = 0;
			 while(rs_check.next()){
				  if(rs_check.getString(1).equals("no")){
					  flag=  1;
				  }					  		  
			
			 }
			 
			 if(flag== 1){
				 
				 continue;
			 }
			 
		
			ResultSet rs =  kb.query_executed(kb.querytypes(tn, id_tofetch));	
		
			kb.sortQueryAnswer(rs, result_name, data);
		    
			
							
		  }
			}catch (SQLException e) {
				e.printStackTrace();
				// TODO Auto-generated catch block
			
			}
		
		return data;
					  
		  }
	  
	  
	  
	  public ResultSet query_executed(String sql){

		  ResultSet rs = null;
		  try {
			Statement sta = DBConnector.conn.createStatement();
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return rs;
		  
	  }
	  
	  public Graph<String, String> sortQueryAnswer(ResultSet rs,String result_name, Graph<String, String> data){
		  //Graph<String, String> graph = data;
		
		 
		  
		  try {
			while (rs.next()){
					
				  ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						  String colname = rsmd.getColumnLabel(i);					  
						  int type = rsmd.getColumnType(i);
						  if (type == Types.VARCHAR || type == Types.CHAR) {
							  //System.out.println(rs.getString(colname));
                                data.add( result_name, rs.getString(colname), colname);
							 
						  }
						  else if(type == Types.DOUBLE || type == Types.FLOAT ){
							  				  
						  }else if (type == Types.INTEGER){
							
							 
						  }
						   
					  }
					
					}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	            return data;
		  
					 
			
		 
	  }
	  
		  
		 
		  
	  }
	  
	    
	    
	    
	    
	    
		//Statement sta = DBConnector.conn.createStatement();
		//String Sql = ("select * from state where "+mainatts_KB+" = "+QueryGenerator.getResultForSqlQuery(sql));
		//ResultSet rs = sta.executeQuery(Sql);
		
		
	
	
		
	
	
	
	
	
	
	
	


