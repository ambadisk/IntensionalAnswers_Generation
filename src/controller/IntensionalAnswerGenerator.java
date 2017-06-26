package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SortingFocusTraversalPolicy;

import java.util.Set;
import edu.stanford.nlp.graph.Graph;
import edu.stanford.nlp.parser.lexparser.SpanishUnknownWordModelTrainer;
import edu.stanford.nlp.graph.DirectedMultiGraph;


public class IntensionalAnswerGenerator {
	public String generateIntensionalAnswer(List<String> results){

		ClauseGenerator cg = new ClauseGenerator();
        /*
		for(String s: results)
			System.out.println(s);
		*/
		DirectedMultiGraph<String, String> clause_LGG =   (DirectedMultiGraph<String, String>) cg.constructClause(results.get(0));
		//System.out.println(clause_LGG);
		
		

		for (int i= 0; i<= results.size(); i++){
			if(i== 0)
				continue;
			DirectedMultiGraph<String, String> clause_new = (DirectedMultiGraph<String, String>) cg.constructClause(results.get(i+1));
			DirectedMultiGraph<String, String> after_LGG = this.LGG(clause_LGG,  clause_new);
			ArrayList<String> Answers_mod = this.evaluateQuery(after_LGG); 
			break;
			
		}
		
		
		
		
		
		
		return null;
	}
	
	private ArrayList<String> evaluateQuery(DirectedMultiGraph<String, String> after_LGG) {
		// TODO Auto-generated method stub
	  

	    QueryEvaluation qe = new QueryEvaluation(after_LGG);
		return null;
	}

	public DirectedMultiGraph<String, String> LGG(DirectedMultiGraph<String, String> clause_LGG, DirectedMultiGraph<String, String> clause_new ){
		
		HashMap<String, ArrayList<String>> clause1 = this.getAllEdges(clause_LGG);
		HashMap<String, ArrayList<String>> clause2 = this.getAllEdges(clause_new);
	    
		DirectedMultiGraph<String, String> combined_LGG = new DirectedMultiGraph<String, String>();
	    
		for (Entry<String, ArrayList<String>> entry1: clause1.entrySet())  {
			for (Entry<String, ArrayList<String>> entry2 : clause2.entrySet()){
				if(entry1.getKey().equals(entry2.getKey())){
					ArrayList<String> list = new ArrayList<String>();
					list.addAll(entry1.getValue());
					list.addAll(entry2.getValue());
                    clause1.put(entry1.getKey(), list)	;			 
				     
				}
			}
		}
	   
		for (Entry<String, ArrayList<String>> entry: clause1.entrySet())  {
			for(String val : entry.getValue() ){
				for(String val2: entry.getValue()){
					combined_LGG.add(val, val2, entry.getKey());
					combined_LGG.add(val2, val, entry.getKey());
				}
				
			}
		}
		combined_LGG.deleteDuplicateEdges();
		
		return combined_LGG;
		
	}
	


	public HashMap<String, ArrayList<String>> getAllEdges(DirectedMultiGraph<String,String> clause){
		clause.deleteDuplicateEdges();
		Set<String> vertices_1 = clause.getAllVertices();
		HashMap<String, ArrayList<String>> edge_list = new HashMap<String, ArrayList<String>>();
		for(String v1:  clause.getAllVertices()){
			for(String v2: clause.getAllVertices()){
		
				if(clause.isEdge(v1, v2)){
					if(edge_list.containsKey(clause.getEdges(v1, v2).get(0))){
						ArrayList<String> vertices= edge_list.get(clause.getEdges(v1, v2).get(0));
						edge_list.remove(clause.getEdges(v1, v2).get(0));
						vertices.add(v1);
						vertices.add(v2);
						edge_list.put(clause.getEdges(v1, v2).get(0), vertices);
					}else{
					ArrayList<String> vertices = new ArrayList<String>();
					vertices.add(v1);
					vertices.add(v2);
					edge_list.put(clause.getEdges(v1, v2).get(0), vertices);
					}
				}
			}
		}
		
		
		return edge_list;
		
	}
		
		
	
	
	public void checkConsistency(){
		
		
		
	} 
	
	

}
