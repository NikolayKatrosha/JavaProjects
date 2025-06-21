/**
 * Task: Swap Two Variables
 *
 * Description:
 * Given two integer variables a and b, the program prints their initial values,
 * swaps them using a temporary variable, and then prints the updated values.
 *
 * Example output:
 * Before swapping:
 * a = 10
 * b = 20
 *
 * After swapping:
 * a = 20
 * b = 10
 */

package hillel.elementary.homework1;

public class Task1 {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;

        System.out.println("Before swapping:");
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        int temp = a;
        a = b;
        b = temp;

        System.out.println("\nAfter swapping:");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
