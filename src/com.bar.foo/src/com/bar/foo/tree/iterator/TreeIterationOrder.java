package com.bar.foo.tree.iterator;

/**
 * An enumeration for basic tree iteration order. Note that an in-order
 * traversal is not declared here. This is because our tree model is not
 * restricted to binary trees, so the definition of mid-order is not inherently
 * clear.
 * 
 * @author djg
 *
 */
public enum TreeIterationOrder {
	/**
	 * Breadth first traversal passes each node from the top down
	 * level-by-level. That is, the root will be visited, then all of root's
	 * children will be visited, then all of root's grandchildren, and so on.
	 */
	BreadthFirst,
	/**
	 * Pre-order traversal visits a node before visiting its children. After a
	 * node and all of its children are visited, its next sibling is visited,
	 * then its children, and so on.
	 */
	PreOrder,
	/**
	 * Post-order traversal visits a node after visiting its children. After the
	 * node's children and the node are visited, its next sibling's children are
	 * visited, then the sibling is visited, and then the same procedure for the
	 * next siblings.
	 */
	PostOrder;
}
