package pl.edu.pw.ee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.services.MinSpanningTree;

public class PrimAlgorithmTest {
    private MinSpanningTree prim;
    //private PrimAlgorithm prim;
    
    @Before
    public void setUp() {
        prim = new PrimAlgorithm(); 
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenGivenPathToFileIsNull(){
        //given
        String path = null;
        
        //when
        prim.findMST(path);
        
        //then
        assert false;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenGivenPathToFileIsEmptyString(){
        //given
        String path = "";
        
        //when
        prim.findMST(path);
        
        //then
        assert false;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenGivenPathToFileIsIsnotDirectoryOrNotExist(){
        //given
        String path = "This path not exist";
        
        //when
        prim.findMST(path);
        
        //then
        assert false;
    }
    
    @Test
    public void should_correctlyFindMST_WhenGivenPathToFileIsCorrect() {
        //given
        String text = "A B 3\n"
                    + "A C 5\n"
                    + "A D 7\n"
                    + "B C 1\n"
                    + "C D 1\n"
                    + "D E 7\n";
        File file = new File("./graph");
        String path = "./graph";
        writeInFile(path,text);
        
        //when
        String result = prim.findMST(path);
        
        //then
        String expected = "A_3_B|B_1_C|C_1_D|D_7_E";
        assertEquals(expected,result);
    }
    
    private void writeInFile(String pathToFile, String text) {
        try ( FileWriter fileWriter = new FileWriter(pathToFile, Charset.forName("UTF-8"));  BufferedWriter writer = new BufferedWriter(fileWriter);) {

            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
