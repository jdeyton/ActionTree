package com.bar.foo.test;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
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
		content.setLayout(new GridLayout(2, false));

		// Create a Context Menu for the content Composite.
		MenuManager menuManager = new MenuManager();
		Menu contextMenu = menuManager.createContextMenu(content);
		content.setMenu(contextMenu);

		createLabel(content, "1 - Plain JFace Action");
		Action jfaceAction = new Action("1") {
			@Override
			public void run() {
				System.out.println("1 - Plain JFace Action");
			}
		};
		toolBarManager.add(jfaceAction);
		menuManager.add(jfaceAction);

		createLabel(content, "2 - ActionTree (defaults, no action)");
		ActionTree tree2 = new ActionTree();
		tree2.text = "2";
		tree2.fill(toolBarManager);
		tree2.fill(menuManager);

		createLabel(content, "3 - ActionTree (with action)");
		ActionTree tree3 = new ActionTree();
		tree3.setAction(new Action("3") {
			@Override
			public void run() {
				System.out.println("3 - ActionTree (with action)");
			}
		});
		tree3.fill(toolBarManager);
		tree3.fill(menuManager);

		// ---- Create an ActionTree with descendants. ---- //
		createLabel(content, "4 - ActionTree (no action, with children)");
		ActionTree tree4 = new ActionTree();
		tree4.text = "4";
		tree4.fill(toolBarManager);
		tree4.fill(menuManager);

		// Add a child.
		ActionTree child1 = new ActionTree();
		child1.setAction(new Action("Child1") {
			@Override
			public void run() {
				System.out.println("Child1");
			}
		});
		tree4.addChild(child1);

		// Add another child.
		ActionTree child2 = new ActionTree();
		child2.text = "Child2";
		tree4.addChild(child2);

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
		tree4.refresh();
		// ------------------------------------------------ //

		// ---- Create another ActionTree with descendants. ---- //
		createLabel(content, "5 - ActionTree (with action, with children)");
		ActionTree tree5 = new ActionTree();
		tree5.setAction(new Action("5") {
			@Override
			public void run() {
				System.out
						.println("5 - ActionTree (with action, with children)");
			}
		});

		// Add a child.
		child1 = new ActionTree();
		child1.setAction(new Action("Child1") {
			@Override
			public void run() {
				System.out.println("Child1 (parent - 5)");
			}
		});
		tree5.addChild(child1);

		// Add another child.
		child2 = new ActionTree();
		child2.text = "Child2";
		tree5.addChild(child2);

		// Add a grandchild.
		grandChild1 = new ActionTree();
		grandChild1.setAction(new Action("GrandChild1") {
			@Override
			public void run() {
				System.out.println("GrandChild1 (grandparent - 5)");
			}
		});
		child2.addChild(grandChild1);

		// Add another grandchild.
		grandChild2 = new ActionTree();
		grandChild2.setAction(new Action("GrandChild2") {
			@Override
			public void run() {
				System.out.println("GrandChild2 (grandparent - 5)");
			}
		});
		child2.addChild(grandChild2);

		// Fill the ToolBar after adding all the descendants.
		tree5.fill(toolBarManager);
		tree5.fill(menuManager);
		// ----------------------------------------------------- //

		// ---- Create a dynamic tree that can be populated via buttons. ---- //
		createLabel(content, "6 - ActionTree (no action, dynamic)");
		final ActionTree tree6 = new ActionTree();
		tree6.text = "6";
		tree6.fill(toolBarManager);
		tree6.fill(menuManager);

		// Create buttons to add/remove children from this tree.
		createButton(content, SWT.RIGHT, new Action("Add child to 6") {
			@Override
			public void run() {
				// Get a name for a new child tree based on the number of
				// children in the dynamic tree.
				int count = tree6.getNumberOfChildren();
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
				tree6.addChild(child);
				tree6.refresh();
				return;
			}
		});
		createButton(content, SWT.LEFT, new Action("Remove child from 6") {
			@Override
			public void run() {
				// Get a name for a new child tree based on the number of
				// children in the dynamic tree.
				int count = tree6.getNumberOfChildren();
				if (count > 0) {
					tree6.removeChild(0);
					tree6.refresh();
				} else {
					System.out.println("No children in the dynamic tree...");
				}
				return;
			}
		});
		// ------------------------------------------------------------------ //

		// ---- Create a dynamic tree that can be populated via buttons. ---- //
		createLabel(content, "7 - ActionTree (action, dynamic)");
		final ActionTree tree7 = new ActionTree();
		tree7.setAction(new Action("7") {
			@Override
			public void run() {
				System.out.println("7 - ActionTree (action, dynamic)");
			}
		});
		tree7.fill(toolBarManager);
		tree7.fill(menuManager);

		// Create buttons to add/remove children from this tree.
		createButton(content, SWT.RIGHT, new Action("Add child to 7") {
			@Override
			public void run() {
				// Get a name for a new child tree based on the number of
				// children in the dynamic tree.
				int count = tree7.getNumberOfChildren();
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
				tree7.addChild(child);
				tree7.refresh();
				return;
			}
		});
		createButton(content, SWT.LEFT, new Action("Remove child from 7") {
			@Override
			public void run() {
				// Get a name for a new child tree based on the number of
				// children in the dynamic tree.
				int count = tree7.getNumberOfChildren();
				if (count > 0) {
					tree7.removeChild(0);
					tree7.refresh();
				} else {
					System.out.println("No children in the dynamic tree...");
				}
				return;
			}
		});
		// ------------------------------------------------------------------ //

		// Refresh the ToolBar and context Menu.
		toolBarManager.update(true);
		menuManager.update(true);

		// Refresh the layout of the content Composite so all labels appear.
		content.layout();

		// Open the shell.
		shell.open();

		// SOP UI loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

		return;
	}

	/**
	 * Creates a label with the specified text inside the parent Composite.
	 */
	private static void createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false,
				2, 1));
		label.setBackground(parent.getBackground());
	}

	/**
	 * Creates a Button, which when clicked performs the specified Action,
	 * inside the parent Composite. The alignment is used for the Button inside
	 * the parent's GridLayout.
	 */
	private static void createButton(Composite parent, int alignment,
			final IAction action) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(action.getText());
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				action.run();
			}
		});
		button.setLayoutData(new GridData(alignment, SWT.CENTER, true, false));
		button.setBackground(parent.getBackground());
	}

}
