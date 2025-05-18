package com.univer.lab.deque;

import com.univer.lab.deque.impl.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListTest {

    private ArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new ArrayList<>();
    }

    @Test
    void add_and_size_success() {
        assertEquals(0, list.size(), "List should be empty during initialization");
        list.add(1);
        assertEquals(1, list.size(), "List size should be 1");
        list.add(2);
        assertEquals(2, list.size(), "List size should be 2");
        assertTrue(list.contains(1), "ArrayList contain 1");
        assertTrue(list.contains(2), "ArrayList contain 2");
    }

    @Test
    void put_success() {
        list.add(10);
        list.add(20);
        int oldValue = list.put(1, 30);
        assertEquals(20, oldValue, "Should return old value");
        assertTrue(list.contains(30), "Should return new value: 30");
        assertThrows(IndexOutOfBoundsException.class, () -> list.put(-1, 40));
        assertThrows(IndexOutOfBoundsException.class, () -> list.put(2, 40));
    }

    @Test
    void remove_by_index_success() {
        list.add(100);
        list.add(200);
        list.add(300);
        int removed = list.remove(1);
        assertEquals(200, removed, "Remove 200");
        assertEquals(2, list.size(), "Size should be less");
        assertFalse(list.contains(200), "No remove element in the list");
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(2));
    }

    @Test
    void add_all_success() {
        boolean changed = list.addAll(Arrays.asList(1, 2, 3));
        assertTrue(changed, "should be true");
        assertEquals(3, list.size(), "List size should ne equal count of elements");
        assertTrue(list.contains(1));
        assertTrue(list.contains(2));
        assertTrue(list.contains(3));

        changed = list.addAll(Collections.emptyList());
        assertFalse(changed, "addAll should return false");
    }

    @Test
    void remove_by_object_success() {
        list.add(5);
        list.add(10);
        list.add(15);
        boolean removed = list.remove((Integer) 10);
        assertTrue(removed, "should return true");
        assertEquals(2, list.size(), "Size should be less");
        assertFalse(list.contains(10), "No contain remove element");

        removed = list.remove((Integer) 42);
        assertFalse(removed, "return FALSE - if object is not found");
    }

    @Test
    void testContains() {
        list.add(7);
        list.add(8);
        list.add(9);
        assertTrue(list.contains(7));
        assertTrue(list.contains(8));
        assertTrue(list.contains(9));
        assertFalse(list.contains(10), "Should not contain 10");
    }
}
