package researchSupport;

public class Reference extends Edge {

	private Paper referee;

	/**
	 * 
	 * @param referee
	 */
	public Reference(Paper referee) {
		this.setReferee(referee);
	}

	/**
	 * @param referee
	 *            the referee to set
	 */
	public void setReferee(Paper referee) {
		this.referee = referee;
	}

	/**
	 * @return the referee
	 */
	public Paper getReferee() {
		return referee;
	}

}
