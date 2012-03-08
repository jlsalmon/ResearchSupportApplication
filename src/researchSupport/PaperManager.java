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
    
    public static final int SEARCH_CITATIONS = 0;
    public static final int SEARCH_REFERENCES = 1;
    public static final int NO_LIMIT = -1;

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
            System.out.println("Paper " + paper.getTitle() + " already exists.");
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
            System.out.println("Paper " + referee + " not found.");
            return false;
        } else if (!isExistingPaper(referrerTitle)) {
            System.out.println("Paper " + referrer + " not found.");
            return false;
        }

        /** Search for cycles */
        papers.resetVisitedState();
        if (!dfs(referee, referee, SEARCH_CITATIONS, NO_LIMIT,
                referrer)
                || !dfs(referee, referee, SEARCH_REFERENCES,
                NO_LIMIT, referrer)) {
            System.out.println(referrerTitle + " -> " + refereeTitle
                    + " would create a cycle.");
            return false;
        }

        /** Check for duplicate references */
        for (Reference r : referrer.getReferences()) {
            if (r.getReferee().equals(referee)) {
                System.out.println("Paper " + referrerTitle
                        + " already references " + refereeTitle
                        + ". Duplicate references not allowed.");
                return false;
            }
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

        if (!isExistingPaper(title)) {
            System.out.println("Paper " + title + " not found.");
            return references;
        }

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
        if (!isExistingPaper(title)) {
            System.out.println("Paper " + title + " not found.");
            return null;
        } else {
            return this.getPaths(getPaper(title).getTitle(),
                    SEARCH_CITATIONS, NO_LIMIT);
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
                    SEARCH_REFERENCES, NO_LIMIT);
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
                    SEARCH_CITATIONS, limit);
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
                    SEARCH_REFERENCES, limit);
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

        if (method == SEARCH_CITATIONS) {
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
                if (!chain.contains(front)) {
                    chain.add(front);
                }

                if (!dfs(origin, children.get(i), method, limit, goal)) {
                    return false;
                }

                chain = new Stack<Paper>();

                if (!chain.contains(origin)) {
                    chain.add(origin);
                }
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

    /**
     * 
     * @param title
     * @return
     */
    public boolean isExistingPaper(String title) {
        return (this.papers.containsVertex(title));
    }
}
