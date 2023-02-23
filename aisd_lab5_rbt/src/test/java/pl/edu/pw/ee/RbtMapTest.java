package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.services.MapInterface;

public class RbtMapTest {
	
    private MapInterface<String,String> rbtMap;
    
    @Before
    public void setUp() {
        rbtMap = new RbtMap<>();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenSetKeyOrValueIsNull() {
        //given
        String key = null;
        String value = null;
        
        //when
        rbtMap.setValue(key, value);
        
        //then
        assert false;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenGetKeyOrValueIsNull() {
        //given
        String key = null;
        
        //when
        rbtMap.getValue(key);
        
        //then
        assert false;
    }
    
    @Test
    public void should_CorrectlySetAndGet_When(){
        //given
        String key = "ola";
        String value = "ala";
        
        //when
        rbtMap.setValue(key, value);
        String result = rbtMap.getValue(key);
        
        //then
        assertEquals("ala", result);
    }
}
