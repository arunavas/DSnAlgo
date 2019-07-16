package ds.algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MagicalSequence {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tests = Integer.parseInt(br.readLine());
        List<String> inputs = new ArrayList<>(tests);
        for (int i = 0; i < tests; i++) {
            br.readLine();
            String s = br.readLine();
            inputs.add(s);
        }
        List<Integer> lst = inputs.stream().parallel().map(MagicalSequence::magicalSubSequenceCount).collect(Collectors.toList());
        //lst.forEach(System.out::println);
        for (int i = 0; i < lst.size(); i++) {
            System.out.println(inputs.get(i) + " = " + lst.get(i));
        }
    }

    private static int magicalSubSequenceCount(String str) {
        int count = 0;
        int n = str == null ? 0 : str.length();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                String s = str.substring(i, j);
                if (isMagicalSequence(s)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isMagicalSequence(String str) {
        char[] arr = str.toCharArray();
        long sum = 0;
        for (int i = arr.length - 1, idx = 0; i >= 0; i--, idx++) {
            int x = Character.getNumericValue(arr[i]);
            if (idx % 2 != 0) {
                sum += doubleIt(x);
            } else {
                sum += x;
            }
        }
        return sum % 10 == 0;
    }

    private static int doubleIt(int x) {
        int d = x * 2;
        while (d >= 10) {
            int a = d / 10;
            int b = d % 10;
            d = a + b;
        }
        return d;
    }
}
