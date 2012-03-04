package researchSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author jussy
 * 
 * @param <V>
 * @param <E>
 */
public class Graph<V extends Vertex> {

	Map<String, V> vertices;
	int numEdges;

	public static final int SEARCH_CITATIONS = 0;
	public static final int SEARCH_REFERENCES = 1;
	public static final int NO_LIMIT = -1;

	/**
	 * 
	 */
	public Graph() {
		this.vertices = new HashMap<String, V>();
		this.numEdges = 0;
	}

	/**
	 * 
	 * @param key
	 * @param v
	 * @return
	 */
	public boolean addVertex(String key, V v) {
		this.vertices.put(key, v);
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, V> getAllVertices() {
		return this.vertices;
	}

	/**
	 * 
	 * @param referee
	 * @return
	 */
	public boolean containsVertex(String name) {
		for (String s : this.vertices.keySet()) {
			if (s.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public V getVertex(String key) {
		return this.vertices.get(key);
	}


	/**
	 * 
	 */
	public void resetVisitedState() {
		for (V v : this.vertices.values()) {
			v.setVisited(false);
		}
	}
}
