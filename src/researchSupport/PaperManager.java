package researchSupport;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author jussy
 * 
 */
public class PaperManager {

	DirectedGraph<Paper, Reference> papers;

	public PaperManager() {
		this.papers = new DirectedGraph<Paper, Reference>();
	}

	/**
	 * 
	 * @param paper
	 * @return
	 */
	public boolean addPaper(Paper paper) {
		return this.papers.addVertex(paper.getTitle(), paper);
	}

	/**
	 * 
	 * @param referee
	 * @param referrer
	 */
	public boolean makeReference(String referee, String referrer) {
		if (!this.papers.containsVertex(referee)) {
			System.out.println("Paper " + referee + " not found.");
		} else if (!this.papers.containsVertex(referrer)) {
			System.out.println("Paper " + referrer + " not found.");
		} else {
			return getPaper(referee).createReference(getPaper(referrer));
		}
		return false;

	}

	/**
	 * 
	 * @param title
	 * @return
	 */
	public Paper getPaper(String title) {
		for (String s : this.papers.getAllVertices().keySet()) {
			if (s.equalsIgnoreCase(title)) {
				return this.papers.getAllVertices().get(s);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param title
	 * @return
	 */
	public Set<Paper> getDirectCitations(String title) {
		return getPaper(title).getCitations();
	}

	/**
	 * 
	 * @param title
	 * @return
	 */
	public Set<Paper> getDirectReferences(String title) {
		Set<Paper> references = new HashSet<Paper>();
		for (Reference r : getPaper(title).getReferences()) {
			references.add(r.getReferee());
		}
		return references;
	}

	public void getNCitations(String title, int n) {
		// TODO Auto-generated method stub

	}

	public void getNReferences(String title, int n) {
		// TODO Auto-generated method stub

	}

	public void getAllCitationChains(String title) {
		// TODO Auto-generated method stub

	}

	public void getAllReferenceChains(String title) {
		// TODO Auto-generated method stub

	}
}
