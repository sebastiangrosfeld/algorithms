package pl.edu.pw.ee;

import pl.edu.pw.ee.services.Sorting;

public class InsertionSort implements Sorting {

    @Override
    public void sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Nums array cannot be null");
        }

        double helpVal;
        int n = nums.length;

        for (int i = 1; i < n; i++) {

            helpVal = nums[i];
            int j = i;

            for (; j > 0 && nums[j - 1] > helpVal; j--) {
                nums[j] = nums[j - 1];
            }

            nums[j] = helpVal;
        }
    }
}
