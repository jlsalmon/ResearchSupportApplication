package researchSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author jussy
 * 
 */
public class Vertex {

	private String name;
	private Map<String, Edge> edges;
	private boolean visited;

	public Vertex(String name) {
		this.name = name;
		this.edges = new HashMap<String, Edge>();
		this.visited = false;
	}

	/**
	 * 
	 * @param destVertex
	 * @return
	 */
	public boolean connect(Vertex destVertex) {
		this.edges.put(destVertex.getName(), new Edge(destVertex));
		return true;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param visited
	 *            the visited to set
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * @return the visited
	 */
	public boolean isVisited() {
		return visited;
	}
}
