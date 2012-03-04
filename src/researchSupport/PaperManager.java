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
		if (this.papers.containsVertex(paper.getTitle())) {
			System.out
					.println("Paper " + paper.getTitle() + " already exists.");
			return false;
		}
		return this.papers.addVertex(paper.getTitle(), paper);
	}

	/**
	 * 
	 * @param referrer
	 * @param referee
	 */
	public boolean makeReference(String referrer, String referee) {

		/*
		 * Prevent circular references:
		 * 
		 * If there is already a path from the referrer to the referee, a
		 * circular reference will be created.
		 */

		papers.resetVisitedState();
		if (!dfs(getPaper(referee), getPaper(referee), Graph.SEARCH_CITATIONS,
				Graph.NO_LIMIT, getPaper(referrer))
				|| !dfs(getPaper(referee), getPaper(referee),
						Graph.SEARCH_REFERENCES, Graph.NO_LIMIT,
						getPaper(referrer))) {
			System.out
					.println(referrer
							+ " -> "
							+ referee
							+ " would create a cycle. Circular references not allowed.");
			return false;
		}

		for (Reference r : getPaper(referrer).getReferences()) {
			if (r.getReferee().equals(getPaper(referee))) {
				System.out.println("Paper " + referrer + " already references "
						+ referee + ". Duplicate references not allowed.");
				return false;
			}
		}

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

	/**
	 * 
	 * @param title
	 * @return
	 */
	public HashSet<Stack<Paper>> getAllCitationChains(String title) {
		if (!this.papers.containsVertex(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					Graph.SEARCH_CITATIONS, Graph.NO_LIMIT);
		}
	}

	/**
	 * 
	 * @param title
	 * @return
	 */
	public HashSet<Stack<Paper>> getAllReferenceChains(String title) {
		if (!this.papers.containsVertex(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					Graph.SEARCH_REFERENCES, Graph.NO_LIMIT);
		}
	}

	/**
	 * 
	 * @param title
	 * @param limit
	 * @return
	 */
	public HashSet<Stack<Paper>> getNCitations(String title, int limit) {
		if (!this.papers.containsVertex(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					Graph.SEARCH_CITATIONS, limit);
		}
	}

	/**
	 * 
	 * @param title
	 * @param limit
	 * @return
	 */
	public HashSet<Stack<Paper>> getNReferences(String title, int limit) {
		if (!this.papers.containsVertex(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					Graph.SEARCH_REFERENCES, limit);
		}
	}

	// TODO refactor these!!
	HashSet<Stack<Paper>> chains = new HashSet<Stack<Paper>>();
	Stack<Paper> chain = new Stack<Paper>();

	/**
	 * 
	 * @param title
	 * @param limit
	 * @return
	 */
	public HashSet<Stack<Paper>> getPaths(String title, int method, int limit) {
		papers.resetVisitedState();
		Paper front = getPaper(title);
		chain.clear();
		chains.clear();

		dfs(front, front, method, limit, null);
		return chains;
	}

	/**
	 * TODO refactor this into DepthFirstSearch class
	 * 
	 * @param origin
	 * @param front
	 * @param method
	 * @param limit
	 * @return
	 */
	private boolean dfs(Paper origin, Paper front, int method, int limit,
			Paper goal) {
		front.setVisited(true);
		chain.add(front);

		Stack<Paper> children;

		if (method == Graph.SEARCH_CITATIONS) {
			children = getUncheckedCitations(front);
		} else {
			children = getUncheckedReferences(front);
		}

		if (children.contains(goal)) {
			return false;
		}

		if (children.isEmpty() || limit-- == 0) {
			chains.add(chain);
			return true;
		} else {
			for (int i = 0; i < children.size(); i++) {
				if (!chain.contains(front))
					chain.add(front);

				if (!dfs(origin, children.get(i), method, limit, goal))
					return false;

				chain = new Stack<Paper>();

				if (!chain.contains(origin))
					chain.add(origin);
			}
			return true;
		}
	}

	/**
	 * TODO refactor this to getUnvisitedChildren() inside Graph
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
	 * TODO refactor this to getUnvisitedChildren() inside Graph
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
