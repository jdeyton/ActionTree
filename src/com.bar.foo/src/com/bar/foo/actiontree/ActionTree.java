package com.bar.foo.actiontree;

import java.util.IdentityHashMap;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;

import com.bar.foo.tree.BasicTree;

/**
 * I prefer the BasicTree version because it's more concise. The developer also
 * does not need to make any calls to {@link #getValue()} when dealing with the
 * iterators, either. You also cannot define this class as
 * {@code ActionTree extends SimpleTree<ActionTree>} because you should call the
 * super constructor with the ActionTree itself as the value.
 * 
 * TODO Update this documentation to describe the default behavior of the
 * ActionTree.
 * 
 * @author Jordan
 * 
 */
public class ActionTree extends BasicTree<ActionTree> {

	// TODO The contribution should be disabled if the menu is empty, there is
	// no default action, or if enabled is set to false.

	/**
	 * The default {@link #text}, "(ActionTree)"
	 */
	private static final String defaultText = "(ActionTree)";
	/**
	 * The default {@link #toolTipText}, (no tool tip, or null).
	 */
	private static final String defaultToolTipText = null;
	/**
	 * The default {@link #style}, {@link IAction#AS_PUSH_BUTTON}.
	 */
	private static final int defaultStyle = Action.AS_PUSH_BUTTON;
	/**
	 * The default {@link #image}, (no image, or null).
	 */
	private static final ImageDescriptor defaultImage = null;

	/**
	 * The string displayed for the {@code ActionTree}'s contributions to
	 * widgets.
	 * 
	 * @see #defaultText
	 */
	public String text = defaultText;
	/**
	 * The string displayed for the tool tip of the {@code ActionTree}'s
	 * contributions to widgets, if applicable.
	 * 
	 * @see defaultToolTipText
	 */
	public String toolTipText = defaultToolTipText;
	/**
	 * The style of the {@code ActionTree}'s contributions to widgets. This
	 * should be set based on the styles set in the class {@link Action}, e.g.,
	 * {@link IAction#AS_PUSH_BUTTON} or {@link IAction#AS_CHECK_BOX}.
	 * 
	 * @see #defaultStyle
	 */
	public int style = defaultStyle;
	/**
	 * The image used for the {@code ActionTree}'s contributions to widgets.
	 * 
	 * @see #defaultImage
	 */
	public ImageDescriptor image = defaultImage;

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
	 * The default {@link Action} associated with this {@code ActionTree}. If
	 * null, then the {@link ActionTree default behavior of the ActionTree} will
	 * take effect.
	 */
	private IAction action = null;

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
		// TODO
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
		// TODO
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
	 * Gets the current {@link #action} associated with this {@code ActionTree}.
	 * This value may be null, in which case the {@link ActionTree default
	 * behavior of the ActionTree} will take effect.
	 * 
	 * @return The {@code ActionTree}'s associated JFace {@code Action}.
	 */
	public IAction getAction() {
		return action;
	}

	/**
	 * Sets the current {@link #action} associated with this {@code ActionTree}.
	 * 
	 * @param action
	 *            If not null, then the related properties ({@link #text},
	 *            {@link #image}, and such) will be synchronized with the
	 *            {@code Action}. If null, then the {@link ActionTree default
	 *            behavior of the ActionTree} will take effect.
	 */
	public void setAction(IAction action) {
		// If the Action is not null, update the other properties.
		if (action != null) {
			text = action.getText();
			toolTipText = action.getToolTipText();
			style = action.getStyle();
			image = action.getImageDescriptor();
		}
		// If the Action is null, set the properties to their defaults.
		else {
			text = defaultText;
			toolTipText = defaultToolTipText;
			style = defaultStyle;
			image = defaultImage;
		}
		this.action = action;

		return;
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
	@Override
	public ActionTree getValue() {
		return this;
	}
	// -------------------------- //
}
