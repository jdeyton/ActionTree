package com.bar.foo.wraptree;

public class SimpleNode<T> implements INode<T> {

	private final ITree<T> parent;
	
	private T value;
	
	public SimpleNode() {
		this(null, null);
	}
	
	public SimpleNode(ITree<T> parent) {
		this(null, parent);
	}
	
	public SimpleNode(T value, ITree<T> parent) {
		this.value = value;
		this.parent = parent;
	}
	
	
	@Override
	public ITree<T> getParent() {
		return parent;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public T getValue() {
		return value;
	}

}
