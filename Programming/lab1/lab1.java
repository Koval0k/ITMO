import java.util.Arrays;

public class lab1 {
    public static void main(String[] args) {
        int[] z1 = new int[10];
        int c = 0;
        for (int i = 22; i >= 4; i = i - 2) {
            z1[c] = i;
            c++;
        }

        double[] x = new double[11];
        for (int i = 0; i < 11; i++) {
            x[i] = Math.random() * 26 - 13;
        }

        double[][] z = new double[10][11];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 11; j++) {
                z[i][j] = MakeArray(z1[i], x[j]);
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.printf("%.4f", z[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static double MakeArray(int z1, double x) {
        double OneThird = (double) 1 / 3;
        return switch (z1) {
            case 12 -> Math.pow(Math.E, Math.cos(Math.asin((double)x / 26)));
            case 6, 8, 14, 18, 20 -> Math.pow(2 * Math.asin((double)x / 26), OneThird);
            default ->
                    Math.atan(Math.pow(Math.E, Math.pow(-Math.pow(Math.sin(Math.pow(Math.asin((double)x / 26), OneThird)), 2), OneThird)));
        };
    }
}