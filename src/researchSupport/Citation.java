package researchSupport;

/**
 * Citation.java
 * 
 * @author Justin Lewis Salmon
 * @student_id 10000937
 * 
 *             Represents a citation between papers where the endpoint of the
 *             connection is the paper that is making the reference, i.e. the
 *             paper at the end of this connection is referencing me, so i'm
 *             going to store it as a citation.
 */
public class Citation extends Edge {

	/**
	 * Creates a new citation, and sets the endpoint (inherited from Edge) to be
	 * the source of the reference, i.e. the source is the paper that is
	 * referencing this one.
	 * 
	 * @param source
	 */
	public Citation(Paper source) {
		this.setEndpoint(source);
	}

	/**
	 * @return the source
	 */
	public Paper getSource() {
		return (Paper) this.getEndpoint();
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(Paper source) {
		this.setEndpoint(source);
	}
}
