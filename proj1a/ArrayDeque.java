/**
 * second part of project1A
 * Deque implemented by Array
 * @author XuandYu000
 */


public class ArrayDeque<T> {
    private int len;
    private int size;
    private int front;
    private int tail;
    private T[] arr;

    public ArrayDeque(){
        len = 8;
        arr = (T[]) new Object[len];
        front = 4;
        tail = 4;
        size = 0;
    }

    private int minusOne(int index){
        if(index == 0){
            return len - 1;
        }
        return index - 1;
    }

    private int plusOne(int index, int module){
        index %= module;
        if(index == module - 1){
            return 0;
        }
        return index + 1;
    }

    private void resize(){
        int newlen = len << 1;
        T[] newArray = (T[]) new Object[newlen];
        int ptr1 = front;
        int ptr2 = len;
        while(ptr1 != tail){
            newArray[ptr2] = arr[ptr1];
            ptr1 = plusOne(ptr1, len);
            ptr2 = plusOne(ptr2, newlen);
        }
        front = len;
        tail = ptr2;
        arr = newArray;
        len = newlen;
    }

    private void shrink(){
        int newlen = len >> 1;
        T[] newArray = (T[]) new Object[newlen];
        int ptr1 = front;
        int ptr2 = len >> 2;
        while (ptr1 != tail){
            newArray[ptr2]  = arr[ptr1];
            ptr1 = plusOne(ptr1, len);
            ptr2 = plusOne(ptr2, newlen);
        }
        front = len >> 2;
        tail = ptr2;
        arr = newArray;
        len = newlen;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void addFirst(T item){
        if(size == len){
            resize();
        }
        front = minusOne(front);
        arr[front] = item;
        size++;
    }

    public void addLast(T item){
        if(size == len){
            resize();
        }
        arr[tail] = item;
        tail = plusOne(tail, len);
        size++;
    }

    public T removeFirst(){
        if(len >= 16 && len / size >= 4){
            shrink();
        }
        if(size == 0) {
            return null;
        }
        T res = arr[front];
        front = plusOne(front, len);
        size --;
        return res;
    }

    public T removeLast(){
        if(len >= 16 && len / size >= 4){
            shrink();
        }
        tail = minusOne(tail);
        size --;
        return arr[tail];
    }

    public T get(int index){
        if(index < 0 || index >= size){
            return null;
        }
        int ptr = front;
        for(int i = 0; i < index; i++){
            ptr = plusOne(ptr, len);
        }
        return arr[ptr];
    }

    public void printDeque(){
        int ptr = front;
        while (ptr != tail){
            System.out.print(arr[ptr] + " ");
            ptr = plusOne(ptr, len);
        }
    }
}