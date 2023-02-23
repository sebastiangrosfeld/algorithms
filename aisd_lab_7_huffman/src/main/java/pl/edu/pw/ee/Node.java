package pl.edu.pw.ee;

public class Node {
    private Character character;
    private int numOfAppear;
    private Node leftDescendant;
    private Node rightDescendant;
    
    public Node(char character, int numOfAppear) {
        this.character = character;
        this.numOfAppear = numOfAppear;
        this.leftDescendant = null;
        this.rightDescendant = null;
    }
    
    public Node(Node left, Node right, int numOfAppear) {
        this.character = null;
        this.leftDescendant = left;
        this.rightDescendant = right;
        this.numOfAppear = numOfAppear;
    }
    
    public void setRight( Node node) {
        this.rightDescendant = node;
    }
    
    public void setLeft( Node node) {
        this.leftDescendant = node;
    }
    
    public Node returnLeft() {
        return leftDescendant;
    }
    
    public Node returnRight() {
        return rightDescendant;
    }
    
    public Character returnCharacter() {
        return character;
    }
    
    public void increaseNumOfAppear() {
        this.numOfAppear = this.numOfAppear + 1;
    }
    
    public int returnNumOfAppear() {
        return numOfAppear + 0;
    }
    
    @Override
    public String toString() {
        String result = character + " " + Integer.toString(numOfAppear);
        
        return result;
    }
}
