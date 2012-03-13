package com.jlewis.researchsupport;

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
 *             Wrapper class for managing the underlying data, which is stored
 *             in an UndirectedGraph of type Paper. Handles adding new papers,
 *             adding new references/citations (while maintaining the acyclic
 *             nature of the graph) and initiates all graph traversals.
 */
public class PaperManager {

	/** The main data structure of this application */
	private UndirectedGraph<Paper> papers;

	/**
	 * Constructor. Instantiates a new empty graph structure.
	 */
	public PaperManager() {
		this.papers = new UndirectedGraph<Paper>();
	}

	/**
	 * Adds a paper to the map of vertices.
	 * 
	 * @param paper
	 *            the paper to be added.
	 * @return true if the add was successful, false if the paper already
	 *         exists.
	 */
	public boolean addPaper(Paper paper) {
		if (isExistingPaper(paper.getTitle())) {
			System.out.println("Paper " + paper.getTitle()
					+ " already exists.");
			return false;
		}
		return this.papers.addVertex(paper.getTitle(), paper);
	}

	/**
	 * Creates a reference/citation relationship between two papers, only if the
	 * two papers exist, there is not already a connection between the two
	 * papers, and that the addition of the reference would not create a cycle.
	 * 
	 * If there is already a path from the referrer to the referee, a circular
	 * reference would be created, which is not allowed (it is nonsensical).
	 * 
	 * @param referrer
	 *            the paper making the reference.
	 * @param referee
	 *            the paper being referenced.
	 * @return true if the connection was successfully created, false otherwise.
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

		/** Search for cycles, upwards and downwards */
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
	 * Gets a paper from the map of vertices.
	 * 
	 * @param title
	 *            the title of the paper to retrieve.
	 * @return the paper object with the specified title.
	 */
	public Paper getPaper(String title) {
		for (Paper p : this.papers.getAllVertices().values()) {
			if (p.getTitle().equalsIgnoreCase(title)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Gets the set of papers which directly reference the paper with the given
	 * title, i.e. all the paper's citations.
	 * 
	 * @param title
	 *            the title of the paper to retrieve citations for.
	 * @return the set of citations, or an empty set if no citations found or
	 *         the paper doesn't exist.
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
	 * Gets the set of papers which are directly cited by the paper with the
	 * given title, i.e. all the paper's references.
	 * 
	 * @param title
	 *            the title of the paper to retrieve references for.
	 * @return the set of references, or an empty set if no references found or
	 *         the paper doesn't exist.
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
	 * Gets the set of all traversal routes from the given paper to a leaf
	 * vertex, following citation links, i.e. traversing up the inward-facing
	 * edges of the graph.
	 * 
	 * @param title
	 *            the title of the paper to begin traversal from.
	 * @return the set of stacks of paper chains representing a citation chain.
	 */
	public LinkedHashSet<Stack<Paper>> getAllCitationChains(String title) {
		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else if (getPaper(title).getCitations().isEmpty()) {
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					DepthFirstSearch.SEARCH_IN_EDGES,
					DepthFirstSearch.NO_SEARCH_LIMIT);
		}
	}

	/**
	 * Gets the set of all traversal routes from the given paper to a leaf
	 * vertex, following reference links, i.e. traversing up the outward-facing
	 * edges of the graph.
	 * 
	 * @param title
	 *            the title of the paper to begin traversal from.
	 * @return the set of stacks of paper chains representing a reference chain.
	 */
	public LinkedHashSet<Stack<Paper>> getAllReferenceChains(String title) {
		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else if (getPaper(title).getReferences().isEmpty()) {
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					DepthFirstSearch.SEARCH_OUT_EDGES,
					DepthFirstSearch.NO_SEARCH_LIMIT);
		}
	}

	/**
	 * Gets the set of all traversal routes from the given paper to a leaf
	 * vertex, following citation links, i.e. traversing up the inward-facing
	 * edges of the graph, up to and including the depth specified.
	 * 
	 * @param title
	 *            the title of the paper to begin traversal from.
	 * @param limit
	 *            the depth at which to stop searching.
	 * @return the set of stacks of paper chains representing a citation chain.
	 */
	public LinkedHashSet<Stack<Paper>> getNCitations(String title, int limit) {
		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else if (getPaper(title).getCitations().isEmpty()) {
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					DepthFirstSearch.SEARCH_IN_EDGES, limit);
		}
	}

	/**
	 * Gets the set of all traversal routes from the given paper to a leaf
	 * vertex, following reference links, i.e. traversing up the outward-facing
	 * edges of the graph, up to and including the depth specified.
	 * 
	 * @param title
	 *            the title of the paper to begin traversal from.
	 * @param limit
	 *            the depth at which to stop searching.
	 * @return the set of stacks of paper chains representing a reference chain.
	 */
	public LinkedHashSet<Stack<Paper>> getNReferences(String title, int limit) {
		if (!isExistingPaper(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else if (getPaper(title).getReferences().isEmpty()) {
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(),
					DepthFirstSearch.SEARCH_OUT_EDGES, limit);
		}
	}

	/**
	 * Initiates a depth-first traversal from the given paper in the given
	 * direction, up to the given depth.
	 * 
	 * @param title
	 *            the title of the paper to begin traversal from.
	 * @param limit
	 *            the search depth limit.
	 * @return the set of paths found.
	 */
	public LinkedHashSet<Stack<Paper>> getPaths(String title, int method,
			int limit) {

		Paper front = getPaper(title);
		DepthFirstSearch<Paper> search = new DepthFirstSearch<Paper>();
		return search.search(front, front, method, limit, null);
	}

	/**
	 * Checks if a paper exists with the given title.
	 * 
	 * @param title
	 *            the title of the paper to search for.
	 * @return true if the paper exists, false otherwise.
	 */
	public boolean isExistingPaper(String title) {
		return this.papers.containsVertex(title);
	}
}
