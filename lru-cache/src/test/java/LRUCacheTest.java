import org.junit.Assert;
import org.junit.Test;

public class LRUCacheTest {
    private final String cafe = "cafe";
    private final String babe = "babe";
    private final String dead = "dead";
    private final String beef = "beef";


    @Test
    public void create() {
        LRUCache<Integer, String> lru = new LRUCache<>(3);
        Assert.assertEquals(0, lru.size());
    }

    @Test
    public void get() {
        LRUCache<Integer, String> lru = new LRUCache<>(3);
        Assert.assertNull(lru.get(-1));
        Assert.assertNull(lru.get(0));
        Assert.assertNull(lru.get(1));
        lru.put(-1, cafe);
        lru.put(1, babe);
        Assert.assertEquals(cafe, lru.get(-1));
        Assert.assertNull(lru.get(0));
        Assert.assertEquals(babe, lru.get(1));
    }

    @Test
    public void put() {
        LRUCache<Integer, String> lru = new LRUCache<>(3);
        lru.put(0, cafe);
        lru.put(1, babe);
        lru.put(2, dead);
        lru.put(3, beef);
        Assert.assertEquals(3, lru.size());
        Assert.assertEquals(babe, lru.get(1));
        Assert.assertEquals(dead, lru.get(2));
        Assert.assertEquals(beef, lru.get(3));
        Assert.assertNull(lru.get(0));
    }

    @Test
    public void remove() {
        String returned;
        LRUCache<Integer, String> lru = new LRUCache<>(3);
        lru.put(0, cafe);
        lru.put(1, babe);
        lru.put(2, dead);

        returned = lru.remove(2);
        Assert.assertEquals(dead, returned);

        lru.put(3, beef);

        Assert.assertEquals(3, lru.size());
        Assert.assertEquals(cafe, lru.get(0));
        Assert.assertEquals(babe, lru.get(1));
        Assert.assertEquals(beef, lru.get(3));
        Assert.assertNull(lru.get(2));

        returned = lru.remove(0);
        Assert.assertEquals(cafe, returned);

        Assert.assertEquals(2, lru.size());
        Assert.assertNull(lru.get(0));
        Assert.assertEquals(babe, lru.get(1));
        Assert.assertEquals(beef, lru.get(3));
        Assert.assertNull(lru.get(2));
    }
}
