package com.bar.foo.actiontree;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;

import com.bar.foo.tree.BasicTree;
import com.bar.foo.tree.iterator.TreeIterationOrder;

/**
 * An {@code ActionTree} is a {@link BasicTree tree}-based structure that
 * contains JFace {@link Action}s. The intent of this class is to make it easy
 * to create {@code Action}s that can be embedded simultaneously in multiple
 * widgets (i.e., a {@code ToolBar} and a context {@code Menu}). Furthermore, it
 * aims to make updating these embedded {@code Action}s easier to update or
 * change on the fly.
 * 
 * <p>
 * The {@link #action} associated with the {@code ActionTree} can be set
 * directly. However, if properties like {@link #text}, {@link #style}, or
 * {@link #image} are set to non-default values, they will override the settings
 * of the {@code Action}.
 * </p>
 * 
 * <p>
 * The below list describes the behavior of {@code ActionTree}s when added to
 * supported widgets like {@link ToolBar}s, {@link Menu}s, and JFace
 * {@link ContributionManager}s.
 * </p>
 * 
 * <ul>
 * <li>If an {@code ActionTree} has children, it will have a dropdown (when
 * added to a {@code ToolBar}) or a sub-menu (when added to a {@code Menu}). The
 * {@link #style} of the {@code Action} will be overridden with
 * {@link IAction#AS_DROP_DOWN_MENU}. If added to a {@code ToolBar}, then
 * clicking the button will do one of the following:
 * <ul>
 * <li>If its {@code Action} is not set, clicking the button will create the
 * dropdown just like clicking the arrow next to it.</li>
 * <li>Otherwise, if its {@code Action is set, clicking the button will perform
 * the specified {@link #action}.</li>
 * </ul>
 * </li>
 * <li>Otherwise, if an {@code ActionTree} does <i>not</i> have children, it
 * will <i>not</i>, by default, have a dropdown or sub-menu. Clicking it will do
 * one of the following:
 * <ul>
 * <li>If its {@code Action} is not set, it will be disabled.</li>
 * <li>Otherwise, if its {@code Action} is set, clicking it will be disabled.</li>
 * </ul>
 * </li>
 * 
 * @author Jordan
 * 
 */
public class ActionTree extends BasicTree<ActionTree> {

	// TODO Implement the ability to disable the ActionTree.

	// TODO Make a new sub-class for "radio style" selection. As a dropdown
	// menu, the sub-menu items should have checkboxes, but only one is selected
	// at a time.
	/**
	 * The default {@link #style}, {@link IAction#AS_PUSH_BUTTON}.
	 */
	public static final int defaultStyle = Action.AS_PUSH_BUTTON;

	/**
	 * The string displayed for the {@code ActionTree}'s contributions to
	 * widgets.
	 */
	public String text = null;
	/**
	 * The string displayed for the tool tip of the {@code ActionTree}'s
	 * contributions to widgets, if applicable.
	 */
	public String toolTipText = null;
	/**
	 * The style of the {@code ActionTree}'s contributions to widgets. This
	 * should be set based on the styles set in the class {@link Action}, e.g.,
	 * {@link IAction#AS_PUSH_BUTTON} or {@link IAction#AS_CHECK_BOX}.
	 * 
	 * @see #defaultStyle
	 */
	public Integer style = null;
	/**
	 * The image used for the {@code ActionTree}'s contributions to widgets.
	 */
	public ImageDescriptor image = null;
	/**
	 * The default {@link Action} associated with this {@code ActionTree}. If
	 * null, then the {@link ActionTree default behavior of the ActionTree} will
	 * take effect.
	 */
	public IAction action = null;

	/**
	 * Whether or not the {@code ActionTree} should be disabled.
	 * <p>
	 * <b>Note:</b> The default behavior of this class is to disable an
	 * {@code ActionTree} if its {@link #action} is unset and has no children
	 * regardless of this flag.
	 * </p>
	 */
	public boolean enabled = true;

	/**
	 * A map containing the {@code ActionTree}'s contributions--stored as
	 * {@link ActionTreeContribution}s--to widgets, keyed on the widgets.
	 */
	private final Map<Object, ActionTreeContribution> contributions = new IdentityHashMap<Object, ActionTreeContribution>();

	/**
	 * The {@link IMenuCreator} used to create context and sub-menus filled with
	 * the {@code ActionTree}'s child {@code ActionTree}s.
	 */
	private ActionTreeMenuCreator menuCreator = null;

	/**
	 * The default constructor. Creates a new {@code ActionTree} with no
	 * children.
	 */
	public ActionTree() {
		// Nothing to do.
	}

	/**
	 * The default copy constructor.
	 * 
	 * @param tree
	 *            The {@code ActionTree} to copy. If not null, the tree will be
	 *            copied, not including its descendants.
	 */
	public ActionTree(ActionTree tree) {
		// Set up the defaults.
		this();

		// If possible, copy all of the tree's properties.
		if (tree != null) {
			text = tree.text;
			toolTipText = tree.toolTipText;
			style = tree.style;
			// Deep copy the ImageDescriptor since this is a resource that can
			// be destroyed.
			if (tree.image != null) {
				ImageData imageData = tree.image.getImageData();
				image = ImageDescriptor.createFromImageData(imageData);
			}
			// We cannot clone or create a duplicate action because its run
			// method cannot be copied.
			tree.action = action;
		}
		// If necessary, throw an exception when the source tree is null.
		else {
			throw new IllegalArgumentException("ActionTree error: "
					+ "Cannot copy from null tree.");
		}

		return;
	}

	/**
	 * The full copy constructor.
	 * 
	 * @param tree
	 *            The {@code ActionTree} to copy. To copy, this value must not
	 *            be null.
	 * @param fullTree
	 *            If true, the tree and all of its descendants will be copied.
	 *            This creates an <i>entirely new tree!</i> If false, only this
	 *            node in the tree will be copied.
	 */
	public ActionTree(ActionTree tree, boolean fullTree) {
		// First, perform the standard, local copy constructor.
		this(tree);

		// If necessary, walk the other tree and copy all child nodes.
		if (tree != null && fullTree) {
			// Get a pre-order iterator to walk the other tree.
			Iterator<ActionTree> iterator;
			iterator = tree.iterator(TreeIterationOrder.PreOrder);
			// Skip the root node since it's already been copied.
			ActionTree node = iterator.next();
			ActionTree child;

			// Create two stacks: one to contain the current parent, and one to
			// contain how many children remain to be copied.
			Stack<ActionTree> parents = new Stack<ActionTree>();
			parents.push(getValue());
			Stack<Integer> childCounts = new Stack<Integer>();
			childCounts.push(node.getNumberOfChildren());

			while (iterator.hasNext()) {
				// Get the next node and copy it.
				node = iterator.next();
				child = new ActionTree(node);

				// Add the child to its parent and decrement the number of
				// children remaining to be copied for the current parent. If no
				// children are left, remove the parent and its remaining count.
				parents.peek().addChild(child);
				int remainingChildren = childCounts.pop() - 1;
				if (remainingChildren > 0) {
					parents.pop();
				} else {
					childCounts.push(remainingChildren);
				}

				// If the copied node has children, we need to update the two
				// stacks so its children are added.
				if (node.hasChildren()) {
					parents.push(child);
					childCounts.push(node.getNumberOfChildren());
				}
			}
		}

		return;
	}

	/**
	 * Gets the {@link IMenuCreator} used to create context and sub-menus filled
	 * with the {@code ActionTree}'s child {@code ActionTree}s.
	 * 
	 * @return The {@code IMenuCreator} for the {@code ActionTree}.
	 */
	protected IMenuCreator getMenuCreator() {
		if (menuCreator == null) {
			menuCreator = new ActionTreeMenuCreator(this);
		}
		return menuCreator;
	}

	/**
	 * Refreshes all of the contributions made by this {@code ActionTree}. This
	 * should be called after one or more of the {@code ActionTree}'s properties
	 * has been changed.
	 */
	public void refresh() {
		for (ActionTreeContribution contribution : contributions.values()) {
			contribution.refresh();
		}
	}

	/**
	 * Populates a {@code Menu} with the {@code ActionTree}'s actions.
	 * 
	 * @param menu
	 *            The {@code Menu} to fill.
	 */
	public void fill(Menu menu) {
		if (menu != null && !contributions.containsKey(menu)) {
			ActionTreeContribution contribution;
			contribution = new ActionTreeContribution(this);
			contributions.put(menu, contribution);
			contribution.fill(menu);
		}

		return;
	}

	/**
	 * Populates a {@code ToolBar} with the {@code ActionTree}'s actions.
	 * 
	 * @param menu
	 *            The {@code ToolBar} to fill.
	 */
	public void fill(ToolBar toolBar) {
		if (toolBar != null && !contributions.containsKey(toolBar)) {
			ActionTreeContribution contribution;
			contribution = new ActionTreeContribution(this);
			contributions.put(toolBar, contribution);
			contribution.fill(toolBar);
		}

		return;
	}

	/**
	 * Populates a {@code ContributionManager} with the {@code ActionTree}'s
	 * actions.
	 * 
	 * @param menu
	 *            The {@code ContributionManager} to fill.
	 */
	public void fill(ContributionManager manager) {
		if (manager != null && !contributions.containsKey(manager)) {
			ActionTreeContribution contribution;
			contribution = new ActionTreeContribution(this);
			contributions.put(manager, contribution);
			contribution.fill(manager);
		}

		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.BasicTree#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		// Note: The other object isn't null if the super method returns true.
		if (equals && this != object && object instanceof ActionTree) {
			ActionTree tree = (ActionTree) object;

			// Compare the following properties of an ActionTree:
			// text, toolTipText, style, image, and action.
			equals = (text == null ? tree.text == null : text.equals(tree.text))
					&& (toolTipText == null ? tree.toolTipText == null
							: toolTipText.equals(tree.toolTipText))
					&& style == tree.style
					&& (image == null ? tree.image == null : image
							.equals(tree.image))
					&& (action == null ? tree.action == null : action
							.equals(tree.action));
		}
		return equals;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.BasicTree#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		// Add hashes for the following properties of an ActionTree:
		// text, toolTipText, style, image, and action.
		hash = hash * 31 + (text == null ? 0 : text.hashCode());
		hash = hash * 31 + (toolTipText == null ? 0 : toolTipText.hashCode());
		hash = hash * 31 + style;
		hash = hash * 31 + (image == null ? 0 : image.hashCode());
		hash = hash * 31 + (action == null ? 0 : action.hashCode());
		return hash;
	}

	// ---- Extends ITree<T> ---- //
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bar.foo.tree.ITree#getValue()
	 */
	@Override
	public ActionTree getValue() {
		return this;
	}
	// -------------------------- //
}
