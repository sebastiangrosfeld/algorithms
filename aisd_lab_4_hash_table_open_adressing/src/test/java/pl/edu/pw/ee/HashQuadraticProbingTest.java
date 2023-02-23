package pl.edu.pw.ee;

import java.lang.reflect.Field;
import java.util.Random;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

public class HashQuadraticProbingTest {

    private Random rand;
    private static final int SEED = 12;

    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenConstantsAreZero() {
        //given
        int size = 10;
        double a = 0;
        double b = 0;

        //when
        HashTable<Double> hash = new HashQuadraticProbing<>(size, a, b);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenConstantsAreInfinity() {
        //given
        int size = 10;
        double a = Double.POSITIVE_INFINITY;
        double b = Double.POSITIVE_INFINITY;

        //when
        HashTable<Double> hash = new HashQuadraticProbing<>(size, a, b);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenConstantsAreMinusInfinity() {
        //given
        int size = 10;
        double a = Double.NEGATIVE_INFINITY;
        double b = Double.NEGATIVE_INFINITY;

        //when
        HashTable<Double> hash = new HashQuadraticProbing<>(size, a, b);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwException_WhenConstantsAreNaN() {
        //given
        int size = 10;
        Double a = Double.NaN;
        Double b = Double.NaN;

        //when
        HashTable<Double> hash = new HashQuadraticProbing<>(size, a, b);

        //then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenInitialSizeIsLowerThanOne() {
        // given
        int initialSize = 0;

        // when
        HashTable<Double> unusedHash = new HashQuadraticProbing<>(initialSize, 1, 1);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenInitialSizeIsIntegerMaxValue() {
        // given
        int initialSize = Integer.MAX_VALUE + 1;

        // when
        HashTable<Double> unusedHash = new HashQuadraticProbing<>(initialSize, 1, 1);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenPutElemIsNull() {
        // given
        HashTable<String> hash = new HashQuadraticProbing<>();
        String newEleme = null;

        // when
        hash.put(newEleme);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenGetElemIsNull() {
        // given
        HashTable<String> hash = new HashQuadraticProbing<>();
        String newEleme = "something";
        String nullElem = null;

        // when
        hash.put(newEleme);
        hash.get(nullElem);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_ThrowException_WhenDelElemIsNull() {
        // given
        HashTable<String> hash = new HashQuadraticProbing<>();
        String newEleme = "something";
        String nullElem = null;

        // when
        hash.put(newEleme);
        hash.delete(nullElem);

        // then
        assert false;
    }

    @Test
    public void should_CorrectlyDeleteElem_WhenItExistInHashTable() {
        // given
        HashTable<String> emptyHash = new HashQuadraticProbing<>();
        String newEleme = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(emptyHash);
        emptyHash.put(newEleme);
        int nOfElemsAfterPut = getNumOfElems(emptyHash);
        emptyHash.delete(newEleme);
        int nOfElemsAfterDelete = getNumOfElems(emptyHash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
        assertEquals(0, nOfElemsAfterDelete);
    }

    @Test
    public void should_CorrectlyAddNewElems_WhenNotExistInHashTable() {
        // given
        HashTable<String> emptyHash = new HashQuadraticProbing<>();
        String newEleme = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(emptyHash);
        emptyHash.put(newEleme);
        int nOfElemsAfterPut = getNumOfElems(emptyHash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
    }

    @Test
    public void should_CorrectlyNotAddNewElems_WhenAlreadyExistInHashTable() {
        // given
        HashTable<String> emptyHash = new HashQuadraticProbing<>();
        String newEleme = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(emptyHash);
        emptyHash.put(newEleme);
        emptyHash.put(newEleme);
        int nOfElemsAfterPut = getNumOfElems(emptyHash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
    }

    @Test
    public void should_CorrectlyGetElems_WhenExistInHashTable() {
        // given
        HashTable<String> hash = new HashQuadraticProbing<>();
        String newEleme = "nothing special";

        // when
        hash.put(newEleme);
        String result = hash.get("nothing special");

        // then
        assertEquals(newEleme, result);
    }

    @Test
    public void should_CorrectlyNotDeleteElem_WhenItNotExistInHashTable() {
        // given
        HashTable<String> hash = new HashQuadraticProbing<>();
        String firstEleme = "nothing special";
        String secondEleme = "something special";
        String thirdEleme = "anything";

        // when
        hash.put(firstEleme);
        hash.put(secondEleme);
        int numberElemsAfterPut = getNumOfElems(hash);
        hash.delete(thirdEleme);
        int numberElemsAfterDelete = getNumOfElems(hash);

        // then
        assertEquals(numberElemsAfterPut, numberElemsAfterDelete);
    }

    @Test
    public void should_ReturnNull_WhenGetElemNotExistInHashTable() {
        // given
        HashTable<String> hash = new HashQuadraticProbing<>();
        String newEleme = "nothing special";
        String otherEleme = "something";

        // when
        hash.put(newEleme);
        String result = hash.get(otherEleme);

        // then
        assertEquals(null, result);
    }

    @Test
    public void should_CorrectlyIncreaseSize_WhenPutManyElems() {
        //given
        int size = 1;
        rand = new Random(SEED);
        HashTable<Double> hash = new HashQuadraticProbing<>(size, 0.5, 0.5);

        //when
        for (int i = 0; i < 1000000; i++) {
            hash.put(rand.nextDouble());
        }

        int resultSize = getSize(hash);
        boolean result;

        if (resultSize > size) {
            result = true;
        } else {
            result = false;
        }

        //then
        boolean expected = true;
        assertEquals(expected, result);
    }

    @Test
    public void should_CorrectlyAddAndDeleteElems_WhenElemsHaveSameHashCode() {
        //given
        TestObject firstElem = new TestObject(1);
        TestObject secondElem = new TestObject(2);
        TestObject thirdElem = new TestObject(3);
        TestObject fourthElem = new TestObject(4);
        HashTable<TestObject> hash = new HashQuadraticProbing<>();

        //when
        hash.put(firstElem);
        hash.put(secondElem);
        hash.put(thirdElem);
        hash.put(fourthElem);

        int numElemsAfterPutAll = getNumOfElems(hash);

        hash.delete(firstElem);

        int numElemsAfterDeleteFirst = getNumOfElems(hash);

        hash.delete(fourthElem);

        int numElemsAfterDeleteFourth = getNumOfElems(hash);

        hash.delete(secondElem);

        int numElemsAfterDeleteSecond = getNumOfElems(hash);

        hash.delete(thirdElem);

        int numElemsAfterDeleteThird = getNumOfElems(hash);

        //then
        int expectedAfterPutAll = 4;
        int expectedAfterDeleteFirst = 3;
        int expectedAfterDeleteFourth = 2;
        int expectedAfterDeleteSecond = 1;
        int expectedAfterDeleteThird = 0;
        assertEquals(expectedAfterPutAll, numElemsAfterPutAll);
        assertEquals(expectedAfterDeleteFirst, numElemsAfterDeleteFirst);
        assertEquals(expectedAfterDeleteFourth, numElemsAfterDeleteFourth);
        assertEquals(expectedAfterDeleteSecond, numElemsAfterDeleteSecond);
        assertEquals(expectedAfterDeleteThird, numElemsAfterDeleteThird);
    }

    @Test
    public void should_CorrectlyAddAndDeleteMidElem_WhenElemsHaveSameHashCode() {
        //given
        TestObject firstElem = new TestObject(1);
        TestObject secondElem = new TestObject(2);
        TestObject thirdElem = new TestObject(3);
        HashTable<TestObject> hash = new HashQuadraticProbing<>();

        //when
        hash.put(firstElem);
        hash.put(secondElem);
        hash.put(thirdElem);

        int numElemsAfterPutAll = getNumOfElems(hash);

        hash.delete(secondElem);

        int numElemsAfterDeleteSecond = getNumOfElems(hash);

        TestObject resultGetSecond = hash.get(secondElem);

        //then
        int expectedAfterPutAll = 3;
        int expectedAfterDeleteSecond = 2;
        TestObject expectedResultGetSecond = null;
        assertEquals(expectedAfterPutAll, numElemsAfterPutAll);
        assertEquals(expectedAfterDeleteSecond, numElemsAfterDeleteSecond);
        assertEquals(expectedResultGetSecond, resultGetSecond);
    }

    @Test
    public void should_CorrectlyPutGetAndDeleteElems_WhenOperateOnManyElems() {
        //given
        int size = 1;
        rand = new Random(SEED);
        int sizeTable = 1000000;
        Double[] table = new Double[sizeTable];
        HashTable<Double> hash = new HashQuadraticProbing<>(size, 0.5, 0.5);
        boolean state = true;

        //when
        for (int i = 0; i < sizeTable; i++) {
            table[i] = rand.nextDouble();
        }

        for (int i = 0; i < sizeTable; i++) {
            hash.put(table[i]);
        }

        int numOfElemsAfterPuOperations = getNumOfElems(hash);

        for (int i = 0; i < sizeTable; i++) {
            if (hash.get(table[i]).compareTo(table[i]) != 0) {
                state = false;
            }
        }

        for (int i = 0; i < sizeTable; i++) {
            hash.delete(table[i]);
        }

        int numOfElemsAfterAllOperations = getNumOfElems(hash);

        //then
        int expectedAfterAll = 0;
        int expectedAfterPut = sizeTable;
        boolean expectedState = true;
        assertEquals(expectedState, state);
        assertEquals(expectedAfterPut, numOfElemsAfterPuOperations);
        assertEquals(expectedAfterAll, numOfElemsAfterAllOperations);
    }

    @Test
    public void should_CorrectlyPutGetAndDeleteElems_WhenOperateOnManyElemsWithSameHashCode() {
        //given
        int size = 1;
        int sizeTable = 10000;
        TestObject[] table = new TestObject[sizeTable];
        HashTable<TestObject> hash = new HashQuadraticProbing<>(size, 0.5, 0.5);
        boolean state = true;

        //when
        for (int i = 0; i < sizeTable; i++) {
            table[i] = new TestObject(i);
        }

        for (int i = 0; i < sizeTable; i++) {
            hash.put(table[i]);
        }

        int numOfElemsAfterPuOperations = getNumOfElems(hash);

        for (int i = 0; i < sizeTable; i++) {
            if (hash.get(table[i]).compareTo(table[i]) != 0) {
                state = false;
            }
        }

        for (int i = 0; i < sizeTable; i++) {
            hash.delete(table[i]);
        }

        int numOfElemsAfterAllOperations = getNumOfElems(hash);

        //then
        int expectedAfterAll = 0;
        int expectedAfterPut = sizeTable;
        boolean expectedState = true;
        assertEquals(expectedState, state);
        assertEquals(expectedAfterPut, numOfElemsAfterPuOperations);
        assertEquals(expectedAfterAll, numOfElemsAfterAllOperations);
    }

    private int getNumOfElems(HashTable<?> hash) {
        String fieldNumOfElems = "nElems";
        try {
            System.out.println(hash.getClass().getSuperclass().getName());
            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldNumOfElems);
            field.setAccessible(true);

            int numOfElems = field.getInt(hash);

            return numOfElems;

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private int getSize(HashTable<?> hash) {
        String fieldSize = "size";
        try {
            System.out.println(hash.getClass().getSuperclass().getName());
            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldSize);
            field.setAccessible(true);

            int size = field.getInt(hash);

            return size;

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private class TestObject implements Comparable<TestObject> {

        private int id;

        public TestObject(int id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return 2137;
        }

        @Override
        public int compareTo(TestObject o) {
            if (o == null) {
                return -1;
            } else if (id == o.id) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
