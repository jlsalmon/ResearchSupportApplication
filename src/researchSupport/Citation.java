package researchSupport;

public class Citation extends Edge {

	/**
	 * 
	 * @param source
	 */
	public Citation(Paper source) {
		this.endpoint = source;
	}

	/**
	 * @return the source
	 */
	public Paper getSource() {
		return (Paper) this.endpoint;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(Paper source) {
		this.endpoint = source;
	}
}
