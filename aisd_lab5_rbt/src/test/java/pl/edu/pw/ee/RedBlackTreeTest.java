package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class RedBlackTreeTest {
	
    private RedBlackTree<String,String> rbtt;
    
    @Before
    public void setUp() {
        rbtt = new RedBlackTree<>();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenPutKeyOrValueIsNull() {
        //given
        String key = null;
        String value = null;
        
        //when
        rbtt.put(key, value);
        
        //then
        assert false;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenGetKeyOrValueIsNull() {
        //given
        String key = null;
        
        //when
        rbtt.get(key);
        
        //then
        assert false;
    }
    
    @Test
    public void should_CorrectlyDeleteMax(){
        //given
        rbtt.put("A", "ala");
        rbtt.put("B", "ola");
        rbtt.put("C", "ula");
        rbtt.put("F", "pola");
        rbtt.put("D", "kula");
        
        //when
        rbtt.deleteMax();
        String result = rbtt.getInOrder();
        
        //then
        assertEquals("A:ala B:ola C:ula D:kula", result);
    }
    
    @Test
    public void should_CorrectlyGetPreOrder(){
        //given
        rbtt.put("A", "ala");
        rbtt.put("B", "ola");
        rbtt.put("C", "ula");
        
        //when
        String result = rbtt.getPreOrder();
        
        
        //then
        assertEquals("B:ola A:ala C:ula", result);
    }
    
    @Test
    public void should_CorrectlyGetInOrder(){
        //given
        rbtt.put("A", "ala");
        rbtt.put("B", "ola");
        rbtt.put("C", "ula");
        
        //when
        String result = rbtt.getInOrder();
        
        
        //then
        assertEquals("A:ala B:ola C:ula", result);
    }
    
    @Test
    public void should_CorrectlyGetPostOrder(){
        //given
        rbtt.put("A", "ala");
        rbtt.put("B", "ola");
        rbtt.put("C", "ula");
        
        //when
        String result = rbtt.getPostOrder();
        
        //then
        assertEquals("A:ala C:ula B:ola", result);
    }
}
