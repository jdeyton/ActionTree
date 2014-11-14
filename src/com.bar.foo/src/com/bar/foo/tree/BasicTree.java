package com.bar.foo.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bar.foo.tree.iterator.BreadthFirstTreeIterator;
import com.bar.foo.tree.iterator.PostOrderTreeIterator;
import com.bar.foo.tree.iterator.PreOrderTreeIterator;
import com.bar.foo.tree.iterator.TreeIterationOrder;

// TODO Documentation
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
		// TODO
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
		// TODO
	}

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

	@Override
	public T getChild(int index) {
		return children.get(index);
	}

	@Override
	public List<T> getChildren() {
		return new ArrayList<T>(children);
	}

	@Override
	public int getNumberOfChildren() {
		return children.size();
	}

	@Override
	public T getParent() {
		return parent;
	}

	@Override
	public boolean hasChild(T child) {
		return children.contains(child);
	}

	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return iterator(TreeIterationOrder.BreadthFirst);
	}

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

	@Override
	public T removeChild(int index) {
		T child = children.remove(index);
		if (child != null) {
			child.setParent(null);
		}
		return child;
	}

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

	@Override
	public boolean equals(Object object) {
		// There is no information that is not directly related to its
		// genealogy stored in the BasicTree. Two objects are only equal if they
		// are the same object.
		return super.equals(object);
	}

	@Override
	public final boolean equals(Object object, boolean fullTree) {
		boolean equals = equals(object);
		if (equals && fullTree && object instanceof BasicTree<?>) {
			// Get iterators for this tree and the other.
			Iterator<T> iterator = iterator();
			BasicTree<?> tree = (BasicTree<?>) object;
			Iterator<?> treeIterator = tree.iterator();

			// Skip the first node, which is this one, since it's already been
			// compared above.
			iterator.next();
			treeIterator.next();

			// Loop over all of the values in the tree and look for
			// inconsistencies between the iterators.
			while (equals && iterator.hasNext() && treeIterator.hasNext()) {
				equals = iterator.next().equals(treeIterator.next());
			}
		}
		return equals;
	}

	@Override
	public int hashCode() {
		// There is no information that is not directly related to its
		// genealogy stored in the BasicTree. Two objects are only equal if they
		// are the same object, thus, there is no fitting hash for a BasicTree.
		return super.hashCode();
	}

	@Override
	public final int hashCode(boolean fullTree) {
		int hash = hashCode();
		if (fullTree) {
			// Loop over all descendants and add their hashes to the hash.
			Iterator<T> iterator = iterator();
			iterator.next();
			while (iterator.hasNext()) {
				hash += 31 * iterator.next().hashCode();
			}
		}
		return hash;
	}

}
