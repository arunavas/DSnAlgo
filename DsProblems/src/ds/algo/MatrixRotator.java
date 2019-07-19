package ds.algo;

/*
Problem:
Note: Try to solve this task in-place (with O(1) additional memory), since this is what you'll be asked to do during an interview.

You are given an n x n 2D matrix that represents an image. Rotate the image by 90 degrees (clockwise).

Example

For

a = [[1, 2, 3],
     [4, 5, 6],
     [7, 8, 9]]
the output should be

rotateImage(a) =
    [[7, 4, 1],
     [8, 5, 2],
     [9, 6, 3]]
 */
public class MatrixRotator {
    public static void main(String[] args) {
        int[][] arr = new int[][] {new int[]{10,9,6,3,7}, new int[]{6,10,2,9,7}, new int[]{7,6,3,8,2}, new int[] {8,9,7,9,9}, new int[] {6,8,6,8,2}};
        for (int[] ints : arr) {
            for (int j = 0; j < ints.length; j++) {
                System.out.print(" " + ints[j]);
            }
            System.out.println();
        }
        System.out.println("========================");
        rotateImage(arr);
        System.out.println("========================");
        for (int[] ints : arr) {
            for (int j = 0; j < ints.length; j++) {
                System.out.print(" " + ints[j]);
            }
            System.out.println();
        }
    }
    static int[][] rotateImage(int[][] a) {
        for (int k = 0; k < a.length / 2; k++) {
            for (int i = k, j = a.length - (k * 2) - 1; i < a.length - k - 1; i++, j--) {
                //System.out.println(k + "," + i);
                int x = k, y = i;
                int x_ = x + j, y_ = k + y - i;
                int temp = a[x][y];
                //System.out.println(x + "," + y + " <- " + x_ + "," + y_);
                a[x][y] = a[x_][y_];
                x = x_;
                y = y_;
                x_ = x + i - k;
                y_ = y_ + j;
                //System.out.println(x + "," + y + " <- " + x_ + "," + y_);
                a[x][y] = a[x_][y_];
                x = x_;
                y = y_;
                x_ = x_ - j;
                y_ = y + i - k;
                //System.out.println(x + "," + y + " <- " + x_ + "," + y_);
                a[x][y] = a[x_][y_];
                x = x_;
                y = y_;
                //System.out.println(x + "," + y + " <- " + k + "," + i);
                a[x][y] = temp;
                //a[0][i] = temp;
            }
        }
        return a;
    }
}
