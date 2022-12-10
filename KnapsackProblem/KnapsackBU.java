import java.io.IOException;
import java.util.ArrayList;
/**
 * Program uses dynamic approach, buttom-up approach to solve 0-1 Knapsack 
 * problem
 * @author Senami Hodonu
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
     * Knapsack buttom up value table
     * @param item
     * @param weight
     * @param value
     * @param Capacity
     * @return
     */
    public int[][] knapsackBU_VTable(int item, int[] weight, int[] value, int Capacity){
        int[][] valueTable = new int[item+1][Capacity+1];
        
        //for item = 0
        for(int c = 0; c <= Capacity; c++){ //base case
            valueTable[0][c] = 0;
        }

        // for capacity = 0
        for(int i = 1; i <= item; i++){ //base case
            valueTable[i][0] = 0;
        }

        for(int i = 1; i <= item; i++){
            for(int c = 1; c <= Capacity; c++){
                if(weight[i-1] > c){
                    valueTable[i][c] = valueTable[i-1][c];
                    tableReferences++;

                } else {
                    valueTable[i][c]= Math.max(valueTable[i-1][c], value[i-1]+valueTable[i-1][c-weight[i-1]]);
                    tableReferences=tableReferences+2;
                    // System.out.println(B[i][c]);
                }
            }
        }
        optimalValue = valueTable[item][Capacity];
        return valueTable;
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
                int[] weightArr = utils.readFile(weights);
                int[] valueArr = utils.readFile(values);
                int[][] knapsackButtomUp = buttomUp.knapsackBU_VTable(numItems, weightArr, valueArr, capacity);
                int[][] decisionTable = utils.decisionTable(numItems, weightArr, valueArr, capacity,knapsackButtomUp);
                int tableReferences = buttomUp.getTableReferences();
                int optimalValue = buttomUp.getOptimalValue();

                utils.resultSet(utils.getOptimalSet(), capacity, optimalValue, tableReferences);

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