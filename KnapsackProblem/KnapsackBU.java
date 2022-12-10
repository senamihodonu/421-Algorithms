import java.io.IOException;
import java.util.ArrayList;
/**
 * Program uses dynamic approache, buttom-up approach to solve 0-1 Knapsack 
 * problem
 */
public class KnapsackBU{

    /*Instance variables*/
    private int[][] decisionTable;
    private int optimalValue;
    private ArrayList<Integer> optimalSet;
    private int tableReferences;
    
    /**
     * Constructor: Initializes variables
     */
    public KnapsackBU(){
        optimalValue = 0;
        tableReferences = 0;
    }

    /**
    * Returns the optimal solution
    * @return optimalSolution
    */
    public int getTableReferences(){
        return tableReferences;
    }

    /**
    * Returns the optimal solution
    * @return optimalSolution
    */
    public int getOptimalValue(){
        return optimalValue;
    }

    /**
    * Returns the items to include in the knapsack
    * @return
    */
    public ArrayList<Integer> getOptimalSet(){
        return optimalSet;
    }

    /**
    * Return decision table from value table
    * @return
    */
    public int[][] getDecisionTable(){
        return decisionTable;
    }

    /**
     * Memoization table
     * @param n - items
     * @param w - weight
     * @param v - value
     * @param C - Capacity
     * @return
     */
    public int[][] knapsackBU_VTable(int item, int[] weight, int[] value, int Capacity){
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
                    tableReferences++;
                    // System.out.println(B[i][c]);
                } else {
                    B[i][c]= Math.max(B[i-1][c], value[i-1]+B[i-1][c-weight[i-1]]);
                    tableReferences=tableReferences+2;
                    // System.out.println(B[i][c]);
                }
            }
        }
        optimalValue = B[item][Capacity];
        return B;
    }

    public static void main(String[] args){
        System.out.println("Knapsack Bottom Up Approach");

        if(args.length < 4){
            System.out.println("java KnapsackBU <n> <W> <w.txt> <v.txt> [<debug level>]");
            System.exit(1);
        }

        KnapsackBU buttomUp = new KnapsackBU();
        Utils utils = new Utils();

        int numItems = Integer.valueOf(args[0]);
        int capacity= Integer.valueOf(args[1]);
        String weights = args[2];
        String values = args[3];
            try {
                int[] w = utils.readFile(weights);
                int[] v = utils.readFile(values);
                int[][] knapsackButtomUp = buttomUp.knapsackBU_VTable(numItems, w, v, capacity);
                int[][] decisionTable = utils.decisionTable(numItems, w, v, capacity,knapsackButtomUp);
                int tableReferences = buttomUp.getTableReferences();
                int optimalValue = buttomUp.getOptimalValue();

                utils.resultSet(utils.getOptimalSet(), capacity, optimalValue, tableReferences);
                System.out.println();    

                if(args.length ==5 && Integer.valueOf(args[4]) == 1){
                    //print to file
                    utils.write2File("KnapsackBU-DTable", utils.print2D(decisionTable));
                    utils.write2File("KnapsackBU-VTable", utils.print2D(knapsackButtomUp));
                } 

            } catch (IOException e) {
                System.out.println("Error encountered");
                e.printStackTrace();
            }

    }
}