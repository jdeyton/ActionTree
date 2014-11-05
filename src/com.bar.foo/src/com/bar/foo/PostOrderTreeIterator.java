package com.bar.foo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
	public T next() {

		// Set the default return value.
		T next = super.next();

		// Take a peek at the top node in the stack.
		T node = stack.peek();

		// If the last node visited was the right-most child of the top node, we
		// have finished traversing the top node. It is the next node that
		// should be visited.
		if (lastNodeVisited == node
				.getChild(node.getNumberOfChildren() - 1)) {
			next = stack.pop();
			lastNodeVisited = next;
		}
		// Otherwise, we are still traversing this node.
		else {
			// If the last node visited was not null, get the sibling to its
			// right.
			if (lastNodeVisited != null) {
				// TODO Figure out how to get around this...
//				node = lastNodeVisited.getNextSibling();
			}
			// Otherwise, we have just started traversing the tree. The current
			// node is the root node. Remove it from the stack because it might
			// be added below.
			else {
				node = stack.pop();
			}

			// Traverse all left-descendants of the node down to a leaf child.
			while (node.getNumberOfChildren() > 0) {
				stack.push(node);
				node = node.getChild(0);
			}

			// The next node to visit is the leaf child.
			next = node;
			lastNodeVisited = next;
		}

		return next;
	}
}
