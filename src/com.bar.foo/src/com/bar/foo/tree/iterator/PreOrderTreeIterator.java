package com.bar.foo.tree.iterator;

import java.util.Stack;

import com.bar.foo.tree.ITree;

/**
 * This class provides a pre-order tree iterator implementation. In other words,
 * it operates on a node before traversing its sub-tree.
 * <p>
 * For instance, for a tree with the following nodes (assume insertion order
 * goes from top to bottom):
 * </p>
 * 
 * <pre>
 *   A
 *   |--B
 *   |  |--D
 *   |  \--E
 *   |
 *   \--C
 *      |--F
 *      \--G
 * </pre>
 * <p>
 * The iteration order will be {@literal A-B-D-E-C-F-G}.
 * </p>
 * 
 * @author Jordan
 *
 * @param <T>
 *            The type of tree node.
 */
public class PreOrderTreeIterator<T extends ITree<T>> extends TreeIterator<T> {

	/**
	 * A stack used to maintain state information about the position of the
	 * iterator. If empty, there is no remaining tree node to visit.
	 */
	private final Stack<T> stack = new Stack<T>();

	/**
	 * The default constructor. Requires a root node. The root node and all
	 * descendants will be traversed in a pre-order traversal (a node is
	 * visited, then its children are visited following the same procedure).
	 * 
	 * @param root
	 *            The root of the tree to iteratively traverse. If null, an
	 *            {@link IllegalArgumentException} will be thrown.
	 */
	public PreOrderTreeIterator(T root) {
		super(root);

		// If the root is not null, we need to start the iteration with it by
		// adding it to the stack.
		if (root != null) {
			stack.push(root);
		}
	}

	/*
	 * Overrides a method from TreeIterator.
	 */
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	/*
	 * Overrides a method from TreeIterator.
	 */
	@Override
	protected T getNext() {
		// If we have another tree node to iterate over, proceed.
		T next = stack.pop();
		for (int i = next.getNumberOfChildren() - 1; i >= 0; i--) {
			stack.push(next.getChild(i));
		}
		return next;
	}

	/*
	 * Overrides a method from TreeIterator.
	 */
	@Override
	protected void removeFromIteration(T subtree) {
		// The last node visited simply pushed all of its children to the stack.
		// Remove all of them from the top of the stack.
		for (int i = 0; i < subtree.getNumberOfChildren(); i++) {
			stack.pop();
		}
	}
}
