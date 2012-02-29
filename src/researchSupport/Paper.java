package researchSupport;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author jussy
 * 
 */
public class Paper {
	private String title;
	private int rating;
	private Map<String, Reference> references;

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
	}

	/**
	 * 
	 * @param referee
	 * @return
	 */
	public boolean createReference(Paper referee) {
		this.references.put(referee.getTitle(), new Reference(referee));
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

	public Set<Paper> getCitations() {
		return null;
	}

	public Collection<Reference> getReferences() {
		return this.references.values();
	}

	@Override
	public String toString() {
		return "Title: " + getTitle() + "\t\tRating: " + getRating();
	}
}
