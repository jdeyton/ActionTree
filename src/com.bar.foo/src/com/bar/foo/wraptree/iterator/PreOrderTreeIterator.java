package com.bar.foo.wraptree.iterator;

import java.util.Stack;

import com.bar.foo.wraptree.ITree;
import com.bar.foo.wraptree.INode;

public class PreOrderTreeIterator<T> extends TreeIterator<T> {

	/**
	 * A stack used to maintain state information about the position of the
	 * iterator. If empty, there is no remaining tree node to visit.
	 */
	private final Stack<INode<T>> stack = new Stack<INode<T>>();

	/**
	 * The default constructor. Requires a root node. The root node and all
	 * descendants will be traversed in a pre-order traversal (a node is
	 * visited, then its children are visited following the same procedure).
	 * 
	 * @param root
	 *            The root of the tree to iteratively traverse. If null, an
	 *            {@link IllegalArgumentException} will be thrown.
	 */
	public PreOrderTreeIterator(INode<T> root) {
		super(root);

		// If the root is not null, we need to start the iteration with it by
		// adding it to the stack.
		if (root != null) {
			stack.push(root);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.TreeIterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
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
		next = stack.pop();
		if (next.hasChildren()) {
			ITree<T> tree = (ITree<T>) next;
			for (int i = tree.getNumberOfChildren() - 1; i >= 0; i--) {
				stack.push(tree.getChild(i));
			}
		}

		return next;
	}
}
