package researchSupport;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Stack;

/**
 * DepthFirstSearch.java
 * 
 * @author Justin Lewis Salmon
 * @student_id 10000937
 * 
 *             Generic class for traversing depth-first through a graph
 *             structure, where the edges of the graph are stored as a HashMap
 *             inside the vertices.
 * 
 *             Contains functionality for finding and returning all search
 *             chains down to the root, depth-limited search, and searching for
 *             a particular goal node. Search can be performed either upward
 *             (via inEdges) or downward (via outEdges).
 */
public class DepthFirstSearch<V extends Vertex<?, ?>> {

	/** For storing search chains */
	private LinkedHashSet<Stack<V>> chains = new LinkedHashSet<Stack<V>>();
	private Stack<V> chain = new Stack<V>();

	public static final int SEARCH_IN_EDGES = 0;
	public static final int SEARCH_OUT_EDGES = 1;
	public static final int NO_SEARCH_LIMIT = -1;

	/**
	 * Constructor. Sets the search chains to empty.
	 */
	public DepthFirstSearch() {
		chain.clear();
		chains.clear();
	}

	/**
	 * Wrapper call for dfs() which returns the set of stacks of vertices found.
	 * 
	 * @param origin
	 *            the vertex to begin searching from.
	 * @param front
	 *            the vertex to be searched next.
	 * @param method
	 *            the direction to traverse the tree.
	 * @param limit
	 *            the depth at which to stop searching.
	 * @param goal
	 *            the goal node to be found.
	 * @return the set of stacks of vertices searched through.
	 */
	public LinkedHashSet<Stack<V>> search(V origin, V front, int method,
			int limit, V goal) {
		this.dfs(origin, front, method, limit, goal);
		return chains;
	}

	/**
	 * Recursive depth-first search algorithm with optional depth-limitation,
	 * and optional goal node search.
	 * 
	 * @param origin
	 *            the vertex to begin searching from.
	 * @param front
	 *            the vertex to be searched next.
	 * @param method
	 *            the direction to traverse the tree.
	 * @param limit
	 *            the depth at which to stop searching.
	 * @param goal
	 *            the goal node to be found. If goal is null, the method will
	 *            effectively search the entire tree and return all paths to
	 *            leaves from the origin.
	 * @return the set of stacks of vertices searched through.
	 */
	public boolean dfs(V origin, V front, int method, int limit, V goal) {
		chain.add(front);

		HashSet<V> children;

		if (method == SEARCH_IN_EDGES) {
			children = getInEdges(front);
		} else {
			children = getOutEdges(front);
		}

		/** Goal vertex found? Return. */
		if (children.contains(goal)) {
			return false;
		}

		/**
		 * If no more children or limit is reached, add the current chain to the
		 * set and unwind.
		 */
		if (children.isEmpty() || limit-- == 0) {
			chains.add(chain);
			return true;
		} else {
			/** Recurse through each child. */
			for (V v : children) {
				if (!chain.contains(front))
					chain.add(front);

				if (!dfs(origin, v, method, limit, goal))
					return false;

				/**
				 * Chop the branch off the search chain up to this point.
				 */
				chain = chop(chain, front);

				if (!chain.contains(origin))
					chain.add(origin);
			}
			return true;
		}
	}

	/**
	 * Gets the set of inward-facing vertices adjacent to the given vertex.
	 * 
	 * @param v
	 *            the vertex to retreive adjacent edges for.
	 * @return the set of adjacent inward edges to this vertex.
	 */
	private HashSet<V> getInEdges(V v) {
		HashSet<V> in = new HashSet<V>();
		for (Edge<V> e : v.getInEdges().values()) {
			in.add(e.getEndpoint());
		}
		return in;
	}

	/**
	 * Gets the set of outward-facing vertices adjacent to the given vertex.
	 * 
	 * @param v
	 *            the vertex to retreive adjacent edges for.
	 * @return the set of adjacent outward edges to this vertex.
	 */
	private HashSet<V> getOutEdges(V v) {
		HashSet<V> in = new HashSet<V>();
		for (Edge<V> e : v.getOutEdges().values()) {
			in.add(e.getEndpoint());
		}
		return in;
	}

	/**
	 * Chops the head off a stack up to the index of the given vertex.
	 * 
	 * @param stack
	 *            the stack to mutilate.
	 * @param top
	 *            the vertex which should become the new top of the stack.
	 * @return the newly trimmed stack.
	 */
	@SuppressWarnings("unchecked")
	private Stack<V> chop(Stack<V> stack, V top) {
		Stack<V> newStack = (Stack<V>) stack.clone();
		for (int i = 0; i < newStack.size(); i++) {
			if (newStack.peek().equals(top)) {
				return newStack;
			} else {
				newStack.pop();
			}
		}
		return newStack;
	}
}
