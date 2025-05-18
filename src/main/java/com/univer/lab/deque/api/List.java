package com.univer.lab.deque.api;

import java.util.Collection;

public interface List<E> {

    /**
     * Add element to the end of the list.
     *
     * @param e the elements to be inserted into this list
     */
    void add(E e);

    /**
     * Replaces the element at the specified position in this list with the
     * specified element (optional operation).
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    E put(int index, E element);

    /**
     * Removes the element at the specified position in this list (optional
     * operation).  Shifts any subsequent elements to the left (subtracts one
     * from their indices).  Returns the element that was removed from the
     * list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *         ({@code index < 0 || index >= size()})
     */
    E remove(int index);

    /**
     * Adds all of the elements in the specified collection at the end
     * of this list, as if by calling {@link #add} on each one,
     * in the order that they are returned by the collection's iterator.
     *
     * @param c the elements to be inserted into this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException if the class of an element of the specified
     *         collection prevents it from being added to this list
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * Removes the first occurrence of the specified element from this list.
     * If the list does not contain the element, it is unchanged.
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if an element was removed as a result of this call
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this list
     */
    boolean remove(Object o);

    /**
     * Returns {@code true} if this list contains the specified element.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this list
     */
    boolean contains(Object o);

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    int size();
}
