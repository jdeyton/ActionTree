package com.bar.foo.tree;

import java.util.Iterator;
import java.util.List;

import com.bar.foo.tree.iterator.TreeIterationOrder;

public interface ITree<T extends ITree<T>> extends Iterable<T> {

	public void addChild(T child);
	
	public int getNumberOfChildren();

	public T getChild(int index);
	
	public List<T> getChildren();
	
	public T getParent();

	public Iterator<T> iterator(TreeIterationOrder order);

	public T removeChild(int index);
}
