package test.util.datagen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.datagen.RandomGenerator;

/**
 * RandomGenerator Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>九月 10, 2019</pre>
 */
public class RandomGeneratorTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }

    /**
     * Method: next()
     */
    @Test
    public void testInteger() throws Exception {
        RandomGenerator.Integer ri = new RandomGenerator.Integer(1, 10);
        for (int i = 0; i < 10; i++) {
            System.out.print(ri.next() + " ");
        }
        System.out.println();
    }

    @Test
    public void testFloat() throws Exception {
        RandomGenerator.Float rf = new RandomGenerator.Float(10.f, 20);
        for (int i = 0; i < 10; i++) {
            System.out.printf("%.3f ", rf.next());
        }
        System.out.println();
    }
}
