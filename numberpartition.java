import java.lang.Math;

public class numberpartition {
    public static void main(String [] args){
        int[] sol = randomSolGen(3);
        solPrint(sol);
        randomMove(sol);
        solPrint(sol);
    }

    /**
     * Repeated random algorithm with standard representation
     */
    public static void repeatedRandom() {
        // TODO
    }

    /**
     * Perform a random move on set s
     */
    public static void randomMove(int[] s) {
        // random indices
        int i = 0;
        int j = 0;
        while (i == j) {
            i = (int) (Math.random() * s.length);
            j = (int) (Math.random() * s.length);
        }
        // switch first index always
        s[i] = 0 - s[i];
        // switch second index with prob 0.5
        if (Math.random() < 0.5) {
            s[j] = 0 - s[j];
        }
    }

    /**
     * Generate random set of n elements
     */
    public static int[] randomSetGen(int n) {
        // TODO
        int[] some = new int[1];
        return some;
    }

    /**
     * Generate a random solution of n elements
     */
    public static int[] randomSolGen(int n) {
        int sol[] = new int[n];
        int counter = 0;

        for (int i = 0; i < n; i++) {
            if (Math.random() < 0.5) {
                sol[i] = 1;
                counter += 1;
            } else {
                sol[i] = -1;
            }
        }

        System.out.println((double)counter / n);
        return sol;
    }

    /**
     * Print a solution array
     */
    public static void solPrint(int[] sol) {
        System.out.printf("[%2d", sol[0]);
        for (int i = 1; i < sol.length; i++) {
            System.out.printf("|%2d", sol[i]);
        }
        System.out.printf("]\n");
    }

}