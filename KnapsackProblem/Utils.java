import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

    // instance variables
    // private int[][] KnapsackBU_VTable;
    // private int KnapsackTD_VTable;
    private int[][] KnapsackBU_DTable;
    private int optimalSolution;
    private ArrayList<Integer> optimalSet;

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
    private int[] readFile(String string) throws IOException{
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
    /**
     * Returns the optimal solution
     * @return optimalSolution
     */
    public int getOptimalSolution(){
        return optimalSolution;
    }

    /**
     * Returns the items to include in the knapsack
     * @return
     */
    public ArrayList<Integer> getOptimalSet(){
        return optimalSet;
    }

    /**
     * Return 
     * @return
     */
    public int[][] getDecisionTable(){
        return KnapsackBU_DTable;
    }
    
    /**
     * Memoization table
     * @param n - items
     * @param w - weight
     * @param v - value
     * @param C - Capacity
     * @return
     */
    public int[][] KnapsackBU_VTable(int item, int[] weight, int[] value, int Capacity){
        int[][] B = new int[item+1][Capacity+1];
        
        //for item = 0
        for(int c = 0; c <= Capacity; c++){ //base case
            B[0][c] = 0;
        }

        // for capacity = 0
        for(int i = 1; i <= item; i++){ //base case
            B[i][0] = 0;
        }

        for(int i = 1; i <= item; i++){
            for(int c = 1; c <= Capacity; c++){
                if(weight[i-1] > c){
                    B[i][c] = B[i-1][c];
                    // System.out.println(B[i][c]);
                } else {
                    B[i][c]= Math.max(B[i-1][c], value[i-1]+B[i-1][c-weight[i-1]]);
                    // System.out.println(B[i][c]);
                }
            }
        }
        optimalSolution = B[item][Capacity];
        return B;
    }

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
    /**
     * Decision/Solution table
     * @param n
     * @param w
     * @param v
     * @param C
     * @return
     */
    public int[][] KnapsackBU_DTable(int n, int[] w, int[] v, int C){
        int[][] decisionArray = new int[n+1][C+1];
        int[][] valueTable = KnapsackBU_VTable(n, w, v, C);
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
    //     if(item == 0 || Capacity == 0 ){
    //         return 0;
    //     }

    //     if(valueTable[item][Capacity] != 0){
    //         return valueTable[item][Capacity];
    //     }

    //     if(weight[item-1] > Capacity){
    //         return valueTable[item][Capacity] = topDownRecursive(item-1, weight, value, Capacity, valueTable);
    //     } else 
    //         return valueTable[item][Capacity] = Math.max(value[item-1] + topDownRecursive(item-1, weight, value, Capacity-weight[item-1], valueTable), topDownRecursive(item-1, weight, value, Capacity, valueTable)); 
    // }

    // public int KnapsackTD_VTable(int item, int[] weight, int[] value, int Capacity){

    //     int valueTable[][] = new int[item + 1][Capacity + 1];
        
    //     for(int i = 0; i < item + 1; i++)  
    //         for(int j = 0; j < Capacity + 1; j++)  
    //             valueTable[i][j] = 0;   
        
    //     int result = topDownRecursive(item, weight, value, Capacity, valueTable);
    //     KnapsackBU_VTable = valueTable;
    //     return result;  
    // }
    ////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args){
        Utils utils = new Utils();

        try {
            int[] v = utils.readFile("./tests/v.txt");
            // System.out.println(Arrays.toString(v));
            int[] w = utils.readFile("./tests/w.txt");
            // utils.problemTable(w, v);

            int[][] B = utils.KnapsackBU_VTable(17, w, v, 20);
            // int[][] D = utils.KnapsackBU_DTable(4, w, v, 5);
            // int T = utils.KnapsackTD_VTable(4, w, v, 5);
            // int[][] T2 =  utils.KnapsackBU_VTable;


            for(int i = 0; i < B.length; i++){
                for(int j = 0; j < B[0].length; j++){
                    System.out.print(B[i][j] + " ");
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
