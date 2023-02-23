
package pl.edu.pw.ee;

public class ElemMatrix {
    private int number;
    private Character arrow;
    private boolean toPrint;
    
    public ElemMatrix(int number,Character arrow){
        this.number = number;
        this.arrow =arrow;
        this.toPrint = false;
    }
    
    public int getNumber() {
        return number;
    }
    
    public Character getArrow() {
        return arrow;
    }
    
    public void setToPrintAsTrue() {
        this.toPrint = true;
    }
    
    public boolean isToPrint() {
        return toPrint;
    }
}
