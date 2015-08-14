package com.bar.foo.tree.iterator;

import java.util.Stack;

import com.bar.foo.tree.ITree;

/**
 * This class provides a post-order tree iterator implementation. In other
 * words, it traverses each sub-tree before operating on the sub-tree's root
 * node.
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
 * The iteration order will be {@literal D-E-B-F-G-C-A}.
 * </p>
 * 
 * @author Jordan
 *
 * @param <T>
 *            The type of tree node.
 */
public class PostOrderTreeIterator<T extends ITree<T>> extends TreeIterator<T> {

	/**
	 * A queue used to maintain state information about the position of the
	 * iterator. If empty, there is no remaining tree nodes to visit.
	 */
	private final Stack<T> stack = new Stack<T>();

	/**
	 * A reference to the last node returned by {@link #next()}. This is null
	 * only at the beginning of the traversal.
	 */
	private T lastNodeVisited = null;

	/**
	 * The default constructor. Requires a root node. The root node and all
	 * descendants will be traversed in a post-order traversal (a node's
	 * children are visited first, then it is visited... the children follow the
	 * same procedure).
	 * 
	 * @param root
	 *            The root of the tree to iteratively traverse. If null, an
	 *            {@link IllegalArgumentException} will be thrown.
	 */
	public PostOrderTreeIterator(T root) {
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
	public T next() {

		// Set the default return value.
		T next = super.next();

		// Take a peek at the top node in the stack.
		T node = stack.peek();

		// If this is the first node visited or the last node visited was not
		// the right-most child, then we need to find the next sibling and add
		// all of its left-descendants to the stack.
		if (node.hasChildren() && lastNodeVisited != node
				.getChild(node.getNumberOfChildren() - 1)) {
			// Note that we must handle the case where no node has yet been
			// visited.
			if (lastNodeVisited == null) {
				node = node.getChild(0);
			}
			// Otherwise, we need to find the next sibling of the last node
			// visited.
			else {
				for (int i = 0; i < node.getNumberOfChildren(); i++) {
					T child = node.getChild(i);
					if (child == lastNodeVisited) {
						node = node.getChild(i + 1);
						break;
					}
				}
			}

			// Add all left-descendants.
			stack.push(node);
			while (node.getNumberOfChildren() > 0) {
				node = node.getChild(0);
				stack.push(node);
			}
		}

		// Pop the top node on the stack, as it is the next node to traverse.
		next = stack.pop();
		lastNodeVisited = next;

		return next;
	}
}
