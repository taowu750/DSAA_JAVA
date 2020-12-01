package algs5_string.sec3_substring.src;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractStringSearch {

    public abstract int search(String pat, String txt);

    @SuppressWarnings("SpellCheckingInspection")
    public static void test(AbstractStringSearch search) {
        assertEquals(search.search("AACAA", "AABRAACADABRAACAADABRA"), 12);
        assertEquals(search.search("NEEDLE", "INANAYSTACKNEEDLEINA"), 11);
        assertEquals(search.search("ABR", "ABACADABRAC"), 6);
        assertEquals(search.search("ABABAC", "BCBAABACAABABACAA"), 9);
        assertEquals(search.search("TOM", "JERRY LOVE TO"), -1);
    }
}
