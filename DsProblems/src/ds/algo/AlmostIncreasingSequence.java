package ds.algo;

/*
Problem:
Given a sequence of integers as an array, determine whether it is possible to obtain a strictly increasing sequence by removing no more than one element from the array.

Note: sequence a0, a1, ..., an is considered to be a strictly increasing if a0 < a1 < ... < an. Sequence containing only one element is also considered to be strictly increasing.

Example

For sequence = [1, 3, 2, 1], the output should be
almostIncreasingSequence(sequence) = false.

There is no one element in this array that can be removed in order to get a strictly increasing sequence.

For sequence = [1, 3, 2], the output should be
almostIncreasingSequence(sequence) = true.

You can remove 3 from the array to get the strictly increasing sequence [1, 2]. Alternately, you can remove 2 to get the strictly increasing sequence [1, 3].
 */
public class AlmostIncreasingSequence {
    public static void main(String[] args) {
        System.out.println(almostIncreasingSequence(new int[] {10, 1, 2, 3, 4, 5}));
        System.out.println(almostIncreasingSequence(new int[] {1, 2, 1, 2}));
        System.out.println(almostIncreasingSequence(new int[] {1, 3, 2, 1}));
        System.out.println(almostIncreasingSequence(new int[] {1, 2, 5, 3, 5}));
    }

    private static boolean almostIncreasingSequence(int[] sequence) {
        if (sequence.length <= 2) return true;
        int last = 0;
        int rCount = 0;
        for (int i = 1; i < sequence.length && rCount < 2; i++) {
            if (sequence[i] <= sequence[last]) {
                if (i == 1 || sequence[i] > sequence[last - 1]) {
                    last = i;
                }
                rCount++;
            } else {
                last = i;
            }
        }
        return rCount < 2;
    }
}
