
import java.io.IOException;
import java.util.ArrayList;
/**
 * Program uses greedy approach  solve 0-1 Knapsack problem
 * @author Senami Hodonu
 */
public class KnapsackGA { 
    private ArrayList<Integer> optimalSet;
    /**
     * Basic constructor
     */
    public KnapsackGA(){
        optimalSet = new ArrayList<>();
    }
    /**
     * Function to return the index of the value to weight ratio
     * @param weight
     * @param values
     * @return index of max value per weight
     */
    private int maxIndex(int[] weight, int[] values) {
        int maxIndex = 0;
        double maxValPerWeight = 0;
        for (int i = 0; i < weight.length; i++) {
            if (weight[i] != 0 && ((double) values[i] / (double) weight[i]) > maxValPerWeight) {
                maxValPerWeight = ((double) values[i] / (double) weight[i]);
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * Function to return optimal value
     * @param capacity
     * @param values
     * @param weight
     * @return optimal value
     */
    private double getOptimalValue(int items, int capacity, int[] values, int[] weight) {
        ArrayList<Integer> temp = new ArrayList<>();
        double value = 0.0;
        for (int i = 0; i < weight.length; i++) {
            if (capacity == 0){
                return value;
            }
            int index = maxIndex(weight, values);
            //seeing if bag is full
            int room = Math.min(capacity, weight[index]);
            value += room * (double) values[index] / weight[index];
            if(weight[index] <= capacity){
                temp.add(weight[index]);
            }
            weight[index] -= room;
            capacity -= room;
            optimalSet = temp;
        }
         return value;
    }

    /**
     * Returns the optimal set of weights
     * @return optimal set
     */
    private ArrayList<Integer> getOptimalSet(){
        return optimalSet;
    }

    public static void main(String[] args) { 
        KnapsackGA ga = new KnapsackGA();
        Utils utils = new Utils();
        int numItems = Integer.valueOf(args[0]);
        int capacity= Integer.valueOf(args[1]);
        String weights = args[2];
        String values = args[3];
        

        try {
            int[] w = utils.readFile(weights);
            int[] v = utils.readFile(values);
            double optimalValue = ga.getOptimalValue(numItems, capacity, v, w); 
            utils.resultSet(ga.getOptimalSet(), capacity, optimalValue, 0); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
} 