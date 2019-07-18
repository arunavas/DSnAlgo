package ds.algo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
Problem:
There are N students in a class, each in possession of a different funny story. As the students were getting bored in the class, they decided to come up with a game so that they can pass their time.

They want to share the funny stories with each other by sending electronic messages. Assume that a sender includes all the funny stories he or she knows at the time the message is sent and that a message may only have one addressee. What is the minimum number of messages they need to send to guarantee that everyone of them gets all the funny stories?

Input:
The first line of input contains an integer T denoting the number of test cases. Then T test cases follow. Each testcase contains an integer N denoting the number of students.

Output:
For each testcase, print the minimum number of messages they need to send to guarantee that everyone of them gets all the funny stories.

Constraints:
1<=T<=100
1<=N<=105

Example:
Input:
1
2
Output:
2
 */
public class MessageSpread {
    public static void main(String[] args) throws Exception {
        List<Integer> input = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        while (t > 0) {
            input.add(Integer.parseInt(br.readLine()));
            t--;
        }

        //2*(n-1)
        input.forEach(i -> System.out.println(2 * (i - 1)));
    }
}
