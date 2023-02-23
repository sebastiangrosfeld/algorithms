package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class HuffmanTest {

    private Huffman huffman;
    private static final String FILENAME = "file.txt";
    private static final String TREEFILENAME = "treeFile.txt";
    private static final String ENCODEDFILENAME = "encodedFile.jp2";
    private static final String DECODEDFILENAME = "decodeFile.txt";
    private static final String PATHTOROOTDIR = "./";
    private final boolean compress = true;
    private final boolean decompress = false;

    @Before
    public void setUp() {
        huffman = new Huffman();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenDirPathIsNull() {
        //given
        String path = null;

        //when
        huffman.huffman(path, compress);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenDirPathIsNotCorrectly() {
        //given
        String path = "This path not exist";

        //when
        huffman.huffman(path, compress);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenDirPathIsNotDirectory() {
        //given
        String path = "./something.txt";
        File file = new File(path);

        //when
        huffman.huffman(path, compress);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenFileToCompressNotExist() {
        //given
        File file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }

        //when
        huffman.huffman(PATHTOROOTDIR, compress);

        //then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void should_ThrowException_WhenTreeFileHasBadFormat() {
        //given
        File file = new File(FILENAME);

        String text = "X";

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeInFile(PATHTOROOTDIR + FILENAME, text);

        //when
        huffman.huffman(PATHTOROOTDIR, compress);
        File treeConfig = new File(TREEFILENAME);
        String treeConfigFileText = "something bad";

        try {
            treeConfig.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeInFile(PATHTOROOTDIR + TREEFILENAME, treeConfigFileText);
        huffman.huffman(PATHTOROOTDIR, decompress);

        //then
        assert false;
    }

    @Test
    public void should_EncodeAndDecode_WhenFileHasOneCharacter() {
        // given
        File file = new File(FILENAME);

        String text = "X";

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeInFile(PATHTOROOTDIR + FILENAME, text);

        // when
        int numOfBits = huffman.huffman(PATHTOROOTDIR, compress);
        int numOfChars = huffman.huffman(PATHTOROOTDIR, decompress);

        String encodedFileText = readFromFile(PATHTOROOTDIR + ENCODEDFILENAME);
        String decodedFileText = readFromFile(PATHTOROOTDIR + DECODEDFILENAME);

        // then
        int expectedNumOfBits = 0;
        int expectedNumOfChars = 1;

        assertEquals(expectedNumOfBits, numOfBits);
        assertEquals(expectedNumOfChars, numOfChars);
        assertEquals("", encodedFileText);
        assertEquals(text, decodedFileText);

    }

    @Test
    public void should_EncodeAndDecode_WhenFileHasThreeCharacters() {
        // given
        File file = new File(FILENAME);

        String text = "XAT";

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeInFile(PATHTOROOTDIR + FILENAME, text);

        // when
        int numOfBits = huffman.huffman(PATHTOROOTDIR, compress);
        int numOfChars = huffman.huffman(PATHTOROOTDIR, decompress);

        String decodedFileText = readFromFile(PATHTOROOTDIR + DECODEDFILENAME);

        // then
        int expectedNumOfBits = 5;
        int expectedNumOfChars = 3;

        assertEquals(expectedNumOfBits, numOfBits);
        assertEquals(expectedNumOfChars, numOfChars);
        assertEquals(text, decodedFileText);

    }

    @Test
    public void should_EncodeAndDecode_WhenFileHasNumericalCharacters() {
        // given
        File file = new File(FILENAME);

        String text = "123456789";

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeInFile(PATHTOROOTDIR + FILENAME, text);

        // when
        huffman.huffman(PATHTOROOTDIR, compress);
        int numOfChars = huffman.huffman(PATHTOROOTDIR, decompress);

        String decodedFileText = readFromFile(PATHTOROOTDIR + DECODEDFILENAME);

        // then
        int expectedNumOfChars = 9;

        assertEquals(expectedNumOfChars, numOfChars);
        assertEquals(text, decodedFileText);

    }

    @Test
    public void should_EncodeAndDecode_WhenFileHasSpecialCharacters() {
        // given
        File file = new File(FILENAME);

        String text = "ęąółńżźć$$$";

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeInFile(PATHTOROOTDIR + FILENAME, text);

        // when
        huffman.huffman(PATHTOROOTDIR, compress);
        int numOfChars = huffman.huffman(PATHTOROOTDIR, decompress);

        String decodedFileText = readFromFile(PATHTOROOTDIR + DECODEDFILENAME);

        // then
        int expectedNumOfChars = 11;

        assertEquals(expectedNumOfChars, numOfChars);
        assertEquals(text, decodedFileText);

    }

    @Test
    public void should_EncodeAndDecode_WhenFileHasSameCharacters() {
        // given
        File file = new File(FILENAME);

        String text = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeInFile(PATHTOROOTDIR + FILENAME, text);

        // when
        huffman.huffman(PATHTOROOTDIR, compress);
        huffman.huffman(PATHTOROOTDIR, decompress);

        String decodedFileText = readFromFile(PATHTOROOTDIR + DECODEDFILENAME);

        // then
        assertEquals(text, decodedFileText);

    }

    @Test
    public void should_EncodeAndDecode_WhenFileHasCharactersWithDifferentFrequency() {
        // given
        File file = new File(FILENAME);

        String text = "XXXXXXXXtttpp55555wqqqq";

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeInFile(PATHTOROOTDIR + FILENAME, text);

        // when
        huffman.huffman(PATHTOROOTDIR, compress);
        huffman.huffman(PATHTOROOTDIR, decompress);

        String decodedFileText = readFromFile(PATHTOROOTDIR + DECODEDFILENAME);

        // then
        assertEquals(text, decodedFileText);

    }
    
    @Test
    public void oddanie() {
       // given
        File file = new File(FILENAME);
        
        // when
        huffman.huffman(PATHTOROOTDIR, compress);
        huffman.huffman(PATHTOROOTDIR, decompress);
        
        //then
        assert true;
    }

    private void writeInFile(String pathToFile, String text) {
        try ( FileWriter fileWriter = new FileWriter(pathToFile, Charset.forName("UTF-8"));  BufferedWriter writer = new BufferedWriter(fileWriter);) {

            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(String pathToFile) {
        String text = "";

        try ( FileReader fileReader = new FileReader(pathToFile, Charset.forName("UTF-8"));  BufferedReader reader = new BufferedReader(fileReader);) {
            String line;

            while ((line = reader.readLine()) != null) {
                text += line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}
