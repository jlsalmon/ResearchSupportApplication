package com.jlewis.researchsupport;

/**
 * Reference.java
 * 
 * @author Justin Lewis Salmon
 * @student_id 10000937
 * 
 *             Represents a reference between papers where the endpoint of the
 *             connection is the paper that is being referenced.
 */
public class Reference extends Edge<Paper> {

	/**
	 * Creates a new reference, and sets the endpoint (inherited from Edge) to
	 * be the referee of the reference, i.e. the referee is the paper that is
	 * being referenced.
	 * 
	 * @param referee
	 *            the paper to set as the referee of this reference.
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
