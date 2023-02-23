package pl.edu.pw.ee.services;

public interface HashTable<T> {

    void add(T value);

    T get(T value);
    
    void delete(T value);

}