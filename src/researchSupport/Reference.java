package researchSupport;

public class Reference extends Edge {

	/**
	 * 
	 * @param referee
	 */
	public Reference(Paper referee) {
		this.setEndpoint(referee);
	}

	/**
	 * @param referee
	 *            the referee to set
	 */
	public void setReferee(Paper referee) {
		this.setEndpoint(referee);
	}

	/**
	 * @return the referee
	 */
	public Paper getReferee() {
		return (Paper) this.getEndpoint();
	}

}
