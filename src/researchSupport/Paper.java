package researchSupport;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author jussy
 * 
 */
public class Paper extends Vertex {
	private String title;
	private int rating;
	private Map<String, Reference> references;
	private Map<String, Citation> citations;
	private int numReferences;
	private int numCitations;

	/**
	 * Creates a new instance of Paper
	 * 
	 * @param title
	 * @param rating
	 */
	public Paper(String title, int rating) {
		this.setTitle(title);
		this.setRating(rating);
		this.references = new HashMap<String, Reference>();
		this.citations = new HashMap<String, Citation>();
		this.setNumReferences(0);
		this.setNumCitations(0);
	}

	/**
	 * 
	 * @param referee
	 * @return
	 */
	public boolean createReference(Paper referee) {
		this.references.put(referee.getTitle(), new Reference(referee));
		this.setNumReferences(this.getNumReferences() + 1);
		return true;
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	public boolean createCitation(Paper source) {
		this.citations.put(source.getTitle(), new Citation(source));
		this.setNumCitations(getNumCitations() + 1);
		return true;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * 
	 * @return
	 */
	public Collection<Citation> getCitations() {
		return this.citations.values();
	}

	/**
	 * 
	 * @return
	 */
	public Collection<Reference> getReferences() {
		return this.references.values();
	}

	/**
	 * @return the numReferences
	 */
	public int getNumReferences() {
		return numReferences;
	}

	/**
	 * @param numReferences
	 *            the numReferences to set
	 */
	public void setNumReferences(int numReferences) {
		this.numReferences = numReferences;
	}

	/**
	 * @return the numCitations
	 */
	public int getNumCitations() {
		return numCitations;
	}

	/**
	 * @param numCitations
	 *            the numCitations to set
	 */
	public void setNumCitations(int numCitations) {
		this.numCitations = numCitations;
	}

	@Override
	public String toString() {
		return "Title: " + getTitle() + "\tRating: " + getRating()
				+ "\tCited by " + getNumCitations();
	}
}
