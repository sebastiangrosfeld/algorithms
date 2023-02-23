package pl.edu.pw.ee.graph;

public class PointPosition {

    private String name;
    private double x;
    private double y;

    public PointPosition(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public String getName() {
        return name;
    }
}
