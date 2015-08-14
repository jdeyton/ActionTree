package com.bar.foo.tree.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.bar.foo.tree.ITree;

public abstract class TreeIterator<T extends ITree<T>> implements Iterator<T> {

	/**
	 * The root of the tree.
	 */
	protected final T root;

	private T current = null;

	/**
	 * The default constructor. Requires a root node. The root node and all
	 * descendants will be traversed.
	 * 
	 * @param root
	 *            The root of the tree to iteratively traverse. If null, an
	 *            {@link IllegalArgumentException} will be thrown.
	 */
	public TreeIterator(T root) {
		this.root = root;
		if (root == null) {
			throw new IllegalArgumentException("TreeIterator error: "
					+ "Cannot construct an iterator from a null tree!");
		}
	}

	/*
	 * Implements a method from Iterator.
	 */
	@Override
	public final T next() {
		// Throw a NoSuchElementException if there is no element left to
		// traverse.
		if (!hasNext()) {
			throw new NoSuchElementException("TreeIterator error: "
					+ "No elements remaining in iterative traversal.");
		}

		// Get the next item to be traversed from the sub-class.
		current = getNext();
		return current;
	}

	/**
	 * Gets the next element in the iteration.
	 * 
	 * @return The next element according to the iteration order.
	 */
	protected abstract T getNext();

	/*
	 * Implements a method from Iterator.
	 */
	@Override
	public final void remove() {
		// If next was called, the current node was set, and it has not already
		// been removed, we can try to remove it from the iteration and from the
		// tree.
		if (current != null) {
			removeFromIteration(current);
			T parent = current.getParent();
			// Note that we cannot actually "remove" the root node.
			if (parent != null) {
				parent.removeChild(current);
			}
			current = null;
		}
		// Otherwise, the current state of the iterator does not support the
		// remove operation.
		else {
			throw new IllegalStateException(getClass().getName() + " error: "
					+ "The next() method has not been called or remove() has "
					+ "already been called after the last call to the next() "
					+ "method.");
		}
		return;
	}

	/**
	 * Removes the sub-tree of the specified node from the iteration order.
	 * 
	 * @param subtree
	 *            The sub-tree to remove.
	 */
	protected abstract void removeFromIteration(T subtree);

}
