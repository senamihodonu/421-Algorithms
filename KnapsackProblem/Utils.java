import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

    // instance variables
    private int[][] KnapsackBU_VTable;
    private int KnapsackTD_VTable;
    private static int tdOptimalSolution;
    // private int[][] KnapsackBU_DTable;
    // private int buOptimalSolution;
    private ArrayList<Integer> optimalSet;

    public static int buRef = 0;
    public static int tdRef = 0;

    public Utils(){

    }
    public void usage(){
        System.out.println("java KnapsackBU <n> <W> <w.txt> <v.txt> [<debug level>]");
    }

    /**
     * Helper method to help parse txt files
     * @param string
     * @return
     * @throws IOException
     */
    int[] readFile(String string) throws IOException{
        File file = new File(string);
        ArrayList<String> fileContent = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;

            while((st=br.readLine()) != null){
                fileContent.add(st);
            }
        }
        int[] arr = new int[fileContent.size()];
        arr = fileContent.stream().mapToInt(i -> Integer.parseInt(i)).toArray();
        return arr;
    }
    // /**
    //  * Returns the optimal solution
    //  * @return optimalSolution
    //  */
    // public int getBUOptimalSolution(){
    //     return buOptimalSolution;
    // }

    // /**
    // * Return 
    // * @return
    // */
    // public int getTDDecisionTable(){
    //     return tdOptimalSolution;
    // }

    /**
     * Returns the items to include in the knapsack
     * @return
     */
    public ArrayList<Integer> getOptimalSet(){
        return optimalSet;
    }

    // /**
    //  * Return 
    //  * @return
    //  */
    // public int[][] getDecisionTable(){
    //     return KnapsackBU_DTable;
    // }
    
    /**
     * Table showing the weight and values to optimize
     * @param w
     * @param v
     * @return
     */
    public void problemTable(int[] w, int[] v){
        int[][] pTable = new int[w.length][2];
        for(int i = 0; i < pTable.length; i++){
            pTable[i][0] = w[i];
            pTable[i][1] = v[i];
        }
        System.out.printf(" %8s  %-8s  %4s %n", "Item", "Weight (w)", "Value (v)");
        int st = 1;
        for(int i = 0; i < pTable.length; i++){
            System.out.printf("%8s", st);
            st++;
            for(int j = 0; j < pTable[0].length; j++){
                System.out.printf("%9s",pTable[i][j]);
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    // /**
    //  * Memoization table
    //  * @param n - items
    //  * @param w - weight
    //  * @param v - value
    //  * @param C - Capacity
    //  * @return
    //  */
    // public int[][] knapsackBU_VTable(int item, int[] weight, int[] value, int Capacity){
    //     int[][] B = new int[item+1][Capacity+1];
        
    //     //for item = 0
    //     for(int c = 0; c <= Capacity; c++){ //base case
    //         B[0][c] = 0;
    //     }

    //     // for capacity = 0
    //     for(int i = 1; i <= item; i++){ //base case
    //         B[i][0] = 0;
    //     }

    //     for(int i = 1; i <= item; i++){
    //         for(int c = 1; c <= Capacity; c++){
    //             if(weight[i-1] > c){
    //                 B[i][c] = B[i-1][c];
    //                 buRef++;
    //                 // System.out.println(B[i][c]);
    //             } else {
    //                 B[i][c]= Math.max(B[i-1][c], value[i-1]+B[i-1][c-weight[i-1]]);
    //                 buRef=buRef+2;
    //                 // System.out.println(B[i][c]);
    //             }
    //         }
    //     }
    //     buOptimalSolution = B[item][Capacity];
    //     return B;
    // }
    
    // public int knapsackBU_OptimalValue(int items, int[] w, int[] v, int Capacity) {
    //     int[][] B = new int[items+1][Capacity+1];
        
    //     //for item = 0
    //     for(int c = 0; c <= Capacity; c++){ //base case
    //         B[0][c] = 0;
    //     }

    //     // for capacity = 0
    //     for(int i = 1; i <= items; i++){ //base case
    //         B[i][0] = 0;
    //     }

    //     for(int i = 1; i <= items; i++){
    //         for(int c = 1; c <= Capacity; c++){
    //             if(w[i-1] > c){
    //                 B[i][c] = B[i-1][c];
    //                 // System.out.println(B[i][c]);
    //             } else {
    //                 B[i][c]= Math.max(B[i-1][c], v[i-1]+B[i-1][c-w[i-1]]);
    //                 // System.out.println(B[i][c]);
    //             }
    //         }
    //     }
    //     buOptimalSolution = B[items][Capacity];
    //     return buOptimalSolution;
    // }


    /**
     * Decision/Solution table
     * @param n
     * @param w
     * @param v
     * @param C
     * @return
     */
    public int[][] decisionTable(int n, int[] w, int[] v, int C, int[][] valueTable){
        int[][] decisionArray = new int[n+1][C+1];
        // int[][] valueTable = knapsackBU_VTable(n, w, v, C);
        ArrayList<Integer> optimalSetTemp = new ArrayList<>();

        int i = n;
        int j = C;

        while(i > 0 && j > 0){
            if(valueTable[i][j] != valueTable[i-1][j]){
                decisionArray[i][j] = 1;
                optimalSetTemp.add(i);
                i--;
                j = j - w[i];
            } else {
                i = i - 1;
            }
        }
        optimalSet = optimalSetTemp;
        return decisionArray;
    }

    public void write2File(String filename, String text){
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(text);
            myWriter.close();
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    ///////////////////////////////////////////////////////////////////////////////
    // /**
    //  * 
    //  * @param item
    //  * @param weight
    //  * @param value
    //  * @param Capacity
    //  * @param valueTable - 2D integer array
    //  * @return
    //  */
    // private int topDownRecursive(int item, int[] weight, int[] value, int Capacity, int valueTable[][]){
    //     tdRef++;
    //     if(item == 0 || Capacity == 0 ){
    //         return 0;
    //     }

    //     if(valueTable[item][Capacity] != 0){
    //         return valueTable[item][Capacity];
    //     }

    //     if(weight[item-1] > Capacity){
    //         return valueTable[item][Capacity] = topDownRecursive(item-1, weight, value, Capacity, valueTable);
    //     } else 
    //         // tdRef=tdRef+3;
    //         return valueTable[item][Capacity] = Math.max(value[item-1] + topDownRecursive(item-1, weight, value, Capacity-weight[item-1], valueTable), topDownRecursive(item-1, weight, value, Capacity, valueTable)); 
    // }

    // public int[][] knapsackTD_VTable(int item, int[] weight, int[] value, int Capacity){
    //     int valueTable[][] = new int[item + 1][Capacity + 1];
    //     for(int i = 0; i < item + 1; i++)  
    //         for(int j = 0; j < Capacity + 1; j++)  
    //             valueTable[i][j] = 0;   
        
    //     int result = topDownRecursive(item, weight, value, Capacity, valueTable);
    //     tdOptimalSolution = result;
    //     return valueTable;  
    // }

    // public int knapsackTD_OptimalValue(int item, int[] weight, int[] value, int Capacity){
    //     int valueTable[][] = new int[item + 1][Capacity + 1];
    //     for(int i = 0; i < item + 1; i++)  
    //         for(int j = 0; j < Capacity + 1; j++)  
    //             valueTable[i][j] = 0;   
        
    //     int result = topDownRecursive(item, weight, value, Capacity, valueTable);
    //     return result;  
    // }

    // public int tdTableReference(){
    //     return tdRef;
    // }

    // public int buTableReference(){
    //     return buRef;
    // }


    /**
     * Helps print out 2Darray
     * @param array
     */
    public String print2D(int[][] array){
        String retVal = "";
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++){
                retVal += array[i][j] + " ";
            }
            retVal += "\n";
        }
       return retVal;
    }

    /**
    * Helps print out 2Darray      
    * @param array
     */
    public void resultSet(ArrayList<Integer> optimalSet, int totalWeight, int optimalValue, int tableReferences){
        System.out.println();
        System.out.println("Optimal solution: ");
        System.out.println(optimalSet); 
        System.out.println("Total Weight: " + totalWeight);
        System.out.println("Optimal Value: " + optimalValue);     
        System.out.println("Number of table references: " + tableReferences);       
    }
    


    ////////////////////////////////////////////////////////////////////////////////
    // public static void main(String[] args){
    //     Utils utils = new Utils();

    //     try {
    //         int[] v = utils.readFile("./tests/v1.txt");
    //         // System.out.println(Arrays.toString(v)); 
    //         int[] w = utils.readFile("./tests/w1.txt");
    //         // utils.problemTable(w, v);

    //         // int[][] B = utils.KnapsackBU_VTable(17, w, v, 20);
    //         // int[][] D = utils.KnapsackBU_DTable(4, w, v, 5);
    //         int T = utils.knapsackTD_OptimalValue(4, w, v, 5);
    //         int[][] T2 =  utils.knapsackTD_VTable(4, w, v, 5);

    //         System.out.println(T);

    //         for(int i = 0; i < T2.length; i++){
    //             for(int j = 0; j < T2[0].length; j++){
    //                 System.out.print(T2[i][j] + " ");
    //             }
    //             System.out.println();
    //         }

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }

    // }


}
