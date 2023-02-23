package pl.edu.pw.ee.graph;

import static java.lang.String.format;

public class Node {

    private String name;

    public Node(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return format("Node: %s", name);
    }
}
