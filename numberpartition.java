import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;
import java.lang.Math;

public class numberpartition {
    
    //number of iterations for approximation algorithms
    public static final int max_iter = 25000;
    public static final long bound = 1000000000000L;
    public static final int setsize = 100;
    
    public static void main(String [] args) throws FileNotFoundException{
        
        PrintStream ps = new PrintStream(System.out);

        //ensure proper arguments
        if(args.length > 1){
            ps.printf("Input should be of the form: ./kk inputfile, 100 integers in file \n");
            return;
        }

        //create file reader
        Scanner s;
        s = new Scanner(new BufferedReader(new FileReader(args[0])));

        //load numbers from file into array
        long[] nums = new long[100];
        for(int i = 0; i < 100; i++){
            nums[i] = s.nextLong();
        }
        
        
        //long[] test = {10,8,7,6,5,10,4,3,13,5};
        
        
        /*
        //partition generation/move tests
        int [] P = randomPartGen(10);
        setPrint(P);
        randomPartMove(P);
        setPrint(P);
        randomPartMove(P);
        setPrint(P);
        randomPartMove(P);
        setPrint(P);
        */
        
        
        //print output
        long file_residue = kk(nums);
        ps.printf("%d\n",file_residue);

        /*
        // log data into CSV file
        try {
            FileWriter writer = new FileWriter("results.csv");
            // 50 random instances
            for (int i = 1; i <= 50; i++) {
                long[] A = standardRandomSetGen(setsize);
                int[] S = standardRandomSolGen(setsize);
                // original residue
                writer.append(String.valueOf(standardResidue(S, A)));
                writer.append(',');
                // KK residue
                writer.append(String.valueOf(kk(A)));
                writer.append(',');
                // Standard Repeated Random residue
                int[] S_a = standardRepeatedRandom(S, A);
                writer.append(String.valueOf(standardResidue(S_a, A)));
                writer.append(',');
                // Standard Hill Climbing residue
                int[] S_b = standardHillClimbing(S, A);
                writer.append(String.valueOf(standardResidue(S_b, A)));
                writer.append(',');
                // Standard Simulated Annealing residue
                int[] S_c = standardSimulatedAnnealing(S, A);
                writer.append(String.valueOf(standardResidue(S_c, A)));
                writer.append(',');
                // Prepartitioning Repeated Random residue
                long res_a = repeatedPartRandom(A);
                writer.append(String.valueOf(res_a));
                writer.append(',');
                // Prepartitioning Hill Climbing residue
                long res_b = hillPartClimb(A);
                writer.append(String.valueOf(res_b));
                writer.append(',');
                // Prepartitioning Simulated Annealing residue
                long res_c = simularedPartAnnealing(A);
                writer.append(String.valueOf(res_c));
                writer.append('\n');
            }

            writer.flush();
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        */
    }

    /**
     * Karmarkar-Karp Algorithm implementation
     */
    public static long kk(long [] input){

        int length = input.length;

        PriorityQueue<Long> heap = new PriorityQueue<>(100,Collections.reverseOrder());

        //add all items to heap
        for(int i = 0; i < length; i++){
            heap.offer(input[i]);
        }

        //perform repeated differencing
        while(heap.size() > 1){
            long num1 = heap.poll();
            long num2 = heap.poll();
            heap.add(num1-num2);
        }

        //return last element in the heap
        return heap.poll();



    }

    /**
     * Repeated random algorithm with standard representation
     */
    public static int[] standardRepeatedRandom(int[] S, long[] A) {
        int[] S_1;
        for (int i = 0; i < max_iter; i++) {
            S_1 = standardRandomSolGen(S.length);
            if (standardResidue(S_1, A) < standardResidue(S, A)) {
                S = S_1;
            }
        }

        return S;
    }

    /**
     * Hill climbing algorithm with standard representation
     */
    public static int[] standardHillClimbing(int[] S, long[] A) {
        int[] S_1;
        for (int i = 0; i < max_iter; i++) {
            S_1 = standardRandomMove(S);
            if (standardResidue(S_1, A) < standardResidue(S, A)) {
                S = S_1;
            }
        }

        return S;
    }

    /**
     * Simulated annealing algorithm with standard representation
     */
    public static int[] standardSimulatedAnnealing(int[] S, long[] A) {
        int[] S_1;
        int[] S_2 = new int[S.length];
        System.arraycopy(S, 0, S_2, 0, S.length);

        for (int i = 0; i < max_iter; i++) {
            S_1 = standardRandomMove(S);
            if (standardResidue(S_1, A) < standardResidue(S, A)) {
                S = S_1;
            } else if (Math.random() < Math.exp(0 - (standardResidue(S_1, A) - standardResidue(S, A)) / T(i))) {
                    S = S_1;
                }
            if (standardResidue(S, A) < standardResidue(S_2, A)) {
                S_2 = S;
            }
        }

        return S_2;
    }

    /**
     * Cooling schedule function
     */
    public static double T(int iter) {
        return Math.pow(10, 10) * Math.pow(0.8, iter / 300.0);
    }

    /**
     * Calculates the residue
     */
    public static long standardResidue(int[] S, long[] A) {
        long residue = 0;
        for (int i = 0; i < S.length; i++) {
            residue += S[i] * A[i];
        }
        if (residue < 0) {
            residue = 0 - residue;
        }

        return residue;
    }

    /**
     * Perform a random move on set s
     */
    public static int[] standardRandomMove(int[] S) {
        int[] rmove = new int[S.length];
        System.arraycopy(S, 0, rmove, 0, S.length);

        // random indices
        int i = 0;
        int j = 0;
        while (i == j) {
            i = (int) (Math.random() * rmove.length);
            j = (int) (Math.random() * rmove.length);
        }
        // switch first index always
        rmove[i] = 0 - rmove[i];
        // switch second index with prob 0.5
        if (Math.random() < 0.5) {
            rmove[j] = 0 - rmove[j];
        }

        return rmove;
    }

    /**
     * Generate random set of n elements
     */
    public static long[] standardRandomSetGen(int n) {
        long[] set = new long[n];
        for (int i = 0; i < n; i++) {
            set[i] = randomLong();
        }

        return set;
    }

    /**
     * Generate a random long between 1 and 10^12
     */
    public static long randomLong() {
        return (long)Math.ceil((Math.random() * bound));
    }

    /**
     * Generate a random solution of n elements
     */
    public static int[] standardRandomSolGen(int n) {
        int sol[] = new int[n];

        for (int i = 0; i < n; i++) {
            if (Math.random() < 0.5) {
                sol[i] = 1;
            } else {
                sol[i] = -1;
            }
        }

        return sol;
    }
    
    
    /**
     * Perform repeated random algorithm with prepartitioning for num_iters trials
     */
    public static long repeatedPartRandom(long[] A){
        
        int length = A.length;
        
        int[] P = randomPartGen(length);
        int[] P2 = P.clone();
        
        long prev_res = partitionResidue(P,A);
        long new_res = -1;
        
        //iterate over possible solutions max_iter times
        //*note* wasting a lot of memory allocation here, can
        //change randomPartGen if necessary
        for(int i = 0; i < max_iter;i++){
            P2 = randomPartGen(length);
            new_res = partitionResidue(P2,A);
            if(new_res < prev_res){
                P = P2.clone();
                prev_res = new_res;
            }
        }
        
        //return residue of final partition
        return prev_res;
        
    }
    
    /**
     * Return residue from hill-climbing on random prepartition
     */
    public static long hillPartClimb(long[] A){
        
        int length = A.length;
        
        int[] P = randomPartGen(length);
        //will hold changes in original partition
        int[] P2 = P.clone();
        
        long prev_res = partitionResidue(P,A);
        long new_res = -1;
        
        //iterate over possible solutions max_iter times
        //*note* wasting a lot of memory allocation here, can
        //change randomPartGen if necessary
        for(int i = 0; i < max_iter;i++){
            randomPartMove(P2);
            new_res = partitionResidue(P2,A);
            if(new_res < prev_res){
                P = P2.clone();
                prev_res = new_res;
            }
        }
        
        //return residue of final partition
        return prev_res;
    }
    
    /**
     * Returns residue from simulated annealing on prepartitioned set
     */
    public static long simularedPartAnnealing(long[] A){
        int length = A.length;
        
        int[] P = randomPartGen(length);
        int[] P2 = P.clone();
        int[] P3 = P.clone();
        
        
        long min_res = partitionResidue(P,A);
        long prev_res = min_res;
        long new_res = -1;
        
        for(int i = 0; i < max_iter; i++){
            randomPartMove(P2);
            new_res = partitionResidue(P2,A);
            if(new_res < prev_res){
                P = P2.clone();
                prev_res = new_res;
            }else if(Math.random() < Math.exp((-(new_res-prev_res)/(((10000000000L)*Math.pow(0.8, i/300.0)))))){
                P = P2.clone();
                prev_res = new_res;
            }
            if(prev_res < min_res){
                P3 = P.clone();
                min_res = prev_res;
            }
        }
        
        return min_res;
        
    }
    
    /**
     * Return a residue from Karmarkar-Karp given a partition and a set
     */
    public static long partitionResidue(int[] P, long[] A){
        int length = P.length;
        
        //this is an error, return -1
        if(length!=A.length){
            return -1;
        }
        
        long[] newInput = new long[length];
        
        for(int i = 0; i < length; i++){
            newInput[P[i]]+=A[i];
        }
        
        return kk(newInput);
    }
    
    /**
     * Generate a random partition of size n 
     */
    public static int[] randomPartGen(int n){
        int[] partition = new int[n];
        for(int i = 0; i < n; i++){
            int part = (int)(Math.random()*n);
            partition[i] = part;
        }
        return partition;
    }
    
    /** 
     * Perform a random move on partition P, given existing partition
     */
    public static void randomPartMove(int[] P){
        //index  i whose partition number p_i will change
        int from_index;
        //index j the partition p_i will change to
        int to_index;
        
        int length = P.length;
        
        from_index = (int)(Math.random()*length);
        
        to_index = (int)(Math.random()*(length-1));
        
        //if equal, increment in order to align range of to_index w/ from_index
        if(to_index==P[from_index]){
            ++to_index;
        }
        
        P[from_index] = to_index;
        
        
        
        
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

    /**
     * Print an array of non-negative integers (long)
     */
    public static void setPrint(long[] sol) {
        System.out.printf("[%2d", sol[0]);
        for (int i = 1; i < sol.length; i++) {
            System.out.printf("|%2d", sol[i]);
        }
        System.out.printf("]\n");
    }

}