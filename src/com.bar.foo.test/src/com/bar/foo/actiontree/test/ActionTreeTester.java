package com.bar.foo.actiontree.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.jface.action.Action;
import org.junit.Test;

import com.bar.foo.actiontree.ActionTree;

/**
 * This class tests the {@link ActionTree} class's exposed methods.
 * 
 * @author Jordan Deyton
 *
 */
public class ActionTreeTester {

	/**
	 * Checks the copy constructors for both local copies and full-tree copies.
	 * 
	 * @see ActionTree#ActionTree(ActionTree)
	 * @see ActionTree#ActionTree(ActionTree, boolean)
	 */
	@Test
	public void checkCopy() {

		ActionTree object = null;
		ActionTree copy;

		// ---- Try invalid arguments to the copy constructor. ---- //
		// Test the local copy constructor.
		try {
			copy = null;
			copy = new ActionTree(object);
			fail("ActionTreeTester failure: "
					+ "Exception was not thrown for invalid constructor argument.");
		} catch (IllegalArgumentException e) {
			// It works as it should.
		}

		// Test the full tree copy constructor.
		try {
			copy = null;
			copy = new ActionTree(object, false);
			fail("ActionTreeTester failure: "
					+ "Exception was not thrown for invalid constructor argument.");
		} catch (IllegalArgumentException e) {
			// It works as it should.
		}

		// Test the full tree copy constructor.
		try {
			copy = null;
			copy = new ActionTree(object, true);
			fail("ActionTreeTester failure: "
					+ "Exception was not thrown for invalid constructor argument.");
		} catch (IllegalArgumentException e) {
			// It works as it should.
		}
		// -------------------------------------------------------- //

		// ---- Base case: single, default ActionTree. ---- //
		object = new ActionTree();

		// Test the local copy constructor.
		copy = null;
		copy = new ActionTree(object);
		assertNotSame(object, copy);
		assertEquals(object, copy);
		// Test the full tree copy constructor.
		copy = null;
		copy = new ActionTree(object, false);
		assertNotSame(object, copy);
		assertEquals(object, copy);
		// Test the full tree copy constructor.
		copy = null;
		copy = new ActionTree(object, true);
		assertNotSame(object, copy);
		assertEquals(object, copy);
		// ------------------------------------------------ //

		// ---- Try a single, non-default ActionTree. ---- //
		object = new ActionTree();
		object.text = "text";
		object.toolTipText = "This is a tooltip. This is helpful.";
		object.style = Action.AS_UNSPECIFIED;
		object.action = new Action("This is an Action.") {
			@Override
			public void run() {
				// Running!
			}
		};

		// Test the local copy constructor.
		copy = null;
		copy = new ActionTree(object);
		assertNotSame(object, copy);
		assertEquals(object, copy);
		// Test the full tree copy constructor.
		copy = null;
		copy = new ActionTree(object, false);
		assertNotSame(object, copy);
		assertEquals(object, copy);
		// Test the full tree copy constructor.
		copy = null;
		copy = new ActionTree(object, true);
		assertNotSame(object, copy);
		assertEquals(object, copy);
		// ----------------------------------------------- //

		// ---- Try a more complicated tree with children. ---- //
		ActionTree tree1;
		ActionTree tree2;

		tree1 = new ActionTree();
		tree1.toolTipText = "B";
		object.addChild(tree1);
		tree1 = new ActionTree();
		tree1.style = Action.AS_CHECK_BOX;
		object.addChild(tree1);
		tree2 = new ActionTree();
		tree2.action = object.action;
		tree1.addChild(tree2);
		// This shouldn't matter.
		tree2.enabled = false;

		// Test the local copy constructor.
		copy = null;
		copy = new ActionTree(object);
		assertNotSame(object, copy);
		assertEquals(object, copy);
		// Test the full tree copy constructor.
		copy = null;
		copy = new ActionTree(object, false);
		assertNotSame(object, copy);
		assertEquals(object, copy);
		// Test the full tree copy constructor.
		copy = null;
		copy = new ActionTree(object, true);
		assertNotSame(object, copy);
		assertEquals(object, copy);

		// The enabled flag of the grandchild should be true, unlike tree2!
		assertFalse(tree2.enabled);
		ActionTree tree2Copy = copy.getChild(1).getChild(0);
		assertTrue(tree2Copy.enabled);
		assertEquals(tree2, tree2Copy);
		// ---------------------------------------------------- //

		return;
	}

	/**
	 * Checks consistency and correctness for equality and hash code methods.
	 * 
	 * @see ActionTree#equals(Object)
	 * @see ActionTree#equals(com.bar.foo.tree.ITree, boolean)
	 * @see ActionTree#hashCode()
	 * @see ActionTree#hashCode(boolean)
	 */
	@Test
	public void checkEquality() {
		ActionTree object = new ActionTree();
		ActionTree equalObject = new ActionTree();
		ActionTree unequalObject = null;

		// ---- Check bad arguments to equals. ---- //
		// Check the default equals method.
		assertFalse(object.equals(unequalObject));
		assertFalse(object.equals("some string"));
		// Check the tree equals method.
		assertFalse(object.equals(unequalObject, false));
		assertFalse(object.equals(unequalObject, true));
		// ---------------------------------------- //

		// ---- Check the base case: two default trees. ---- //
		// The references are not the same.
		assertNotSame(object, equalObject);

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
		// ------------------------------------------------- //

		// ---- Try changing the properties. ---- //
		object = new ActionTree();
		equalObject = new ActionTree();
		unequalObject = new ActionTree();
		// TODO Add an ImageDescriptor to these objects.

		// Set up the object, equalObject, and unequalObject.
		object.text = "derp";
		object.toolTipText = "woot";
		object.style = Action.AS_PUSH_BUTTON;
		object.action = new Action("Hey hey hey!") {
			@Override
			public void run() {
				// Can't catch me, fast as can be!
			}
		};
		equalObject.text = "derp";
		equalObject.toolTipText = "woot";
		equalObject.style = Action.AS_PUSH_BUTTON;
		// The action must be the same reference since Action doesn't override
		// Object.equals(Object)!
		equalObject.action = object.action;
		// TODO Add an ImageDescriptor.
		unequalObject.text = "derp";
		unequalObject.toolTipText = "woot!"; // Different!
		unequalObject.style = Action.AS_PUSH_BUTTON;
		unequalObject.action = object.action;

		// The references are not the same.
		assertNotSame(object, equalObject);
		assertNotSame(object, unequalObject);
		assertNotSame(equalObject, unequalObject);

		// The next tests check equality between object and equalObject.

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

		// The next tests check inequality between object and unequalObject and
		// between equalObject and unequalObject.

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
		ActionTree tree1;
		ActionTree tree2;

		// Create a test Action as a property.
		Action action = new Action("some action") {
			@Override
			public void run() {
				// Nothing to do.
			}
		};

		// Create a tree with 4 elements as follows:
		// Breadth first order: ABCD, A has children B and C, C has child D.
		object = new ActionTree();
		object.text = "A";
		tree1 = new ActionTree();
		tree1.toolTipText = "B";
		object.addChild(tree1);
		tree1 = new ActionTree();
		tree1.style = Action.AS_CHECK_BOX;
		object.addChild(tree1);
		tree2 = new ActionTree();
		tree2.action = action;
		tree1.addChild(tree2);
		tree2.enabled = true;

		// Duplicate the tree for the equals tree.
		equalObject = new ActionTree();
		equalObject.text = "A";
		tree1 = new ActionTree();
		tree1.toolTipText = "B";
		equalObject.addChild(tree1);
		tree1 = new ActionTree();
		tree1.style = Action.AS_CHECK_BOX;
		equalObject.addChild(tree1);
		tree2 = new ActionTree();
		// Note: The Action must be the same object because two Actions cannot
		// otherwise satisfy Action.equals(Object).
		tree2.action = action;
		tree1.addChild(tree2);
		// The enabled property shouldn't matter.
		tree2.enabled = false;

		// For an unequal tree, create a different tree with the same
		// breadth-first order (just add all nodes to A).
		unequalObject = new ActionTree();
		unequalObject.text = "A";
		tree1 = new ActionTree();
		tree1.toolTipText = "B";
		unequalObject.addChild(tree1);
		tree1 = new ActionTree();
		tree1.style = Action.AS_CHECK_BOX;
		unequalObject.addChild(tree1);
		tree2 = new ActionTree();
		// Note: The Action must be the same object because two Actions cannot
		// otherwise satisfy Action.equals(Object).
		tree2.action = action;
		unequalObject.addChild(tree2); // Different!
		tree2.enabled = true;

		// The references are not the same.
		assertNotSame(object, equalObject);
		assertNotSame(object, unequalObject);
		assertNotSame(equalObject, unequalObject);

		// The next tests check equality between object and equalObject.

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

		// The next tests check inequality between object and unequalObject and
		// between equalObject and unequalObject.

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

		// TODO Check that Actions that do the same thing cannot be equal.
		// TODO Check that ImageDescriptors that are "copies" cannot be equal.

		return;
	}
}
