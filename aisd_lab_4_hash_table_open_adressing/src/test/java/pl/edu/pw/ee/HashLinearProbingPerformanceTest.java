package pl.edu.pw.ee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.services.HashTable;

public class HashLinearProbingPerformanceTest {

    private HashTable<String> hashTable;
    ;
    private static final String FILENAME = "words.txt";
    private static final int WORDLISTSIZE = 100000;
    private File file;
    private Scanner scanner;
    private String[] words;
    private static final int HASHSIZE = 512;
    private long startRegisterTime;
    private long endRegisterTime;

    @Before
    public void setUp() throws FileNotFoundException {
        words = new String[WORDLISTSIZE];

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
    public void shouldRegisterPutTimeForDifferentHashSize() {
        System.out.println("Adresowanie liniowe");
        System.out.println("LP  " + "Poczatkowy rozmiar hasza  " + "Sredni czas wstawiania 100000 elementow");

        int counter = 1;

        for (int i = 1; i <= 512; i *= 2) {
            System.out.println(counter++ + "  " + i * HASHSIZE + "  " + registerTime(i * HASHSIZE));
        }
    }

    private double registerTime(int hashTableSize) {
        hashTable = new HashLinearProbing<>(hashTableSize);
        startRegisterTime = System.nanoTime();

        for (int i = 0; i < WORDLISTSIZE; i++) {
            hashTable.put(words[i]);
        }

        endRegisterTime = System.nanoTime();
        return (endRegisterTime - startRegisterTime) / 1000;
    }
}
