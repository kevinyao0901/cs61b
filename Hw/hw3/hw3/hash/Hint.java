package hw3.hash;

public class Hint {
    public static void main(String[] args) {
        //256是1后面跟8个0，而int的表示范围是1后面跟32个0，所以最多乘四次就会溢出。
        // 所以hashCode的结果取决于ComplexOomage的List中的最后四个数。
        System.out.println("The powers of 256 in Java are:");
        int x = 1;
        for (int i = 0; i < 10; i += 1) {
            System.out.println(i + "th power: " + x);
            x = x * 256;
        }
    }
} 
