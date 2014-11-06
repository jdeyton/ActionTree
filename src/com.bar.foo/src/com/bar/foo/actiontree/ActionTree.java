package com.bar.foo.actiontree;

import com.bar.foo.tree.BasicTree;

/**
 * I prefer the BasicTree version because it's more concise. The developer also
 * does not need to make any calls to {@link #getValue()} when dealing with the
 * iterators, either. You also cannot define this class as
 * {@code ActionTree extends SimpleTree<ActionTree>} because you should call the
 * super constructor with the ActionTree itself as the value.
 * 
 * @author Jordan
 * 
 */
public class ActionTree extends BasicTree<ActionTree> {

	// TODO Remove this.
	public String name;

	@Override
	public ActionTree getValue() {
		return this;
	}

}
