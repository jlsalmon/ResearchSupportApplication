package researchSupport;

public class Edge {

	private Vertex destVertex;

	/**
	 * 
	 * @param destVertex
	 */
	public Edge(Vertex destVertex) {
		this.setDestVertex(destVertex);
	}

	/**
	 * @param destVertex
	 *            the destVertex to set
	 */
	public void setDestVertex(Vertex destVertex) {
		this.destVertex = destVertex;
	}

	/**
	 * @return the destVertex
	 */
	public Vertex getDestVertex() {
		return destVertex;
	}

}
