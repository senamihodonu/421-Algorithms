import java.io.IOException;
import java.util.ArrayList;
/**
 * Program uses dynamic approach, top-down approach to solve 0-1 Knapsack 
 * problem
 * @author Senami Hodonu
 */
public class KnapsackTD{

    private int[][] decisionTable;
    private int optimalValue;
    private ArrayList<Integer> optimalSet;
    private int tableReferences;
    
    /**
     * Basic constructor
     */
    public KnapsackTD(){
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
    * Return decistion table
    * @return
    */
    public int[][] getDecisionTable(){
        return decisionTable;
    }

    /**
     * Recursive function to solve Knapsack problem
     * @param item
     * @param weight
     * @param value
     * @param Capacity
     * @param valueTable - 2D integer array
     * @return
     */
    private int topDownRecursive(int item, int[] weight, int[] value, int Capacity, int valueTable[][]){
        tableReferences++;
        if(item == 0 || Capacity == 0 ){
            return 0;
        }

        if(valueTable[item][Capacity] != 0){
            return valueTable[item][Capacity];
        }

        if(weight[item-1] > Capacity){
            return valueTable[item][Capacity] = topDownRecursive(item-1, weight, value, Capacity, valueTable);
        } else 

            return valueTable[item][Capacity] = Math.max(value[item-1] + topDownRecursive(item-1, weight, value, Capacity-weight[item-1], valueTable), topDownRecursive(item-1, weight, value, Capacity, valueTable)); 
    }

    /**
     * Knapsack top down value table
     * @param item
     * @param weight
     * @param value
     * @param Capacity
     * @return
     */
    public int[][] knapsackTD_VTable(int item, int[] weight, int[] value, int Capacity){
        int valueTable[][] = new int[item + 1][Capacity + 1];
        for(int i = 0; i < item + 1; i++)  
            for(int j = 0; j < Capacity + 1; j++)  
                valueTable[i][j] = 0;   
        
        int result = topDownRecursive(item, weight, value, Capacity, valueTable);
        optimalValue = result;
        return valueTable;  
    }

    public static void main(String[] args){
        System.out.println("Knapsack Top Down Approach");

        if(args.length < 4){
            System.out.println("java KnapsackTD <n> <W> <w.txt> <v.txt> [<debug level>]");
            System.exit(1);
        }

        KnapsackTD td = new KnapsackTD();
        Utils utils = new Utils();

        int numItems = Integer.valueOf(args[0]);
        int capacity= Integer.valueOf(args[1]);
        String weights = args[2];
        String values = args[3];
            try {
                int[] weightArr = utils.readFile(weights);
                int[] valueArr = utils.readFile(values);
                int[][] valueTable = td.knapsackTD_VTable(numItems, weightArr, valueArr, capacity);
                int[][] decisionTable = utils.decisionTable(numItems, weightArr, valueArr, capacity,valueTable);
                int tableReferences = td.getTableReferences();
                int optimalValue = td.getOptimalValue();

                utils.resultSet(utils.getOptimalSet(), capacity, optimalValue, tableReferences); 

                if(args.length == 5 && Integer.valueOf(args[4]) == 1){
                    //print to file
                    utils.write2File("KnapsackTD-DTable", utils.print2D(decisionTable));
                    utils.write2File("KnapsackTD-VTable", utils.print2D(valueTable));
                } 

            } catch (IOException e) {
                System.out.println("Error encountered");
                e.printStackTrace();
            }


        
        
    }

    
}
