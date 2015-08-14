package com.bar.foo.tree.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bar.foo.tree.BasicTree;
import com.bar.foo.tree.iterator.TreeIterationOrder;

/**
 * A simple extension of BasicTree (which cannot be instantiated directly due to
 * being abstract and due to its generic class arguments) that adds one string
 * property to the mix.
 * 
 * @author Jordan
 *
 */
public class BasicTestTree extends BasicTree<BasicTestTree> {

	/**
	 * A simple string property.
	 */
	public String property = null;

	/**
	 * The map of expected tree iteration orders. This is only initialized for
	 * the root node of the test tree.
	 */
	private Map<TreeIterationOrder, List<BasicTestTree>> expectedOrders;

	/**
	 * Required.
	 */
	@Override
	public BasicTestTree getValue() {
		return this;
	}

	/**
	 * Overrides the node equals method to factor in the {@link #property}.
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals && this != object && object instanceof BasicTestTree) {
			BasicTestTree tree = (BasicTestTree) object;
			// Compare the string property.
			equals &= (property == null ? tree.property == null
					: property.equals(tree.property));
		}
		return equals;
	}

	/**
	 * Overrides the node hash method to factor in the {@link #property}.
	 */
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = hash * 31 + (property == null ? 0 : property.hashCode());
		return hash;
	}

	/**
	 * Creates a tree for testing purposes. This is intended for use with the
	 * iterator tests. The structure of the tree is shown below (assume
	 * insertion order is top-to-bottom):
	 * 
	 * <pre>
	 * A1
	 * |--B1
	 * |  \-C1
	 * |
	 * \--B2
	 *    |--C2
	 *    |--C3
	 *    |  |--D1
	 *    |  \--D2
	 *    |
	 *    \--C4
	 *       |--D3
	 *       |--D4
	 *       \--D5
	 * </pre>
	 * 
	 * @return A tree with 4 layers and several children per layer.
	 */
	public static BasicTestTree createTestTree() {

		BasicTestTree a1;
		BasicTestTree b1, b2;
		BasicTestTree c1, c2, c3, c4;
		BasicTestTree d1, d2, d3, d4, d5;

		// The root node is labeled A for top level and 1 for first "A".
		a1 = new BasicTestTree();
		a1.property = "A1";

		// The first sub-tree is B1 with a child C1.
		b1 = new BasicTestTree();
		b1.property = "B1";
		a1.addChild(b1);
		c1 = new BasicTestTree();
		c1.property = "C1";
		b1.addChild(c1);

		// The second sub-tree is B2. It has 3 C children and 5 D grandchildren.
		b2 = new BasicTestTree();
		b2.property = "B2";
		a1.addChild(b2);

		// C2 is a child of B2 but has no children.
		c2 = new BasicTestTree();
		c2.property = "C2";
		b2.addChild(c2);

		// C3 is a child of B2 and has children D1 and D2.
		c3 = new BasicTestTree();
		c3.property = "C3";
		b2.addChild(c3);
		// C3's children...
		d1 = new BasicTestTree();
		d1.property = "D1";
		c3.addChild(d1);
		d2 = new BasicTestTree();
		d2.property = "D2";
		c3.addChild(d2);

		// C4 is a child of B2 and has children D3, D4, and D5.
		c4 = new BasicTestTree();
		c4.property = "C4";
		b2.addChild(c4);
		// C4's children...
		d3 = new BasicTestTree();
		d3.property = "D3";
		c4.addChild(d3);
		d4 = new BasicTestTree();
		d4.property = "D4";
		c4.addChild(d4);
		d5 = new BasicTestTree();
		d5.property = "D5";
		c4.addChild(d5);

		// Set up the map of expected tree iteration orders.
		a1.expectedOrders = new HashMap<TreeIterationOrder, List<BasicTestTree>>();

		// pre-order: A1 B1 C1 B2 C2 C3 D1 D2 C4 D3 D4 D5
		a1.expectedOrders.put(TreeIterationOrder.PreOrder,
				createList(a1, b1, c1, b2, c2, c3, d1, d2, c4, d3, d4, d5));

		// post-order: C1 B1 C2 D1 D2 C3 D3 D4 D5 C4 B2 A1
		a1.expectedOrders.put(TreeIterationOrder.PostOrder,
				createList(c1, b1, c2, d1, d2, c3, d3, d4, d5, c4, b2, a1));

		// breadth-first: A1 B1 B2 C1 C2 C3 C4 D1 D2 D3 D4 D5
		a1.expectedOrders.put(TreeIterationOrder.BreadthFirst,
				createList(a1, b1, b2, c1, c2, c3, c4, d1, d2, d3, d4, d5));

		return a1;
	}

	/**
	 * Creates a list from the nodes specified in the arguments. No error
	 * handling is performed.
	 */
	private static List<BasicTestTree> createList(BasicTestTree... nodes) {
		List<BasicTestTree> list = new ArrayList<BasicTestTree>(nodes.length);
		for (BasicTestTree node : nodes) {
			list.add(node);
		}
		return list;
	}

	/**
	 * Gets the expected ordering of nodes based on the desired tree iteration
	 * order.
	 * 
	 * @param order
	 *            The desired order of nodes.
	 * @return The expected order of nodes.
	 */
	public List<BasicTestTree> getExpectedOrder(TreeIterationOrder order) {
		return (expectedOrders != null ? expectedOrders.get(order) : null);
	}
}