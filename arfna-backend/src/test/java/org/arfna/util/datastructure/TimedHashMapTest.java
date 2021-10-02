package org.arfna.util.datastructure;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TimedHashMapTest {

    @Test
    public void testSuccessInMap() throws InterruptedException {
        Map<Integer, Integer> map = new TimedHashMap<>(10);
        map.put(5, 10);
        map.put(3, 14);
        Thread.sleep(5 * 1000);
        assertTrue(map.containsKey(5));
        assertTrue(map.containsValue(10));
        assertFalse(map.containsValue(12));
    }

    @Test
    public void testExpiredInMap() throws InterruptedException {
        Map<Integer, Integer> map = new TimedHashMap<>(4);
        map.put(5, 10);
        map.put(3, 14);
        Thread.sleep(5 * 1000);
        assertFalse(map.containsKey(5));
    }

    @Test
    public void testSizeMap() throws InterruptedException {
        Map<Integer, Integer> map = new TimedHashMap<>(4);
        map.put(5, 10);
        map.put(3, 14);
        Thread.sleep(5 * 1000);
        map.put(7, 35);
        map.put(1, 11);
        assertEquals(2, map.size());
        map.remove(1);
        assertEquals(1, map.size());
        map.remove(7);
        assertTrue(map.isEmpty());
    }

    @Test
    public void testPropsMap() throws InterruptedException {
        Map<Integer, Integer> map = new TimedHashMap<>(4);
        map.put(5, 10);
        map.put(3, 14);
        Thread.sleep(5 * 1000);
        map.put(7, 35);
        map.put(1, 11);
        map.put(2, 4);
        Set<Integer> expectedKeySet = new HashSet<>(Arrays.asList(7, 1, 2));
        Set<Integer> expectedValues = new HashSet<>(Arrays.asList(35, 11, 4));
        assertTrue(map.keySet().containsAll(expectedKeySet) && expectedKeySet.containsAll(map.keySet()));
        assertTrue(map.values().containsAll(expectedValues) && expectedValues.containsAll(map.values()));
        map.forEach((key, value) -> assertTrue(expectedKeySet.contains(key) && expectedValues.contains(value)));
    }

}
