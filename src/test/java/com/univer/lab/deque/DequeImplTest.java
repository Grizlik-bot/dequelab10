package com.univer.lab.deque;

import com.univer.lab.deque.impl.DequeImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DequeImplTest {

    @Test
    void testAddFirst() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        deque.addFirst(10);
        assertEquals(10, deque.getFirst());
        assertEquals(10, deque.getLast());
        assertEquals(1, deque.size());
    }

    @Test
    void testAddLast() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        deque.addLast(19);
        assertEquals(19, deque.getFirst());
        assertEquals(19, deque.getLast());
        assertEquals(1, deque.size());
    }

    @Test
    void testRemoveFirst() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        deque.addFirst(21);
        assertEquals(21, deque.removeFirst());
        assertEquals(0, deque.size());
    }

    @Test
    void testRemoveLast() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        deque.addLast(40);
        assertEquals(40, deque.removeLast());
        assertEquals(0, deque.size());
    }

    @Test
    void testRemoveFirst_ThrowsException() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        assertThrows(NoSuchElementException.class, deque::removeFirst);
    }

    @Test
    void testRemoveLast_ThrowsException() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        assertThrows(NoSuchElementException.class, deque::removeLast);
    }

    @Test
    void testGetFirst() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        deque.addFirst(53);
        assertEquals(53, deque.getFirst());
    }

    @Test
    void testGetLast() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        deque.addLast(60);
        assertEquals(60, deque.getLast());
    }

    @Test
    void testAddAll() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        deque.addAll(Arrays.asList(11, 12, 13));
        assertEquals(3, deque.size());
        assertEquals(11, deque.getFirst());
        assertEquals(13, deque.getLast());
    }

    @Test
    void testRemoveElement() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        deque.addLast(7);
        deque.addLast(8);
        deque.addLast(9);
        assertTrue(deque.remove(8));
        assertFalse(deque.contains(8));
        assertEquals(2, deque.size());
    }

    @Test
    void testContains() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        deque.addFirst(100);
        assertTrue(deque.contains(100));
        assertFalse(deque.contains(200));
    }

    @Test
    void testSize() {
        DequeImpl<Integer> deque = new DequeImpl<>();
        deque.addLast(110);
        deque.addLast(120);
        assertEquals(2, deque.size());
    }

}
