import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestArrayDequeGold {
    @Test
    public void testArratDeque()
    {
        ArrayDequeSolution<Integer> ads=new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad=new StudentArrayDeque<>();
        //add last
        for(int i=0;i<10;i++)
        {
            int random=StdRandom.uniform(100);
            ads.addLast(random);
            sad.addLast((random));
        }
        for(int i=0;i<10;i++)
        {
            int actual=ads.get(i);
            int expected=sad.get(i);
            assertEquals("Oh noooo!\nThis is bad in addLast():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }

        //add first
        for(int i=0;i<10;i++)
        {
            int random=StdRandom.uniform(100);
            ads.addFirst(random);
            sad.addFirst(random);
        }
        for(int i=0;i<10;i++)
        {
            int actual=ads.get(i);
            int expected=sad.get(i);
            assertEquals("Oh noooo!\nThis is bad in addFirst():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }

        //remove first
        List<Integer> actualList=new ArrayList<>();
        List<Integer> expectedList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            actualList.add(ads.removeFirst());
            expectedList.add(sad.get(i));
        }
        for(int i=0;i<10;i++)
        {
            int actual=ads.get(i);
            int expected=sad.get(i);
            assertEquals("Oh noooo!\nThis is bad in removeFirst():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }
        for(int i=0;i<10;i++)
        {
            int actual=actualList.get(i);
            int expected=expectedList.get(i);
            assertEquals("Oh noooo!\nThis is bad in removeFirst():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }

        //remove last
        actualList.clear();
        expectedList.clear();
        for(int i=0;i<10;i++)
        {
            actualList.add(ads.removeLast());
            expectedList.add(sad.removeLast());
        }
        int actual=ads.size();
        int expected=sad.size();
        assertEquals("Oh noooo!\nThis is bad in removeLast():\n   actual size " + actual
                        + " not equal to " + expected + "!",
                expected, actual);
        for(int i=0;i<10;i++)
        {
            assertEquals("Oh noooo!\nThis is bad in removeLast():\n   actual size " + actual
                            + " not equal to " + expected + "!",
                    expectedList.get(i), actualList.get(i));
        }
    }



}
