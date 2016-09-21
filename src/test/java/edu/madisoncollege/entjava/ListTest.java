package edu.madisoncollege.entjava;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Practice working with JUnit by writing some test cases for the Interface List<E>:
 * 1.1 void add(int index, E e)
 * 1.2 void remove(int index)
 * 1.3 void get(int index)
 *
 * Add at least one test for an exception.
 */
public class ListTest {

    private List<String> myList;

    @Before
    public void setup() {
        myList = new ArrayList<String>();
        myList.add("Item 1");
        myList.add("Item 2");
        myList.add("Item 3");
    }

    @Test
    public void testAddSuccess() {
        String elementToInsert = "Item 4";

        myList.add(elementToInsert);
        assertEquals("List size is incorrect", 4, myList.size());
        assertTrue("List missing inserted element", myList.contains(elementToInsert));
    }

    @Test
    public void testGetSuccess() {
        String elementToGet = "Item 1";

        assertEquals("List element to get does not match", elementToGet, myList.get(0));
    }

    @Test
    public void testRemoveSuccess() {
        int listSize = myList.size();
        String itemToRemove = myList.get(0);

        myList.remove(0);
        assertEquals("List remove size is incorrect", listSize - 1, myList.size());
        assertTrue("List removed item is present", !myList.contains(itemToRemove));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowsIndexOutOfBoundsException() {
        int listSize = myList.size();

        myList.get(listSize + 1);
    }

}