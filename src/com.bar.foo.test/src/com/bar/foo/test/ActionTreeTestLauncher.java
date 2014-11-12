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

		// Add a basic ActionTree (no children).
		ActionTree basicActionTree = new ActionTree();
		basicActionTree.setAction(new Action("ActionTree") {
			@Override
			public void run() {
				System.out.println("Basic ActionTree");
			}
		});
		basicActionTree.fill(toolBarManager);

		// ---- Add an ActionTree with children and grandchildren. ---- //
		ActionTree tree = new ActionTree();
		tree.text = "ActionTree";

		// Add a child.
		ActionTree child1 = new ActionTree();
		child1.setAction(new Action("Child1") {
			@Override
			public void run() {
				System.out.println("Child1");
			}
		});
		tree.addChild(child1);

		tree.fill(toolBarManager);
		// TODO Try filling first, then refreshing after all children are added.
		// ------------------------------------------------------------ //

		// Add an ActionTree with children and a default action.
		// TODO

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
