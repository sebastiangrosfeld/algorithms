package pl.edu.pw.ee;

import java.util.Arrays;
import java.util.Random;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.services.Sorting;

public abstract class CommonSortTest {

    protected Sorting sorter;
    public final int EPS = 0;
    public Random random1, random2;
    public final int SIZE = 100000;
    public final int MULTIPLIER = 10000;
    public final int SEED = 14;

    @Before
    public void setUp() {
        random1 = new Random(SEED);
        random2 = new Random(SEED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenArrayIsNull() {
        //given
        double[] nums = null;

        //when
        sorter.sort(nums);

        //then
        assert false;
    }

    @Test
    public void shouldSortArrayWhenArrayHasNoElements() {
        //given
        double[] nums = {};

        //when
        sorter.sort(nums);

        //then
        int expectedSize = 0;
        assertEquals(expectedSize, nums.length);
    }

    @Test
    public void shouldSortArrayWhenArrayHasOneElement() {
        //given
        double[] nums = {1};

        //when
        sorter.sort(nums);

        //then
        double[] expected = {1};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortArrayWhenArrayIsUnsorted() {
        //given
        double[] nums = {-1, 2, 4, -2, 6, 3, 0, 8, -4, 7, 3, 0};

        //when
        sorter.sort(nums);

        //then
        double[] expected = {-4, -2, -1, 0, 0, 2, 3, 3, 4, 6, 7, 8};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortArrayWhenArrayIsHalfSorted() {
        //given
        double[] nums = {-2, -1, 0, 2, 4, 6, 3, 5};

        //when
        sorter.sort(nums);

        //then
        double[] expected = {-2, -1, 0, 2, 3, 4, 5, 6};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortArrayWhenNumsAreRandomlyGenerated() {
        //given
        double[] nums = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            nums[i] = MULTIPLIER * random1.nextDouble();
        }

        //when
        sorter.sort(nums);

        //then
        double[] expected = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            expected[i] = MULTIPLIER * random2.nextDouble();
        }
        Arrays.sort(expected);
        assertArrayEquals(expected, nums, EPS);
    }
}
