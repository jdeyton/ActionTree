package com.bar.foo.test;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;

import com.bar.foo.actiontree.ActionTree;

public class ActionTreeTestLauncher {

	public static void main(String[] args) {

		// Create the Display.
		Display display = new Display();

		// Create the Shell (window).
		final Shell shell = new Shell(display);
		shell.setText("Exodus Mesh Tester");
		shell.setSize(1024, 768);
		shell.setLayout(new GridLayout(1, false));

		// Create a ToolBar.
		ToolBarManager toolBarManager = new ToolBarManager();
		ToolBar toolBar = toolBarManager.createControl(shell);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// Create a content Composite to compress the ToolBar.
		Composite content = new Composite(shell, SWT.NONE);
		content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		content.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));

		// Add a basic JFace Action for comparison.
		Action jfaceAction = new Action("JFace Action") {
			@Override
			public void run() {
				System.out.println("JFace Action");
			}
		};
		toolBarManager.add(jfaceAction);

		// Add a basic ActionTree (default configuration).
		ActionTree defaultActionTree = new ActionTree();
		defaultActionTree.fill(toolBarManager);

		// Define the dynamic tree so it can be used in the next ActionTree.
		final ActionTree dynamicTree = new ActionTree();

		// Add a basic ActionTree (no children). When clicked, this should
		// generate a new child ActionTree and add it to the dynamic tree.
		ActionTree addAction = new ActionTree();
		addAction.setAction(new Action("Add (to Dynamic Tree)") {
			@Override
			public void run() {
				// Get a name for a new child tree based on the number of
				// children in the dynamic tree.
				int count = dynamicTree.getNumberOfChildren();
				String text = "Child" + Integer.toString(count + 1);
				// Create the new child in the dynamic tree.
				ActionTree child = new ActionTree();
				child.setAction(new Action(text) {
					@Override
					public void run() {
						System.out.println(getText());
					}
				});
				// Add the child to the dynamic tree.
				dynamicTree.addChild(child);
				dynamicTree.refresh();
				return;
			}
		});
		addAction.fill(toolBarManager);

		// Add a basic ActionTree (no children). When clicked, this should
		// remove a child ActionTree from the dynamic tree.
		ActionTree removeAction = new ActionTree();
		removeAction.setAction(new Action("Remove (from Dynamic Tree)") {
			@Override
			public void run() {
				// Get a name for a new child tree based on the number of
				// children in the dynamic tree.
				int count = dynamicTree.getNumberOfChildren();
				if (count > 0) {
					dynamicTree.removeChild(0);
					dynamicTree.refresh();
				} else {
					System.out.println("No children in the dynamic tree...");
				}
				return;
			}
		});
		removeAction.fill(toolBarManager);

		// ---- Add an ActionTree with children and grandchildren. ---- //
		ActionTree tree = new ActionTree();
		tree.text = "ActionTree";

		// Try filling first, then refreshing after all children are added.
		tree.fill(toolBarManager);

		// Add a child.
		ActionTree child1 = new ActionTree();
		child1.setAction(new Action("Child1") {
			@Override
			public void run() {
				System.out.println("Child1");
			}
		});
		tree.addChild(child1);

		// Add another child.
		ActionTree child2 = new ActionTree();
		child2.text = "Child2";
		tree.addChild(child2);

		// Add a grandchild.
		ActionTree grandChild1 = new ActionTree();
		grandChild1.setAction(new Action("GrandChild1") {
			@Override
			public void run() {
				System.out.println("GrandChild1");
			}
		});
		child2.addChild(grandChild1);

		// Add another grandchild.
		ActionTree grandChild2 = new ActionTree();
		grandChild2.setAction(new Action("GrandChild2") {
			@Override
			public void run() {
				System.out.println("GrandChild2");
			}
		});
		child2.addChild(grandChild2);

		// Now try refreshing the tree.
		tree.refresh();
		// ------------------------------------------------------------ //

		// ---- Add an ActionTree with children and a default action. ---- //
		dynamicTree.setAction(new Action("Dynamic Tree") {
			@Override
			public void run() {
				System.out.println("Some default action...");
			}
		});

		// Add a child.
		child1 = new ActionTree();
		child1.setAction(new Action("Child1") {
			@Override
			public void run() {
				System.out.println("Child1 (second tree)");
			}
		});
		dynamicTree.addChild(child1);

		// Add another child.
		child2 = new ActionTree();
		child2.setAction(new Action("Child2") {
			@Override
			public void run() {
				System.out.println("Child2 (second tree)");
			}
		});
		dynamicTree.addChild(child2);

		dynamicTree.fill(toolBarManager);
		// --------------------------------------------------------------- //

		// Refresh the ToolBar.
		toolBarManager.update(true);

		// Open the shell.
		shell.open();

		// SOP UI loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

		// Right now, this is the simplest way to halt the JME3 application.
		// Otherwise, the program does not actually terminate.
		System.exit(0);

		return;
	}

}
