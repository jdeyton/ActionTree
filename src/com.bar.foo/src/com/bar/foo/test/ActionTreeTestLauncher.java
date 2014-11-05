package com.bar.foo.test;

import java.util.Iterator;

import com.bar.foo.IterationOrder;
import com.bar.foo.actiontree.ActionTree;

public class ActionTreeTestLauncher {

	public static void main(String[] args) {

		ActionTree tree = new ActionTree();
		tree.name = "root";

		ActionTree child = new ActionTree();
		child.name = "child 1";
		tree.addChild(child);

		ActionTree grandChild = new ActionTree();
		grandChild.name = "grandChild 1";
		child.addChild(grandChild);

		child = new ActionTree();
		child.name = "child 2";
		tree.addChild(child);

		Iterator<ActionTree> iter = tree.iterator(IterationOrder.PreOrder);
		while (iter.hasNext()) {
			tree = iter.next();
			System.out.println(tree.name);
		}

		return;
	}

}
