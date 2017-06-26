package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.stanford.nlp.graph.DirectedMultiGraph;

public class QueryEvaluation {
	
	HashMap<String, List<String>> clause_toverify = null;

	DirectedMultiGraph<String, String> clause = null;

	public QueryEvaluation(DirectedMultiGraph<String, String> after_LGG) {
		this.clause = after_LGG;
        this.clause_toverify= (HashMap<String, List<String>>) after_LGG.toMap();
        System.out.println(this.clause_toverify);
	}

}
