import edu.stanford.nlp.graph.DirectedMultiGraph;
import edu.stanford.nlp.graph.Graph;

public class test {

	public static void main(String[] args) {
		Graph<String,String> graph= new DirectedMultiGraph<String,String>();
		graph.add(new String("Saarland"), new String("RF"), new String("Border"));
		graph.add(new String("Saarland"), new String("Hesse"), new String("Border"));
		graph.add(new String("California"), new String("Colorado"), new String("River") );
		graph.add(new String("Arizona"),new String("Phoenix"),new String("city"));
	
		
		for(String p : graph.getAllEdges()) {
		System.out.println(p);
		}
		// TODO Auto-generated method stub

	}


}
