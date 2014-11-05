package com.bar.foo;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class TreeIterator<T extends ITree<T>> implements Iterator<T> {

	/**
	 * The root of the tree.
	 */
	protected final T root;

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
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public abstract boolean hasNext();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public T next() {

		// Set the default return value.
		T next = null;

		// Throw a NoSuchElementException if there is no element left to
		// traverse.
		if (!hasNext()) {
			throw new NoSuchElementException("TreeIterator error: "
					+ "No elements remaining in iterative traversal.");
		}

		return next;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		// TODO Implement this method to actually remove the iterator.

		throw new UnsupportedOperationException("TreeIterator error: "
				+ "Removing elements is currently not supported.");
	}

}
