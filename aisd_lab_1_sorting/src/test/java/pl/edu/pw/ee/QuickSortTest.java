package pl.edu.pw.ee;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class QuickSortTest extends CommonSortTest {

    public QuickSortTest() {
        this.sorter = new QuickSort();
    }

    @Test
    public void shouldSortArrayWhenArrayHasElementsInOptimisticVariant() {
        //given
        double[] nums = {6, 3, 5, 7, 8, 9, 4};

        //when
        sorter.sort(nums);

        //then
        double[] expected = {3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortArrayWhenArrayHasElementsInPesimisticVariant() {
        //given
        double[] nums = {4, 5, 6, 7, 8, 9, 10, 11, 12};

        //when
        sorter.sort(nums);

        //then
        double[] expected = {4, 5, 6, 7, 8, 9, 10, 11, 12};
        assertArrayEquals(expected, nums, EPS);
    }
}
