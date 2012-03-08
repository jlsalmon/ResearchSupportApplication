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

	private Graph<Paper> papers;
	HashSet<Stack<Paper>> chains = new HashSet<Stack<Paper>>();
	Stack<Paper> chain = new Stack<Paper>();

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
		if (isExistingPaper(paper.getTitle())) {
			System.out.println("\nPaper " + paper.getTitle()
					+ " already exists.");
			return false;
		}
		return this.papers.addVertex(paper.getTitle(), paper);
	}

	/**
	 * If there is already a path from the referrer to the referee, a circular
	 * reference will be created.
	 * 
	 * @param referrer
	 * @param referee
	 */
	public boolean makeReference(String referrerTitle, String refereeTitle) {
		Paper referrer = getPaper(referrerTitle);
		Paper referee = getPaper(refereeTitle);

		/** Check papers exist first */
		if (!isExistingPaper(refereeTitle)) {
			System.out.println("Paper " + refereeTitle + " not found.");
			return false;
		} else if (!isExistingPaper(referrerTitle)) {
			System.out.println("Paper " + referrerTitle + " not found.");
			return false;
		}

		/** Check for duplicate references */
		for (Paper p : referrer.getReferences()) {
			if (p.equals(referee)) {
				System.out.println("Duplicate reference " + referrerTitle
						+ " -> " + refereeTitle + ".");
				return false;
			}
		}

		/** Search for cycles */
		papers.resetVisitedState();
		if (!dfs(referee, referee, Graph.SEARCH_CITATIONS, Graph.NO_LIMIT,
				referrer)
				|| !dfs(referee, referee, Graph.SEARCH_REFERENCES,
						Graph.NO_LIMIT, referrer)) {
			System.out.println(referrerTitle + " -> " + refereeTitle
					+ " would create a cycle.");
			return false;
		}

		/** OK to make reference! */
		return referrer.createReference(referee)
				&& referee.createCitation(referrer);
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

		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return citations;
		}

		for (Paper p : getPaper(title).getCitations()) {
			citations.add(p);
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

		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return references;
		}

		for (Paper p : getPaper(title).getReferences()) {
			references.add(p);
		}
		return references;
	}

	/**
	 * 
	 * @param title
	 * @return
	 */
	public HashSet<Stack<Paper>> getAllCitationChains(String title) {
		if (!isExistingPaper(title)) {
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
		if (!isExistingPaper(title)) {
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
		if (!isExistingPaper(title)) {
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
		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					Graph.SEARCH_REFERENCES, limit);
		}
	}

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

		HashSet<Paper> children;

		if (method == Graph.SEARCH_CITATIONS) {
			children = front.getCitations();
		} else {
			children = front.getReferences();
		}

		if (children.contains(goal)) {
			return false;
		}

		if (children.isEmpty() || limit-- == 0) {
			chains.add(chain);
			return true;
		} else {
			for (Paper p : children) {
				if (!chain.contains(front))
					chain.add(front);

				if (!dfs(origin, p, method, limit, goal))
					return false;

				chain = new Stack<Paper>();

				if (!chain.contains(origin))
					chain.add(origin);
			}
			return true;
		}
	}

	/**
	 * 
	 * @param title
	 * @return
	 */
	public boolean isExistingPaper(String title) {
		return (this.papers.containsVertex(title));
	}
}
