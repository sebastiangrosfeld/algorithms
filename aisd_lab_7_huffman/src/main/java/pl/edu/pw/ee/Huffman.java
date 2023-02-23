package pl.edu.pw.ee;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Huffman {

    private static final String FILENAME = "file.txt";
    private static final String TREEFILENAME = "treeFile.txt";
    private static final String ENCODEDFILENAME = "encodedFile.jp2";
    private static final String DECODEDFILENAME = "decodeFile.txt";
    private static final String EMPTYSTRING = "";
    private String text;
    private String encodeText;
    private List<Node> listOfNodes;
    private List<Code> listOfCodes;
    private Node rootOfTree;
    private int lengthOfEncoded;
    private String treeConfiguration;

    public Huffman() {
        listOfNodes = new ArrayList<>();
        listOfCodes = new ArrayList<>();
    }

    public int huffman(String pathToRootDir, boolean compress) {
        if(pathToRootDir == null) {
            throw new IllegalArgumentException("Path is null");
        }
        
        int result;

        validateRootDirectory(pathToRootDir);

        if (compress == true) {
            validateGivenPathFile(pathToRootDir + FILENAME);

            result = compressGivenFile(pathToRootDir);
        } else {
            validateGivenPathFile(pathToRootDir + ENCODEDFILENAME);
            validateGivenPathFile(pathToRootDir + TREEFILENAME);

            result = deCompressGivenFile(pathToRootDir);

            validateGivenPathFile(pathToRootDir + DECODEDFILENAME);
        }

        return result;
    }

    private int compressGivenFile(String path) {
        readFromFile(path);

        createTreeConfiguration();

        buildTree();

        createCodes();

        int result = getEncodedText();

        createCompressFile(path, encodeText);

        createTreeFile(path);

        return result;
    }

    private int deCompressGivenFile(String path) {
        listOfNodes.clear();

        readTreeConfigurationFromFile(path);

        int result = decodeFile(path);

        return result;
    }

    private int decodeFile(String path) {
        String binaryText = readFromBinaryFile(path);
        text = EMPTYSTRING;

        if (lengthOfEncoded == 0 && rootOfTree.returnLeft() == null && rootOfTree.returnRight() == null) {
            int numOfAppear = rootOfTree.returnNumOfAppear();
            for (int i = 0; i < numOfAppear; i++) {
                text += rootOfTree.returnCharacter();
            }
            createDecompressFile(path, text);

            return text.length();
        }

        String encodedText = binaryText.substring(0, lengthOfEncoded);
        Node iterNode = rootOfTree;

        for (int i = 0; i < lengthOfEncoded; i++) {
            iterNode = getNextNode(iterNode, encodedText.charAt(i));
        }

        getNextNode(iterNode, encodedText.charAt(lengthOfEncoded - 1));
        createDecompressFile(path, text);

        return text.length();
    }

    private Node getNextNode(Node node, int character) {
        if (node.returnLeft() != null && character == '0') {
            return node.returnLeft();
        } else if (node.returnRight() != null && character == '1') {
            return node.returnRight();
        } else {
            text += node.returnCharacter();

            node = rootOfTree;

            if (character != -1) {
                return getNextNode(node, character);
            } else {
                return node;
            }
        }
    }

    private String changeNumberAsCharToString(String number) {
        String newNumber = EMPTYSTRING;
        for (int i = 0; i < number.length(); i += 2) {
            String digitals = number.substring(i, i + 2);
            char digital = (char) Integer.parseInt(digitals);
            newNumber += digital;
        }

        return newNumber;
    }

    private String readFromBinaryFile(String path) {
        String result = EMPTYSTRING;

        try ( FileInputStream fileInputStream = new FileInputStream(path + ENCODEDFILENAME);  BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);) {

            int getByte;

            while ((getByte = bufferedInputStream.read()) != -1) {
                result += String.format("%08d", Integer.parseInt(Integer.toString(getByte, 2), 10));
            }

            bufferedInputStream.close();
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong");
        }

        return result;

    }

    private void readTreeConfigurationFromFile(String path) {
        try ( FileReader fileReader = new FileReader(path + TREEFILENAME, Charset.forName("UTF-8"));  BufferedReader reader = new BufferedReader(fileReader);) {

            int length;

            try {
                if ((length = Integer.parseInt(reader.readLine())) != -1) {
                    lengthOfEncoded = length;

                }
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Something went wrong with treeFile");
            }

            int digitOfNumOfAppear;
            int character;

            StringBuilder numOfAppearForChar = new StringBuilder();

            while ((character = reader.read()) != -1) {
                if (reader.read() != ' ') {
                    throw new IllegalStateException("TreeFile has bad format!");
                }

                while ((digitOfNumOfAppear = reader.read()) != -1) {
                    if ((char) digitOfNumOfAppear <= '9' && (char) digitOfNumOfAppear >= '0') {
                        numOfAppearForChar.append(digitOfNumOfAppear);
                    } else {
                        if (digitOfNumOfAppear != ' ') {
                            throw new IllegalStateException("TreeFile has bad format!");
                        }
                        break;
                    }
                }

                if (numOfAppearForChar.isEmpty()) {
                    return;
                }

                addToListOfNodes((char) character, Integer.parseInt(changeNumberAsCharToString(numOfAppearForChar.toString())));
                numOfAppearForChar.delete(0, numOfAppearForChar.length());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong");
        }

        buildTree();

    }

    private void createTreeFile(String path) {
        createFile(path, TREEFILENAME);

        try ( FileWriter fileWriter = new FileWriter(path + TREEFILENAME, Charset.forName("UTF-8"));  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {

            bufferedWriter.write(lengthOfEncoded + "\n" + treeConfiguration);

            bufferedWriter.close();
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong");
        }
    }

    private void createTreeConfiguration() {
        treeConfiguration = EMPTYSTRING;

        for (int i = 0; i < listOfNodes.size(); i++) {
            treeConfiguration += listOfNodes.get(i).toString() + " ";
        }
    }

    private void createCompressFile(String path, String text) {
        createFile(path, ENCODEDFILENAME);

        try ( FileOutputStream fileOutputStream = new FileOutputStream(path + ENCODEDFILENAME);  BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);) {

            while (text.length() % 8 != 0) {
                text += "0";
            }

            for (int i = 0; i < text.length(); i += 8) {
                String byteString = text.substring(i, i + 8);
                int parsedByte = 0xFF & Integer.parseInt(byteString, 2);

                bufferedOutputStream.write(parsedByte);
            }

            bufferedOutputStream.close();
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong");
        }
    }

    private void createDecompressFile(String path, String text) {
        createFile(path, DECODEDFILENAME);

        try ( FileWriter fileWriter = new FileWriter(path + DECODEDFILENAME, Charset.forName("UTF-8"));  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {

            bufferedWriter.write(text);

            bufferedWriter.close();
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong");
        }
    }

    private int getEncodedText() {
        String encodText = EMPTYSTRING;

        char[] charArray = text.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            for (int j = 0; j < listOfCodes.size(); j++) {
                if (listOfCodes.get(j).returnCharacter() == charArray[i]) {
                    encodText += listOfCodes.get(j).returnBitCode();
                    break;
                }
            }
        }

        this.encodeText = encodText;
        this.lengthOfEncoded = encodText.length();
        return encodText.length();
    }

    private void createFile(String path, String filename) {
        File file = new File(path + filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IllegalStateException("Something went wrong");
            }
        }
    }

    private void readFromFile(String path) {
        try ( FileReader fileReader = new FileReader(path + FILENAME, Charset.forName("UTF-8"));  BufferedReader reader = new BufferedReader(fileReader);) {

            int character;
            text = EMPTYSTRING;

            while ((character = reader.read()) != -1) {
                text += (char) character;

                addToListOfNodes((char) character, 1);
            }

        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong");
        }

        if (listOfNodes.isEmpty()) {
            throw new IllegalStateException("File is empty");
        }
    }

    private void addToListOfNodes(char character, int numOfAppear) {
        boolean isInList = false;

        for (int i = 0; i < listOfNodes.size(); i++) {
            if (listOfNodes.get(i).returnCharacter() == character) {
                listOfNodes.get(i).increaseNumOfAppear();
                isInList = true;
                break;
            }
        }

        if (!isInList) {
            listOfNodes.add(new Node(character, numOfAppear));
        }
    }

    private void buildTree() {
        NodeComparator nodecomparator = new NodeComparator();

        while (listOfNodes.size() > 1) {
            listOfNodes.sort(nodecomparator);

            Node firstNode = listOfNodes.remove(0);
            Node secondNode = listOfNodes.remove(0);

            listOfNodes.add(new Node(firstNode, secondNode, firstNode.returnNumOfAppear() + secondNode.returnNumOfAppear()));
        }

        rootOfTree = listOfNodes.get(0);
    }

    private void createCodes() {
        createCode(EMPTYSTRING, rootOfTree);
    }

    private void createCode(String precode, Node node) {
        if (node.returnCharacter() != null) {
            listOfCodes.add(new Code(node.returnCharacter(), precode));
        }

        if (node.returnLeft() != null) {
            createCode(precode + "0", node.returnLeft());
        }

        if (node.returnRight() != null) {
            createCode(precode + "1", node.returnRight());
        }
    }

    private void validateGivenPathFile(String path) {
        File file = new File(path);

        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("File " + path + " not exist!");
        }
    }

    private void validateRootDirectory(String path) {
        File file = new File(path);

        if (!file.exists() || !file.isDirectory()) {
            throw new IllegalArgumentException("Incorrect given root directory!");
        }
    }

    private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            if (o1.returnNumOfAppear() > o2.returnNumOfAppear()) {
                return 1;
            } else if (o1.returnNumOfAppear() == o2.returnNumOfAppear()) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
