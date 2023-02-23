package pl.edu.pw.ee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class HeapTest {

    private Heap<Double> heap;
    List<Double> data = new ArrayList<>();
    private Random random;
    private final int SEED = 18;
    private final int SIZE = 50;

    @Before
    public void setUp() {
        heap = new Heap(data);
        random = new Random(SEED);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhenTryPutAndDataIsNotHeap() {
        //given
        data.add(1.0);
        data.add(3.0);

        //when
        heap.put(4.0);

        //then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhenTryPopAndDataIsNotHeap() {
        //given
        data.add(1.0);
        data.add(3.0);

        //when
        heap.pop();

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenItemIsNull() {
        //given
        Double item = null;

        //when
        heap.put(item);

        //then
        assert false;
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void shouldThrowArrayIndexOutOfBoundsExceptionWhenPopFromEmptyHeap() {
        //given
        data.removeAll(data);

        //when
        heap.pop();

        //then
        assert false;
    }

    @Test
    public void shouldReturnElementWhenHeapHasOneElement() {
        //given
        Double item = 7.0;

        //when
        heap.put(item);
        Double result = heap.pop();

        //then
        Double expected = 7.0;
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnBiggestElementWhenHeapHasTwoElements() {
        //given
        Double firstItem = 1.0;
        Double secondItem = 6.0;

        //when
        heap.put(firstItem);
        heap.put(secondItem);
        Double result = heap.pop();

        //then
        Double expected = 6.0;
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnBiggestElementWhenHeapHasFewElements() {
        //given
        Double firstItem = 1.0;
        Double secondItem = 6.0;
        Double thirdItem = 3.0;
        Double fourthItem = 5.0;
        Double fifthtem = 2.0;
        Double sixthItem = 7.0;

        //when
        heap.put(firstItem);
        heap.put(secondItem);
        heap.put(thirdItem);
        heap.put(fourthItem);
        heap.put(fifthtem);
        heap.put(sixthItem);
        Double result = heap.pop();

        //then
        Double expected = 7.0;
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnBiggestElementBeOnTopHeapAfterPop() {
        //given
        Double firstItem = 1.0;
        Double secondItem = 6.0;
        Double thirdItem = 3.0;
        Double fourthItem = 5.0;
        Double fifthtem = 2.0;
        Double sixthItem = 7.0;

        //when
        heap.put(firstItem);
        heap.put(secondItem);
        heap.put(thirdItem);
        heap.put(fourthItem);
        heap.put(fifthtem);
        heap.put(sixthItem);
        heap.pop();
        Double result = heap.pop();

        //then
        Double expected = 6.0;
        assertEquals(expected, result);
    }

    @Test
    public void shouldBuildMethodGenerateMaxHeap() {
        //given
        Collections.addAll(data, 10.0, 0.0, 2.0, 1.0, 7.0, 100.0);
        heap.build();

        //when
        boolean testResult = true;
        for (int i = 0; i <= (data.size() - 1) / 2; i++) {
            int l = i * 2 + 1;
            if (l < data.size()) {
                if (data.get(l) > data.get(i)) {
                    testResult = false;
                    break;
                }
            }
            int p = i * 2 + 2;
            if (p < data.size()) {
                if (data.get(p) > data.get(i)) {
                    testResult = false;
                    break;
                }
            }
        }

        //then
        boolean expected = true;
        assertEquals(expected, testResult);
    }

    @Test
    public void shouldReturnBiggestElementWhenHeapHasRandomElements() {
        //given
        Double[] nums = new Double[SIZE];
        nums[0] = random.nextDouble();
        Double expectedValue = nums[0];

        for (int i = 1; i < SIZE; i++) {
            nums[i] = random.nextDouble();
            if (nums[i] > expectedValue) {
                expectedValue = nums[i];
            }
        }

        //when
        for (int i = 0; i < SIZE; i++) {
            heap.put(nums[i]);
        }
        Double resultValue = heap.pop();

        //then
        assertEquals(expectedValue, resultValue);
    }
}
