package researchSupport;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 
 * @author jussy
 * 
 */
public class PaperManager {

	Graph<Paper, Reference> papers;

	/**
	 * 
	 */
	public PaperManager() {
		this.papers = new Graph<Paper, Reference>();
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
	 * @param referrer
	 * @param referee
	 */
	public boolean makeReference(String referrer, String referee) {
		if (!this.papers.containsVertex(referee)) {
			System.out.println("Paper " + referee + " not found.");
		} else if (!this.papers.containsVertex(referrer)) {
			System.out.println("Paper " + referrer + " not found.");
		} else {
			return getPaper(referrer).createReference(getPaper(referee))
					&& getPaper(referee).createCitation(getPaper(referrer));
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
		Set<Paper> citations = new HashSet<Paper>();
		for (Citation c : getPaper(title).getCitations()) {
			citations.add(c.getSource());
		}
		return citations;
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

	public Queue getAllCitationChains(String title) {
		if (!this.papers.containsVertex(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					Graph.SEARCH_CITATIONS, Graph.NO_LIMIT);
		}
	}

	public Queue getAllReferenceChains(String title) {
		if (!this.papers.containsVertex(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					Graph.SEARCH_REFERENCES, Graph.NO_LIMIT);
		}
	}

	public void getNCitations(String title, int n) {
		// TODO Auto-generated method stub

	}

	public void getNReferences(String title, int n) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param title
	 * @param limit
	 * @return
	 */
	public Queue getPaths(String title, int method, int limit) {

		papers.resetVisitedState();

		Paper front = getPaper(title);
		front.setVisited(true);
		Paper next;

		Queue traversalOrder = new Queue();
		Queue vertexQueue = new Queue();

		traversalOrder.joinBack(front.getTitle());
		vertexQueue.joinBack(front);

		while (!vertexQueue.isEmpty()) {
			front = (Paper) vertexQueue.leaveFront();

			if (method == Graph.SEARCH_CITATIONS) {
				next = getUncheckedCitation(front);
			} else {
				next = getUncheckedReference(front);
			}

			while (next != null) {
				next.setVisited(true);
				traversalOrder.joinBack(next.getTitle());
				vertexQueue.joinBack(next);

				if (method == Graph.SEARCH_CITATIONS) {
					next = getUncheckedCitation(front);
				} else {
					next = getUncheckedReference(front);
				}
			}
		}

		return traversalOrder;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	private Paper getUncheckedReference(Paper p) {
		for (Reference r : p.getReferences()) {
			if (r.getReferee().isVisited()) {
				return null;
			} else {
				return r.getReferee();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	private Paper getUncheckedCitation(Paper p) {
		for (Citation c : p.getCitations()) {
			if (c.getSource().isVisited()) {
				return null;
			} else {
				return c.getSource();
			}
		}
		return null;
	}
}
