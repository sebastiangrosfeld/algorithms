package pl.edu.pw.ee;

import java.util.List;
import pl.edu.pw.ee.services.HeapExtension;
import pl.edu.pw.ee.services.HeapInterface;

public class Heap<T extends Comparable<T>> implements HeapInterface<T>, HeapExtension {

    int FIRST = 0;
    private List<T> data;

    public Heap(List<T> data) {
        this.data = data;
    }

    @Override
    public void put(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if (ifDataIsHeap() == false) {
            throw new IllegalStateException("Data must be heap");
        }

        data.add(item);
        heapUP();
    }

    @Override
    public T pop() {
        if (data.isEmpty() == true) {
            throw new ArrayIndexOutOfBoundsException("You cannot pop from empty data");
        }

        if (ifDataIsHeap() == false) {
            throw new IllegalStateException("Data must be heap");
        }

        T popValue = data.get(FIRST);

        data.remove(FIRST);
        heapify(0, data.size());

        return popValue;
    }

    @Override
    public void build() {
        int n = data.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(i, n);
        }
    }

    @Override
    public void heapify(int startId, int endId) {
        while (true) {
            int largestElem = startId;
            int left = 2 * largestElem + 1;
            int right = 2 * largestElem + 2;

            if (leftIsBiggerThanParent(left, largestElem, endId)) {
                largestElem = left;
            }

            if (rightIsBiggerThanParent(right, largestElem, endId)) {
                largestElem = right;
            }

            if (largestElem != startId) {
                swap(startId, largestElem);
                startId = largestElem;
            } else {
                break;
            }
        }
    }

    private boolean leftIsBiggerThanParent(int left, int largest, int end) {
        if (left < end && data.get(left).compareTo(data.get(largest)) > 0) {
            return true;
        }

        return false;
    }

    private boolean rightIsBiggerThanParent(int right, int largest, int end) {
        if (right < end && data.get(right).compareTo(data.get(largest)) > 0) {
            return true;
        }

        return false;
    }

    private boolean ifDataIsHeap() {
        boolean Result = true;

        for (int i = 0; i <= (data.size() - 1) / 2; i++) {
            int l = i * 2 + 1;

            if (l < data.size()) {
                if (data.get(l).compareTo(data.get(i)) > 0) {
                    Result = false;
                    break;
                }
            }

            int p = i * 2 + 2;

            if (p < data.size()) {
                if (data.get(p).compareTo(data.get(i)) > 0) {
                    Result = false;
                    break;
                }
            }
        }

        return Result;
    }

    private void heapUP() {
        int elemId = data.size() - 1;

        while (elemId > 0) {

            int parent = (elemId - 1) / 2;

            if (data.get(parent).compareTo(data.get(elemId)) > 0) {
                return;
            }

            swap(parent, elemId);

            elemId = parent;
        }
    }

    private void swap(int firstId, int secondId) {
        T firstVal = data.get(firstId);
        data.set(firstId, data.get(secondId));
        data.set(secondId, firstVal);
    }
}
