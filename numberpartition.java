import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;
import java.lang.Math;

public class numberpartition {
    public static void main(String [] args) throws FileNotFoundException{
        PrintStream ps = new PrintStream(System.out);


        //ensure proper arguments
        if(args.length != 2){
            ps.printf("Input should be of the form: ./kk inputfile, 100 integers in file \n");
            return;
        }

        //create file reader
        Scanner s;
        s = new Scanner(new BufferedReader(new FileReader(args[1])));

        //load numbers from file into array
        int[] nums = new int[100];
        for(int i = 0; i < 100; i++){
            nums[i] = s.nextInt();
        }

        int[] test = {10,8,7,6,5};

        //print output
        int residue = kk(test,5);
        ps.printf("%d\n",residue);

    } 
    
    public static int kk(int [] input, int length){

        //make sets for debugging purposes
        ArrayList<Integer> set1 = new ArrayList<>();
        ArrayList<Integer> set2 = new ArrayList<>();

        PriorityQueue<Integer> heap = new PriorityQueue<>(100,Collections.reverseOrder());

        //add all items to heap
        for(int i = 0; i < length; i++){
            heap.offer(input[i]);
        }

        //perform repeated differencing
        while(heap.size() > 1){
            int num1 = heap.poll();
            int num2 = heap.poll();
            heap.add(num1-num2);
        }

        //return last element in the heap
        return heap.poll();


// TEST STUFF FOR RANDOM REPRESENTATION
//        int[] sol = randomSolGen(3);
//        solPrint(sol);
//        randomMove(sol);
//        solPrint(sol);

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