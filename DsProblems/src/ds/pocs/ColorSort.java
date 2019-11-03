package com.relcache.core.pocs;

import java.util.Arrays;

public class ColorSort {
    public static void main(String[] args) {
        System.out.println(1|3);
        int[] colors = new int[] {0, 1, 2, 1, 0};
        System.out.println(Arrays.toString(sortColors(colors)));
    }

    private static int[] sortColors(int[] colors) {
        int x = 0, y = 0, z = 0;
        for (int i = 0; i < colors.length; i++) {
            switch (colors[i]) {
                case 0:
                    x++;
                    break;
                case 1:
                    y++;
                    break;
                case 2:
                    z++;
                    break;
            }
        }
        int idx = 0;
        while (x > 0) {
            colors[idx++] = 0;
            x--;
        }
        while (y > 0) {
            colors[idx++] = 1;
            y--;
        }
        while (z > 0) {
            colors[idx++] = 2;
            z--;
        }
        return colors;
    }
}
