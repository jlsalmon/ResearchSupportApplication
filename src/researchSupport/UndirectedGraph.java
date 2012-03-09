package researchSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * UndirectedGraph.java
 * 
 * @author Justin Lewis Salmon
 * @student_id 10000937
 * 
 *             Holds a map of objects which extend the Vertex class. The
 *             subclass must instantiate two sets of edges; one set of
 *             inward-facing and one of outward-facing edges, hence acheiving a
 *             bi-directional graph.
 * 
 *             The graph may not necessarily be connected, i.e. a vertex may be
 *             added but have no connections with any other vertices.
 * 
 * @param <V>
 *            the generic type which extends Vertex.
 */
public class UndirectedGraph<V extends Vertex<?, ?>> {

	private HashMap<String, V> vertices;

	/**
	 * Constructor. Instantiates a new empty graph.
	 */
	public UndirectedGraph() {
		this.vertices = new HashMap<String, V>();
	}

	/**
	 * Adds a vertex to the graph.
	 * 
	 * @param key
	 *            the key to hash.
	 * @param v
	 *            the object to put in the bucket.
	 * @return true, this method always succeeds.
	 */
	public boolean addVertex(String key, V v) {
		this.vertices.put(key, v);
		return true;
	}

	/**
	 * Gets the entire set of vertices in this graph.
	 * 
	 * @return the map of all vertices.
	 */
	public Map<String, V> getAllVertices() {
		return this.vertices;
	}

	/**
	 * Checks if a vertex with the supplieed key exists in the graph.
	 * 
	 * @param key
	 *            the key to find a value vertex for.
	 * @return true if the vertex exists, false otherwise.
	 */
	public boolean containsVertex(String key) {
		for (String s : this.vertices.keySet()) {
			if (s.equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retrieves a specific vertex from the graph.
	 * 
	 * @param key
	 *            the key of the vertex value to retrieve.
	 * @return the requested vertex.
	 */
	public V getVertex(String key) {
		return this.vertices.get(key);
	}
}
