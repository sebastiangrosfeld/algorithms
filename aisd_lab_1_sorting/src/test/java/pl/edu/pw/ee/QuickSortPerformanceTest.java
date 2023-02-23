package pl.edu.pw.ee;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static pl.edu.pw.ee.QuickSortPerformanceTest.Direction.RANDOM;
import static pl.edu.pw.ee.QuickSortPerformanceTest.Direction.OPTIMISTIC;
import static pl.edu.pw.ee.QuickSortPerformanceTest.Direction.PESIMIC;
import pl.edu.pw.ee.services.Sorting;

public class QuickSortPerformanceTest {

    private Sorting sorter;
    private Random random;
    private final int SEED = 10;
    private long startSurvey;
    private long endSurvey;

    @Before
    public void setUp() {
        sorter = new QuickSort();
        random = new Random(SEED);
    }

    @Test
    public void registerExecutionTimeWhenArrayIsInOptimisticVariant() {
        registerExecutionTimeInChosenVariant(10, 100, 10, OPTIMISTIC);
        registerExecutionTimeInChosenVariant(100, 1000, 100, OPTIMISTIC);
        registerExecutionTimeInChosenVariant(1000, 30000, 500, OPTIMISTIC);
    }

    @Test
    public void registerExecutionTimeWhenArrayIsInPesimicVariant() {
        registerExecutionTimeInChosenVariant(10, 100, 10, PESIMIC);
        registerExecutionTimeInChosenVariant(100, 1000, 100, PESIMIC);
        registerExecutionTimeInChosenVariant(1000, 30000, 500, PESIMIC);
    }

    @Test
    public void registerExecutionTimeWhenArrayIsRandomlyGenerated() {
        registerExecutionTimeInChosenVariant(10, 100, 10, RANDOM);
        registerExecutionTimeInChosenVariant(100, 1000, 100, RANDOM);
        registerExecutionTimeInChosenVariant(1000, 30000, 500, RANDOM);
    }

    private void registerTimeInChosenVariant(int index, Direction variant) {
        double[] nums = new double[index];

        if (variant == OPTIMISTIC) {
            generateArrayRandom(nums, index);
        }

        if (variant == PESIMIC) {
            generateArrayInPesimicVariant(nums, index);
        }

        if (variant == RANDOM) {
            generateArrayRandom(nums, index);
        }

        startSurvey = System.nanoTime();
        sorter.sort(nums);
        endSurvey = System.nanoTime();
        System.out.println(index + ": " + (endSurvey - startSurvey) / 1000);
    }

    public void registerExecutionTimeInChosenVariant(int startIndex, int endIndex, int increase, Direction variant) {
        for (int i = startIndex; i < endIndex + 1; i += increase) {
            registerTimeInChosenVariant(i, variant);
        }
    }

    private void generateArrayInPesimicVariant(double[] nums, int size) {
        for (int i = size - 1; i >= 0; i--) {
            nums[i] = size - i;
        }
    }

    private void generateArrayRandom(double[] nums, int size) {
        for (int i = 0; i < size; i++) {
            nums[i] = random.nextDouble();
        }
    }

    public enum Direction {
        OPTIMISTIC,
        PESIMIC,
        RANDOM
    }
}
