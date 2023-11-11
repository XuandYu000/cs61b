package synthesizer;

/**
 * @author Xu Zhiyu
 * @date 2023/11/10 下午10:11
 * @desciption: an abstract class which implements BoundedQueue,
 * capturing the redundancies between methods in BoundedQueue
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    // If a class don't implement all the methods from Interface,
    // it must be declared as an abstract class.
    protected int fillCount;
    protected int capacity;
    public int capacity() {
        return this.capacity;
    }

    public int fillCount() {
        return this.fillCount;
    }
}
