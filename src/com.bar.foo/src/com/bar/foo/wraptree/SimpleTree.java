package com.bar.foo.wraptree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bar.foo.wraptree.iterator.BreadthFirstTreeIterator;
import com.bar.foo.wraptree.iterator.PostOrderTreeIterator;
import com.bar.foo.wraptree.iterator.PreOrderTreeIterator;
import com.bar.foo.wraptree.iterator.TreeIterationOrder;

public class SimpleTree<T> extends SimpleNode<T> implements ITree<T> {

	private final List<INode<T>> children = new ArrayList<INode<T>>();

	public SimpleTree() {
		super();
	}

	public SimpleTree(ITree<T> parent) {
		super(parent);
	}

	public SimpleTree(T value, ITree<T> parent) {
		super(value, parent);
	}

	@Override
	public boolean hasChildren() {
		return children.size() > 0;
	}

	@Override
	public List<INode<T>> getChildren() {
		return new ArrayList<INode<T>>(children);
	}

	@Override
	public int getNumberOfChildren() {
		return children.size();
	}

	public INode<T> getChild(int index) {
		return children.get(index);
	}

	@Override
	public void addChild(INode<T> node) {
		children.add(node);
	}

	@Override
	public void removeChild(int index) {
		children.remove(index);
	}

	@Override
	public Iterator<INode<T>> iterator() {
		return iterator(TreeIterationOrder.BreadthFirst);
	}

	@Override
	public Iterator<INode<T>> iterator(TreeIterationOrder order) {
		Iterator<INode<T>> iterator = null;

		if (order != null) {
			switch (order) {
			case BreadthFirst:
				iterator = new BreadthFirstTreeIterator<T>(this);
				break;
			case PreOrder:
				iterator = new PreOrderTreeIterator<T>(this);
				break;
			case PostOrder:
				iterator = new PostOrderTreeIterator<T>(this);
				break;
			}
		}

		return iterator;
	}

}
