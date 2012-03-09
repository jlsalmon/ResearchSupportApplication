package researchSupport;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

/**
 * PaperManager.java
 * 
 * @author Justin Lewis Salmon
 * @student_id 10000937
 * 
 *             Wrapper class for managing
 */
public class PaperManager {

	/** The main data structure of this application */
	private UndirectedGraph<Paper> papers;

	/**
	 * 
	 */
	public PaperManager() {
		this.papers = new UndirectedGraph<Paper>();
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
		DepthFirstSearch<Paper> search = new DepthFirstSearch<Paper>();

		if (!search.dfs(referee, referee, DepthFirstSearch.SEARCH_IN_EDGES,
				DepthFirstSearch.NO_SEARCH_LIMIT, referrer)) {
			System.out.println(referrerTitle + " -> " + refereeTitle
					+ " would create a cycle.");
			return false;
		}

		if (!search.dfs(referee, referee, DepthFirstSearch.SEARCH_OUT_EDGES,
				DepthFirstSearch.NO_SEARCH_LIMIT, referrer)) {
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
	public LinkedHashSet<Stack<Paper>> getAllCitationChains(String title) {
		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					DepthFirstSearch.SEARCH_IN_EDGES,
					DepthFirstSearch.NO_SEARCH_LIMIT);
		}
	}

	/**
	 * 
	 * @param title
	 * @return
	 */
	public LinkedHashSet<Stack<Paper>> getAllReferenceChains(String title) {
		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					DepthFirstSearch.SEARCH_OUT_EDGES,
					DepthFirstSearch.NO_SEARCH_LIMIT);
		}
	}

	/**
	 * 
	 * @param title
	 * @param limit
	 * @return
	 */
	public LinkedHashSet<Stack<Paper>> getNCitations(String title, int limit) {
		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					DepthFirstSearch.SEARCH_IN_EDGES, limit);
		}
	}

	/**
	 * 
	 * @param title
	 * @param limit
	 * @return
	 */
	public LinkedHashSet<Stack<Paper>> getNReferences(String title, int limit) {
		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					DepthFirstSearch.SEARCH_OUT_EDGES, limit);
		}
	}

	/**
	 * 
	 * @param title
	 * @param limit
	 * @return
	 */
	public LinkedHashSet<Stack<Paper>> getPaths(String title, int method,
			int limit) {

		Paper front = getPaper(title);
		DepthFirstSearch<Paper> search = new DepthFirstSearch<Paper>();

		return search.search(front, front, method, limit, null);
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
	// private boolean dfs(Paper origin, Paper front, int method, int limit,
	// Paper goal) {
	// chain.add(front);
	//
	// HashSet<Paper> children;
	//
	// if (method == SEARCH_CITATIONS) {
	// children = front.getCitations();
	// } else {
	// children = front.getReferences();
	// }
	//
	// if (children.contains(goal)) {
	// return false;
	// }
	//
	// if (children.isEmpty() || limit-- == 0) {
	// chains.add(chain);
	// return true;
	// } else {
	// for (Paper p : children) {
	// if (!chain.contains(front))
	// chain.add(front);
	//
	// if (!dfs(origin, p, method, limit, goal))
	// return false;
	//
	// if (children.size() > 1) {
	// chain = chop(chain, front);
	// }
	//
	// if (!chain.contains(origin))
	// chain.add(origin);
	// }
	// return true;
	// }
	// }
	//
	// /**
	// *
	// * @param stack
	// * @param front
	// * @return
	// */
	// @SuppressWarnings("unchecked")
	// private Stack<Paper> chop(Stack<Paper> stack, Paper front) {
	// Stack<Paper> newStack = (Stack<Paper>) stack.clone();
	// for (int i = 0; i < newStack.size(); i++) {
	// if (newStack.peek().equals(front)) {
	// return newStack;
	// } else {
	// newStack.pop();
	// }
	// }
	// return newStack;
	// }

	/**
	 * 
	 * @param title
	 * @return
	 */
	public boolean isExistingPaper(String title) {
		return (this.papers.containsVertex(title));
	}
}
