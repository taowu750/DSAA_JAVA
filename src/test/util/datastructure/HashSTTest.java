package test.util.datastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.algs.StdRandom;
import util.datastructure.HashST;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * HashST Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十月 15, 2019</pre>
 */
public class HashSTTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }


    @Test
    public void testSimple() throws Exception {
        HashST<Integer, String> hst = new HashST<>();

        assertTrue(hst.isEmpty());

        for (int i = 0; i < 100; i++) {
            int k = StdRandom.uniform(-1000, 1000);
            hst.put(k, k + "");
        }

        for (Integer k : hst.keys()) {
            System.out.println(k + " : " + hst.get(k));
        }
    }
} 
