package com.bar.foo.tree.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.bar.foo.tree.BasicTree;
import com.bar.foo.tree.ITree;

/**
 * This class tests the {@link ITree} implementation provided by
 * {@link BasicTree}.
 * 
 * @author Jordan
 *
 */
public class TreeTester {

	/**
	 * Checks that the child addition method works properly.
	 * 
	 * @see BasicTree#addChild(BasicTree)
	 */
	@Test
	public void checkAddChild() {

		// TODO Check that the same child can't be added twice.

		// Add a child and check the connections between child and parent.
		DummyTree parent = new DummyTree();
		DummyTree newParent = new DummyTree();
		DummyTree child = new DummyTree();

		// Add the child to the first parent and check the connections.
		parent.addChild(child);
		checkConnected(parent, child, 0);
		checkSevered(newParent, child);

		// Add the child to the second parent and check the connections.
		newParent.addChild(child);
		checkSevered(parent, child);
		checkConnected(newParent, child, 0);

		return;
	}

	/**
	 * Checks that the child removal methods work properly.
	 * 
	 * @see BasicTree#removeChild(BasicTree)
	 * @see BasicTree#removeChild(int)
	 */
	@Test
	public void checkRemoveChild() {

		// TODO Check that the same child can't be removed twice.

		// Add a child, remove it, then make sure all connections are severed.
		DummyTree parent = new DummyTree();
		DummyTree child = new DummyTree();

		// Check removeChild(int)
		parent.addChild(child);
		parent.removeChild(0);
		checkSevered(parent, child);

		// Check removeChild(T)
		parent.addChild(child);
		parent.removeChild(child);
		checkSevered(parent, child);

		return;
	}

	/**
	 * Checks that the breadth-first traversal of the tree works properly.
	 * 
	 * @see BasicTree#iterator()
	 * @see BasicTree#iterator(com.bar.foo.tree.iterator.TreeIterationOrder)
	 */
	@Test
	public void checkBreadthFirstIterator() {
		// TODO
		return;
	}

	/**
	 * Checks that the pre-order traversal of the tree works properly.
	 * 
	 * @see BasicTree#iterator(com.bar.foo.tree.iterator.TreeIterationOrder)
	 */
	@Test
	public void checkPreOrderIterator() {
		// TODO
		return;
	}

	/**
	 * Checks that the post-order traversal of the tree works properly.
	 * 
	 * @see BasicTree#iterator(com.bar.foo.tree.iterator.TreeIterationOrder)
	 */
	@Test
	public void checkPostOrderIterator() {
		// TODO
		return;
	}

	/**
	 * Checks consistency and correctness for equality and hash code methods.
	 * Note: This test does not currently test the transitive property.
	 * 
	 * @see BasicTree#equals(Object)
	 * @see BasicTree#equals(com.bar.foo.tree.ITree, boolean)
	 * @see BasicTree#hashCode()
	 * @see BasicTree#hashCode(boolean)
	 */
	@Test
	public void checkEquality() {

		DummyTree object = new DummyTree();
		DummyTree equalObject = new DummyTree();
		DummyTree unequalObject = null;

		// ---- Check bad arguments to equals. ---- //
		// Check the default equals method.
		assertFalse(object.equals(unequalObject));
		assertFalse(object.equals("some string"));
		// Check the tree equals method.
		assertFalse(object.equals(unequalObject, false));
		assertFalse(object.equals(unequalObject, true));
		// ---------------------------------------- //

		// ---- Check the base case: two single nodes. ---- //
		equalObject = new DummyTree();
		// Check the default equals method in both directions.
		// Reflexive check.
		assertTrue(object.equals(object));
		// Symmetric check.
		assertTrue(object.equals(equalObject));
		assertTrue(equalObject.equals(object));
		// Check the tree equals method in both directions.
		// Reflexive check.
		assertTrue(object.equals(object, false));
		assertTrue(object.equals(object, true));
		// Symmetric check.
		assertTrue(object.equals(equalObject, false));
		assertTrue(object.equals(equalObject, true));
		assertTrue(equalObject.equals(object, false));
		assertTrue(equalObject.equals(object, true));

		// Check their hash codes!
		assertEquals(object.hashCode(), equalObject.hashCode());
		assertEquals(object.hashCode(false), equalObject.hashCode(false));
		assertEquals(object.hashCode(true), equalObject.hashCode(true));
		// ------------------------------------------------ //

		// ---- Try changing the properties. ---- //
		unequalObject = new DummyTree();
		object.property = "derp";
		equalObject.property = "derp";

		// Check the default equals method in both directions.
		// Reflexive check.
		assertTrue(object.equals(object));
		// Symmetric check.
		assertTrue(object.equals(equalObject));
		assertTrue(equalObject.equals(object));
		// Check the tree equals method in both directions.
		// Reflexive check.
		assertTrue(object.equals(object, false));
		assertTrue(object.equals(object, true));
		// Symmetric check.
		assertTrue(object.equals(equalObject, false));
		assertTrue(object.equals(equalObject, true));
		assertTrue(equalObject.equals(object, false));
		assertTrue(equalObject.equals(object, true));

		// Check their hash codes!
		assertEquals(object.hashCode(), equalObject.hashCode());
		assertEquals(object.hashCode(false), equalObject.hashCode(false));
		assertEquals(object.hashCode(true), equalObject.hashCode(true));

		// Check the default equals against the unequal object.
		assertFalse(object.equals(unequalObject));
		assertFalse(unequalObject.equals(object));
		assertFalse(equalObject.equals(unequalObject));
		assertFalse(unequalObject.equals(equalObject));

		// Check the tree equals against the unequal object.
		assertFalse(object.equals(unequalObject, false));
		assertFalse(object.equals(unequalObject, true));
		assertFalse(unequalObject.equals(object, false));
		assertFalse(unequalObject.equals(object, true));
		assertFalse(equalObject.equals(unequalObject, false));
		assertFalse(equalObject.equals(unequalObject, true));
		assertFalse(unequalObject.equals(equalObject, false));
		assertFalse(unequalObject.equals(equalObject, true));

		// Check their hash codes!
		assertFalse(object.hashCode() == unequalObject.hashCode());
		assertFalse(object.hashCode(false) == unequalObject.hashCode(false));
		assertFalse(object.hashCode(true) == unequalObject.hashCode(true));
		assertFalse(equalObject.hashCode() == unequalObject.hashCode());
		assertFalse(equalObject.hashCode(false) == unequalObject
				.hashCode(false));
		assertFalse(equalObject.hashCode(true) == unequalObject.hashCode(true));
		// -------------------------------------- //

		// ---- Try a more complicated tree. ---- //
		DummyTree tree1;
		DummyTree tree2;
		// Create a tree with 4 elements as follows:
		// Breadth first order: ABCD, A has children B and C, C has child D.
		object = new DummyTree();
		object.property = "A";
		tree1 = new DummyTree();
		tree1.property = "B";
		object.addChild(tree1);
		tree1 = new DummyTree();
		tree1.property = "C";
		object.addChild(tree1);
		tree2 = new DummyTree();
		tree2.property = "D";
		tree1.addChild(tree2);

		// Duplicate the tree for the equals tree.
		equalObject = new DummyTree();
		equalObject.property = "A";
		tree1 = new DummyTree();
		tree1.property = "B";
		equalObject.addChild(tree1);
		tree1 = new DummyTree();
		tree1.property = "C";
		equalObject.addChild(tree1);
		tree2 = new DummyTree();
		tree2.property = "D";
		tree1.addChild(tree2);

		// For an unequal tree, create a different tree with the same
		// breadth-first order (just add all nodes to A).
		unequalObject = new DummyTree();
		unequalObject.property = "A";
		tree1 = new DummyTree();
		tree1.property = "B";
		unequalObject.addChild(tree1);
		tree1 = new DummyTree();
		tree1.property = "C";
		unequalObject.addChild(tree1);
		tree2 = new DummyTree();
		tree2.property = "D";
		unequalObject.addChild(tree2); // Different!

		// Check the default equals method in both directions.
		// Reflexive check.
		assertTrue(object.equals(object));
		// Symmetric check.
		assertTrue(object.equals(equalObject));
		assertTrue(equalObject.equals(object));
		// Check the tree equals method in both directions.
		// Reflexive check.
		assertTrue(object.equals(object, false));
		assertTrue(object.equals(object, true));
		// Symmetric check.
		assertTrue(object.equals(equalObject, false));
		assertTrue(object.equals(equalObject, true));
		assertTrue(equalObject.equals(object, false));
		assertTrue(equalObject.equals(object, true));

		// Check their hash codes!
		assertEquals(object.hashCode(), equalObject.hashCode());
		assertEquals(object.hashCode(false), equalObject.hashCode(false));
		assertEquals(object.hashCode(true), equalObject.hashCode(true));

		// Check the default equals against the unequal object.
		// Note: This will return true because the tree's structure is not
		// tested!
		assertTrue(object.equals(unequalObject));
		assertTrue(unequalObject.equals(object));
		assertTrue(equalObject.equals(unequalObject));
		assertTrue(unequalObject.equals(equalObject));

		// Check the tree equals against the unequal object.
		// Note: When passing in fullTree as false, the tree's structure is not
		// tested!
		assertTrue(object.equals(unequalObject, false));
		assertFalse(object.equals(unequalObject, true));
		assertTrue(unequalObject.equals(object, false));
		assertFalse(unequalObject.equals(object, true));
		assertTrue(equalObject.equals(unequalObject, false));
		assertFalse(equalObject.equals(unequalObject, true));
		assertTrue(unequalObject.equals(equalObject, false));
		assertFalse(unequalObject.equals(equalObject, true));

		// Check their hash codes! Note: The hash codes will be equal for the
		// default hashCode() and the tree hashCode() with fullTree set to
		// false, since only the node itself, and not its subtree, is hashed.
		assertTrue(object.hashCode() == unequalObject.hashCode());
		assertTrue(object.hashCode(false) == unequalObject.hashCode(false));
		assertFalse(object.hashCode(true) == unequalObject.hashCode(true));
		assertTrue(equalObject.hashCode() == unequalObject.hashCode());
		assertTrue(equalObject.hashCode(false) == unequalObject.hashCode(false));
		assertFalse(equalObject.hashCode(true) == unequalObject.hashCode(true));
		// -------------------------------------- //

		// ---- Try comparing against another type of BasicTree. ---- //
		FakeDummyTree fakeObject = new FakeDummyTree();
		assertFalse(object.equals(fakeObject));
		// ---------------------------------------------------------- //

		return;
	}

	/**
	 * Checks that the copy constructors correctly copy the contents of the tree
	 * node and (if applicable) its subtrees.
	 */
	@Test
	public void checkCopyConstructor() {
		// TODO
		return;
	}

	/**
	 * Checks that the child is properly connected to the parent at the
	 * specified index.
	 * 
	 * @param parent
	 *            The parent tree. Assumed not to be null.
	 * @param child
	 *            The child tree. Assumed not to be null.
	 * @param index
	 *            The index. If greater than or equal to 0, this method will
	 *            check that the child has the specified index in the parent's
	 *            list of children. Otherwise, it just tries to find the child
	 *            in the parent's list of children. If the index is greater than
	 *            the number of children, this method causes a fail.
	 */
	private void checkConnected(DummyTree parent, DummyTree child, int index) {
		// Make sure the child's parent is the same parent.
		assertTrue(parent == child.getParent());

		// Get the list of children and check its size vs the reported size from
		// getNumberOfChildren().
		List<DummyTree> children = parent.getChildren();
		assertEquals(children.size(), parent.getNumberOfChildren());

		// Make sure the index is valid.
		assertTrue(index < children.size());

		if (index >= 0) {
			assertSame(child, children.get(index));
			assertSame(child, parent.getChild(index));
		} else {
			// Find the child in the List returned via getChildren().
			boolean found = false;
			for (int i = 0; !found && i < children.size(); i++) {
				found = (child == children.get(i));
			}
			assertTrue(found);
			// Find the child by requesting each child via getChild(int).
			found = false;
			for (int i = 0; i < parent.getNumberOfChildren(); i++) {
				found = (child == parent.getChild(i));
			}
			assertTrue(found);
		}

		return;
	}

	/**
	 * Checks that the child is not connected to the parent.
	 * 
	 * @param parent
	 *            The parent tree. Assumed not to be null.
	 * @param child
	 *            The child tree. Assumed not to be null.
	 */
	private void checkSevered(DummyTree parent, DummyTree child) {
		// Make sure the child's parent is not the same parent.
		assertTrue(parent != child.getParent());

		// Get the list of children and check its size vs the reported size from
		// getNumberOfChildren().
		List<DummyTree> children = parent.getChildren();
		assertEquals(children.size(), parent.getNumberOfChildren());

		// Find the child in the List returned via getChildren().
		boolean found = false;
		for (int i = 0; !found && i < children.size(); i++) {
			found = (child == children.get(i));
		}
		assertFalse(found);
		// Find the child by requesting each child via getChild(int).
		found = false;
		for (int i = 0; i < parent.getNumberOfChildren(); i++) {
			found = (child == parent.getChild(i));
		}
		assertFalse(found);

		return;
	}

	/**
	 * A fake {@link DummyTree} with no properties.
	 * 
	 * @author Jordan
	 *
	 */
	private class FakeDummyTree extends BasicTree<FakeDummyTree> {
		@Override
		public FakeDummyTree getValue() {
			return this;
		}
	}

	/**
	 * A simple extension of BasicTree (which is abstract) that adds one string
	 * property to the mix.
	 * 
	 * @author Jordan
	 *
	 */
	private class DummyTree extends BasicTree<DummyTree> {

		/**
		 * A simple string property.
		 */
		public String property = null;

		/**
		 * Required.
		 */
		@Override
		public DummyTree getValue() {
			return this;
		}

		/**
		 * Overrides the node equals method to factor in the {@link #property}.
		 */
		@Override
		public boolean equals(Object object) {
			boolean equals = super.equals(object);
			if (equals && this != object && object instanceof DummyTree) {
				DummyTree tree = (DummyTree) object;
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
	}
}
