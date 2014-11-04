package com.bar.foo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class BasicTree<T> implements Iterable<T> {

	private final List<T> children = new ArrayList<T>();

	@Override
	public void forEach(Consumer<? super T> arg0) {
		// TODO Auto-generated method stub

	}

	public List<T> getChildren() {
		return new ArrayList<T>(children);
	}

	@Override
	public Iterator<T> iterator() {
		return new BreadthFirstBasicTreeIterator<T>(this);
	}

	public Iterator<T> iterator(IterationOrder order) {

		Iterator<T> iterator = null;

		if (order != null) {
			switch (order) {
			case BreadthFirst:
				iterator = new BreadthFirstBasicTreeIterator<T>(this);
				break;
			case PreOrder:
				iterator = new PreOrderBasicTreeIterator<T>(this);
				break;
			case PostOrder:
				iterator = new PostOrderBasicTreeIterator<T>(this);
				break;
			}
			;
		}

		return iterator;
	}

	@Override
	public Spliterator<T> spliterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
