package controller;

import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;

public class ClauseData {
	
	public String clauseName;
	public String MapFrom;
	public String  Mapto;
	
	public ClauseData (String clausename, String name1, String name2){
		this.clauseName = clausename;
		this.MapFrom = name1;
		this.Mapto= name2;
		
		
	}
	
	public ClauseData (String clausename, String name1){
		this.clauseName = clausename;
		this.MapFrom = name1;
		//this.Mapto = null;
	}
	
    public String toString(){
    	String toS = this.clauseName + "\t" + this.MapFrom + "\t" + this.Mapto ;
    	return toS;
    	
	   
   }
	
 	
	

}
