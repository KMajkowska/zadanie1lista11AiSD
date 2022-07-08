import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class Graphv4 {
	
	static class DirectedGraphNode{ 
		String label; 
		double weight;
		List<DirectedGraphNode> neighbors ;		

		DirectedGraphNode(String  x, double w) { 
			label = x; 
			w = (-1)*Math.log(w);
			this.weight = w;
			neighbors =  new ArrayList<>();	
		} 
		
	    public String toString() {	    	
	    	return String.valueOf(label);
	    }
	} 
	
	Map<String, DirectedGraphNode> adj = new HashMap<>();
	HashMap<String, Boolean> visited = new HashMap<>();
	public void addEdge(String a, String b, double weight) {
		adj.putIfAbsent(a, new DirectedGraphNode(a, weight));
		adj.putIfAbsent(b, new DirectedGraphNode(b, weight));
		DirectedGraphNode node1 = adj.get(a);
		DirectedGraphNode node2 = adj.get(b);
		node1.neighbors.add(node2); 
	}
 
	public boolean detectCycle() {
	    Map<String, Boolean> visited = new HashMap<>();
	    Map<String, Boolean> backTrack = new HashMap<>();
 	 	for (String key: adj.keySet()) {
	    	visited.put(key, false);
	    	backTrack.put(key, false);
 	 	}
	    for (String key: adj.keySet()) {	
	        if (!visited.get(key)) {
	            if (cycleUtil(key, visited, backTrack)) {                
	                return true;
	            }   
	        }
	    }
	    return false;
	}

	private boolean cycleUtil(String v,  Map<String, Boolean> visited, Map<String, Boolean> backTrack){
    	if (backTrack.get(v)) 
    		return true;   
	    if (visited.get(v))
	    	return false;	    
	    visited.put(v, true);
	    backTrack.put(v, true);;
	    for (DirectedGraphNode ne : adj.get(v).neighbors) {
           if (cycleUtil(ne.label, visited, backTrack))
                return true; 
	    }
	    backTrack.put(v, false);
	    return false;
	}

	public boolean removeCycle() {
		for (String key: adj.keySet()) {
			for (DirectedGraphNode ne: adj.get(key).neighbors) {
				removeEdge(key, ne.label);
				boolean b = detectCycle();
				if (!b) {//no more cycle
					System.out.println("remove edge: "+ key+", "+ne);
					return true;				
				}
				else 
					addEdge(key, ne.label, ne.weight); //add back
			}
		}
		return false;
	}
	
	public void removeEdge(String a, String b) {
		DirectedGraphNode node1 = adj.get(a);
		DirectedGraphNode node2 = adj.get(b);
		if (node1 == null || node2 == null)
			return;
		node1.neighbors.remove(node2);
	}
	
	public String[] bfs(String src) { 
	    Queue<String> q = new LinkedList<>(); 
	    HashMap<String, Boolean> visited = new HashMap<>(); 
	    ArrayList<String> result = new ArrayList<>();
	    String [] successors;
	    q.add(src); 
	    visited.put(src, true); 
	    while (!q.isEmpty()) { 
	    	String v = q.poll(); 
	    	result.add(v);     
	        for(int i=0; i<adj.get(v).neighbors.size(); i++)
	        { 
	        	String ne = adj.get(v).neighbors.get(i).label;
	        	if (visited.get(ne) == null) { 
	                q.add(ne); 
	                visited.put(ne, true); 
	            
	            } 
	        }   
	    } 
	   successors = new String[result.size()];
	   successors = result.toArray(successors);
	   return successors;
	} 
	
	public HashMap<String, Boolean> dfsTraversal(String src) {
		//HashMap<String, Boolean> visited = new HashMap<>();
	    helper(src, visited);
	    return visited;
	}
	
	private void helper(String v, HashMap<String, Boolean> visited) {
	    visited.put(v, true);
	    System.out.print(v.toString() + " ");
	    for(int i=0; i<adj.get(v).neighbors.size(); i++)
        { 
        	String ne = adj.get(v).neighbors.get(i).label;
	        if (visited.get(ne) == null)
	            helper(ne, visited);
	        if(ne.equals("</s>"))
	        	break;
	    }
	}
	
	
	
	
}
