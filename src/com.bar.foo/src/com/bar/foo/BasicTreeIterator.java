package com.bar.foo;

import java.util.Iterator;
import java.util.function.Consumer;

public abstract class BasicTreeIterator<T> implements Iterator<T> {

	protected final BasicTree<T> root;

	public BasicTreeIterator(BasicTree<T> root) {
		if (root != null) {
			this.root = root;
		} else {
			this.root = new BasicTree<T>();
			throw new IllegalArgumentException("BasicTreeIterator error: "
					+ "Cannot construct an iterator from a null tree!");
		}
	}

	@Override
	public void forEachRemaining(Consumer<? super T> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
