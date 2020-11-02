package test.util.datastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.datastructure.MyLinkedList;

/**
 * MyLinkedList Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>九月 22, 2019</pre>
 */
public class MyLinkedListTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }

    @Test
    public void testSort() throws Exception {
        MyLinkedList<Integer> linkedList = new MyLinkedList<>();
        linkedList.add(5);
        linkedList.add(-9);
        linkedList.add(1);
        linkedList.add(20);
        linkedList.add(-11);
        linkedList.add(-3);
        linkedList.add(9);
        linkedList.add(4);
        linkedList.add(-1);
        linkedList.add(14);
        System.out.println(linkedList + "\n");

        linkedList.sort();
        System.out.println(linkedList);
    }
} 
