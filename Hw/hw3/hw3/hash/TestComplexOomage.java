package hw3.hash;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {

        // Your code here.
        //256是1后面跟8个0，而int的表示范围是1后面跟32个0，所以最多乘四次就会溢出。
        // 所以hashCode的结果取决于ComplexOomage的List中的最后四个数。
        List<Oomage> deadlyList = new ArrayList<>();
        int N=10;
        ArrayList<Integer> params = new ArrayList<>(N);
        for (int i = 0; i < N; i += 1) {
            params.add(1);
        }
        for (int i = 0; i < N; i += 1) {
            ComplexOomage tmp=new ComplexOomage(params);
            deadlyList.add(tmp);
        }
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));




    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
