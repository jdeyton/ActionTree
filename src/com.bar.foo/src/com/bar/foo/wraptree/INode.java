package com.bar.foo.wraptree;

public interface INode<T> {

	public ITree<T> getParent();

	public boolean hasChildren();
	
	public void setValue(T value);
	
	public T getValue();
}
