package com.bar.foo.actiontree;

import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

/**
 * This class provides an {@link IMenuCreator} that handles child
 * {@link ActionTree}s. It supports both popup menus and sub-menus. When the
 * underlying {@code ActionTree} has changed, then {@link #markDirty()} should
 * be called. The next time a {@code Menu} is requested, it will refresh based
 * on the child {@code ActionTree}s.
 * 
 * @author Jordan
 * 
 */
public class ActionTreeMenuCreator implements IMenuCreator {

	/**
	 * The parent {@code ActionTree}. {@code Menu}s are provided for this tree.
	 */
	private final ActionTree actionTree;

	/**
	 * The popup or context {@code Menu}.
	 */
	private Menu popupMenu = null;
	/**
	 * Whether or not the {@link #popupMenu} needs to be refreshed.
	 */
	private boolean popupDirty = true;
	/**
	 * The parent {@code Control}, usually a {@code Shell}, for the
	 * {@link #popupMenu}.
	 */
	private Control popupParent;

	/**
	 * The sub-{@code Menu}.
	 */
	private Menu subMenu = null;
	/**
	 * Whether or not the {@link #subMenu} needs to be refreshed.
	 */
	private boolean subMenuDirty = true;
	/**
	 * THe parent {@code Menu} for the {@link #subMenu}.
	 */
	private Menu subMenuParent;

	/**
	 * The default constructor.
	 * 
	 * @param actionTree
	 *            The {@link ActionTree} for which this will provide popup
	 *            (context) and/or sub-{@code Menu}s.
	 */
	public ActionTreeMenuCreator(ActionTree actionTree) {
		if (actionTree != null) {
			this.actionTree = actionTree;
		} else {
			this.actionTree = new ActionTree();
			throw new IllegalArgumentException("ActionTreeMenuCreator error: "
					+ "Cannot provide Menus for null ActionTree.");
		}

		return;
	}

	/**
	 * When called, the {@code Menu}s will be updated the next time they are
	 * shown.
	 */
	public void markDirty() {
		popupDirty = true;
		subMenuDirty = true;
	}

	// ---- Implements IMenuCreator ---- //
	/**
	 * Disposes both {@link #popupMenu} and {@link #subMenu}.
	 */
	@Override
	public void dispose() {
		dispose(popupMenu);
		popupMenu = null;
		popupDirty = true;

		dispose(subMenu);
		subMenu = null;
		subMenuDirty = true;

		return;
	}

	/**
	 * This gets the {@link #popupMenu} and refreshes it if {@link #popupDirty}
	 * is true.
	 */
	@Override
	public Menu getMenu(Control parent) {

		if (parent != null && (popupDirty || parent != popupParent)) {
			dispose(popupMenu);
			popupMenu = new Menu(parent);
			fillMenu(popupMenu);
			popupParent = parent;
			popupDirty = false;
		}

		return popupMenu;
	}

	/**
	 * This gets the {@link #subMenu} and refreshes it if {@link #subMenuDirty}
	 * is true.
	 */
	@Override
	public Menu getMenu(Menu parent) {

		if (parent != null && (subMenuDirty || parent != subMenuParent)) {
			dispose(subMenu);
			subMenu = new Menu(parent);
			fillMenu(subMenu);
			subMenuParent = parent;
			subMenuDirty = false;
		}

		return subMenu;
	}

	// --------------------------------- //

	/**
	 * Convenience method for disposing a {@code Menu} without throwing a null
	 * pointer or widget disposed exception.
	 * 
	 * @param menu
	 *            The menu to dispose.
	 */
	private void dispose(Menu menu) {
		if (menu != null && !menu.isDisposed()) {
			menu.dispose();
		}
	}

	/**
	 * Convenience method for filling a {@code Menu} with the {@code ActionTree}
	 * 's {@link #contributions}.
	 * 
	 * @param menu
	 *            The {@code Menu} to fill.
	 */
	private void fillMenu(Menu menu) {
		for (ActionTree childTree : actionTree.getChildren()) {
			childTree.fill(menu);
		}
	}

}
