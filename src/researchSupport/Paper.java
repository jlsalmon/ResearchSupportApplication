package researchSupport;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	public HashSet<Paper> getCitations() {
		HashSet<Paper> cits = new HashSet<Paper>();
		for (Citation c : this.citations.values()) {
			cits.add(c.getSource());
		}
		return cits;
	}

	/**
	 * 
	 * @return
	 */
	public HashSet<Paper> getReferences() {
		HashSet<Paper> refs = new HashSet<Paper>();
		for (Reference r : this.references.values()) {
			refs.add(r.getReferee());
		}
		return refs;
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
		String rating = "";
		for (int i = 0; i < getRating(); i++) {
			rating += '*';
		}
		return "Title: " + getTitle()
				+ (getTitle().length() > 8 ? "\t" : "\t\t") + "Rating: "
				+ rating + "\tCited by " + getNumCitations();
	}
}
