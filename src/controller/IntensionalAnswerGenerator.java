package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SortingFocusTraversalPolicy;
import javax.xml.transform.Result;

import java.util.Set;
import edu.stanford.nlp.graph.Graph;
import edu.stanford.nlp.parser.lexparser.SpanishUnknownWordModelTrainer;
import edu.stanford.nlp.graph.DirectedMultiGraph;


public class IntensionalAnswerGenerator {
	List<String> results;
	public String generateIntensionalAnswer(List<String> results){
        this.results = results;
		ClauseGenerator cg = new ClauseGenerator();
		
		List<ClauseData> clause_LGG =   cg.constructClause(results.get(0));
	
		for (int i= 0; i<= results.size(); i++){			
			if(i== 0)
				continue;
			List<ClauseData> clause_new =  cg.constructClause(results.get(i));
			
			List<ClauseData> after_LGG = this.LGG(clause_LGG,  clause_new);
			
			//ArrayList<String> Answers_mod = this.evaluateQuery(after_LGG); 
			break;
		}
		return null;
	}
	
	/*private ArrayList<String> evaluateQuery(DirectedMultiGraph<String, String> after_LGG) {
		// TODO Auto-generated method stub
	    QueryEvaluation qe = new QueryEvaluation(after_LGG);
		return null;
	}*/

	public List<ClauseData> LGG(List<ClauseData> clause_LGG, List<ClauseData> clause_new ){
		/* 
		 * Here call a method called "make map* and send bothe the clauses i.e clause_LGG and clause_new and write the code 
		 * for making the Map. 
		 * 
		 * signature : 
		 * 
		 * public HashMap<String, HashMap<String, ArrayList<String>>>make_Map(List<ClauseData> clause))
		 * 
		 * 
		 * return the map 
		 * */
		
		HashMap<String, HashMap<String, ArrayList<String>>> clause_c1 = this.get_Mapform(clause_LGG);
		HashMap<String, HashMap<String, ArrayList<String>>> clause_c2 = this.get_Mapform(clause_new);
		System.out.println(clause_c1);
		System.out.println(clause_c2);
		List<ClauseData> clause_general = new ArrayList<ClauseData>();
		
		int num =  0;
		for(ClauseData cd : clause_LGG){
			
			if(cd.Mapto == null) {
				clause_general.add(new ClauseData(cd.clauseName, "X"));			
			}else{
				for(ClauseData c : clause_general){
					if(c.clauseName.equals(cd.clauseName)){						
						 num = Integer.parseInt(c.Mapto);
					     num ++;
					     clause_general.remove(c); 
					     break;
					}
				}
				clause_general.add(new ClauseData(cd.clauseName, "X", Integer.toString(num)));
				
					num ++;
					clause_general.add(new ClauseData(cd.clauseName, "X", Integer.toString(num)) );	
				}
				
			}
		
		
		System.out.println("dddddddddddddddddddddddddddd");
		
	return null;
		
		
		//List<ClauseData> cl =  this.makeClause(clause_LGG);
		
		//HashMap<String, ArrayList<String>> clause1 = this.getAllEdges(clause_LGG);
		
		//HashMap<String, ArrayList<String>> clause2 = this.getAllEdges(clause_new); 
        /*
		DirectedMultiGraph<String, String> combined_LGG = new DirectedMultiGraph<String, String>();	    
		for (Entry<String, ArrayList<String>> entry1: clause1.entrySet())  {
			for (Entry<String, ArrayList<String>> entry2 : clause2.entrySet()){
				if(entry1.getKey().equals(entry2.getKey())){
					ArrayList<String> list = new ArrayList<String>();
					list.addAll(entry1.getValue());
					list.addAll(entry2.getValue());
					Set<String> hs = new HashSet<>();
					hs.addAll(list);
					list.clear();
					list.addAll(hs);
                    clause1.put(entry1.getKey(), list);			 
				     
				}
			}
		}
	   
		for (Entry<String, ArrayList<String>> entry: clause1.entrySet())  {
			for(String val : entry.getValue() ){
				for(String val2: entry.getValue()){
					if(this.results.contains(val) && this.results.contains(val2))
						continue;
					//combined_LGG.add(val, val2, entry.getKey());
					//if(this.results.contains(val) || this.results.contains(val2))
					  combined_LGG.add(val2, val, entry.getKey());
				}
				
			}
		}
	
		//combined_LGG.deleteDuplicateEdges();
		//System.out.println(combined_LGG);
		return null; 
	
		*/
	}
	
	public HashMap<String, HashMap<String, ArrayList<String>>> get_Mapform (List<ClauseData> clause_LGG){
	HashMap<String, HashMap<String, ArrayList<String>>> mapping = new HashMap<String, HashMap<String, ArrayList<String>>>();
		
		for (ClauseData cd : clause_LGG){
			 HashMap<String , ArrayList<String>> this_data=  mapping.get(cd.clauseName);
			 if(this_data == null){
				  ArrayList<String> list = new ArrayList<String>();
				  list.add(cd.Mapto);
				  HashMap<String, ArrayList<String>> toadd = new HashMap<String, ArrayList<String>>();
				  toadd.put(cd.MapFrom, list);
				  mapping.put(cd.clauseName, toadd);
			 }else{
				 ArrayList<String> list = this_data.get(cd.MapFrom);
				 list.add(cd.Mapto);
				 this_data.remove(cd.clauseName);
				 HashMap<String, ArrayList<String>> toadd = new HashMap<String, ArrayList<String>>();
				 toadd.put(cd.MapFrom, list);
				 mapping.put(cd.clauseName, toadd );				 
			 }
			 
		}
		
		
		return mapping;
	}
	 
	
	
	
	
	
	
	
	
	

	private List<ClauseData> makeClause(DirectedMultiGraph<String, String> clause_LGG) {
		
			System.out.println("cccccccccccccccccccccccccccccccccc");
            for(String s: clause_LGG.getAllVertices()){
            	
            }

			
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		
		
			
		
		// TODO Auto-generated method stub
		return null;
	}

	public HashMap<String, ArrayList<String>> getAllEdges(List<ClauseData> clauseLGG){ 
		return null;
		//clause_LGG){
		//clause_LGG.deleteDuplicateEdges();
    
		//Set<String> vertices_1 = //clause_LGG.getAllVertices();
		//String[] vertices = vertices_1.toArray(new String[vertices_1.size()]);
       
		
		
  /*
		HashMap<String, ArrayList<String>> edge_list = new HashMap<String, ArrayList<String>>();
			
			for( int i = 0; i< vertices.length; i++){
				for( int j = 0; j< vertices.length; j++){	
					
				if(//clause_LGG.isEdge(vertices[i], vertices[j])){
					
					if(edge_list.containsKey(//clause_LGG.getEdges(vertices[i], vertices[j]).get(0))){
						ArrayList<String> verticestemp= edge_list.get(//clause_LGG.getEdges(vertices[i], vertices[j]).get(0));
						edge_list.remove(//clause_LGG.getEdges(vertices[i], vertices[j]).get(0));
						verticestemp.add(vertices[i]);
						verticestemp.add(vertices[j]);
						edge_list.put(//clause_LGG.getEdges(vertices[i], vertices[j]).get(0), verticestemp);
					}else{
					    ArrayList<String> verticestemp = new ArrayList<String>();
					    //verticestemp.add(vertices[i]);
					    verticestemp.add(vertices[j]);
					    edge_list.put(//clause_LGG.getEdges(vertices[i], vertices[j]).get(0), verticestemp);
					}
				}
			
			}
			}
		
        
     */
		//return null;
		
	}
	
		
	
	
	public void checkConsistency(){
		
		
		
	} 
	
	

}
