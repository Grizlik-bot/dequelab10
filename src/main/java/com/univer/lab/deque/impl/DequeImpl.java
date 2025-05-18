package com.univer.lab.deque.impl;

import com.univer.lab.deque.api.Deque;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Implementation of the Deque interface using a doubly-linked list.
 * @param <E> the type of elements in the deque
 */
public class DequeImpl<E> implements Deque<E> {

    private static class Node<E> {
        E item;
        Node<E> previousElement;
        Node<E> nextElement;

        Node(E item, Node<E> previousElement, Node<E> nextElement) {
            this.item = item;
            this.previousElement = previousElement;
            this.nextElement = nextElement;
        }
    }

    private Node<E> headNode;
    private Node<E> tailNode;
    private int size;

    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, null, headNode);
        if (headNode != null) {
            headNode.previousElement = newNode;
        } else {
            tailNode = newNode;
        }
        headNode = newNode;
        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e, tailNode, null);
        if (tailNode != null) {
            tailNode.nextElement = newNode;
        } else {
            headNode = newNode;
        }
        tailNode = newNode;
        size++;
    }

    @Override
    public E removeFirst() {
        if (headNode == null) {
            throw new NoSuchElementException();
        }
        E item = headNode.item;
        headNode = headNode.nextElement;
        if (headNode != null) {
            headNode.previousElement = null;
        } else {
            tailNode = null;
        }
        size--;
        return item;
    }

    @Override
    public E removeLast() {
        if (tailNode == null) {
            throw new NoSuchElementException();
        }
        E item = tailNode.item;
        tailNode = tailNode.previousElement;
        if (tailNode != null) {
            tailNode.nextElement = null;
        } else {
            headNode = null;
        }
        size--;
        return item;
    }

    @Override
    public E getFirst() {
        if (headNode == null) throw new NoSuchElementException();
        return headNode.item;
    }

    @Override
    public E getLast() {
        if (tailNode == null) throw new NoSuchElementException();
        return tailNode.item;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            addLast(e);
            modified = true;
        }
        return modified;
    }

    @Override
    public boolean remove(final Object o) {
        for (Node<E> current = headNode; current != null; current = current.nextElement) {
            if ((o == null && current.item == null) || (o != null && o.equals(current.item))) {
                if (current.previousElement != null) {
                    current.previousElement.nextElement = current.nextElement;
                } else {
                    headNode = current.nextElement;
                }
                if (current.nextElement != null) {
                    current.nextElement.previousElement = current.previousElement;
                } else {
                    tailNode = current.previousElement;
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(final Object o) {
        for (Node<E> current = headNode; current != null; current = current.nextElement) {
            if ((o == null && current.item == null) || (o != null && o.equals(current.item))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }
}
