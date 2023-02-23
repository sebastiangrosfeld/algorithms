package pl.edu.pw.ee;

public class ElemOfQueue {

    private String firstNode;
    private String secondNode;
    private int wage;

    public ElemOfQueue(String node1, String node2, int wage) {
        this.firstNode = node1;
        this.secondNode = node2;
        this.wage = wage;
    }
    
    public int getWage() {
        return wage;
    }
    
    public String getFirstNode() {
        return firstNode;
    }
    
    public String getSecondNode() {
        return secondNode;
    }
    
    @Override
    public String toString() {
        return firstNode + "_" + wage + "_" + secondNode;
    }
}
