package researchSupport;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Stack;

public class DepthFirstSearch<V extends Vertex<?, ?>> {

	/** For storing search chains */
	private LinkedHashSet<Stack<V>> chains = new LinkedHashSet<Stack<V>>();
	private Stack<V> chain = new Stack<V>();

	public static final int SEARCH_IN_EDGES = 0;
	public static final int SEARCH_OUT_EDGES = 1;
	public static final int NO_SEARCH_LIMIT = -1;

	public DepthFirstSearch() {
		chain.clear();
		chains.clear();
	}

	public LinkedHashSet<Stack<V>> search(V origin, V front, int method,
			int limit, V goal) {
		this.dfs(origin, front, method, limit, goal);
		return chains;
	}

	public boolean dfs(V origin, V front, int method, int limit, V goal) {
		chain.add(front);

		HashSet<V> children;

		if (method == SEARCH_IN_EDGES) {
			children = getInEdges(front);
		} else {
			children = getOutEdges(front);
		}

		if (children.contains(goal)) {
			return false;
		}

		if (children.isEmpty() || limit-- == 0) {
			chains.add(chain);
			return true;
		} else {
			for (V v : children) {
				if (!chain.contains(front))
					chain.add(front);

				if (!dfs(origin, v, method, limit, goal))
					return false;

				if (children.size() > 1) {
					chain = chop(chain, front);
				}

				if (!chain.contains(origin))
					chain.add(origin);
			}
			return true;
		}
	}

	private HashSet<V> getInEdges(V v) {
		HashSet<V> in = new HashSet<V>();
		for (Edge e : v.getInEdges().values()) {
			in.add((V) e.getEndpoint());
		}
		return in;
	}

	private HashSet<V> getOutEdges(V v) {
		HashSet<V> in = new HashSet<V>();
		for (Edge e : v.getOutEdges().values()) {
			in.add((V) e.getEndpoint());
		}
		return in;
	}

	/**
	 * 
	 * @param stack
	 * @param front
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Stack<V> chop(Stack<V> stack, V front) {
		Stack<V> newStack = (Stack<V>) stack.clone();
		for (int i = 0; i < newStack.size(); i++) {
			if (newStack.peek().equals(front)) {
				return newStack;
			} else {
				newStack.pop();
			}
		}
		return newStack;
	}
}
