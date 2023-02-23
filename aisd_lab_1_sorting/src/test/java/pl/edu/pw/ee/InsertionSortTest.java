package pl.edu.pw.ee;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class InsertionSortTest extends CommonSortTest {

    public InsertionSortTest() {
        this.sorter = new InsertionSort();
    }

    @Test
    public void shouldSortArrayWhenArrayHasElementsInOptimisticVariant() {
        //given
        double[] nums = {1, 2, 3, 4, 5};

        //when
        sorter.sort(nums);

        //then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, EPS);
    }

    @Test
    public void shouldSortArrayWhenArrayHasElementsInPesimisticVariant() {
        //given
        double[] nums = {5, 4, 3, 2, 1};

        //when
        sorter.sort(nums);

        //then
        double[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, nums, EPS);
    }
}
