package com.bar.foo;

import java.util.Iterator;
import java.util.List;

public interface ITree<T extends ITree<T>> extends Iterable<T> {

	public void addChild(T child);
	
	public int getNumberOfChildren();

	public T getChild(int index);
	
	public List<T> getChildren();
	
	public T getParent();

	public Iterator<T> iterator(IterationOrder order);

	public T removeChild(int index);
}
