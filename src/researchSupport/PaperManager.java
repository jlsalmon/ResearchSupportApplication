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

	Graph<Paper> papers;

	/**
	 * 
	 */
	public PaperManager() {
		this.papers = new Graph<Paper>();
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

	public HashSet<Stack<Paper>> getAllCitationChains(String title) {
		if (!this.papers.containsVertex(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					Graph.SEARCH_CITATIONS, Graph.NO_LIMIT);
		}
	}

	public HashSet<Stack<Paper>> getAllReferenceChains(String title) {
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
	HashSet<Stack<Paper>> chains = new HashSet<Stack<Paper>>();
	Stack<Paper> chain = new Stack<Paper>();

	public HashSet<Stack<Paper>> getPaths(String title, int method, int limit) {

		papers.resetVisitedState();

		Paper front = getPaper(title);

		dfs(front, method, limit);

		return chains;

		// front.setVisited(true);
		// Paper next;
		//
		// Queue traversalOrder = new Queue();
		// Queue vertexQueue = new Queue();
		//
		// traversalOrder.joinBack(front.getTitle());
		// vertexQueue.joinBack(front);
		//
		// while (!vertexQueue.isEmpty()) {
		// front = (Paper) vertexQueue.leaveFront();
		//
		// if (method == Graph.SEARCH_CITATIONS) {
		// next = getUncheckedCitation(front);
		// } else {
		// next = getUncheckedReference(front);
		// }
		//
		// while (next != null) {
		// next.setVisited(true);
		// traversalOrder.joinBack(next.getTitle());
		// vertexQueue.joinBack(next);
		//
		// if (method == Graph.SEARCH_CITATIONS) {
		// next = getUncheckedCitation(front);
		// } else {
		// next = getUncheckedReference(front);
		// }
		// }
		// }
		//
		// return traversalOrder;
	}

	private boolean dfs(Paper front, int method, int limit) {
		front.setVisited(true);
		chain.add(front);

		Stack<Paper> children;

		if (method == Graph.SEARCH_CITATIONS) {
			children = getUncheckedCitations(front);
		} else {
			children = getUncheckedReferences(front);
		}

		if (children.isEmpty() || limit == 0) {
			chains.add(chain);
			return true;
		} else {
			for (Paper p : children) {
				if (dfs(children.pop(), method, --limit)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	private Stack<Paper> getUncheckedReferences(Paper p) {
		Stack<Paper> refs = new Stack<Paper>();
		for (Reference r : p.getReferences()) {
			if (!r.getReferee().isVisited()) {
				refs.push(r.getReferee());
			}
		}
		return refs;
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	private Stack<Paper> getUncheckedCitations(Paper p) {
		Stack<Paper> cits = new Stack<Paper>();
		for (Citation c : p.getCitations()) {
			if (!c.getSource().isVisited()) {
				cits.push(c.getSource());
			}
		}
		return cits;
	}
}
