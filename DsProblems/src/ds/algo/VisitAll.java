package ds.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

public class VisitAll {

  public static void main(String[] args) {
    int[][] b = new int[][] {
        new int[] {13, 14, 15, 16},
        new int[] {9, 10, 11, 12},
        new int[] {6, 6, 7, 8},
        new int[] {1, 2, 3, 4}
    };

    Solution s = new Solution(b, new Pair<>(3, 3));
    System.out.println(s.findSolution(new Pair<>(0, 0)));
  }

  private static class Solution {

    private int[][] board;
    private Pair<Integer, Integer> target;
    private int h;
    private int w;
    private int size;

    public Solution(int[][] b, Pair<Integer, Integer> e) {
      this.board = b;
      h = b.length;
      w = b[0].length;
      size = h * w;
      this.target = e;
    }

    public List<Integer> findSolution(Pair<Integer, Integer> start) {
      return solution(Arrays.asList(Arrays.asList(start)));
    }

    private List<Integer> solution(List<List<Pair<Integer, Integer>>> paths) {
      if (paths == null || paths.isEmpty()) return Collections.emptyList();
      System.out.println();
      paths.stream().map(l -> l.stream().map(p -> board[p.first()][p.second()])
          .collect(Collectors.toList())).forEach(System.out::println);
      Optional<List<Pair<Integer, Integer>>> opPath = paths.stream()
          .filter(l -> l.size() == size && target.equals(last(l))).findFirst();
      System.out.println("opPath: " + opPath);
      if (opPath.isPresent()) {
        return opPath.get().stream().map(p -> board[p.first()][p.second()])
            .collect(Collectors.toList());
      }
      return solution(paths.stream().flatMap(l -> next(l).stream()).collect(Collectors.toList()));
    }

    private List<List<Pair<Integer, Integer>>> next(List<Pair<Integer, Integer>> path) {
      Pair<Integer, Integer> curr = last(path);
      return Arrays.asList(
          new Pair<>(curr.first(), curr.second() + 1),
          new Pair<>(curr.first() + 1, curr.second()),
          new Pair<>(curr.first(), curr.second() - 1),
          new Pair<>(curr.first() - 1, curr.second())
      ).stream().filter(p -> validMove(p) && !path.contains(p)).map(p -> new Pair<>(new ArrayList<>(path), p)).map(p -> {
        p.first().add(p.second());
        return p.first();
      }).collect(Collectors.toList());
    }

    private boolean validMove(Pair<Integer, Integer> step) {
      return step.first() >= 0 && step.first() < h && step.second() >= 0 && step.second() < w;
    }

    private Pair<Integer, Integer> last(List<Pair<Integer, Integer>> l) {
      return l.get(l.size() - 1);
    }
  }
}
