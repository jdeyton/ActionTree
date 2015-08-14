package com.bar.foo.tree.iterator.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.bar.foo.tree.iterator.PostOrderTreeIterator;
import com.bar.foo.tree.iterator.TreeIterationOrder;
import com.bar.foo.tree.test.BasicTestTree;

/**
 * This class tests the post-order tree iterator implementation. To do this, it
 * uses the {@link BasicTestTree}, which uses the default
 * {@link PostOrderTreeIterator} that is being tested.
 * 
 * @author Jordan
 *
 */
public class PostOrderTreeIteratorTester {

	/**
	 * The {@link TreeIterationOrder} for this test class.
	 */
	private static final TreeIterationOrder ORDER = TreeIterationOrder.PostOrder;

	/**
	 * Checks the iteration order for the base case (one node) and a more
	 * complex case (several nodes) from the {@link BasicTestTree}.
	 */
	@Test
	public void checkOrder() {

		BasicTestTree root;
		Iterator<BasicTestTree> iterator;
		Iterator<BasicTestTree> expectedIterator;

		// ---- Check the base case order. ---- //
		// Create the test tree. In this case, it's just a single node.
		root = new BasicTestTree();

		// There should be only one node: the root.
		iterator = root.iterator(ORDER);
		assertTrue(iterator.hasNext());
		assertSame(root, iterator.next());
		assertFalse(iterator.hasNext());
		// ------------------------------------ //

		// ---- Check a more complicated tree's order. ---- //
		// Create the test tree. It has several layers of nodes.
		root = BasicTestTree.createTestTree();

		// Get an iterator for the known iteration order list.
		expectedIterator = root.getExpectedOrder(ORDER).iterator();

		// Get an iterator from the tree. This iterator will be compared against
		// the known good iterator above.
		iterator = root.iterator(ORDER);

		// Compare the tested iteration order against the expected order.
		while (expectedIterator.hasNext()) {
			assertTrue(iterator.hasNext());
			assertSame(expectedIterator.next(), iterator.next());
		}
		assertFalse(iterator.hasNext());
		// ------------------------------------------------ //

		return;
	}

	/**
	 * Checks that the remove operation either removes the iterator element at
	 * the specified location or throws an {@link UnsupportedOperationException}
	 * if iterator removal is not supported.
	 */
	@Test
	public void checkRemove() {

		BasicTestTree root;
		BasicTestTree node;
		Iterator<BasicTestTree> iterator;

		// ---- Check the base case remove. ---- //
		// Create the test tree. In this case, it's just a single node.
		root = new BasicTestTree();

		// There should be only one node: the root.
		iterator = root.iterator(ORDER);

		// Make sure the remove operation throws an IllegalStateException
		// because next() has not been called.
		try {
			iterator.remove();
			fail(getClass().getName() + " error: "
					+ "IllegalStateException was not thrown when remove() "
					+ "called before next().");
		} catch (IllegalStateException e) {
			// The exception was thrown as expected. Do nothing.
		}

		assertTrue(iterator.hasNext());
		assertSame(root, iterator.next());

		// We should be able to "remove" the root.
		try {
			iterator.remove();
		} catch (IllegalStateException e) {
			fail(getClass().getName() + " error: "
					+ "IllegalStateException was thrown when remove() "
					+ "called after next().");
		}

		// There should be no more elements in the iteration.
		assertFalse(iterator.hasNext());
		// ------------------------------------- //

		// ---- Check a more complicated tree node removal. ---- //
		// Create the test tree. It has several layers of nodes.
		root = BasicTestTree.createTestTree();

		// Get an iterator for the known iteration order list.
		iterator = root.iterator(ORDER);

		// Skip the B1 sub-tree.
		assertEquals("C1", iterator.next().property);
		assertEquals("B1", iterator.next().property);
		// Enter the B2 sub-tree.
		// Skip the C2 sub-tree.
		assertEquals("C2", iterator.next().property);
		// Skip the contents of the C3 sub-tree.
		assertEquals("D1", iterator.next().property);
		assertEquals("D2", iterator.next().property);
		// Remove sub-tree C3.
		node = iterator.next();
		assertEquals("C3", node.property);
		iterator.remove();
		// Skip the C4 sub-tree.
		assertEquals("D3", iterator.next().property);
		assertEquals("D4", iterator.next().property);
		assertEquals("D5", iterator.next().property);
		assertEquals("C4", iterator.next().property);
		// Exit the B2 sub-tree.
		assertEquals("B2", iterator.next().property);
		// Skip the root node.
		assertEquals("A1", iterator.next().property);
		// There are no more nodes to iterate.
		assertFalse(iterator.hasNext());

		// The entire sub-tree C3 should still be intact.
		assertEquals(2, node.getNumberOfChildren());
		assertEquals("D1", node.getChild(0).property);
		assertEquals("D2", node.getChild(1).property);

		// The entire sub-tree C3 should have been removed from the root tree.
		assertNull(node.getParent());
		// Get its parent node B2.
		node = root.getChild(1);
		assertEquals("B2", node.property);
		// Check that node C3 is not among its children.
		assertEquals(2, node.getNumberOfChildren());
		assertEquals("C2", node.getChild(0).property);
		assertEquals("C4", node.getChild(1).property);
		// ----------------------------------------------------- //

		/*-
		 * Check an unusual case where the deleted node is the leftmost child of
		 * a large sub-tree.
		 * 
		 * This tree looks like:
		 * 
		 * A1
		 * |--B1
		 * |--B2
		 * |  \--C1
		 * |     |--D1
		 * |     \--D2
		 * \--B3
		 * 
		 * We will remove node C1. Under the hood, the "previous" node visited 
		 * will them become B1 and the next node visited B2.
		 */
		BasicTestTree child;
		BasicTestTree subtreeToRemove;

		// Add a root node A1.
		root = new BasicTestTree();
		root.property = "A1";
		// Add a node B1 to the root node.
		node = new BasicTestTree();
		node.property = "B1";
		root.addChild(node);
		// Add a node B2 to the root node.
		node = new BasicTestTree();
		node.property = "B2";
		root.addChild(node);
		// Add a node C1 to node B2.
		child = new BasicTestTree();
		child.property = "C1";
		subtreeToRemove = child; // This is the tree we will remove.
		node.addChild(child);
		// Add two nodes D1 and D2 to node C1.
		node = child;
		child = new BasicTestTree();
		child.property = "D1";
		node.addChild(child);
		child = new BasicTestTree();
		child.property = "D2";
		node.addChild(child);
		// Add a node B3 to the root node.
		node = new BasicTestTree();
		node.property = "B3";
		root.addChild(node);

		// Skip all the way to the node we want to remove (C1).
		iterator = root.iterator(ORDER);
		assertEquals("B1", iterator.next().property);
		assertEquals("D1", iterator.next().property);
		assertEquals("D2", iterator.next().property);
		// Get the node C2.
		node = iterator.next();
		assertEquals("C1", node.property);
		iterator.remove();
		// Skip the remaining nodes in the tree.
		assertEquals("B2", iterator.next().property);
		assertEquals("B3", iterator.next().property);
		assertEquals("A1", iterator.next().property);
		// There are no more nodes to iterate.
		assertFalse(iterator.hasNext());

		// The entire sub-tree C1 should still be intact.
		assertEquals(2, node.getNumberOfChildren());
		assertEquals("D1", node.getChild(0).property);
		assertEquals("D2", node.getChild(1).property);

		// The entire sub-tree C3 should have been removed from the root tree.
		assertNull(node.getParent());
		// Get its parent node B2.
		node = root.getChild(1);
		assertEquals("B2", node.property);
		// Check that node C3 is not among its children.
		assertEquals(0, node.getNumberOfChildren());

		/*-
		 * Check an unusual case where the deleted node is a leftmost subtree of
		 * the entire tree.
		 * 
		 * This tree looks like:
		 * 
		 * A1
		 * |--B2
		 * |  \--C1
		 * |     |--D1
		 * |     \--D2
		 * \--B3
		 * 
		 * We will remove node C1. Under the hood, the "previous" node visited 
		 * will them become B1 and the next node visited B2.
		 */

		// Remove node B1 and B3.
		root.removeChild(0);
		// Add node C1 back to node B2.
		root.getChild(0).addChild(subtreeToRemove);

		// Skip all the way to the node we want to remove (C1).
		iterator = root.iterator(ORDER);
		assertEquals("D1", iterator.next().property);
		assertEquals("D2", iterator.next().property);
		// Get the node C2.
		node = iterator.next();
		assertEquals("C1", node.property);
		iterator.remove();
		// Skip the remaining nodes in the tree.
		assertEquals("B2", iterator.next().property);
		assertEquals("B3", iterator.next().property);
		assertEquals("A1", iterator.next().property);
		// There are no more nodes to iterate.
		assertFalse(iterator.hasNext());

		// The entire sub-tree C1 should still be intact.
		assertEquals(2, node.getNumberOfChildren());
		assertEquals("D1", node.getChild(0).property);
		assertEquals("D2", node.getChild(1).property);

		// The entire sub-tree C3 should have been removed from the root tree.
		assertNull(node.getParent());
		// Get its parent node B2.
		node = root.getChild(0);
		assertEquals("B2", node.property);
		// Check that node C3 is not among its children.
		assertEquals(0, node.getNumberOfChildren());

		return;
	}

	/**
	 * Checks that exceptions are thrown at the appropriate times, including the
	 * following:
	 * <ul>
	 * <li>A {@link NoSuchElementException} is thrown when
	 * {@link Iterator#next()} is called with no remaining elements.</li>
	 * </ul>
	 */
	@Test
	public void checkExceptions() {

		BasicTestTree root;
		Iterator<BasicTestTree> iterator;

		// ---- Check exception when calling next() with no elements. ---- //
		try {
			root = new BasicTestTree();
			iterator = root.iterator(ORDER);
			// hasNext() should return true.
			assertTrue(iterator.hasNext());
			// The return value for the first call to next() should be the tree.
			assertSame(root, iterator.next());
			// hasNext() should return false.
			assertFalse(iterator.hasNext());

			// The below call should throw an exception.
			iterator.next();
			fail(ORDER + " failure: "
					+ "When no elements remain, next() should throw a NoSuchElementException.");
		} catch (NoSuchElementException e) {

		}
		// --------------------------------------------------------------- //

		return;
	}

}
