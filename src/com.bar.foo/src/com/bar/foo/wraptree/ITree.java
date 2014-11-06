package com.bar.foo.wraptree;

import java.util.Iterator;
import java.util.List;

import com.bar.foo.wraptree.iterator.TreeIterationOrder;

public interface ITree<T> extends INode<T> {

	public List<INode<T>> getChildren();

	public int getNumberOfChildren();

	public INode<T> getChild(int index);
	
	public void addChild(INode<T> node);

	public void removeChild(int index);

	public Iterator<INode<T>> iterator();

	public Iterator<INode<T>> iterator(TreeIterationOrder order);
}
