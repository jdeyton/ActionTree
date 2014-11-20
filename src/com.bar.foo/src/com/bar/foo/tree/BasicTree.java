package com.bar.foo.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bar.foo.tree.iterator.BreadthFirstTreeIterator;
import com.bar.foo.tree.iterator.PostOrderTreeIterator;
import com.bar.foo.tree.iterator.PreOrderTreeIterator;
import com.bar.foo.tree.iterator.TreeIterationOrder;

/**
 * This class provides a basic implementation of {@link ITree}. It implements
 * all of the applicable methods from {@code ITree}. The result is that a
 * sub-class need only implement a few methods to provide a tree of that object
 * type. For example, consider the following class:
 * 
 * <pre>
 * <code>
 * public class Foo extends {@literal BasicTree<Foo>} {
 *     
 *     public int bar = 1;
 * 
 *     {@literal @Override}
 *     public Foo getValue() {
 *         return this;
 *     }
 * }
 * </code>
 * </pre>
 * 
 * The end result of this example class is a tree of {@code Foo} nodes where
 * each node maintains an integer value {@code bar}.
 * 
 * @author Jordan
 *
 * @param <T>
 *            The type of node contained in the tree. This should be the class
 *            name, e.g., {@code Foo} in the above example.
 */
public abstract class BasicTree<T extends BasicTree<T>> implements ITree<T> {

	/**
	 * The parent of this tree node.
	 */
	private T parent = null;

	/**
	 * The children of this tree node.
	 */
	private final List<T> children = new ArrayList<T>();

	/**
	 * The default constructor. Creates a new {@code BasicTree} with no
	 * children.
	 */
	public BasicTree() {
		// Nothing to do.
	}

	/**
	 * The default copy constructor.
	 * 
	 * @param tree
	 *            The {@code BasicTree} to copy. If not null, the tree will be
	 *            copied, not including its descendants.
	 */
	protected BasicTree(BasicTree<T> tree) {
		// Nothing to do. There is no state information maintained in this class
		// other than its relatives in the tree.
	}

	/**
	 * The full copy constructor.
	 * 
	 * @param tree
	 *            The {@code BasicTree} to copy. To copy, this value must not be
	 *            null.
	 * @param fullTree
	 *            If true, the tree and all of its descendants will be copied.
	 *            This creates an <i>entirely new tree!</i> If false, only this
	 *            node in the tree will be copied.
	 */
	protected BasicTree(BasicTree<T> tree, boolean fullTree) {
		// This method cannot be implemented because the local (non-traversing)
		// constructor for T cannot be inferred.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#addChild(com.bar.foo.tree.ITree)
	 */
	@Override
	public boolean addChild(T child) {
		boolean added = false;
		if (child != null && !hasChild(child)) {
			if (children.add(child)) {
				added = true;
				T parent = child.getParent();
				if (parent != null) {
					parent.removeChild(child);
				}
				child.setParent(getValue());
			}
		}
		return added;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#getChild(int)
	 */
	@Override
	public T getChild(int index) {
		return children.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#getChildren()
	 */
	@Override
	public List<T> getChildren() {
		return new ArrayList<T>(children);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#getNumberOfChildren()
	 */
	@Override
	public int getNumberOfChildren() {
		return children.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#getParent()
	 */
	@Override
	public T getParent() {
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#hasChild(com.bar.foo.tree.ITree)
	 */
	@Override
	public boolean hasChild(T child) {
		return children.contains(child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return iterator(TreeIterationOrder.BreadthFirst);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bar.foo.tree.ITree#iterator(com.bar.foo.tree.iterator.TreeIterationOrder
	 * )
	 */
	@Override
	public Iterator<T> iterator(TreeIterationOrder order) {
		Iterator<T> iterator = null;

		if (order != null) {
			switch (order) {
			case BreadthFirst:
				iterator = new BreadthFirstTreeIterator<T>(getValue());
				break;
			case PreOrder:
				iterator = new PreOrderTreeIterator<T>(getValue());
				break;
			case PostOrder:
				iterator = new PostOrderTreeIterator<T>(getValue());
				break;
			}
		}

		return iterator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#removeChild(int)
	 */
	@Override
	public T removeChild(int index) {
		T child = children.remove(index);
		if (child != null) {
			child.setParent(null);
		}
		return child;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#removeChild(com.bar.foo.tree.ITree)
	 */
	@Override
	public boolean removeChild(T child) {
		boolean removed = children.remove(child);
		if (removed) {
			child.setParent(null);
		}
		return removed;
	}

	/**
	 * This method is for use solely inside {@link BasicTree}.
	 * 
	 * @param parent
	 *            The new parent.
	 */
	protected final void setParent(T parent) {
		this.parent = parent;
	}

	/**
	 * Performs a simple equality check with another object. Since no
	 * information except tree structure is maintained in {@code BasicTree},
	 * this method will only return true if the two objects are the same or if
	 * the other object is a {@code BasicTree<T>} with the same type {@code T}.
	 * <p>
	 * Sub-classes should override this method and provide a better equality
	 * check. For instance, if the sub-class has {@code String property}, then
	 * the overridden {@code equals(Object)} should look like:
	 * 
	 * <pre>
	 * <code>
	 * boolean equals = super.equals(object);
	 * // Note: The instanceof check will always be true if equals is true, but
	 * // it is necessary to satisfy the Java compiler when casting the object.
	 * if (equals && this != object && object instanceof T) {
	 *     T t = (T) object;
	 *     equals = (property == null ? t.property == null : property.equals(t.property));
	 *     // Compare other properties of the two objects...
	 * }
	 * return equals;
	 * </code>
	 * </pre>
	 * 
	 * <b>Note:</b> This method <i>should not traverse</i> its children to
	 * determine their hash codes! This is already handled in
	 * {@link #hashCode(boolean)} when the boolean is {@code true}.
	 * </p>
	 */
	@Override
	public boolean equals(Object object) {
		// There is no information that is not directly related to its
		// genealogy stored in the BasicTree. Thus, two objects are equal if
		// they are the same or if they are both non-null BasicTrees with the
		// same generic type.
		boolean equals = (this == object);
		if (!equals && object != null && object instanceof BasicTree<?>) {
			BasicTree<?> tree = (BasicTree<?>) object;
			// It's not enough to check that it's a BasicTree, but it has to
			// have the same generic type T.
			equals = getValue().getClass().equals(tree.getValue().getClass());
		}
		return equals;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#equals(com.bar.foo.tree.ITree, boolean)
	 */
	@Override
	public final boolean equals(ITree<T> object, boolean fullTree) {
		boolean equals = equals(object);

		if (equals && fullTree && object instanceof BasicTree<?>) {
			BasicTree<T> tree = (BasicTree<T>) object;

			// Grab breadth-first iterators for these trees.
			Iterator<T> iterator = tree.iterator(TreeIterationOrder.BreadthFirst);
			Iterator<T> treeIterator = iterator(TreeIterationOrder.BreadthFirst);

			// Skip the first node, which is this one, since it's already been
			// compared above.
			T subtree = iterator.next();
			T treeSubtree = treeIterator.next();

			// Loop over all of the values in the tree and look for
			// inconsistencies between the iterators.
			while (equals && iterator.hasNext() && treeIterator.hasNext()) {
				subtree = iterator.next();
				treeSubtree = treeIterator.next();
				// We need to compare both the number of children and the node
				// data to determine if the two nodes are the same. We must
				// check the number of children to satisfy equality for
				// non-recursive breadth first search.
				equals = (subtree.getNumberOfChildren() == treeSubtree
						.getNumberOfChildren() && subtree.equals(treeSubtree));
			}
		}

		return equals;
	}

	/**
	 * Provides a hash code for a {@code BasicTree}. Since no information except
	 * tree structure is maintained in {@code BasicTree}, the hash is always 0.
	 * <p>
	 * Sub-classes should override this method and provide a better hash. For
	 * instance, if the sub-class has {@code String property}, then the
	 * overridden {@code hashCode()} should look like:
	 * 
	 * <pre>
	 * <code>
	 * int hash = super.hashCode();
	 * hash = hash * 31 + (property == null ? 0 : property.hashCode());
	 * // Add other properties to the hash...
	 * return hash;
	 * </code>
	 * </pre>
	 * 
	 * <b>Note:</b> This method <i>should not traverse</i> its children to
	 * determine their hash codes! This is already handled in
	 * {@link #hashCode(boolean)} when the boolean is {@code true}.
	 * </p>
	 */
	@Override
	public int hashCode() {
		// There is no information that is not directly related to its
		// genealogy stored in the BasicTree. There is no property to hash!
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#hashCode(boolean)
	 */
	@Override
	public final int hashCode(boolean fullTree) {
		int hash = hashCode();

		if (fullTree) {
			// Loop over all descendants and add their hashes to the hash.
			Iterator<T> iterator = new BreadthFirstTreeIterator<T>(getValue());
			T subtree = iterator.next();
			while (iterator.hasNext()) {
				subtree = iterator.next();
				// We have to hash the number of children because we use a
				// breadth-first traversal instead of recursion.
				hash = hash * 31 + subtree.getNumberOfChildren();
				hash = hash * 31 + subtree.hashCode();
			}
		}

		return hash;
	}

}
