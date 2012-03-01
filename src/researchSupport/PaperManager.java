package researchSupport;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

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

	public HashSet<Stack<Paper>> getAllCitationChains(String title) {
		if (!this.papers.containsVertex(title)) {
			System.out.println("Paper " + title + " not found.");
			return null;
		} else {
			return this.getPaths(getPaper(title).getTitle(), Graph.NO_LIMIT);
		}
	}

	public void getAllReferenceChains(String title) {
		// TODO Auto-generated method stub
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
	public HashSet<Stack<Paper>> getPaths(String title, int limit) {

		papers.resetVisitedState();
		HashSet<Stack<Paper>> chains = new HashSet<Stack<Paper>>();

		chains.add(getPath(title, new Stack<Paper>(), limit));

		return chains;
	}

	/**
	 * 
	 * @param key
	 * @param limit
	 * @return
	 */
	private Stack<Paper> getPath(String key, Stack<Paper> chain, int limit) {
		
		Paper current = getPaper(key);
		Paper next = getUncheckedCitation(current);
		
		/*
		 * if current is visited return null
		 * 
		 * else if current has no neighbours, add to stack and return it
		 * 
		 * else if limit == 0 return null
		 * 
		 * else add current to stack, and
		 * getPath(current.getUnvisitedNeighbour().getTitle(), limit -1)
		 */

		if (current.isVisited()) {
			return null;
		} else if (next == null) {
			chain.add(current);
		} else if (limit != 0) {
			chain.add(current);
			current.setVisited(true);
			
			for (Citation c : current.getCitations()) {
				getPath(c.getSource().getTitle(), chain, --limit);
			}
		}

		return chain;
	}

	/**
	 * 
	 * @param key
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
