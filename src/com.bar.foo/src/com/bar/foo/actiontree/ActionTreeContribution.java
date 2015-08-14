package com.bar.foo.actiontree;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * This class manages {@link ActionContributionItem}s to widgets, like
 * {@code Menu}s and {@code ToolBar}s, for {@link ActionTree}s.
 * <p>
 * For example, to embed an {@code ActionTree} in a {@code Menu}, create an
 * {@code ActionTreeContribution} and call {@link #fill(Menu)}, passing in the
 * target {@code Menu}. You could also use a {@link MenuManager} with
 * {@link #fill(ContributionManager)} or embed the {@code ActionTree} into a
 * {@code ToolBar} with {@link #fill(ToolBar)}.
 * </p>
 * 
 * @author Jordan
 * 
 */
public class ActionTreeContribution {

	/**
	 * The {@link ActionTree} for which this provides {@link IContributionItem}s
	 * to {@code Control}s or {@code Menu}s.
	 */
	private final ActionTree actionTree;

	/**
	 * An object that manages contributing the {@link #item} to a widget, like a
	 * {@code Menu} or {@code ToolBar}, depending on the fill method called.
	 */
	private FillManager fillManager;

	/**
	 * The contribution to a widget, like a {@code Menu} or {@code ToolBar}.
	 */
	private ActionContributionItem item;

	/**
	 * The index of the {@link #item} in its container. The default value is -1,
	 * or the end of the list. However, when refreshed, the index will be
	 * preserved so the item will not lose its place in its parent container.
	 */
	private int index = -1;

	/**
	 * The default constructor.
	 * 
	 * @param actionTree
	 *            The {@link ActionTree} for which this provides
	 *            {@link IContributionItem}s to {@code Control}s or {@code Menu}
	 *            s.
	 */
	public ActionTreeContribution(ActionTree actionTree) {
		if (actionTree != null) {
			this.actionTree = actionTree;
		} else {
			this.actionTree = new ActionTree();
			throw new IllegalArgumentException("ActionTreeContribution error: "
					+ "Cannot provide contributions for null ActionTree.");
		}

		return;
	}

	/**
	 * Adds a new {@code MenuItem} representing the {@code ActionTree} to a
	 * {@code Menu}.
	 * 
	 * @param menu
	 *            The {@code Menu} which will get a new item.
	 */
	public void fill(final Menu menu) {
		if (menu != null && fillManager == null) {
			fillManager = new FillManager() {
				@Override
				public void fill() {
					item = getActionContributionItem();
					item.fill(menu, index);
				}

				@Override
				public void update() {
					// Determine the index of the MenuItem currently used by the
					// ActionContributionItem so that the Menu is not reordered.
					MenuItem menuItem = (MenuItem) item.getWidget();
					MenuItem[] items = menu.getItems();

					// Make sure the index matches the MenuItem's location in
					// the Menu.
					if (index >= items.length || menuItem != items[index]) {
						for (int i = 0; i < items.length; i++) {
							if (items[i] == menuItem) {
								index = i;
								break;
							}
						}
					}

					// Dispose the old ActionContributionItem.
					item.dispose();
					item = null;

					// Add the new ActionContributionItem to the Menu.
					fill();

					return;
				}
			};
			fillManager.fill();
		}

		return;
	}

	/**
	 * Adds a new {@code ToolItem} representing the {@code ActionTree} to a
	 * {@code ToolBar}.
	 * 
	 * @param toolBar
	 *            The {@code ToolBar} which will get a new item.
	 */
	public void fill(final ToolBar toolBar) {
		if (toolBar != null && fillManager == null) {
			fillManager = new FillManager() {
				@Override
				public void fill() {
					// Add the new ActionContributionItem to the ToolBar.
					item = getActionContributionItem();
					item.fill(toolBar, index);
				}

				@Override
				public void update() {
					// Determine the index of the ToolItem currently used by the
					// ActionContributionItem so that the ToolBar is not
					// reordered.
					ToolItem toolItem = (ToolItem) item.getWidget();
					ToolItem[] items = toolBar.getItems();

					// Make sure the index matches the ToolItem's location in
					// the ToolBar.
					if (index >= items.length || toolItem != items[index]) {
						for (int i = 0; i < items.length; i++) {
							if (items[i] == toolItem) {
								index = i;
								break;
							}
						}
					}

					// Dispose the old ActionContributionItem.
					item.dispose();
					item = null;

					// Add the new ActionContributionItem to the ToolBar.
					fill();

					return;
				}
			};
			fillManager.fill();
		}

		return;
	}

	/**
	 * Adds a new {@code ActionContributionItem} representing the
	 * {@code ActionTree} to a {@code ContributionManager}.
	 * 
	 * @param manager
	 *            The {@code ContributionManager} which will get a new item.
	 */
	public void fill(final ContributionManager manager) {
		if (manager != null && fillManager == null) {
			fillManager = new FillManager() {
				@Override
				public void fill() {
					// Add the new ActionContributionItem to the
					// ContributionManager.
					item = getActionContributionItem();
					// You can't use -1 as an index for ContributionManagers,
					// so we need to set it on the first pass.
					if (index >= 0) {
						manager.insert(index, item);
					} else {
						index = manager.getSize();
						manager.add(item);
					}
					// This has to be called when items are added to a
					// ContributionManager, otherwise they won't appear.
					manager.update(true);
					return;
				}

				@Override
				public void update() {
					// Determine the index of the ActionContributionItem in the
					// ContributionManager so the items are not reordered.
					IContributionItem[] items = manager.getItems();

					// Make sure the index matches the item's location in
					// the ContributionManager.
					if (index >= items.length || item != items[index]) {
						for (int i = 0; i < items.length; i++) {
							if (items[i] == item) {
								index = i;
								break;
							}
						}
					}

					// Dispose the old ActionContributionItem.
					manager.remove(item);
					item.dispose();
					item = null;

					// Add the new ActionContributionItem to the
					// ContributionManager.
					fill();

					return;
				}
			};
			fillManager.fill();
		}

		return;
	}

	/**
	 * Gets the current contribution {@link #item}. If the item has not been
	 * initialized, this method creates it.
	 * 
	 * @return The {@link #item}.
	 */
	private ActionContributionItem getActionContributionItem() {

		// The code below is structured roughly the same as the documentation
		// for ActionTree.

		// If the item is uninitialized, we need to create it.
		if (item == null) {
			// Get the current Action for the ActionTree.
			IAction action = actionTree.action;

			// Load the current Action properties.
			String text = actionTree.text;
			String toolTipText = actionTree.toolTipText;
			Integer style = actionTree.style;
			ImageDescriptor image = actionTree.image;

			// If the Action is set and any property unset, try to set the
			// property based on the ones available in the Action.
			if (action != null) {
				text = (text == null ? action.getText() : text);
				toolTipText = (toolTipText == null ? action.getToolTipText()
						: toolTipText);
				style = (style == null ? action.getStyle() : style);
				image = (image == null ? action.getImageDescriptor() : image);
			}

			// We cannot have a null style! Default to a button style.
			if (style == null) {
				style = IAction.AS_PUSH_BUTTON;
			}

			if (actionTree.hasChildren()) {
				// The style is overridden so we can get a dropdown/sub-menu.
				style = IAction.AS_DROP_DOWN_MENU;
				if (action == null) {
					// Create a dropdown-style Action whose default click brings
					// up the child Menu.
					action = new Action(text, style) {
						@Override
						public void run() {
							// Get the corresponding ToolItem and Menu.
							ToolItem toolItem = (ToolItem) item.getWidget();
							IMenuCreator menuCreator = actionTree
									.getMenuCreator();
							Menu menu = menuCreator
									.getMenu(toolItem.getParent());

							// We want to align the menu with the bottom-left
							// corner
							// of
							// the ToolItem arrow.
							Rectangle r = toolItem.getBounds();
							Point p = new Point(r.x, r.y + r.height);
							p = toolItem.getParent().toDisplay(p.x, p.y);
							menu.setLocation(p.x, p.y);
							menu.setVisible(true);

							return;
						}
					};
				} else {
					// Create a dropdown-style Action whose default click action
					// re-directs to the ActionTree.
					final IAction defaultAction = action;
					action = new Action(text, style) {
						@Override
						public void run() {
							defaultAction.run();
						}
					};
				}
				// Add a MenuCreator so that a dropdown- or sub-menu will show.
				action.setMenuCreator(actionTree.getMenuCreator());

			} else if (action == null) {
				// If no action is set, we need to create a dummy Action.
				action = new Action(text, style) {
					@Override
					public void run() {
						// Do nothing.
					}
				};
				// No Action is set and there are no children, so disable the
				// placeholder action.
				action.setEnabled(false);
			} else {
				// Lastly, the ActionTree has no children, but does have an
				// Action. Create a wrapper action that has the style/properties
				// of the ActionTree.
				final IAction defaultAction = action;
				action = new Action(text, style) {
					@Override
					public void run() {
						defaultAction.run();
					}
				};
			}

			// If the ActionTree is disabled, disable the exposed Action.
			if (!actionTree.enabled) {
				action.setEnabled(false);
			}

			// Update the unset properties of the exposed Action.
			// Text and style are already set!
			action.setToolTipText(toolTipText);
			action.setImageDescriptor(image);

			// We can now create the ActionContributionItem. Note that
			// ActionTrees without children will not have a Menu.
			item = new ActionContributionItem(action);
		}

		return item;
	}

	/**
	 * If the {@code ActionTreeContribution} has been attached to a widget via
	 * one of the fill methods, this method refreshes that contribution to sync
	 * it with any changes to the associated {@link #actionTree}.
	 */
	public void refresh() {
		if (fillManager != null) {
			fillManager.update();
		}
	}

	/**
	 * This is an interface used to link (or <i>contribute</i>) the
	 * {@code ActionTreeContribution} to a widget. Instances are to be
	 * initialized in the fill methods and used to enforce changes to the
	 * associated {@link ActionTreeContribution#actionTree actionTree} to the
	 * already contributed {@link ActionTreeContribution#item item}s.
	 * 
	 * @author Jordan
	 * 
	 */
	private interface FillManager {
		/**
		 * Adds the {@link ActionTreeContribution#item item} to a widget at the
		 * specified {@link ActionTreeContribution#index index}. If the index is
		 * -1, then the item should be appended to the widget (this is normally
		 * the default SWT behavior).
		 */
		public void fill();

		/**
		 * Replaces the existing {@link ActionTreeContribution#item item} with a
		 * new one to enforce changes to the associated
		 * {@link ActionTreeContribution#actionTree actionTree}.
		 */
		public void update();
	}
}
