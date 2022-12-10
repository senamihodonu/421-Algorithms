import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Utility class to service the Knapsack aprroaches
 * @author Senami Hodonu
 */
public class Utils {

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

    /**
     * Returns the items to include in the knapsack
     * @return
     */
    public ArrayList<Integer> getOptimalSet(){
        return optimalSet;
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

    /**
     * Help write string to text file
     * @param filename
     * @param text
     */
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
    public void resultSet(ArrayList<Integer> optimalSet, int totalWeight, Object optimalValue, int tableReferences){
        System.out.println();
        System.out.println("Optimal solution: ");
        System.out.println(optimalSet); 
        System.out.println("Total Weight: " + totalWeight);
        System.out.println("Optimal Value: " + optimalValue);  
        if(tableReferences > 0){
            System.out.println("Number of table references: " + tableReferences);       
        }
        System.out.println();  
    }

}
