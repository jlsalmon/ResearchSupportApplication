package researchSupport;

/**
 * Edge.java
 * 
 * @author Justin Lewis Salmon
 * @student_id 10000937
 * 
 *             Represents a generic edge in a graph, where the endpoint is the
 *             other end of the connection. Edges are stored _inside_ Vertices,
 *             so the vertex in which an Edge is stored is the start of the
 *             connection.
 */
public class Edge {
	private Vertex<?, ?> endpoint;

	/**
	 * @return the endpoint
	 */
	public Vertex<?, ?> getEndpoint() {
		return endpoint;
	}

	/**
	 * @param endpoint
	 *            the endpoint to set
	 */
	public void setEndpoint(Vertex<?, ?> endpoint) {
		this.endpoint = endpoint;
	}

}
