package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class HashListChainingTest {

    private HashListChaining<Double> hashTable;
    private static final int SIZE = 100;
    private static final int EPS = 0;

    @Before
    public void setUp() {
        hashTable = new HashListChaining<>(SIZE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenGivenSizeIsLessThanZero() {
        //given
        int size = -7;

        //when
        hashTable = new HashListChaining<>(size);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenGivenSizeIsIntegerMaxValue() {
        //given
        int size = Integer.MAX_VALUE;

        //when
        hashTable = new HashListChaining<>(size);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenGivenSizeIsEqualToZero() {
        //given
        int size = 0;

        //when
        hashTable = new HashListChaining<>(size);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenTryAddElemWhichIsNull() {
        //given
        Double elem = null;

        //when
        hashTable.add(elem);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenTryGetElemWhichIsNull() {
        //given
        Double elem = null;

        //when
        hashTable.get(elem);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenTryDeleteElemWhichIsNull() {
        //given
        Double elem = null;

        //when
        hashTable.delete(elem);

        //then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhenTryDeleteElemFromEmptyHashTable() {
        //given
        Double elem = 4.0;

        //when
        hashTable.delete(elem);

        //then
        assert false;
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhenTryDeleteElemWhichWasDeletedBeforeAndNotAddedAgain() {
        //given
        Double elem = 4.0;

        //when
        hashTable.add(elem);
        hashTable.delete(elem);
        hashTable.delete(elem);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenHashCodeIsIntegerMinValue() {
        //given
        int hashCode = Integer.MIN_VALUE;

        //when
        hashTable.getHashId(hashCode);

        //then
        assert false;
    }

    @Test
    public void shouldAddElemToHashTable() {
        //given
        Double elem = 14.0;

        //when
        hashTable.add(elem);
        double result = hashTable.get(elem);

        //then
        double expected = elem;
        assertEquals(expected, result, EPS);
    }

    @Test
    public void shouldReturnCorrectNumberOfElementsInHashTable() {
        //given
        Double firstElem = 7.0;
        Double secondElem = 42.0;
        Double thirdElem = 3.0;
        Double fourthElem = 8.0;

        //when
        hashTable.add(firstElem);
        hashTable.add(secondElem);
        hashTable.add(thirdElem);
        hashTable.add(fourthElem);
        int result = hashTable.getNumberOfElemnts();

        //then
        int expected = 4;
        assertEquals(expected, result, EPS);
    }

    @Test
    public void shouldHashTableHaveUniqueElements() {
        //given
        Double firstElem = 6.0;
        Double secondElem = 6.0;

        //when
        hashTable.add(firstElem);
        hashTable.add(secondElem);
        int result = hashTable.getNumberOfElemnts();

        //then
        int expected = 1;
        assertEquals(expected, result, EPS);
    }

    @Test
    public void shouldAddAndGetElemInTheSameList() {
        //given
        int size = 1;
        hashTable = new HashListChaining<>(size);
        Double firstElem = 6.0;
        Double secondElem = 8.0;
        Double thirdElem = 9.0;

        //when
        hashTable.add(firstElem);
        hashTable.add(secondElem);
        hashTable.add(thirdElem);
        hashTable.get(secondElem);
        Double result = hashTable.get(secondElem);

        //then
        Double expected = 8.0;
        assertEquals(expected, result, EPS);
    }

    @Test
    public void shouldDeleteFirstElemInTheSameList() {
        //given
        int size = 1;
        hashTable = new HashListChaining<>(size);
        Double firstElem = 6.0;
        Double secondElem = 8.0;
        Double thirdElem = 9.0;

        //when
        hashTable.add(firstElem);
        hashTable.add(secondElem);
        hashTable.add(thirdElem);
        hashTable.delete(firstElem);
        Double result = hashTable.get(firstElem);

        //then
        Double expected = null;
        assertEquals(expected, result);
    }

    @Test
    public void shouldIncreaseNumberOfElementsWhenNewItemWasAdded() {
        //given
        Double firstElem = 4.0;
        Double secondElem = 5.0;
        Double thirdElem = 9.0;

        //when
        hashTable.add(firstElem);
        hashTable.add(secondElem);
        int numberOfElemBeforeAddedThird = hashTable.getNumberOfElemnts();
        hashTable.add(thirdElem);
        int numberOfElemAfterAddedThird = hashTable.getNumberOfElemnts();
        int result = numberOfElemAfterAddedThird - numberOfElemBeforeAddedThird;

        //then
        int expected = 1;
        assertEquals(expected, result, EPS);
    }

    @Test
    public void shouldReduceNumberOfElementsWhenNewItemWasDeleted() {
        //given
        Double firstElem = 4.0;
        Double secondElem = 5.0;
        Double thirdElem = 9.0;

        //when
        hashTable.add(firstElem);
        hashTable.add(secondElem);
        hashTable.add(thirdElem);
        int numberOfElemBeforeDeletedThird = hashTable.getNumberOfElemnts();
        hashTable.delete(thirdElem);
        int numberOfElemAfterDeletedThird = hashTable.getNumberOfElemnts();
        int result = numberOfElemBeforeDeletedThird - numberOfElemAfterDeletedThird;

        //then
        int expected = 1;
        assertEquals(expected, result, EPS);
    }

    @Test
    public void shouldDeleteLastElemInTheSameList() {
        //given
        int size = 1;
        hashTable = new HashListChaining<>(size);
        Double firstElem = 4.0;
        Double secondElem = 5.0;
        Double thirdElem = 9.0;

        //when
        hashTable.add(firstElem);
        hashTable.add(secondElem);
        hashTable.add(thirdElem);
        hashTable.delete(thirdElem);
        Double result = hashTable.get(thirdElem);

        //then
        Double expected = null;
        assertEquals(expected, result);
    }

    @Test
    public void shouldDeleteMidElemInTheSameList() {
        //given
        int size = 1;
        hashTable = new HashListChaining<>(size);
        Double firstElem = 4.0;
        Double secondElem = 5.0;
        Double thirdElem = 9.0;
        Double fourthElem = 12.0;
        Double fifthElem = 18.0;

        //when
        hashTable.add(firstElem);
        hashTable.add(secondElem);
        hashTable.add(thirdElem);
        hashTable.add(fourthElem);
        hashTable.add(fifthElem);
        hashTable.delete(thirdElem);
        Double result = hashTable.get(thirdElem);

        //then
        Double expected = null;
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnCorrectValuableFromCountLoadFactor() {
        //given
        int size = 3;
        hashTable = new HashListChaining<>(size);
        Double firstElem = 4.0;
        Double secondElem = 5.0;
        Double thirdElem = 9.0;

        //when
        hashTable.add(firstElem);
        hashTable.add(secondElem);
        hashTable.add(thirdElem);
        double result = hashTable.countLoadFactor();

        //then
        double expected = 1.0;
        assertEquals(expected, result, EPS);
    }
}
