package com.bar.foo.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bar.foo.tree.iterator.BreadthFirstTreeIterator;
import com.bar.foo.tree.iterator.PostOrderTreeIterator;
import com.bar.foo.tree.iterator.PreOrderTreeIterator;
import com.bar.foo.tree.iterator.TreeIterationOrder;

public class BasicTree<T extends ITree<T>> implements ITree<T> {

	private T parent;

	private final List<T> children = new ArrayList<T>();

	public BasicTree() {
		this(null);
	}

	public BasicTree(T parent) {
		this.parent = parent;
	}

	@Override
	public void addChild(T child) {
		if (child != null) {
			children.add(child);
		}
	}

	@Override
	public int getNumberOfChildren() {
		return children.size();
	}

	@Override
	public List<T> getChildren() {
		return new ArrayList<T>(children);
	}

	@Override
	public T getParent() {
		return parent;
	}

	@Override
	public T getChild(int index) {
		T child = null;
		if (index >= 0 && index < children.size()) {
			child = children.get(index);
		}
		return child;
	}

	@Override
	public Iterator<T> iterator() {
		return iterator(TreeIterationOrder.BreadthFirst);
	}

	@Override
	public Iterator<T> iterator(TreeIterationOrder order) {
		Iterator<T> iterator = null;

		// This can be done, because T extends (or implements) ITree<T>.
		T value = (T) this;

		if (order != null) {
			switch (order) {
			case BreadthFirst:
				iterator = new BreadthFirstTreeIterator<T>(value);
				break;
			case PreOrder:
				iterator = new PreOrderTreeIterator<T>(value);
				break;
			case PostOrder:
				iterator = new PostOrderTreeIterator<T>(value);
				break;
			}
		}

		return iterator;
	}

	@Override
	public T removeChild(int index) {
		return children.remove(index);
	}
}
