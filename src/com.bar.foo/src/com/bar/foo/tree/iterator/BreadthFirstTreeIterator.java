package com.bar.foo.tree.iterator;

import java.util.LinkedList;
import java.util.Queue;

import com.bar.foo.tree.ITree;

public class BreadthFirstTreeIterator<T extends ITree<T>> extends
		TreeIterator<T> {

	/**
	 * A queue used to maintain state information about the position of the
	 * iterator. If empty, there is no remaining tree nodes to visit.
	 */
	private final Queue<T> queue = new LinkedList<T>();

	/**
	 * The default constructor. Requires a root node. The root node and all
	 * descendants will be traversed in breadth first order (all nodes at the
	 * same level will be traversed first).
	 * 
	 * @param root
	 *            The root of the tree to iteratively traverse. If null, an
	 *            {@link IllegalArgumentException} will be thrown.
	 */
	public BreadthFirstTreeIterator(T root) {
		super(root);

		// If the root is not null, we need to start the iteration with it by
		// adding it to the queue.
		if (root != null) {
			queue.add(root);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.TreeIterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.TreeIterator#next()
	 */
	@Override
	public T next() {

		// Set the default return value (null).
		T next = super.next();

		// If we have another tree node to iterate over, proceed.
		next = queue.poll();
		for (T child : next.getChildren()) {
			queue.add(child);
		}

		return next;
	}
}
