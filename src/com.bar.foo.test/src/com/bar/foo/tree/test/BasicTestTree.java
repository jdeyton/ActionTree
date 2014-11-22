package com.bar.foo.tree.test;

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
			equals &= (property == null ? tree.property == null : property
					.equals(tree.property));
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
	 * iterator tests.
	 * 
	 * @return A tree with 4 layers and several children per layer.
	 */
	public static BasicTestTree createTestTree() {
		BasicTestTree root;

		/*-
		 * Here's how the tree breaks down:
		 * 
		 * A1
		 * |-B1
		 * | \-C1
		 * \-B2
		 *   |-C2
		 *   |-C3
		 *   | |-D1
		 *   | \-D2
		 *   \-C4
		 *     |-D3
		 *     |-D4
		 *     \-D5
		 */

		// The root node is labeled A for top level and 1 for first "A".
		root = new BasicTestTree();
		root.property = "A1";

		// There are 3 additional levels of the tree (B, C, and D).
		BasicTestTree b, c, d;

		// The first sub-tree is B1 with a child C1.
		b = new BasicTestTree();
		b.property = "B1";
		root.addChild(b);
		c = new BasicTestTree();
		c.property = "C1";
		b.addChild(c);

		// The second sub-tree is B2. It has 3 C children and 5 D grandchildren.
		b = new BasicTestTree();
		b.property = "B2";
		root.addChild(b);

		// C2 is a child of B2 but has no children.
		c = new BasicTestTree();
		c.property = "C2";
		b.addChild(c);

		// C3 is a child of B2 and has children D1 and D2.
		c = new BasicTestTree();
		c.property = "C3";
		b.addChild(c);
		// C3's children...
		d = new BasicTestTree();
		d.property = "D1";
		c.addChild(d);
		d = new BasicTestTree();
		d.property = "D2";
		c.addChild(d);

		// C4 is a child of B2 and has children D3, D4, and D5.
		c = new BasicTestTree();
		c.property = "C4";
		b.addChild(c);
		// C4's children...
		d = new BasicTestTree();
		d.property = "D3";
		c.addChild(d);
		d = new BasicTestTree();
		d.property = "D4";
		c.addChild(d);
		d = new BasicTestTree();
		d.property = "D5";
		c.addChild(d);

		return root;
	}

	/**
	 * Gets a string containing the {@link #property} values of the tree based
	 * on the provided iteration order. These values are hard-coded based on the
	 * structure of the tree created by {@link #createTestTree()}.
	 * 
	 * @param order
	 *            The order of iteration.
	 * @return The order of property strings from the tree. Each property string
	 *         will be separated by a space, and there will be a space at the
	 *         end.
	 */
	public static String getPropertyIterationOrder(TreeIterationOrder order) {
		String properties = null;
		switch (order) {
		case BreadthFirst:
			properties = "A1 B1 B2 C1 C2 C3 C4 D1 D2 D3 D4 D5 ";
			break;
		case PreOrder:
			properties = "A1 B1 C1 B2 C2 C3 D1 D2 C4 D3 D4 D5 ";
			break;
		case PostOrder:
			properties = "C1 B1 C2 D1 D2 C3 D3 D4 D5 C4 B2 A1 ";
			break;
		}
		return properties;
	}
}