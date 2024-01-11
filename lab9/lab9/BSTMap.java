package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Xu Zhiyu
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if(key == null) {throw new IllegalArgumentException("calls get() with a null key");}
        if(p == null) { return null;}
        int cmp = key.compareTo(p.key);
        if (cmp < 0) return getHelper(key, p.left);
        else if (cmp > 0) return getHelper(key, p.right);
        else return p.value;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) return new Node(key, value);
        int cmp = key.compareTo(p.key);
        if (cmp < 0) p.left = putHelper(key, value, p.left);
        else if (cmp > 0) p.right = putHelper(key, value, p.right);
        else p.value = value;
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if(key == null)  throw new IllegalArgumentException("calls put() with a null key");
        root = putHelper(key, value, root);
        this.size ++;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map.*/
    private void keySetHelper(Node p, Set<K> keys) {
        if (p == null) return;
        keys.add(p.key);
        keySetHelper(p.left, keys);
        keySetHelper(p.right, keys);
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        keySetHelper(root, keys);
        return keys;
    }

    /* Return VALUE removed if key exists. Return Null if key doesn't exist.*/
    private V removeHelper(Node p, K key) {
        if (p == null) return null;
        int cmp = key.compareTo(p.key);
        if (cmp < 0) return removeHelper(p.left, key);
        else if (cmp > 0) return removeHelper(p.right, key);
        else {
            V removedValue = p.value;
            if (p.right == null) {
                p = p.left;
            } else if (p.left == null) {
                p = p.right;
            } else {
                p.right = swapSmallest(p.right, p);
            }
            return removedValue;
        }
    }

    /* Move the <key, value> from the first node in T(in an inorder
    * traversal) to node R (over-writing the current <key, value> of R),
    * remove the first node of T from T, and return the resulting tree.
    * */
    private Node swapSmallest(Node T, Node R) {
        if (T.left == null) {
            R.value = T.value;
            R.key = T.key;
            return T.right;
        } else {
            T.left = swapSmallest(T.left, R);
            return T;
        }
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("calls remove(K key) with a null key.");
        V val = get(key);
        if(val == null) {
            return null;
        }
        size --;
        return removeHelper(root, key);
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls remove(K key, V value) with a null key.");
        if (value.equals(get(key))) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
