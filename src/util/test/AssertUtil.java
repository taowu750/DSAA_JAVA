package util.test;


/**
 * 提供对各类输入正确性的检测
 */
public class AssertUtil {

    private AssertUtil() {
        throw new IllegalStateException("You cannot obtain objects of this class!");
    }


    public static void assertTrue(boolean expression, String errorMessage) {
        try {
            assertExpression(expression);
        } catch (AssertionError e) {
            System.err.println("assertEquals failed: " + errorMessage);
        }
    }

    public static void assertTrue(boolean expression) {
        assertTrue(expression, "This condition does not hold the judgement expression!");
    }

    public static void assertEquals(Object o1, Object o2, String errorMessage) {
        try {
            assertExpression(o1.equals(o2));
        } catch (AssertionError e) {
            System.err.println("assertEquals failed: " + errorMessage);
        }
    }

    public static void assertEquals(Object o1, Object o2) {
        assertEquals(o1, o2, "The two objects are not equal!");
    }

    public static void assertEquals(byte b1, byte b2, String errorMessage) {
        try {
            assertExpression(b1 == b2);
        } catch (AssertionError e) {
            System.err.println("assertEquals failed: " + errorMessage);
        }
    }

    public static void assertEquals(byte b1, byte b2) {
        assertEquals(b1, b2, "The two bytes are not equal!");
    }

    public static void assertEquals(char c1, char c2, String errorMessage) {
        try {
            assertExpression(c1 == c2);
        } catch (AssertionError e) {
            System.err.println("assertEquals failed: " + errorMessage);
        }
    }

    public static void assertEquals(char c1, char c2) {
        assertEquals(c1, c2, "The two chars are not equal!");
    }

    public static void assertEquals(short s1, short s2, String errorMessage) {
        try {
            assertExpression(s1 == s2);
        } catch (AssertionError e) {
            System.err.println("assertEquals failed: " + errorMessage);
        }
    }

    public static void assertEquals(short s1, short s2) {
        assertEquals(s1, s2, "The two shorts are not equal!");
    }

    public static void assertEquals(int i1, int i2, String errorMessage) {
        try {
            assertExpression(i1 == i2);
        } catch (AssertionError e) {
            System.err.println("assertEquals failed: " + errorMessage);
        }
    }

    public static void assertEquals(int i1, int i2) {
        assertEquals(i1, i2, "The two ints are not equal!");
    }

    public static void assertEquals(long l1, long l2, String errorMessage) {
        try {
            assertExpression(l1 == l2);
        } catch (AssertionError e) {
            System.err.println("assertEquals failed: " + errorMessage);
        }
    }

    public static void assertEquals(long l1, long l2) {
        assertEquals(l1, l2, "The two longs are not equal!");
    }

    public static void assertEquals(float f1, float f2, String errorMessage) {
        try {
            assertExpression(Float.compare(f1, f2) == 0);
        } catch (AssertionError e) {
            System.err.println("assertEquals failed: " + errorMessage);
        }
    }

    public static void assertEquals(float f1, float f2) {
        assertEquals(f1, f2, "The two floats are not equal!");
    }

    public static void assertEquals(double d1, double d2, String errorMessage) {
        try {
            assertExpression(Double.compare(d1, d2) == 0);
        } catch (AssertionError e) {
            System.err.println("assertEquals failed: " + errorMessage);
        }
    }

    public static void assertEquals(double d1, double d2) {
        assertEquals(d1, d2, "The two doubles are not equal!");
    }

    private static void assertExpression(Boolean expression) throws AssertionError {
        if (!expression) {
            throw new AssertionError();
        }
    }
}
