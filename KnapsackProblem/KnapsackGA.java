import java.util.Scanner;
public class KnapsackGA {
    private static int getMaxIndex(int[] weight, int[] values) {
        int max_i = 0;
        double max = 0;
        for (int i = 0; i < weight.length; i++) {
            if (weight[i] != 0 && (double) values[i] / weight[i] > max) {
                max = (double) values[i] / weight[i];
                max_i = i;
            }
        }
        return max_i;
    }
    private static double getOptimalValue(int capacity_is, int[] values, int[] weight) {
        double value = 0.0;
        for (int i = 0; i < weight.length; i++) {
            if (capacity_is == 0)
                return value;
            int index = getMaxIndex(weight, values);
            int a = Math.min(capacity_is, weight[index]);
            value += a * (double) values[index] / weight[index];
            weight[index] -= a;
            capacity_is -= a;
        }
        return value;
    }
    public static void main(String args[]) {
        try (Scanner scanner = new Scanner(System.in)) {
            int p = scanner.nextInt();
            int capacity_is = scanner.nextInt();
            int[] values = new int[p];
            int[] weight = new int[p];
            for (int i = 0; i < p; i++) {
                values[i] = scanner.nextInt();
                weight[i] = scanner.nextInt();
            }
            System.out.println(getOptimalValue(capacity_is, values, weight));
        }
    }
}