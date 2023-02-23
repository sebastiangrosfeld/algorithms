package pl.edu.pw.ee;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pl.edu.pw.ee.services.PatternSearch;

public class DeterministicFiniteAutomatonTextSearchTest {

    private PatternSearch pattern = new DeterministicFiniteAutomatonTextSearch("OLA");

    @Test
    public void test0_jest_1_wzorzec() {

        int result = pattern.findFirst("AAAOLAQ");

        int expected = 3;

        assertEquals(expected, result);
    }

    @Test
    public void test1_nie_ma_wzorca_first() {

        int result = pattern.findFirst("AAAOLQ");

        int expected = -1;

        assertEquals(expected, result);
    }

    @Test
    public void test2_nie_ma_wzorca_all() {

        int result[] = pattern.findAll("AAAOLQ");

        int expected[] = new int[0];

        assertArrayEquals(expected, result);
    }

    @Test
    public void test3_sa_2_wzorce_i_znajduje_pierwszy() {

        int result = pattern.findFirst("AAAOLAQOLA");

        int expected = 3;

        assertEquals(expected, result);
    }

    @Test
    public void test4_wszystkie_wzorce() {
        int[] result = pattern.findAll("OLAAAAOLAIIOLA");

        int[] expected = new int[3];
        expected[0] = 0;
        expected[1] = 6;
        expected[2] = 11;

        int expectedsize = 3;
        int resultsize = result.length;
        assertEquals(expectedsize, resultsize);
        assertArrayEquals(expected, result);
    }

    @Test
    public void test5_podane_wzorce_first() {
        pattern = new DeterministicFiniteAutomatonTextSearch("ABABA");
        int result = pattern.findFirst("ABABABABA");

        int expected = 0;

        assertEquals(expected, result);
    }

    @Test
    public void test6_podane_wzorce_all() {
        pattern = new DeterministicFiniteAutomatonTextSearch("ABABA");
        int result[] = pattern.findAll("ABABABABA");

        int expected[] = new int[3];
        expected[0] = 0;
        expected[1] = 2;
        expected[2] = 4;

        assertArrayEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test7_wyjatek_pattern() {
        pattern = new DeterministicFiniteAutomatonTextSearch(null);
        int result[] = pattern.findAll("ABABABABA");

        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void test8_wyjatek_text() {
        pattern = new DeterministicFiniteAutomatonTextSearch("HUHU");
        int result[] = pattern.findAll(null);

        assert false;
    }
}
