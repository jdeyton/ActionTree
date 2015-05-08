package com.bar.foo.tree.iterator.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Test;

import com.bar.foo.tree.ITree;
import com.bar.foo.tree.iterator.TreeIterator;
import com.bar.foo.tree.test.BasicTestTree;

/**
 * This class checks the basic implementation provided by the abstract
 * {@link TreeIterator} class.
 * 
 * @author Jordan
 *
 */
public class TreeIteratorTester {

	/**
	 * This method checks that any class variables created for sub-classes are
	 * properly assigned at construction.
	 */
	@Test
	public void checkConstruction() {

		TreeIterator<BasicTestTree> iterator;

		// Create the iterator.
		BasicTestTree root = new BasicTestTree();
		iterator = new FakeTreeIterator<BasicTestTree>(root);

		// Make sure the root node was set.
		assertSame(root, ((FakeTreeIterator<BasicTestTree>) iterator).getRoot());

		return;
	}

	/**
	 * Checks that the default behavior of {@link TreeIterator#next()} returns
	 * null and throws a {@link NoSuchElementException} if
	 * {@link TreeIterator#hasNext()} returns {@code false}.
	 */
	@Test
	public void checkNext() {

		TreeIterator<BasicTestTree> iterator;

		// Create the iterator.
		BasicTestTree root = new BasicTestTree();
		iterator = new FakeTreeIterator<BasicTestTree>(root);

		// Make sure the next() operation throws a NoSuchElementException if
		// hasNext() returns false.
		try {
			assertFalse(iterator.hasNext());
			iterator.next();
			fail("TreeIteratorTester error: "
					+ "NoSuchElementException was not thrown when there is no next element to traverse.");
		} catch (NoSuchElementException e) {
			// The exception was thrown as expected. Do nothing.
		}

		// Make sure the next() operation returns null if hasNext() returns
		// true.
		iterator = new FakeTreeIterator<BasicTestTree>(root) {
			@Override
			public boolean hasNext() {
				return true;
			}
		};
		assertTrue(iterator.hasNext());
		assertNull(iterator.next());

		return;
	}

	/**
	 * Checks that the remove operation throws an
	 * {@link UnsupportedOperationException}.
	 */
	@Test
	public void checkRemove() {

		TreeIterator<BasicTestTree> iterator;

		// Create the iterator.
		BasicTestTree root = new BasicTestTree();
		iterator = new FakeTreeIterator<BasicTestTree>(root);

		// Make sure the remove operation throws an
		// UnsupportedOperationException.
		try {
			iterator.remove();
			fail("TreeIteratorTester error: "
					+ "UnsupportedOperationException was not thrown when the iterator was removed.");
		} catch (UnsupportedOperationException e) {
			// The exception was thrown as expected. Do nothing.
		}

		return;
	}

	private class FakeTreeIterator<T extends ITree<T>> extends TreeIterator<T> {
		public FakeTreeIterator(T root) {
			super(root);
		}

		public T getRoot() {
			return root;
		}

		@Override
		public boolean hasNext() {
			return false;
		}
	}
}
