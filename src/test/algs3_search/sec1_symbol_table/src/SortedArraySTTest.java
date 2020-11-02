package test.algs3_search.sec1_symbol_table.src;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.datastructure.SortedArrayST;
import util.datastructure.SortedSymbolTable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SortedArrayST Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十月 5, 2019</pre>
 */
public class SortedArraySTTest {

    @BeforeEach
    public void before() throws Exception {
    }

    @AfterEach
    public void after() throws Exception {
    }

    @Test
    public void testSimple() throws Exception {
        SortedSymbolTable<String, Integer> ast = new SortedArrayST<>();

        assertTrue(ast.isEmpty());
        ast.put("my", 1);
        ast.put("name", 2);
        ast.put("is", 3);
        ast.put("wutao", 4);
        ast.put(".", 5);

        for (String k : ast.keys())
            System.out.print(k + " ");
        System.out.println();
        for (String k : ast.keys("name", "wutao"))
            System.out.print(k + " ");
        System.out.println();

        assertFalse(ast.isEmpty());
        assertEquals(ast.size(), 5);
        assertEquals(ast.size("name", "z"), 2);

        assertEquals(ast.get("name"), new Integer(2));
        assertEquals(ast.get("null"), null);

        assertTrue(ast.contains("is"));
        assertEquals(ast.delete("is"), new Integer(3));
        assertEquals(ast.get("is"), null);
        assertFalse(ast.contains("is"));
        assertEquals(ast.size(), 4);
        assertEquals(ast.delete("null"), null);

        assertEquals(ast.floor("n").value(), new Integer(1));
        assertEquals(ast.ceil("name").value(), new Integer(2));

        assertEquals(ast.deleteMin().value(), new Integer(5));
        assertEquals(ast.deleteMax().value(), new Integer(4));
        assertEquals(ast.size(), 2);
    }
}
