package pl.edu.pw.ee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;
import pl.edu.pw.ee.services.MinSpanningTree;

public class PrimAlgorithm implements MinSpanningTree {

    private Integer[][] matrix = new Integer[1][1];
    private int sizeMatrix = 0;
    private static final Integer NIL = null;
    private static final int FIRST = 0;
    private static final String EMPTYSTRING = "";
    private ArrayList<ElemOfQueue> priorityQueue = new ArrayList<>();
    private ArrayList<String> nodes = new ArrayList<>();
    private ArrayList<String> visited = new ArrayList<>();

    public String findMST(String pathToFile) {
        String result = EMPTYSTRING;
        ElemComparator comparator = new ElemComparator();

        preFillMatrix(matrix);
        validatePathToFile(pathToFile);
        getGraphFromFile(pathToFile);
        validateMatrix();
        addToQueue(nodes.get(FIRST));
        priorityQueue.sort(comparator);
        visited.add(priorityQueue.get(FIRST).getFirstNode());

        while (!priorityQueue.isEmpty()) {
            priorityQueue.sort(comparator);

            if (!isVisited(priorityQueue.get(FIRST))) {
                result += priorityQueue.get(FIRST).toString() + "|";
                addToVisited(priorityQueue.get(FIRST));
                addToQueue(priorityQueue.get(FIRST).getSecondNode());
                priorityQueue.remove(FIRST);
            } else {
                priorityQueue.remove(FIRST);
            }
        }

        result = result.substring(0, result.length() - 1);

        return result;
    }

    private void preFillMatrix(Integer[][] matrix) {
        for (int i = 0; i < sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                matrix[i][j] = NIL;
            }
        }
    }

    private void validatePathToFile(String path) {
        if (path == null || EMPTYSTRING.equals(path)) {
            throw new IllegalArgumentException("Path file is wrong");
        }
    }

    private void addToQueue(String node) {
        int indexOfNode = nodes.indexOf(node);
        for (int i = 0; i < sizeMatrix; i++) {
            if (!Objects.equals(matrix[indexOfNode][i], NIL)) {
                priorityQueue.add(new ElemOfQueue(nodes.get(indexOfNode), nodes.get(i), matrix[indexOfNode][i]));
            }
        }
    }

    private void addToVisited(ElemOfQueue elem) {
        if (visited.contains(elem.getFirstNode())) {
            visited.add(elem.getSecondNode());
        }
        if (visited.contains(elem.getSecondNode())) {
            visited.add(elem.getFirstNode());
        }
    }

    private boolean isVisited(ElemOfQueue elem) {
        if (visited.contains(elem.getFirstNode()) && visited.contains(elem.getSecondNode())) {
            return true;
        } else {
            return false;
        }
    }

    private void validateMatrix() {
        for (int i = 0; i < sizeMatrix; i++) {
            if (!Objects.equals(matrix[i][i], NIL)) {
                throw new IllegalStateException("Detected loop!");
            }
        }
    }

    private void getGraphFromFile(String pathToFile) {

        File file = new File(pathToFile);

        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("File not exist");
        }
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                int newSize = sizeMatrix;

                String firstNode;
                if (lineScanner.hasNext()) {
                    firstNode = lineScanner.next();
                    if (!nodes.contains(firstNode)) {
                        nodes.add(firstNode);
                        newSize++;
                    }
                } else {
                    throw new IllegalStateException("File has wrong format");
                }

                String secondNode;
                if (lineScanner.hasNext()) {
                    secondNode = lineScanner.next();
                    if (!nodes.contains(secondNode)) {
                        nodes.add(secondNode);
                        newSize++;
                    }
                } else {
                    throw new IllegalStateException("File has wrong format");
                }

                if (lineScanner.hasNextInt()) {
                    increaseMatrixIfNeeded(newSize);
                    matrix[nodes.indexOf(firstNode)][nodes.indexOf(secondNode)] = lineScanner.nextInt();
                } else {
                    throw new IllegalStateException("File has wrong format");
                }
            }
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException("File not exist");
        }
    }

    private void increaseMatrixIfNeeded(int newSize) {
        if (newSize > sizeMatrix) {
            Integer[][] newMatrix = new Integer[newSize][newSize];
            preFillMatrix(newMatrix);

            for (int i = 0; i < sizeMatrix; i++) {
                for (int j = 0; j < sizeMatrix; j++) {
                    newMatrix[i][j] = matrix[i][j];
                }
            }
            this.sizeMatrix = newSize;
            this.matrix = newMatrix;
        }
    }

    private class ElemComparator implements Comparator<ElemOfQueue> {

        @Override
        public int compare(ElemOfQueue o1, ElemOfQueue o2) {
            if (o1.getWage() > o2.getWage()) {
                return 1;
            } else if (o1.getWage() == o2.getWage()) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
