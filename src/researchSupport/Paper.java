package researchSupport;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Paper.java
 * 
 * @author Justin Lewis Salmon
 * @student_id 10000937
 * 
 *             Stores all information about a particular paper. Lists of
 *             references and citations are stored generically in the
 *             superclass. The superclass also keeps count of the number of
 *             references and citations.
 */
public class Paper extends Vertex<Citation, Reference> {

	private String title;
	private int rating;

	/**
	 * Creates a new instance of Paper.
	 * 
	 * @param title
	 * @param rating
	 */
	public Paper(String title, int rating) {
		super.setInEdges(new HashMap<String, Citation>());
		super.setOutEdges(new HashMap<String, Reference>());

		this.setTitle(title);
		this.setRating(rating);
		this.setNumReferences(0);
		this.setNumCitations(0);
	}

	/**
	 * Adds a reference to the list of outward-facing edges stored in the
	 * superclass, and increments the count.
	 * 
	 * @param referee
	 *            the Paper to be set as referenced.
	 * @return true, this method always succeeds.
	 */
	public boolean createReference(Paper referee) {
		super.getOutEdges().put(referee.getTitle(), new Reference(referee));
		this.setNumReferences(this.getNumReferences() + 1);
		return true;
	}

	/**
	 * Adds a citation to the list of inward-facing edges stored in the
	 * superclass, and increments the count.
	 * 
	 * @param source
	 *            the Paper to be set as a citation.
	 * @return true, this method always succeeds.
	 */
	public boolean createCitation(Paper source) {
		super.getInEdges().put(source.getTitle(), new Citation(source));
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
	 * Returns a HashSet of all the Papers that cite this paper.
	 * 
	 * @return all the citations for this paper.
	 */
	public HashSet<Paper> getCitations() {
		HashSet<Paper> cits = new HashSet<Paper>();
		for (Citation c : super.getInEdges().values()) {
			cits.add(c.getSource());
		}
		return cits;
	}

	/**
	 * Returns a HashSet of all the papers that reference this paper.
	 * 
	 * @return all the references for this paper.
	 */
	public HashSet<Paper> getReferences() {
		HashSet<Paper> refs = new HashSet<Paper>();
		for (Reference r : super.getOutEdges().values()) {
			refs.add(r.getReferee());
		}
		return refs;
	}

	/**
	 * @return the number of references for this paper.
	 */
	public int getNumReferences() {
		return super.getOutDegree();
	}

	/**
	 * @param numReferences
	 *            the numReferences to set
	 */
	public void setNumReferences(int numReferences) {
		super.setOutDegree(numReferences);
	}

	/**
	 * @return the number of citations for this paper.
	 */
	public int getNumCitations() {
		return super.getInDegree();
	}

	/**
	 * @param numCitations
	 *            the numCitations to set
	 */
	public void setNumCitations(int numCitations) {
		super.setInDegree(numCitations);
	}

	@Override
	public String toString() {
		String rating = "";
		for (int i = 0; i < getRating(); i++) {
			rating += '*';
		}
		return "Title: " + getTitle()
				+ (getTitle().length() > 8 ? "\t" : "\t\t") + "Rating: "
				+ rating + "\t" + getNumReferences()
				+ " reference(s) | Cited by " + getNumCitations();
	}
}
