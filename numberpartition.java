import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;


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
        
    }

}