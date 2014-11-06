package com.bar.foo.tree;

import java.util.Iterator;
import java.util.List;

import com.bar.foo.tree.iterator.TreeIterationOrder;

public interface ITree<T extends ITree<T>> extends Iterable<T> {

	public boolean addChild(T child);

	public T getChild(int index);

	public List<T> getChildren();

	public int getNumberOfChildren();

	public T getParent();

	/**
	 * This <b>must</b> be implemented by the outermost concrete sub-class. The
	 * implementation need only contain the code {@code return this;}
	 * 
	 * @return This object {@code T extends ITree<T>}.
	 */
	public T getValue();

	public boolean hasChild(T child);
	
	public boolean hasChildren();
	
	public Iterator<T> iterator(TreeIterationOrder order);

	public T removeChild(int index);

	public boolean removeChild(T child);
}
