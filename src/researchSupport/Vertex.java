package researchSupport;

import java.util.Map;

/**
 * 
 * @author jussy
 * 
 */
public class Vertex<I extends Edge, O extends Edge> {

	private Map<String, I> inEdges;
	private Map<String, O> outEdges;
	private int inDegree;
	private int outDegree;

	/**
	 * @return the inEdges
	 */
	public Map<String, I> getInEdges() {
		return inEdges;
	}

	/**
	 * @param inEdges
	 *            the inEdges to set
	 */
	public void setInEdges(Map<String, I> inEdges) {
		this.inEdges = inEdges;
	}

	/**
	 * @return the outEdges
	 */
	public Map<String, O> getOutEdges() {
		return outEdges;
	}

	/**
	 * @param outEdges
	 *            the outEdges to set
	 */
	public void setOutEdges(Map<String, O> outEdges) {
		this.outEdges = outEdges;
	}

	/**
	 * @return the inDegree
	 */
	public int getInDegree() {
		return inDegree;
	}

	/**
	 * @param inDegree
	 *            the inDegree to set
	 */
	public void setInDegree(int inDegree) {
		this.inDegree = inDegree;
	}

	/**
	 * @return the outDegree
	 */
	public int getOutDegree() {
		return outDegree;
	}

	/**
	 * @param outDegree
	 *            the outDegree to set
	 */
	public void setOutDegree(int outDegree) {
		this.outDegree = outDegree;
	}

}
