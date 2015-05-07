package com.bar.foo.tree.iterator.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.bar.foo.tree.iterator.BreadthFirstTreeIterator;
import com.bar.foo.tree.iterator.PostOrderTreeIterator;
import com.bar.foo.tree.iterator.PreOrderTreeIterator;
import com.bar.foo.tree.iterator.TreeIterationOrder;
import com.bar.foo.tree.test.BasicTestTree;

/**
 * This test class is used to test the tree iteration classes directly. It
 * checks for potential exceptions when creating and using them as well as their
 * iteration orders.
 * 
 * @author Jordan
 *
 */
public class TreeIteratorTester {

	/**
	 * This checks the proper exceptions when using a breadth-first iterator and
	 * checks the order.
	 */
	@Test
	public void checkBreadthFirstIterator() {

		// Set the order used by this test.
		final TreeIterationOrder order = TreeIterationOrder.BreadthFirst;
		final String orderString = order.toString();
		final IteratorCreator iteratorCreator = new IteratorCreator() {
			@Override
			public Iterator<BasicTestTree> createIterator(BasicTestTree root) {
				return new BreadthFirstTreeIterator<BasicTestTree>(root);
			}

			@Override
			public Iterator<BasicTestTree> createExpectedIterator(
					BasicTestTree root) {
				return root.getBreadthFirstNodes().iterator();
			}
		};

		BasicTestTree root;
		Iterator<BasicTestTree> iterator;
		Iterator<BasicTestTree> expectedIterator;

		// ---- Check the base case order. ---- //
		// Create the test tree. In this case, it's just a single node.
		root = new BasicTestTree();

		// There should be only one node: the root.
		iterator = iteratorCreator.createIterator(root);
		assertTrue(iterator.hasNext());
		assertSame(root, iterator.next());
		// ------------------------------------ //

		// ---- Check a more complicated tree's order. ---- //
		// Create the test tree. It has several layers of nodes.
		root = BasicTestTree.createTestTree();

		// Get an iterator for the known iteration order list.
		expectedIterator = iteratorCreator.createExpectedIterator(root);

		// Get an iterator from the tree. This iterator will be compared against
		// the known good iterator above.
		iterator = iteratorCreator.createIterator(root);

		// Compare the tested iteration order against the expected order.
		while (expectedIterator.hasNext()) {
			assertTrue(iterator.hasNext());
			assertSame(expectedIterator.next(), iterator.next());
		}
		// ------------------------------------------------ //

		// ---- Check exception when initialized with a null tree. ---- //
		try {
			root = null;

			// The below call should throw an exception.
			iterator = iteratorCreator.createIterator(root);
			fail(orderString + " failure: "
					+ "Null root node should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		// ------------------------------------------------------------ //

		// ---- Check exception when calling next() with no elements. ---- //
		try {
			root = new BasicTestTree();
			iterator = iteratorCreator.createIterator(root);
			// hasNext() should return true.
			assertTrue(iterator.hasNext());
			// The return value for the first call to next() should be the tree.
			assertSame(root, iterator.next());
			// hasNext() should return false.
			assertFalse(iterator.hasNext());

			// The below call should throw an exception.
			iterator.next();
			fail(orderString
					+ " failure: "
					+ "When no elements remain, next() should throw a NoSuchElementException.");
		} catch (NoSuchElementException e) {

		}
		// --------------------------------------------------------------- //

		// ---- Check exception when calling remove(). ---- //
		// Remove is currently not supported.
		try {
			root = new BasicTestTree();
			iterator = iteratorCreator.createIterator(root);
			iterator.next();

			// The below call should throw an exception.
			iterator.remove();
			fail(orderString + " failure: "
					+ "remove() should throw an UnsupportedOperationException.");
		} catch (UnsupportedOperationException e) {

		}
		// ------------------------------------------------ //

		return;
	}

	/**
	 * This checks the proper exceptions when using a pre-order iterator and
	 * checks the order.
	 */
	@Test
	public void checkPreOrderIterator() {

		// Set the order used by this test.
		final TreeIterationOrder order = TreeIterationOrder.PreOrder;
		final String orderString = order.toString();
		final IteratorCreator iteratorCreator = new IteratorCreator() {
			@Override
			public Iterator<BasicTestTree> createIterator(BasicTestTree root) {
				return new PreOrderTreeIterator<BasicTestTree>(root);
			}

			@Override
			public Iterator<BasicTestTree> createExpectedIterator(
					BasicTestTree root) {
				return root.getPreOrderNodes().iterator();
			}
		};

		BasicTestTree root;
		Iterator<BasicTestTree> iterator;
		Iterator<BasicTestTree> expectedIterator;

		// ---- Check the base case order. ---- //
		// Create the test tree. In this case, it's just a single node.
		root = new BasicTestTree();

		// There should be only one node: the root.
		iterator = iteratorCreator.createIterator(root);
		assertTrue(iterator.hasNext());
		assertSame(root, iterator.next());
		// ------------------------------------ //

		// ---- Check a more complicated tree's order. ---- //
		// Create the test tree. It has several layers of nodes.
		root = BasicTestTree.createTestTree();

		// Get an iterator for the known iteration order list.
		expectedIterator = iteratorCreator.createExpectedIterator(root);

		// Get an iterator from the tree. This iterator will be compared against
		// the known good iterator above.
		iterator = iteratorCreator.createIterator(root);

		// Compare the tested iteration order against the expected order.
		while (expectedIterator.hasNext()) {
			assertTrue(iterator.hasNext());
			assertSame(expectedIterator.next(), iterator.next());
		}
		// ------------------------------------------------ //

		// ---- Check exception when initialized with a null tree. ---- //
		try {
			root = null;

			// The below call should throw an exception.
			iterator = iteratorCreator.createIterator(root);
			fail(orderString + " failure: "
					+ "Null root node should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		// ------------------------------------------------------------ //

		// ---- Check exception when calling next() with no elements. ---- //
		try {
			root = new BasicTestTree();
			iterator = iteratorCreator.createIterator(root);
			// hasNext() should return true.
			assertTrue(iterator.hasNext());
			// The return value for the first call to next() should be the tree.
			assertSame(root, iterator.next());
			// hasNext() should return false.
			assertFalse(iterator.hasNext());

			// The below call should throw an exception.
			iterator.next();
			fail(orderString
					+ " failure: "
					+ "When no elements remain, next() should throw a NoSuchElementException.");
		} catch (NoSuchElementException e) {

		}
		// --------------------------------------------------------------- //

		// ---- Check exception when calling remove(). ---- //
		// Remove is currently not supported.
		try {
			root = new BasicTestTree();
			iterator = iteratorCreator.createIterator(root);
			iterator.next();

			// The below call should throw an exception.
			iterator.remove();
			fail(orderString + " failure: "
					+ "remove() should throw an UnsupportedOperationException.");
		} catch (UnsupportedOperationException e) {

		}
		// ------------------------------------------------ //

		return;
	}

	/**
	 * This checks the proper exceptions when using a post-order iterator and
	 * checks the order.
	 */
	@Test
	public void checkPostOrderIterator() {

		// Set the order used by this test.
		final TreeIterationOrder order = TreeIterationOrder.PostOrder;
		final String orderString = order.toString();
		final IteratorCreator iteratorCreator = new IteratorCreator() {
			@Override
			public Iterator<BasicTestTree> createIterator(BasicTestTree root) {
				return new PostOrderTreeIterator<BasicTestTree>(root);
			}

			@Override
			public Iterator<BasicTestTree> createExpectedIterator(
					BasicTestTree root) {
				return root.getPostOrderNodes().iterator();
			}
		};

		BasicTestTree root;
		Iterator<BasicTestTree> iterator;
		Iterator<BasicTestTree> expectedIterator;

		// ---- Check the base case order. ---- //
		// Create the test tree. In this case, it's just a single node.
		root = new BasicTestTree();

		// There should be only one node: the root.
		iterator = iteratorCreator.createIterator(root);
		assertTrue(iterator.hasNext());
		assertSame(root, iterator.next());
		// ------------------------------------ //

		// ---- Check a more complicated tree's order. ---- //
		// Create the test tree. It has several layers of nodes.
		root = BasicTestTree.createTestTree();

		// Get an iterator for the known iteration order list.
		expectedIterator = iteratorCreator.createExpectedIterator(root);

		// Get an iterator from the tree. This iterator will be compared against
		// the known good iterator above.
		iterator = iteratorCreator.createIterator(root);

		// Compare the tested iteration order against the expected order.
		while (expectedIterator.hasNext()) {
			assertTrue(iterator.hasNext());
			assertSame(expectedIterator.next(), iterator.next());
		}
		// ------------------------------------------------ //

		// ---- Check exception when initialized with a null tree. ---- //
		try {
			root = null;

			// The below call should throw an exception.
			iterator = iteratorCreator.createIterator(root);
			fail(orderString + " failure: "
					+ "Null root node should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {

		}
		// ------------------------------------------------------------ //

		// ---- Check exception when calling next() with no elements. ---- //
		try {
			root = new BasicTestTree();
			iterator = iteratorCreator.createIterator(root);
			// hasNext() should return true.
			assertTrue(iterator.hasNext());
			// The return value for the first call to next() should be the tree.
			assertSame(root, iterator.next());
			// hasNext() should return false.
			assertFalse(iterator.hasNext());

			// The below call should throw an exception.
			iterator.next();
			fail(orderString
					+ " failure: "
					+ "When no elements remain, next() should throw a NoSuchElementException.");
		} catch (NoSuchElementException e) {

		}
		// --------------------------------------------------------------- //

		// ---- Check exception when calling remove(). ---- //
		// Remove is currently not supported.
		try {
			root = new BasicTestTree();
			iterator = iteratorCreator.createIterator(root);
			iterator.next();

			// The below call should throw an exception.
			iterator.remove();
			fail(orderString + " failure: "
					+ "remove() should throw an UnsupportedOperationException.");
		} catch (UnsupportedOperationException e) {

		}
		// ------------------------------------------------ //

		return;
	}

	/**
	 * This class is used to create an iterator. Its primary purpose is to act
	 * as a factory so that the order-specific code can be written once and the
	 * test code can be re-used.
	 * 
	 * @author Jordan
	 */
	private interface IteratorCreator {
		public Iterator<BasicTestTree> createIterator(BasicTestTree root);

		public Iterator<BasicTestTree> createExpectedIterator(BasicTestTree root);
	}
}
