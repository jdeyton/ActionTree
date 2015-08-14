package com.bar.foo.tree;

import java.util.Iterator;
import java.util.List;

import com.bar.foo.tree.iterator.TreeIterationOrder;

/**
 * This interface provides a tree structure that uses Java generics to enforce a
 * uniform super type across the tree. Node that the nodes themselves are the
 * type of object stored in the tree, hence objects are not "wrapped" in a node.
 * <p>
 * <b>Note:</b> Cycles should not be placed in the tree (in which case it is no
 * longer a tree, but a graph).
 * </p>
 * 
 * @author Jordan
 *
 * @param <T>
 *            The type of object. Must be an implementation of ITree.
 */
public interface ITree<T extends ITree<T>> extends Iterable<T> {

	/**
	 * Adds a new child to this tree node. Any number of children may be added,
	 * but the same child may not be added twice.
	 * 
	 * @param child
	 *            The child node to add.
	 * @return True if the child could be added, false otherwise.
	 */
	public boolean addChild(T child);

	/**
	 * Compares this tree with another tree. A boolean can be specified in which
	 * case either the nodes or their full sub-trees are compared.
	 * 
	 * @param object
	 *            The other tree to compare against.
	 * @param fullTree
	 *            If true, the entire tree will be compared. If false, only the
	 *            node-specific content of the two trees will be compared.
	 * @return True if the two trees are equivalent (depending on the full tree
	 *         boolean), false otherwise.
	 */
	public boolean equals(ITree<T> object, boolean fullTree);

	/**
	 * Gets the child of the tree at the specified index.
	 * 
	 * @param index
	 *            The insertion-order-specific index of the child node.
	 * @return The child at that location, or {@code null} if the index is
	 *         invalid.
	 */
	public T getChild(int index);

	/**
	 * Gets the list of children of this node in their original insertion order.
	 * 
	 * @return The list of children, or an empty list if no children are
	 *         present.
	 */
	public List<T> getChildren();

	/**
	 * Gets the number of children of this node.
	 * 
	 * @return The number of child nodes.
	 */
	public int getNumberOfChildren();

	/**
	 * Gets the parent of this node in the tree.
	 * 
	 * @return The parent, or {@code null} if this is the root node.
	 */
	public T getParent();

	/**
	 * Gets the node itself cast as its original type.
	 * <p>
	 * This <b>must</b> be implemented by the outermost concrete sub-class. The
	 * implementation need only contain the code:
	 * </p>
	 * <p>
	 * {@code return this;}
	 * </p>
	 * 
	 * @return <b>this</b> (an object {@code T extends ITree<T>}).
	 */
	public T getValue();

	/**
	 * Gets whether or not the specified tree is a child of this node.
	 * 
	 * @param child
	 *            The child to check.
	 * @return True if the specified tree is a child of this node, false
	 *         otherwise.
	 */
	public boolean hasChild(T child);

	/**
	 * Gets whether or not the node has any children.
	 * 
	 * @return True if the tree node has children, false otherwise.
	 */
	public boolean hasChildren();

	/**
	 * Gets the hash code for the tree. A boolean can be specified in which case
	 * either the node itself or its full sub-tree is used to compute the hash
	 * code.
	 * 
	 * @param fullTree
	 *            If true, the full sub-tree will be used to compute the has
	 *            code. Otherwise, only the node itself will be used.
	 * @return The hash code of the node or its full sub-tree depending on the
	 *         specified flag.
	 */
	public int hashCode(boolean fullTree);

	/**
	 * Gets an iterator for the tree.
	 * 
	 * @param order
	 *            The iteration order to be used for the iterator.
	 * @return An iterator for the tree based on the specified iteration order.
	 */
	public Iterator<T> iterator(TreeIterationOrder order);

	/**
	 * Removes the child node at the specified index.
	 * 
	 * @param index
	 *            The index of the child to remove.
	 * @return The removed child, or {@code null} if the index was invalid.
	 */
	public T removeChild(int index);

	/**
	 * Removes the specified child node from the list of children.
	 * 
	 * @param child
	 *            The child node to remove.
	 * @return True if a node was removed from the tree, false otherwise
	 *         (including if it was not in the tree).
	 */
	public boolean removeChild(T child);
}
