package com.bar.foo.actiontree;

import com.bar.foo.BasicTree;
import com.bar.foo.IterationOrder;

public class ActionTree extends BasicTree<ActionTree> {

	public void main() {
		this.iterator(IterationOrder.BreadthFirst);
	}
	
}
