package pl.edu.pw.ee;

class LongestCommonSubsequence {

    private final String topStr;
    private final String leftStr;
    private final ElemMatrix[][] matrix;
    private final char acrossArrow = 92;
    private final char leftArrow = '<';
    private final char upArrow = '^';

    public LongestCommonSubsequence(String topStr, String leftStr) {

        this.topStr = topStr;
        this.leftStr = leftStr;

        validateArguments();

        this.matrix = new ElemMatrix[leftStr.length() + 1][topStr.length() + 1];

        prebuildMatrix();
    }

    public String findLCS() {
        fillMatrix();

        String result = designateStringFromMatrix();

        return result;
    }

    public void display() {
        printFirstRow();

        printSecondRow();

        printRestOfMatrix();

    }

    private void validateArguments() {
        if (topStr == null || leftStr == null) {
            throw new IllegalArgumentException("Given String is null");
        }
        if (topStr.length() == 0 || leftStr.length() == 0) {
            throw new IllegalArgumentException("Given String is empty");
        }
    }

    private String designateStringFromMatrix() {
        int actualRow = leftStr.length();
        int actualColumn = topStr.length();
        ElemMatrix currentElem = matrix[actualRow][actualColumn];
        String result = "";

        if (matrix[actualRow][actualColumn].getNumber() > 0) {
            matrix[actualRow][actualColumn].setToPrintAsTrue();
        }

        while (currentElem.getNumber() > 0) {

            switch (currentElem.getArrow()) {
                case upArrow:
                    actualRow--;
                    currentElem = matrix[actualRow][actualColumn];

                    if (matrix[actualRow][actualColumn].getArrow() != null) {
                        matrix[actualRow][actualColumn].setToPrintAsTrue();
                    }
                    break;
                case leftArrow:
                    actualColumn--;
                    currentElem = matrix[actualRow][actualColumn];

                    if (matrix[actualRow][actualColumn].getArrow() != null) {
                        matrix[actualRow][actualColumn].setToPrintAsTrue();
                    }
                    break;
                case acrossArrow:
                    actualRow--;
                    actualColumn--;
                    currentElem = matrix[actualRow][actualColumn];
                    result = topStr.charAt(actualColumn) + result;

                    if (matrix[actualRow][actualColumn].getArrow() != null) {
                        matrix[actualRow][actualColumn].setToPrintAsTrue();
                    }
                    break;
                default:
                    break;
            }
        }

        return result;
    }

    private void fillMatrix() {
        for (int i = 1; i < leftStr.length() + 1; i++) {
            for (int j = 1; j < topStr.length() + 1; j++) {
                int max;
                if (matrix[i - 1][j].getNumber() >= matrix[i][j - 1].getNumber()) {
                    max = matrix[i - 1][j].getNumber();
                    matrix[i][j] = new ElemMatrix(max, upArrow);
                } else {
                    max = matrix[i][j - 1].getNumber();
                    matrix[i][j] = new ElemMatrix(max, leftArrow);
                }

                if (topStr.charAt(j - 1) == leftStr.charAt(i - 1)) {
                    matrix[i][j] = new ElemMatrix(matrix[i - 1][j - 1].getNumber() + 1, acrossArrow);
                }
            }
        }
    }

    private void printFirstRow() {
        String line = addMidLine();
        line += addEmptyLine();
        line += "|       |     ";
        for (int i = 0; i < topStr.length(); i++) {
            line += "| " + printChar(topStr.charAt(i)) + "  ";
        }
        line += "|\n";
        line += addEmptyLine();
        
        System.out.print(line);
    }

    private void printSecondRow() {
        String line = addMidLine();
        line += addEmptyLine();
        line += "|       ";
        for (int i = 0; i < topStr.length() + 1; i++) {
            line += "|  " + matrix[0][i].getNumber() + "  ";
        }
        line += "|\n";
        line += addEmptyLine();

        System.out.print(line);
    }

    private void printRestOfMatrix() {
        String line = "";
        for (int i = 0; i < leftStr.length(); i++) {

            line += addMidLine();
            line += "|       ";
            for (int j = 0; j < topStr.length() + 1; j++) {
                line += "|";
                if (matrix[i + 1][j] != null && matrix[i + 1][j].isToPrint() && matrix[i + 1][j].getArrow() == acrossArrow) {
                    line += matrix[i + 1][j].getArrow() + " ";
                } else {
                    line += "  ";
                }

                if (matrix[i + 1][j] != null && matrix[i + 1][j].isToPrint() && matrix[i + 1][j].getArrow() == upArrow) {
                    line += matrix[i + 1][j].getArrow();
                } else {
                    line += " ";
                }
                line += "  ";
            }
            line += "|\n";
            line += "|  " + printChar(leftStr.charAt(i)) + "   ";
            for (int j = 0; j < topStr.length() + 1; j++) {
                line += "|";
                if (matrix[i + 1][j] != null && matrix[i + 1][j].isToPrint() && matrix[i + 1][j].getArrow() == leftArrow) {
                    line += matrix[i + 1][j].getArrow() + " ";
                } else {
                    line += "  ";
                }
                if (matrix[i + 1][j] != null) {
                    line += matrix[i + 1][j].getNumber();
                }
                line += "  ";
            }
            line += "|\n";
            line += addEmptyLine();
        }
        line += addMidLine();

        System.out.println(line);
    }

    private void prebuildMatrix() {
        for (int i = 0; i < topStr.length() + 1; i++) {
            matrix[0][i] = new ElemMatrix(0, null);
        }
        for (int i = 0; i < leftStr.length() + 1; i++) {
            matrix[i][0] = new ElemMatrix(0, null);
        }
    }

    private String addMidLine() {
        String line = "";
        line += "+-------";
        for (int j = 0; j < topStr.length() + 1; j++) {
            line += "+-----";
        }
        line += "+\n";

        return line;
    }

    private String addEmptyLine() {
        String line = "";
        line += "|       ";
        for (int i = 0; i < topStr.length() + 1; i++) {
            line += "|     ";
        }
        line += "|\n";

        return line;
    }

    private String printChar(char character) {
        switch (character) {
            case '\f':
                return "\\f";
            case '\'':
                return "\\'";
            case '\"':
                return "\\\"";
            case '\\':
                return "\\\\";
            case '\r':
                return "\\r";
            case '\n':
                return "\\n";
            case '\t':
                return "\\t";
            case '\b':
                return "\\b";
            default:
                return " " + character + "";
        }
    }
}
