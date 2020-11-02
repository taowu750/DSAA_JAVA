package test.util.datagen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.datagen.ArrayData;
import util.datagen.CountingGenerator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * CountingGenerator Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>九月 9, 2019</pre>
 */
public class CountingGeneratorTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }


    @Test
    public void testInteger() throws Exception {
        Integer[] a = ArrayData.array(Integer.class, new CountingGenerator.Integer(), 10);
        assertArrayEquals(a, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});

        a = ArrayData.array(Integer.class, new CountingGenerator.Integer(1), 10);
        assertArrayEquals(a, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        a = ArrayData.array(Integer.class, new CountingGenerator.Integer(false), 10);
        assertArrayEquals(a, new Integer[]{0, -1, -2, -3, -4, -5, -6, -7, -8, -9});

        a = ArrayData.array(Integer.class, new CountingGenerator.Integer(1, 2), 10);
        assertArrayEquals(a, new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19});

        a = ArrayData.array(Integer.class, new CountingGenerator.Integer(10, false), 10);
        assertArrayEquals(a, new Integer[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1});

        a = ArrayData.array(Integer.class, new CountingGenerator.Integer(5, 2, false), 10);
        assertArrayEquals(a, new Integer[]{5, 3, 1, -1, -3, -5, -7, -9, -11, -13});
    }
} 
