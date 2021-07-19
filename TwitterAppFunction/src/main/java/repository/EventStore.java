package repository;

public interface EventStore<T> {
    void save(T object);
    void delete(T object);
}
