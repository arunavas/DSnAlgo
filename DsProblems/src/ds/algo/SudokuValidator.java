package ds.algo;

import java.util.HashSet;
import java.util.Set;

public class SudokuValidator {

    public static void main(String[] args) {
        char[][] grid = new char[][] {
                {'.','.','4','.','.','.','6','3','.'},
                {'.','.','.','.','.','.','.','.','.'},
                {'5','.','.','.','.','.','.','9','.'},
                {'.','.','.','5','6','.','.','.','.'},
                {'4','.','3','.','.','.','.','.','1'},
                {'.','.','.','7','.','.','.','.','.'},
                {'.','.','.','5','.','.','.','.','.'},
                {'.','.','.','.','.','.','.','.','.'},
                {'.','.','.','.','.','.','.','.','.'}
        };
        System.out.println(sudoku2(grid));
    }

    //Sudoku with characters and spaces (denoted by dots (.))
    static boolean sudoku2(char[][] grid) {
        boolean res = true;
        Set[] numbers = initializeSets(3);
        Set[] columns = initializeSets(9);
        for (int i = 0; i < 9; i++) {
            Set<Integer> currs = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != '.') {
                    int n = Character.getNumericValue(grid[i][j]);
                    if (n < 1 || n > 9 || currs.contains(n) || numbers[j / 3].contains(n) || columns[j].contains(n)) {
                        System.out.println("F" + i + "." + j + " - " + n + " " + currs + " " + numbers[j / 3] + " " + columns[j]);
                        res = false;
                        j = 9;
                        i = 9;
                    } else {
                        System.out.println("T" + i + "." + j + " - " + n + " " + currs + " " + numbers[j / 3] + " " + columns[j]);
                        currs.add(n);
                        numbers[j / 3].add(n);
                        columns[j].add(n);
                    }
                }
            }
            if ((i + 1) % 3 == 0) {
                System.out.println("Cleared!");
                numbers[0].clear();
                numbers[1].clear();
                numbers[2].clear();
            }
        }

        return res;
    }

    private static Set[] initializeSets(int c) {
        Set[] sets = new Set[c];
        for (int i = 0; i < c; i++) {
            sets[i] = new HashSet<Integer>();
        }
        return sets;
    }

    //Full Set of Sudoku
    //untested -- NEED TO TEST
    private static boolean validate(int[][] board) {
        boolean res = true;
        int[] numbers = new int[3];
        int[] columns = new int[9];
        for (int i = 0; i < 9; i++) {
            int total = 0;
            for (int j = 0; j < 9; j++) {
                if (board[i][j] > 0 && board[i][j] < 10) {
                    total += board[i][j];
                    numbers[j / 3] += board[i][j];
                    columns[j] += board[i][j];
                } else {
                    j = 9;
                    i = 9;
                    System.out.println("Invalid Number '" + board[i][j] + "' in sudoku!");
                    res = false;
                }
            }
            if (total != 45) {
                i = 9;
            }
            if ((i + 1) % 3 == 0) {
                if (numbers[0] != 45 || numbers[1] != 45 || numbers[2] != 45) {
                    i = 9;
                } else {
                    numbers[0] = 0;
                    numbers[1] = 0;
                    numbers[2] = 0;
                }
            }
        }
        if (res) {
            for (int i = 0; i < columns.length; i++) {
                if (columns[i] != 45) {
                    i = columns.length;
                    res = false;
                }
            }
        }

        return res;
    }

}
