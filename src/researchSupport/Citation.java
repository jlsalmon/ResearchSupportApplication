package researchSupport;

public class Citation extends Edge {
	
	private Paper source;

	/**
	 * 
	 * @param source
	 */
	public Citation(Paper source) {
		this.source = source;
	}

	/**
	 * @return the source
	 */
	public Paper getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(Paper source) {
		this.source = source;
	}
}
