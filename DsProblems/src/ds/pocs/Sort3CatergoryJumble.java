package com.relcache.core.pocs;

import java.util.*;

import static com.relcache.core.pocs.Sort3CatergoryJumble.Ball.Color.*;

public class Sort3CatergoryJumble {

    public static void main(String[] args) {

        Ball[] balls = generateJumbledBalls(10);
        System.out.println(Arrays.toString(balls));
        sort(balls);
        System.out.println(Arrays.toString(balls));

    }

    static class Ball {
        static enum Color {
            RED, GREEN, BLUE
        }
        private final Color color;

        Ball(Color color) {
            Objects.requireNonNull(color);
            this.color = color;
        }

        @Override
        public String toString() {
            return color.toString();
        }
    }

    static Ball[] generateJumbledBalls(int size) {
        /*Random rnd = new Random();
        return IntStream.range(0, 10)
                .mapToObj(i -> new Ball(toColor(rnd.nextInt(3))))
                .toArray(s -> new Ball[s]);*/
        return new Ball[] {new Ball(BLUE), new Ball(RED), new Ball(RED), new Ball(RED), new Ball(BLUE), new Ball(RED), new Ball(RED), new Ball(RED), new Ball(BLUE),
                new Ball(GREEN), new Ball(RED), new Ball(RED), new Ball(RED), new Ball(GREEN), new Ball(RED), new Ball(RED), new Ball(RED), new Ball(GREEN)};
    }

    private static Ball.Color toColor(final int i) {
        switch (i) {
            case 0: return Ball.Color.RED;
            case 1: return Ball.Color.GREEN;
            case 2: return Ball.Color.BLUE;
            default: return null;
        }
    }

    private static void sort(final Ball[] balls) {
        Objects.requireNonNull(balls);
        int left = 0; int right = balls.length -1; int leftAhead = left; int rightAhead = right;
        // 1. The left pointer ensures every ball before this position is BLUE
        // 2. The right pointer ensures every ball after this position is GREEN
        // 3. The leftAhead pointer ensures there is no BLUE ball before this position
        // 4. The rightAhead pointer ensures there is no GREEN ball after this position
        int i = 1;
        while (inRange(balls, left, right)
                    && (inRange(balls, leftAhead, right)
                            || inRange(balls, left, rightAhead))) {
            System.out.println("At iteration[" + i + "]: " + Arrays.toString(balls));
            // Moving left and right so that they fulfill the conditions in 1 and 2
            print(i, 1, left, right, leftAhead, rightAhead);
            while (inRange(balls, left, right) && balls[left].color == BLUE) {
                left++;
            }
            print(i, 2, left, right, leftAhead, rightAhead);
            while (inRange(balls, left, right) && balls[right].color == GREEN) {
                right--;
            }
            print(i, 3, left, right, leftAhead, rightAhead);
            while (inRange(balls, left, right) && balls[left].color == GREEN) {
                swap(balls, left, right--);
            }
            print(i, 4, left, right, leftAhead, rightAhead);
            while (inRange(balls, left, right) && balls[right].color == BLUE) {
                swap(balls, left++, right);
            }
            print(i, 5, left, right, leftAhead, rightAhead);

            // Moving leftAhead so that it fulfills the condition in 3
            if (leftAhead < left) {
                leftAhead = left;
            }
            while (inRange(balls, leftAhead, right) && balls[leftAhead].color != BLUE) {
                leftAhead++;
            }
            if (inRange(balls, leftAhead, right)) {
                swap(balls, leftAhead, left++);
            }
            print(i, 6, left, right, leftAhead, rightAhead);

            // Moving rightAhead so that it fulfills the condition in 4
            if (rightAhead > right) {
                rightAhead = right;
            }
            while (inRange(balls, left, rightAhead) && balls[rightAhead].color != GREEN) {
                rightAhead--;
            }
            if (inRange(balls, left, rightAhead)) {
                swap(balls, rightAhead, right--);
            }
            print(i, 7, left, right, leftAhead, rightAhead);
            i++;
            // To ensure it doesn't run into infinite loop
            if (i > 100) {
                break;
            }
        }
    }

    private static void print(int iteration, int step, int left, int right, int tempLeft, int tempRight) {
        System.out.println("[Iteration: " + iteration + ", Step: " + step + "] Left: " + left + "; tempLeft: " + tempLeft + "; tempRight: " + tempRight + "; Right: " + right);
    }

    private static boolean inRange(Ball[] balls, int left, int right) {
        return inRange(balls, left) && inRange(balls, right) && left <= right;
    }

    private static boolean inRange(Ball[] balls, int i) {
        return i >= 0 && i < balls.length;
    }

    private static void swap(final Ball[] balls, int x, int y) {
        if (inRange(balls, x) && inRange(balls, y) && x != y) {
            Ball t = balls[x];
            balls[x] = balls[y];
            balls[y] = t;
        }
    }


}
