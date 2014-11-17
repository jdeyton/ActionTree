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
		ActionTree basicActionTree = new ActionTree();
		basicActionTree.setAction(new Action("ActionTree (adds children)") {
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
		basicActionTree.fill(toolBarManager);

		// ---- Add an ActionTree with children and grandchildren. ---- //
		dynamicTree.text = "ActionTree";

		// Try filling first, then refreshing after all children are added.
		dynamicTree.fill(toolBarManager);

		// Add a child.
		ActionTree child1 = new ActionTree();
		child1.setAction(new Action("Child1") {
			@Override
			public void run() {
				System.out.println("Child1");
			}
		});
		dynamicTree.addChild(child1);

		// Add another child.
		ActionTree child2 = new ActionTree();
		child2.text = "Child2";
		dynamicTree.addChild(child2);

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
		dynamicTree.refresh();
		// ------------------------------------------------------------ //

		// ---- Add an ActionTree with children and a default action. ---- //
		ActionTree treeDefaultAction = new ActionTree();
		treeDefaultAction.setAction(new Action("ActionTree (with set action)") {
			@Override
			public void run() {
				System.out.println("ActionTree (with set action)");
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
		treeDefaultAction.addChild(child1);

		// Add another child.
		child2 = new ActionTree();
		child2.setAction(new Action("Child2") {
			@Override
			public void run() {
				System.out.println("Child2 (second tree)");
			}
		});
		treeDefaultAction.addChild(child2);

		treeDefaultAction.fill(toolBarManager);
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
