/**
 * first part of project1A
 * Deque implemented by Linked List
 * @author XuandYu000
 */

public class LinkedListDeque<T> {
    /** inner class Node*/
    public class Node{
        private T item;
        private Node prev;
        private Node next;

        public Node(){
            prev = next = null;
        }

        public Node(T item, Node ppre, Node nnext){
            this.item = item;
            prev = ppre;
            next = nnext;
        }

        public Node(Node ppre, Node nnext){
            prev = ppre;
            next = nnext;
        }

    }

    private Node sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new Node(null, null);
        sentinel.prev = sentinel.next = sentinel;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void addFirst(T item){
        Node e = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = e;
        sentinel.next = e;
        size++;
    }

    public void addLast(T item){
        Node e = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = e;
        sentinel.prev = e;
        size++;
    }

    public T removeFirst(){
        if(size == 0) {
            return null;
        }
        T res = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        return res;
    }

    public T removeLast(){
        if(size == 0){
            return null;
        }
        T res = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        return res;
    }

    public T get(int index){
        if(index < 0 || index >= size ){
            return null;
        }
        Node ptr = sentinel.next;
        for(int i = 0; i < index; i ++){
            ptr = ptr.next;
        }
        return ptr.item;
    }

    private T getRecursiveHelper(Node ptr, int index){
        if(index == 0){
            return ptr.item;
        }
        return getRecursiveHelper(ptr.next, index - 1);
    }

    public T getRecursive(int index){
        if(index < 0 || index >= size){
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    public void printDeque(){
        Node ptr = sentinel.next;
        while (ptr != sentinel){
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
    }
}