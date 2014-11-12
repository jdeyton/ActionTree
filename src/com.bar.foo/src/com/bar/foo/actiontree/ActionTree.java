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
 * @author Jordan
 * 
 */
public class ActionTree extends BasicTree<ActionTree> {

	// TODO The contribution should be disabled if the menu is empty, there is
	// no default action, or if enabled is set to false.

	private static final String defaultText = "(ActionTree)";
	private static final String defaultToolTipText = null;
	private static final int defaultStyle = Action.AS_PUSH_BUTTON;
	private static final ImageDescriptor defaultImage = null;

	public String text = defaultText;
	public String toolTipText = defaultToolTipText;
	public int style = defaultStyle;
	public ImageDescriptor image = defaultImage;

	public boolean enabled = true;

	private IAction action = null;

	private final Map<Object, ActionTreeContribution> contributions = new IdentityHashMap<Object, ActionTreeContribution>();

	private ActionTreeMenuCreator menuCreator = null;

	protected IMenuCreator getMenuCreator() {
		if (menuCreator == null) {
			menuCreator = new ActionTreeMenuCreator(this);
		}
		return menuCreator;
	}

	public IAction getAction() {
		return action;
	}

	public void setAction(IAction action) {
		if (action != null) {
			text = action.getText();
			toolTipText = action.getToolTipText();
			style = action.getStyle();
			image = action.getImageDescriptor();
		} else {
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
	 * should be called after the text, tool tip, style, or image is changed.
	 */
	public void refresh() {
		// TODO
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

	// ---- Extends ITree<T> ---- //
	@Override
	public ActionTree getValue() {
		return this;
	}
	// -------------------------- //
}
