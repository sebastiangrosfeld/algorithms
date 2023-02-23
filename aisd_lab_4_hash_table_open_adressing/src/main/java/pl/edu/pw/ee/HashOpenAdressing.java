package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public abstract class HashOpenAdressing<T extends Comparable<T>> implements HashTable<T> {

    private final T nil = null;
    private int size;
    private int nElems;
    private T[] hashElems;
    private final double correctLoadFactor;
    private final Del del = new Del();

    private class Del implements Comparable<T> {

        @Override
        public int compareTo(T o) {
            if (equals(o) == true) {
                return 0;
            } else if (o == null) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    HashOpenAdressing() {
        this(2039);
    }

    HashOpenAdressing(int size) {
        validateHashInitSize(size);

        this.size = size;
        this.hashElems = (T[]) new Comparable[this.size];
        this.correctLoadFactor = 0.75;
    }

    @Override
    public void put(T newElem) {
        validateInputElem(newElem);
        resizeIfNeeded();

        int key = newElem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        int loopChecker = 0;

        while (hashElems[hashId] != nil && hashElems[hashId].compareTo(newElem) != 0 && del.compareTo(hashElems[hashId]) != 0) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
            loopChecker++;

            if (loopChecker > size) {
                doubleResize();
                put(newElem);

                return;
            }
        }

        if (hashElems[hashId] != nil && hashElems[hashId].compareTo(newElem) == 0) {
            hashElems[hashId] = newElem;
        } else {
            hashElems[hashId] = newElem;
            nElems++;
        }
    }

    @Override
    public T get(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        int loopChecker = 0;

        while (hashElems[hashId] != nil && hashElems[hashId].compareTo(elem) != 0) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
            loopChecker++;

            if (loopChecker > size) {
                return null;
            }
        }
        if (hashElems[hashId] == nil) {
            return null;
        }
        return hashElems[hashId];
    }

    @Override
    public void delete(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        int loopChecker = 0;

        while (hashElems[hashId] != nil && hashElems[hashId].compareTo(elem) != 0) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
            loopChecker++;

            if (loopChecker > size) {
                return;
            }
        }

        if (hashElems[hashId] == nil) {

        } else {
            hashElems[hashId] = (T) del;
            nElems--;
        }
    }

    private void validateHashInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
        }
        if (!(initialSize <= Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("Initial size is too big");
        }
    }

    private void validateInputElem(T newElem) {
        if (newElem == null) {
            throw new IllegalArgumentException("Input elem cannot be null!");
        }
    }

    abstract int hashFunc(int key, int i);

    int getSize() {
        return size;
    }

    private void resizeIfNeeded() {
        double loadFactor = countLoadFactor();

        if (loadFactor >= correctLoadFactor) {
            doubleResize();
        }
    }

    private double countLoadFactor() {
        return (double) nElems / size;
    }

    private void doubleResize() {
        int preSize = hashElems.length;
        this.size *= 2;
        T[] secondTable = (T[]) new Comparable[this.size];
        int loopChecker;

        for (int i = 0; i < preSize; i++) {
            if (hashElems[i] != nil) {
                int key = hashElems[i].hashCode();
                int j = 0;
                int hashId = hashFunc(key, j);
                loopChecker = 0;

                while (secondTable[hashId] != nil && secondTable[hashId].compareTo(hashElems[i]) != 0) {
                    j = (j + 1) % size;
                    hashId = hashFunc(key, j);
                    loopChecker++;

                    if (loopChecker > size) {
                        doubleResize();

                        return;
                    }
                }
                secondTable[hashId] = hashElems[i];
            }
        }
        this.hashElems = secondTable;
    }
}
