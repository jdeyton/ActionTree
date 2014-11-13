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

	private T parent = null;

	private final List<T> children = new ArrayList<T>();

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

	// TODO Override equals, copy, and clone.

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		Iterator<T> iterator = iterator();
		while (iterator.hasNext()) {
			hash += 31 * iterator.next().nodeHashCode();
		}
		return hash;
	}

}
