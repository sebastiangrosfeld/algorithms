package pl.edu.pw.ee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

public class HashListChainingPerformanceTest {

    private HashTable<String> hashTable;
    private File file;
    private Scanner scanner;
    private static final String FILENAME = "words.txt";
    private static final int WORDLISTSIZE = 100000;
    private static final int NUMOFTIMEREGISTRATIONS = 30;
    private static final int FIRSTIMPORTANT = 10;
    private static final int LASTIMPORTANT = 20;
    private static final int HASHSIZE = 4096;
    private String[] words;
    private long[] timeRegistrations;
    private long startRegisterTime;
    private long endRegisterTime;

    @Before
    public void setUp() throws FileNotFoundException {
        words = new String[WORDLISTSIZE];
        timeRegistrations = new long[NUMOFTIMEREGISTRATIONS];

        try {
            file = new File(FILENAME);
            scanner = new Scanner(file);
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException("Can not open file");
        }

        for (int i = 0; i < WORDLISTSIZE; i++) {
            words[i] = scanner.nextLine();
        }
    }

    @Test
    public void shouldPrintRegisteredTimeForAllHashTableSizesVariantPowerOfTwo() {
        System.out.println("LP  " + "Rozmiar hasza  " + "Sredni czas wyszukiwania");

        int counter = 1;

        for (int i = 1; i <= 64; i *= 2) {
            System.out.println(counter++ + "  " + i * HASHSIZE + "  " + registerTime(i * HASHSIZE));
        }
    }

    @Test
    public void shouldPrintRegisteredTimeForAllHashTableSizesVariantPrimeNumbers() {
        System.out.println("LP  " + "Rozmiar hasza  " + "Sredni czas wyszukiwania");
        int counter = 1;
        System.out.println(counter++ + "  " + findPrimeNumberSmallerThanArgument(HASHSIZE) + "  " + registerTime(findPrimeNumberSmallerThanArgument(HASHSIZE)));
        for (int i = 2; i <= 64; i *= 2) {
            if (i == 64) {
                System.out.println(counter++ + "  " + findPrimeNumberBiggerThanArgument(i * HASHSIZE) + "  " + registerTime(findPrimeNumberBiggerThanArgument(i * HASHSIZE)));
                break;
            }
            System.out.println(counter++ + "  " + findPrimeNumberNearestOfArgument(i * HASHSIZE) + "  " + registerTime(findPrimeNumberNearestOfArgument(i * HASHSIZE)));
        }
    }

    @Test
    public void shouldPrintSearchTime() {
        int hashSize = 65539;

        System.out.println("Sredni czas wyszukiwania dla hasza o rozmiare " + hashSize + " to " + registerTime(hashSize));
    }

    private double registerTime(int hashTableSize) {
        hashTable = new HashListChaining<>(hashTableSize);

        for (int i = 0; i < WORDLISTSIZE; i++) {
            hashTable.add(words[i]);
        }
        for (int i = 0; i < NUMOFTIMEREGISTRATIONS; i++) {
            startRegisterTime = System.nanoTime();

            for (int j = 0; j < WORDLISTSIZE; j++) {
                hashTable.get(words[i]);
            }

            endRegisterTime = System.nanoTime();
            timeRegistrations[i] = (endRegisterTime - startRegisterTime) / 1000;
        }

        Arrays.sort(timeRegistrations);

        return calculateAverageFromRegistrations(timeRegistrations);
    }

    private double calculateAverageFromRegistrations(long[] array) {
        long sum = 0;

        for (int i = FIRSTIMPORTANT; i < LASTIMPORTANT; i++) {
            sum += array[i];
        }

        return sum / (LASTIMPORTANT - FIRSTIMPORTANT);
    }

    private int findPrimeNumberBiggerThanArgument(int number) {
        for (;; number++) {
            boolean isPrime = true;

            for (int i = 2; i * i <= number; i++) {
                if (number % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                return number;
            }
        }
    }

    private int findPrimeNumberSmallerThanArgument(int number) {
        for (;; number--) {
            boolean isPrime = true;

            for (int i = 2; i * i <= number; i++) {
                if (number % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                return number;
            }
        }
    }

    private int findPrimeNumberNearestOfArgument(int number) {
        int deltaBigger = findPrimeNumberBiggerThanArgument(number) - number;
        int deltaSmaller = number - findPrimeNumberSmallerThanArgument(number);

        if (deltaBigger < deltaSmaller) {
            return findPrimeNumberBiggerThanArgument(number);
        } else {
            return findPrimeNumberSmallerThanArgument(number);
        }
    }
}
