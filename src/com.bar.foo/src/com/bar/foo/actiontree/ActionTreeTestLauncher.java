package com.bar.foo.actiontree;

import java.util.Iterator;

import com.bar.foo.tree.iterator.TreeIterationOrder;

public class ActionTreeTestLauncher {

	public static void main(String[] args) {

		return;
	}

}

// The code below is for the SimpleTree version of ActionTree.
//import com.bar.foo.wraptree.iterator.TreeIterationOrder;
//import com.bar.foo.wraptree.INode;
//
//public class ActionTreeTestLauncher {
//
//	public static void main(String[] args) {
//
//		ActionTree tree = new ActionTree();
//		tree.name = "root";
//
//		ActionTree child = new ActionTree();
//		child.name = "child 1";
//		tree.addChild(child);
//
//		ActionTree grandChild = new ActionTree();
//		grandChild.name = "grandChild 1";
//		child.addChild(grandChild);
//
//		child = new ActionTree();
//		child.name = "child 2";
//		tree.addChild(child);
//
//		Iterator<INode<ActionTree>> iter = tree.iterator(TreeIterationOrder.PreOrder);
//		while (iter.hasNext()) {
//			tree = iter.next().getValue();
//			System.out.println(tree.name);
//		}
//
//		return;
//	}
//
//}
