import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final int capacity;

    private final Map<K, Node<K, V>> cache;
    private int size = 0;
    private Node<K, V> LRU;
    private Node<K, V> MRU;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        LRU = new Node<>(null, null, null, null);
        MRU = LRU;
    }

    public int size() {
        assert size >= 0 && size <= capacity;
        return size;
    }

    public V get(K key) {
        int sizeBeforeGet = size();

        Node<K, V> queryNode = cache.get(key);
        if (queryNode == null){
            assert sizeBeforeGet == size();
            return null;
        } else if (queryNode.key == MRU.key) {
            assert sizeBeforeGet == size();
            return MRU.value;
        }

        Node<K, V> next = queryNode.next;
        Node<K, V> previous = queryNode.previous;
        if (queryNode.key == LRU.key) {
            next.previous = null;
            LRU = next;
        } else {
            previous.next = next;
            next.previous = previous;
        }

        queryNode.previous = MRU;
        MRU.next = queryNode;
        MRU = queryNode;
        MRU.next = null;

        assert sizeBeforeGet == size();
        return queryNode.value;
    }

    public void put(K key, V value) {
        int sizeBeforePut = size();
        boolean wasInCache = cache.containsKey(key);

        if (!cache.containsKey(key)) {
            Node<K, V> node = new Node<>(MRU, null, key, value);
            cache.put(key, node);
            MRU.next = node;
            MRU = node;
            if (size >= capacity) {
                LRU = LRU.next;
                LRU.previous = null;
                cache.remove(LRU.key);
            } else {
                size += 1;
            }
        }

        assert (sizeBeforePut == capacity && size() == capacity) || (sizeBeforePut < capacity
                && ((wasInCache && sizeBeforePut == size()) || (!wasInCache && sizeBeforePut + 1 == size())));
        assert cache.containsKey(key) && cache.get(key).value == value;
    }

    public V remove(K key) {
        int sizeBeforePut = size();
        boolean wasInCache = cache.containsKey(key);

        if (cache.containsKey(key)) {
            Node<K, V> node = cache.get(key);
            cache.remove(key);
            if (node.key == MRU.key) {
                MRU = MRU.previous;
                MRU.next = null;
            } else if (node.key == LRU.key){
                LRU = LRU.next;
                LRU.previous = null;
            } else {
                node.previous.next = node.next;
                node.next.previous = node.previous;
            }
            size -= 1;
            assert ((wasInCache && sizeBeforePut - 1 == size())
                || (!wasInCache && sizeBeforePut == size()));
            return node.value;
        }
        return null;
    }

    static class Node<T, U> {
        Node<T, U> previous;
        Node<T, U> next;
        T key;
        U value;

        public Node(Node<T, U> previous, Node<T, U> next, T key, U value){
            this.previous = previous;
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }
}
