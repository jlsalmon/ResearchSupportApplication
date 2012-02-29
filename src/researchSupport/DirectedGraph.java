package researchSupport;

import java.util.*;

/**
 * 
 * @author jussy
 * 
 * @param <V>
 * @param <E>
 */
public class DirectedGraph<V, E> {

	Map<String, V> vertices;
	int numEdges;

	/**
	 * 
	 */
	public DirectedGraph() {
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
}
