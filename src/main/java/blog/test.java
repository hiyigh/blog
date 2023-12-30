package blog;

import java.util.Scanner;

public class test {
    public static void test(String args[]) {
        int[][] arr = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                arr[i][j] = (int) (Math.random() * 10 + 1);

                if (i == 3) {
                    arr[i][j] = arr[0][j] + arr[1][j] + arr[2][j];
                } else if (j == 3) {
                    arr[i][j] = arr[i][0] + arr[i][1] + arr[i][2];
                }
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
