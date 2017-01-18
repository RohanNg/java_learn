package test;

import main.Container;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by rohan on 1/18/17.
 */
class ContainerTest {
    private Container<String> container1 = new Container<String>(1000, "Hello", 2000);

    private <T> Container<T> make(double capa, T content, double init){
        return new Container<T>(capa, content, init);
    }

    private <T> Container<T> make(double capa, T content){
        return new Container<T>(capa, content);
    }

    @Test
    void exceptionShouldbeThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            make(-100, "hello", 10);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            make(0, "hello", 10);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            make(100, "hello", -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            container1.fill(-1);
        });
    }

    /*
    Input space partition:
        capacity: (<0 ,0) 10, Double.POSITIVE_INFINITY
        initialAmount: _, (<0,) 0, 10, Double.POSITIVE_INFINITY

    Out put space partition:
        capacity = initialAmount, +INFINITY, >0
        initialAmount: + INFINITY, 0, capacity
        content = content
     */
    @Test
    void testConstrutors(){
        assertEquals(1000, container1.getCapacity(), "Capacity should be 1000");
        assertEquals(1000, container1.getAvailableAmount(),"Available amount should be 1000");
        assertEquals(0.0, make(100,"Hello").getAvailableAmount());
        assertTrue(make(1000,"asd").isEmpty());
        assertEquals("Hello", make(100,"Hello").getContent());
        assertEquals(Double.POSITIVE_INFINITY, make(Double.POSITIVE_INFINITY, "Hello").getCapacity());
        assertEquals(Double.POSITIVE_INFINITY, make(Double.POSITIVE_INFINITY, "Hello", Double.POSITIVE_INFINITY).getAvailableAmount());
        assertTrue(Arrays.deepEquals(new Array[3],make(10, new Array[3], 1).getContent()));
    }

    /*
        Test change content for string
     */
    @Test
    void testChangeContent() {
        // change content for immutable object
        Container<String> ctn= make(100,"123456");
        ctn.changeContent("abc");
        assertEquals("abc", ctn.getContent());
        ctn.changeContent("");
        assertEquals("", ctn.getContent());
        // change content for mutable object
        Container<List<String>> ctn2 = make(100, new ArrayList<>());
        assertTrue(ctn2.getContent().equals(new ArrayList<>()));
        ctn2.changeContent(Arrays.asList("a","b","c"));
        assertTrue(ctn2.getContent().equals(Arrays.asList("a","b","c")));
    }

    @Test
    void testFill() {
        Container<String> ctn= make(100,"123456");
        ctn.fill(50);
        assertEquals(50.0, ctn.getAvailableAmount());
        ctn.fill(20.0);
        assertEquals(70.0, ctn.getAvailableAmount());
        ctn.fill(100);
        assertEquals(100.0, ctn.getAvailableAmount());
        ctn.fill(Double.POSITIVE_INFINITY);
        assertEquals(100.0, ctn.getAvailableAmount());
    }

    @Test
    void testFillFull() {
        Container<String> ctn= make(100000,"123456");
        ctn.fillFull();
        assertEquals(100000, ctn.getAvailableAmount());
        assertTrue(ctn.isFull());

        Container<String> ctn2= make(Double.POSITIVE_INFINITY,"123456");
        ctn2.fill(100);
        assertEquals(100.0, ctn2.getAvailableAmount());

        ctn2.fillFull();
        assertTrue(ctn2.isFull());
        assertEquals(Double.POSITIVE_INFINITY, ctn2.getAvailableAmount());
    }

    @Test
    void testTake() {

        Container<String> ctn= make(100000,"123456");
        assertFalse(ctn.take(1000));
        assertEquals(0, ctn.getAvailableAmount());
        assertTrue(ctn.isEmpty());

        assertFalse(ctn.take(Double.POSITIVE_INFINITY));
        assertEquals(0, ctn.getAvailableAmount());

        ctn.fill(Double.POSITIVE_INFINITY);
        assertTrue(ctn.take(10));
        assertEquals(100000 - 10, ctn.getAvailableAmount());
        assertFalse(ctn.take(Double.POSITIVE_INFINITY));
        assertFalse(ctn.take(ctn.getAvailableAmount() + 1));
        assertTrue(ctn.take(ctn.getAvailableAmount()));
        assertTrue(ctn.isEmpty());

        // taking from +INFINITY
        Container<String> ctn2= make(Double.POSITIVE_INFINITY,"123456", Double.POSITIVE_INFINITY);
        assertTrue(ctn2.take(Double.POSITIVE_INFINITY));
        assertEquals(0, ctn2.getAvailableAmount());
    }


    // BOSS TEST
    @Test
    void randomCombinationTest() {
        Random ran = new Random();
        double capacity = 10000;
        Container<String> ctn2= make(capacity,"123456");
        for(int x = 1; x < 1000 ; x++) {
            int ranInt = Math.abs(ran.nextInt()%4);
            int ranAmount = Math.abs(ran.nextInt());
            double initial = ctn2.getAvailableAmount();
            switch (ranInt) {
                case 0:
                    ctn2.fill(ranAmount);
                    assertTrue(ctn2.getAvailableAmount() ==
                            ((capacity <= initial + ranAmount)? capacity : initial + ranAmount));
                    break;
                case 1:
                    if(ranAmount > initial) {
                        assertFalse(ctn2.take(ranAmount));
                        assertEquals(initial, ctn2.getAvailableAmount());
                    } else {
                        assertTrue(ctn2.take(ranAmount));
                        assertEquals(initial - ranAmount, ctn2.getAvailableAmount());
                    }
                    break;
                case 2:
                    ctn2.fillFull();
                    assertTrue(ctn2.isFull());
                    break;
                case 3:
                    ctn2.takeAll();
                    assertTrue(ctn2.isEmpty());
            }
        }
    }
}