package com.bar.foo.tree.iterator.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.bar.foo.tree.iterator.PreOrderTreeIterator;
import com.bar.foo.tree.iterator.TreeIterationOrder;
import com.bar.foo.tree.test.BasicTestTree;

/**
 * This class tests the pre-order tree iterator implementation. To do this, it
 * uses the {@link BasicTestTree}, which uses the default
 * {@link PreOrderTreeIterator} that is being tested.
 * 
 * @author Jordan
 *
 */
public class PreOrderTreeIteratorTester {

	/**
	 * The {@link TreeIterationOrder} for this test class.
	 */
	private static final TreeIterationOrder ORDER = TreeIterationOrder.PreOrder;

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
		Iterator<BasicTestTree> iterator;

		// Remove is currently not supported.
		try {
			root = new BasicTestTree();
			iterator = root.iterator(ORDER);
			iterator.next();

			// The below call should throw an exception.
			iterator.remove();
			fail(ORDER + " failure: "
					+ "remove() should throw an UnsupportedOperationException.");
		} catch (UnsupportedOperationException e) {

		}

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
			fail(ORDER
					+ " failure: "
					+ "When no elements remain, next() should throw a NoSuchElementException.");
		} catch (NoSuchElementException e) {

		}
		// --------------------------------------------------------------- //

		return;
	}

}
