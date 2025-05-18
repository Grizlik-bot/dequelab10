package com.univer.lab.deque.api;

import java.util.Collection;

public interface Deque<E>  {

    void addFirst(E e);

    void addLast(E e);

    E removeFirst();

    E removeLast();

    E getFirst();

    E getLast();

    /**
     * Adds all of the elements in the specified collection at the end
     * of this deque, as if by calling {@link #addLast} on each one,
     * in the order that they are returned by the collection's iterator.
     *
     * @param c the elements to be inserted into this deque
     * @return {@code true} if this deque changed as a result of the call
     * @throws ClassCastException if the class of an element of the specified
     *         collection prevents it from being added to this deque
     */
    boolean addAll(Collection<? extends E> c);


    /**
     * Removes the first occurrence of the specified element from this deque.
     * If the deque does not contain the element, it is unchanged.
     *

     * @param o element to be removed from this deque, if present
     * @return {@code true} if an element was removed as a result of this call
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this deque
     */
    boolean remove(Object o);

    /**
     * Returns {@code true} if this deque contains the specified element.
     *
     * @param o element whose presence in this deque is to be tested
     * @return {@code true} if this deque contains the specified element
     * @throws ClassCastException if the class of the specified element
     *         is incompatible with this deque
     */
    boolean contains(Object o);

    /**
     * Returns the number of elements in this deque.
     *
     * @return the number of elements in this deque
     */
    int size();
}