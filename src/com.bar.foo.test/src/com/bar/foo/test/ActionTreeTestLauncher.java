package com.bar.foo.test;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;

import com.bar.foo.actiontree.ActionTree;

public class ActionTreeTestLauncher {

	// TODO Fix the following bugs:
	// Image and text cannot be displayed at the same time.
	// When an image is set, the ToolBar gets taller, but does not shrink back
	// down when the image is unset.

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
		content.setLayout(new GridLayout(1, false));

		// Create a Context Menu for the content Composite.
		MenuManager menuManager = new MenuManager();
		Menu contextMenu = menuManager.createContextMenu(content);
		content.setMenu(contextMenu);

		Composite contentRow;

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
		tree3.action = new Action("3") {
			@Override
			public void run() {
				System.out.println("3 - ActionTree (with action)");
			}
		};
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
		child1.action = new Action("Child1") {
			@Override
			public void run() {
				System.out.println("Child1");
			}
		};
		tree4.addChild(child1);

		// Add another child.
		ActionTree child2 = new ActionTree();
		child2.text = "Child2";
		tree4.addChild(child2);

		// Add a grandchild.
		ActionTree grandChild1 = new ActionTree();
		grandChild1.action = new Action("GrandChild1") {
			@Override
			public void run() {
				System.out.println("GrandChild1");
			}
		};
		child2.addChild(grandChild1);

		// Add another grandchild.
		ActionTree grandChild2 = new ActionTree();
		grandChild2.action = new Action("GrandChild2") {
			@Override
			public void run() {
				System.out.println("GrandChild2");
			}
		};
		child2.addChild(grandChild2);

		// Now try refreshing the tree.
		tree4.refresh();
		// ------------------------------------------------ //

		// ---- Create another ActionTree with descendants. ---- //
		createLabel(content, "5 - ActionTree (with action, with children)");
		ActionTree tree5 = new ActionTree();
		tree5.action = new Action("5") {
			@Override
			public void run() {
				System.out
						.println("5 - ActionTree (with action, with children)");
			}
		};

		// Add a child.
		child1 = new ActionTree();
		child1.action = new Action("Child1") {
			@Override
			public void run() {
				System.out.println("Child1 (parent - 5)");
			}
		};
		tree5.addChild(child1);

		// Add another child.
		child2 = new ActionTree();
		child2.text = "Child2";
		tree5.addChild(child2);

		// Add a grandchild.
		grandChild1 = new ActionTree();
		grandChild1.action = new Action("GrandChild1") {
			@Override
			public void run() {
				System.out.println("GrandChild1 (grandparent - 5)");
			}
		};
		child2.addChild(grandChild1);

		// Add another grandchild.
		grandChild2 = new ActionTree();
		grandChild2.action = new Action("GrandChild2") {
			@Override
			public void run() {
				System.out.println("GrandChild2 (grandparent - 5)");
			}
		};
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

		// Create a new Composite to contain two buttons.
		contentRow = new Composite(content, SWT.NONE);
		contentRow.setBackground(content.getBackground());
		contentRow
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		contentRow.setLayout(new GridLayout(2, true));

		// Create buttons to add/remove children from this tree.
		createButton(contentRow, SWT.RIGHT, new Action("Add child to 6") {
			@Override
			public void run() {
				// Get a name for a new child tree based on the number of
				// children in the dynamic tree.
				int count = tree6.getNumberOfChildren();
				String text = "Child" + Integer.toString(count + 1);
				// Create the new child in the dynamic tree.
				ActionTree child = new ActionTree();
				child.action = new Action(text) {
					@Override
					public void run() {
						System.out.println(getText());
					}
				};
				// Add the child to the dynamic tree.
				tree6.addChild(child);
				tree6.refresh();
				return;
			}
		});
		createButton(contentRow, SWT.LEFT, new Action("Remove child from 6") {
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
		tree7.action = new Action("7") {
			@Override
			public void run() {
				System.out.println("7 - ActionTree (action, dynamic)");
			}
		};
		tree7.fill(toolBarManager);
		tree7.fill(menuManager);

		// Create a new Composite to contain two buttons.
		contentRow = new Composite(content, SWT.NONE);
		contentRow.setBackground(content.getBackground());
		contentRow
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		contentRow.setLayout(new GridLayout(2, true));

		// Create buttons to add/remove children from this tree.
		createButton(contentRow, SWT.RIGHT, new Action("Add child to 7") {
			@Override
			public void run() {
				// Get a name for a new child tree based on the number of
				// children in the dynamic tree.
				int count = tree7.getNumberOfChildren();
				String text = "Child" + Integer.toString(count + 1);
				// Create the new child in the dynamic tree.
				ActionTree child = new ActionTree();
				child.action = new Action(text) {
					@Override
					public void run() {
						System.out.println(getText());
					}
				};
				// Add the child to the dynamic tree.
				tree7.addChild(child);
				tree7.refresh();
				return;
			}
		});
		createButton(contentRow, SWT.LEFT, new Action("Remove child from 7") {
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

		// ---- Create a dynamic tree that starts off unconfigured. ---- //
		createLabel(content, "8 - ActionTree (unconfigured, dynamic)");
		final ActionTree tree8 = new ActionTree();
		tree8.fill(toolBarManager);
		tree8.fill(menuManager);

		// Create a new Composite to contain two buttons.
		contentRow = new Composite(content, SWT.NONE);
		contentRow.setBackground(content.getBackground());
		contentRow
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		contentRow.setLayout(new GridLayout(2, true));

		// Create buttons to add/remove children.
		createButton(contentRow, SWT.RIGHT, new Action("Add child to 8") {
			@Override
			public void run() {
				// Get a name for a new child tree based on the number of
				// children in the dynamic tree.
				int count = tree8.getNumberOfChildren();
				String text = "Child" + Integer.toString(count + 1);
				// Create the new child in the dynamic tree.
				ActionTree child = new ActionTree();
				child.action = new Action(text) {
					@Override
					public void run() {
						System.out.println(getText());
					}
				};
				// Add the child to the dynamic tree.
				tree8.addChild(child);
				tree8.refresh();
				return;
			}
		});
		createButton(contentRow, SWT.LEFT, new Action("Remove child from 8") {
			@Override
			public void run() {
				// Get a name for a new child tree based on the number of
				// children in the dynamic tree.
				int count = tree8.getNumberOfChildren();
				if (count > 0) {
					tree8.removeChild(0);
					tree8.refresh();
				} else {
					System.out.println("No children in the dynamic tree...");
				}
				return;
			}
		});

		// Create a new Composite to contain an image switcher and Action
		// toggler.
		contentRow = new Composite(content, SWT.NONE);
		contentRow.setBackground(content.getBackground());
		contentRow
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		contentRow.setLayout(new GridLayout(2, true));

		// Create a button to change its image.
		final ImageDescriptor[] images = new ImageDescriptor[6];
		images[0] = null;
		images[1] = getImage(display, "blue.png");
		images[2] = getImage(display, "green.png");
		images[3] = getImage(display, "orange.png");
		images[4] = getImage(display, "red.png");
		images[5] = getImage(display, "yellow.png");
		final AtomicInteger index = new AtomicInteger();
		createButton(contentRow, SWT.RIGHT, new Action("Change image for 8") {
			@Override
			public void run() {
				// Increment the index, but make sure it stays within the number
				// of images.
				int i = index.incrementAndGet() % images.length;
				index.set(i);

				// Update the ActionTree's image.
				tree8.image = images[i];
				tree8.refresh();

				return;
			}
		});
		// Create a button to toggle (add/remove) an Action for the ActionTree.
		createButton(contentRow, SWT.LEFT, new Action("Toggle Action for 8") {
			@Override
			public void run() {
				tree8.action = (tree8.action != null ? null : new Action(
						"some action") {
					@Override
					public void run() {
						System.out.println("Some action runs here...");
					}
				});
				tree8.refresh();
			}
		});

		// Create a new Composite to contain a label and text widget.
		// This will be used to set the text of the last action.
		contentRow = new Composite(content, SWT.NONE);
		contentRow.setBackground(content.getBackground());
		contentRow
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		contentRow.setLayout(new GridLayout(2, false));

		Label label = new Label(contentRow, SWT.NONE);
		label.setText("Text of 8:");
		label.setBackground(contentRow.getBackground());
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		final Text textText = new Text(contentRow, SWT.SINGLE);
		textText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		textText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				tree8.text = textText.getText();
				tree8.refresh();
			}
		});

		// Create a new Composite to contain a label and text widget.
		// This will be used to set the toolTipText of the last action.
		contentRow = new Composite(content, SWT.NONE);
		contentRow.setBackground(content.getBackground());
		contentRow
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		contentRow.setLayout(new GridLayout(2, false));

		label = new Label(contentRow, SWT.NONE);
		label.setText("ToolTipText of 8:");
		label.setBackground(contentRow.getBackground());
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		final Text toolTipTextText = new Text(contentRow, SWT.SINGLE);
		toolTipTextText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));

		toolTipTextText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				tree8.toolTipText = toolTipTextText.getText();
				tree8.refresh();
			}
		});
		// ------------------------------------------------------------- //

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

	private static ImageDescriptor getImage(Display display, String filename) {
		InputStream stream = ActionTreeTestLauncher.class
				.getResourceAsStream("/" + filename);
		Image image = new Image(display, stream);
		return ImageDescriptor.createFromImage(image);
	}

	/**
	 * Creates a label with the specified text inside the parent Composite.
	 */
	private static void createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
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
