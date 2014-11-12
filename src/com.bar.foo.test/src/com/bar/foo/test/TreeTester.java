package com.bar.foo.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.bar.foo.tree.BasicTree;

public class TreeTester {

	private class DummyTree extends BasicTree<DummyTree> {
		@Override
		public DummyTree getValue() {
			return this;
		}
	}

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

	@Test
	public void checkBreadthFirstIterator() {
		// TODO
	}

	@Test
	public void checkPreOrderIterator() {
		// TODO
	}

	@Test
	public void checkPostOrderIterator() {
		// TODO
	}

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
}
