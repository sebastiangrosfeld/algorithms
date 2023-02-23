package pl.edu.pw.ee;

public class HashDoubleHashing<T extends Comparable<T>> extends HashOpenAdressing<T> {

    public HashDoubleHashing() {
        super();
    }

    public HashDoubleHashing(int size) {
        super(size);

        validateGivenSize(size);
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();
        int hash = (fFunc(key) + i * gFunc(key)) % m;
        hash = hash < 0 ? -hash : hash;

        return hash;
    }

    private int fFunc(int key) {
        int m = getSize();

        return key % m;
    }

    private int gFunc(int key) {
        int m = getSize() - 3;
        int result = 1 + (key % m);

        if (result == 0) {
            return 1;
        }
        return result;
    }

    private void validateGivenSize(int size) {
        if (size == 3) {
            throw new IllegalArgumentException("Size cannot be 3 -> number % 0");
        }
    }
}
