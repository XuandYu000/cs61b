package synthesizer;

/**
 * @author Xu Zhiyu
 * @date 2023/11/10 下午3:58
 * @desciption: an interface which declares all the methods that must be implemented by any class that implements BoundedQueue
 */
public interface BoundedQueue<T> {
    int capacity();     // return size of the buffer
    int fillCount();    // return number of items currently in the buffer
    void enqueue(T x);  // add item x to the end
    T dequeue();        // delete and return item from the front
    T peek();           // return (but do not delete) item from the front

    default boolean isEmpty() {
        return fillCount() == 0;
    }

    default boolean isFull() {
        return fillCount() == capacity();
    }
}
