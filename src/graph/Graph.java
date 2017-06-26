package graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Graph {
	
	
	private HashSet<Node> node_list;
	private Map<Node, List<Node>> edge_list;
	
	public Graph(HashSet<Node> node_list, Map<Node, List<Node>> edge_list) {
		super();
		this.node_list = node_list;
		this.edge_list = edge_list;
	}

	public HashSet<Node> getNode_list() {
		return node_list;
	}

	public void setNode_list(HashSet<Node> node_list) {
		this.node_list = node_list;
	}

	public Map<Node, List<Node>> getEdge_list() {
		return edge_list;
	}

	public void setEdge_list(Map<Node, List<Node>> edge_list) {
		this.edge_list = edge_list;
	}
	
	public void addNode(Node t) {
		node_list.add(t);
	}
	
	public void addEdge(Node u, Node v) {
		if (!node_list.contains(u) || !node_list.contains(v)) {
			
		} else {
			if (edge_list.containsKey(u)) {
				edge_list.get(u).add(v);
			} else {
				List<Node> t = new ArrayList<Node>();
				t.add(v);
				edge_list.put(u,t);
				
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edge_list == null) ? 0 : edge_list.hashCode());
		result = prime * result + ((node_list == null) ? 0 : node_list.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Graph other = (Graph) obj;
		if (edge_list == null) {
			if (other.edge_list != null)
				return false;
		} else if (!edge_list.equals(other.edge_list))
			return false;
		if (node_list == null) {
			if (other.node_list != null)
				return false;
		} else if (!node_list.equals(other.node_list))
			return false;
		return true;
	}
	
	
}
