package pl.edu.pw.ee;

public class HashQuadraticProbing<T extends Comparable<T>> extends HashOpenAdressing<T> {

    private double a;
    private double b;

    public HashQuadraticProbing() {
        super();
        this.a = 0.5;
        this.b = 0.5;
    }

    public HashQuadraticProbing(int size, double firstConstant, double secondConstant) {
        super(size);
        this.a = firstConstant;
        this.b = secondConstant;
        validateGivenConstants();
    }

    @Override
    int hashFunc(int key, int i) {
        int m = getSize();
        int hash = (int) (key + a * i + b * i * i) % m;
        hash = hash < 0 ? -hash : hash;

        return hash;
    }

    private void validateGivenConstants() {
        if (this.a == 0 || this.b == 0) {
            throw new IllegalArgumentException("Constants cannot be equal to 0");
        }
        if (this.a == Double.POSITIVE_INFINITY || this.b == Double.POSITIVE_INFINITY) {
            throw new IllegalArgumentException("Constants cannot be infinity");
        }
        if (this.a == Double.NEGATIVE_INFINITY || this.b == Double.NEGATIVE_INFINITY) {
            throw new IllegalArgumentException("Constants cannot be infinity");
        }
        if (Double.isNaN(this.a) || Double.isNaN(this.b)) {
            throw new IllegalArgumentException("Constants cannot be NAN");
        }
    }
}
