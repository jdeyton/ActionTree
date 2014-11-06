package com.bar.foo.wraptree.iterator;

import java.util.LinkedList;
import java.util.Queue;

import com.bar.foo.wraptree.INode;
import com.bar.foo.wraptree.ITree;

public class BreadthFirstTreeIterator<T> extends TreeIterator<T> {

	/**
	 * A queue used to maintain state information about the position of the
	 * iterator. If empty, there is no remaining tree nodes to visit.
	 */
	private final Queue<INode<T>> queue = new LinkedList<INode<T>>();

	/**
	 * The default constructor. Requires a root node. The root node and all
	 * descendants will be traversed in breadth first order (all nodes at the
	 * same level will be traversed first).
	 * 
	 * @param root
	 *            The root of the tree to iteratively traverse. If null, an
	 *            {@link IllegalArgumentException} will be thrown.
	 */
	public BreadthFirstTreeIterator(INode<T> root) {
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
	public INode<T> next() {

		// Set the default return value (null).
		INode<T> next = super.next();

		// If we have another tree node to iterate over, proceed.
		next = queue.poll();
		if (next.hasChildren()) {
			ITree<T> tree = (ITree<T>) next;
			for (INode<T> child : tree.getChildren()) {
				queue.add(child);
			}
		}

		return next;
	}
}
