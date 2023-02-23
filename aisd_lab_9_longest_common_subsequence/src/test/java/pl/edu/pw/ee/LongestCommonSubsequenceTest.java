package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LongestCommonSubsequenceTest {

    private LongestCommonSubsequence nwp;

    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenGivenStringIsNull() {
        //given
        String topStr = null;
        String leftStr = null;
        nwp = new LongestCommonSubsequence(topStr, leftStr);

        //when
        nwp.findLCS();

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenGivenStringIsEmpty() {
        //given
        String topStr = "";
        String leftStr = "";
        nwp = new LongestCommonSubsequence(topStr, leftStr);

        //when
        nwp.findLCS();

        //then
        assert false;
    }

    @Test
    public void should_returnEmptyString_WhenStringsHaveEmptyCLS() {
        //given
        nwp = new LongestCommonSubsequence("abcde", "fghijk");

        //when
        String result = nwp.findLCS();
        nwp.display();

        //then
        String expected = "";

        assertEquals(expected, result);
    }

    @Test
    public void should_returnEmptyString_WhenStringsAreSingleCharWithLCS() {
        //given
        nwp = new LongestCommonSubsequence("A", "A");

        //when
        String result = nwp.findLCS();
        nwp.display();

        //then
        String expected = "A";

        assertEquals(expected, result);
    }

    @Test
    public void should_returnEmptyString_WhenStringsAreSingleCharWithoutLCS() {
        //given
        nwp = new LongestCommonSubsequence("A", "B");

        //when
        String result = nwp.findLCS();
        nwp.display();

        //then
        String expected = "";

        assertEquals(expected, result);
    }

    @Test
    public void should_correctlyfindLCSAndDisplay_WhenStringsAreInEasyVariant() {
        //given
        nwp = new LongestCommonSubsequence("kanapki", "napisy");

        //when
        String result = nwp.findLCS();
        nwp.display();

        //then
        String expected = "napi";

        assertEquals(expected, result);
    }

    @Test
    public void should_correctlyfindLCSAndDisplay_WhenStringsHaveTwoLCS() {
        //given
        nwp = new LongestCommonSubsequence("abcdefghijklm", "jklmabcd");

        //when
        String result = nwp.findLCS();
        nwp.display();

        //then
        String expected = "jklm";

        assertEquals(expected, result);
    }

    @Test
    public void should_correctlyfindLCSAndDisplay_WhenStringsHaveAFewSpecialCharacters() {
        //given
        nwp = new LongestCommonSubsequence("\n\n\t\r\f\f", "\r\t\f\f");

        //when
        String result = nwp.findLCS();
        nwp.display();

        //then
        String expected = "\r\f\f";

        assertEquals(expected, result);
    }

    @Test
    public void should_correctlyfindLCSAndDisplay_WhenStringsAreSame() {
        //given
        nwp = new LongestCommonSubsequence("kanapki", "kanapki");

        //when
        String result = nwp.findLCS();
        nwp.display();

        //then
        String expected = "kanapki";

        assertEquals(expected, result);
    }

    @Test
    public void should_findLCSAndDisplay_WhenStringsHaveSpecialCharacters() {
        //given
        nwp = new LongestCommonSubsequence("często_z_odkrywaniem", "rzeczy_nie_trzeba\n_się_spieszyć");

        //when
        String result = nwp.findLCS();
        nwp.display();

        //then
        String expected = "cz__raie";

        assertEquals(expected, result);
    }
}
