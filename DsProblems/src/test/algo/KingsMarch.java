import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        Function<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> kingMoveF = p -> Arrays.asList(new Pair<>(p.first(), p.second() - 1), new Pair<>(p.first() - 1, p.second() - 1), new Pair<>(p.first() - 1, p.second()));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tests = Integer.parseInt(br.readLine());
        List<Pair<ChessBoard, King>> inputs = new ArrayList<>(tests);
        for (int i = 0; i < tests; i++) {
            int n = Integer.parseInt(br.readLine());
            ChessBoard.ChessSquare[][] chessSquares = new ChessBoard.ChessSquare[n][n];
            for (int j = 0; j < n; j++) {
                String str = br.readLine();
                String[] arr = str.split(" ");
                for (int k = 0; k < arr.length; k++) {
                    ChessBoard.ChessSquare v = ChessBoard.ChessSquare.fromString(arr[k]);
                    if (v == null) {
                        throw new Exception("Invalid Board Element '" + arr[k] + "'. Must be among 's', 'e', 'x', '1..9'!");
                    } else {
                        chessSquares[j][k] = v;
                    }
                }
            }
            King king = new King(new Pair<Integer, Integer>(n - 1, n - 1), kingMoveF);
            ChessBoard board = new ChessBoard(chessSquares);
            inputs.add(new Pair<>(board, king));
        }
        /*for (Pair<ChessBoard, King> p : inputs) {
            Pair<Integer, Integer> res = findMaxPointsAndPath(p.first(), p.second());
            System.out.println(res.first() + " " + res.second());
        }*/
        long s = System.currentTimeMillis();
        List<Pair<Integer, Integer>> lst = inputs.stream().parallel().map(p -> findMaxPointsAndPath(p.first(), p.second())).collect(Collectors.toList());
        lst.forEach(res -> System.out.println(res.first() + " " + res.second()));
        System.out.println("Took: " + (System.currentTimeMillis() - s) + " millis!");
    }

    public static Pair<Integer, Integer> findMaxPointsAndPath(ChessBoard board, King king) {
        int maxCompleted = 0;
        int maxPath = 0;
        while (king.canMoveNext()) {
            //Map<Pair<x, y>, Pair<Count, Score>>
            //System.out.println("Current: " + king.getCurrentPositions() + " - " + king.getCompletedPathMap());
            Map<Pair<Integer, Integer>, Pair<Integer, Integer>> nexts = new HashMap<>();
            for (Map.Entry<Pair<Integer, Integer>, Pair<Integer, Integer>> pp : king.getCurrentPositions().entrySet()) {
                List<Pair<Integer, Integer>> temp = king.nextSteps(pp.getKey(), board::isValidPoint);
                //System.out.println("\t" + pp.getKey() + " -> " + temp);
                for (Pair<Integer, Integer> p : temp) {
                    int score = pp.getValue().second() + board.getScore(p);
                    if (board.isTarget(p)) {
                        king.addCompletedPath(score, Math.max(1, pp.getValue().first()));
                    } else {
                        Pair<Integer, Integer> existing = nexts.getOrDefault(p, null);
                        if (existing == null) {
                            nexts.put(p, new Pair<>(Math.max(1, pp.getValue().first()), score));
                        } else if (existing.second() == score) {
                            nexts.put(p, new Pair<>(existing.first() + pp.getValue().first(), score));
                        } else if (existing.second() < score) {
                            nexts.put(p, new Pair<>(Math.max(1, pp.getValue().first()), score));
                        } /*else {
                            nexts.put(p, new Pair<>(1, score));
                        }*/
                    }
                }
            }
            king.moveNext(nexts);
            if (king.getCompletedPathMap().size() > maxCompleted) {
                maxCompleted = king.getCompletedPathMap().size();
            }
            if (king.getCurrentPositions().size() > maxPath) {
                maxPath = king.getCurrentPositions().size();
            }
        }

        System.out.println("MaxPath: " + maxPath + " | MaxCompleted: " + maxCompleted);
        if (king.getCompletedPathMap().isEmpty()) {
            return new Pair<Integer, Integer>(0, 0);
        } else {
            Map.Entry<Integer, Integer> e = king.getCompletedPathMap().firstEntry();
            return new Pair<Integer, Integer>(e.getKey(), e.getValue());
        }
    }
}

class King {
    private final Function<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> moveF;
    private Map<Pair<Integer, Integer>, Pair<Integer, Integer>> currentPositions = new HashMap<>();
    private TreeMap<Integer, Integer> completedPathMap = new TreeMap<>(Collections.reverseOrder());

    public King(Pair<Integer, Integer> initial, Function<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> moveF) {
        this.moveF = moveF;
        currentPositions.put(initial, new Pair<>(0, 0));
    }

    public boolean canMoveNext() {
        return currentPositions != null && !currentPositions.isEmpty();
    }

    public void moveNext(Map<Pair<Integer, Integer>, Pair<Integer, Integer>> nexts) {
        this.currentPositions = nexts;
    }

    public List<Pair<Integer, Integer>> nextSteps(Pair<Integer, Integer> point, Function<Pair<Integer, Integer>, Boolean> validatorF) {
        return moveF.apply(point).stream().filter(validatorF::apply).collect(Collectors.toList());
    }

    public Map<Pair<Integer, Integer>, Pair<Integer, Integer>> getCurrentPositions() {
        return this.currentPositions;
    }

    public void addCompletedPath(int score, int count) {
        completedPathMap.put(score, completedPathMap.getOrDefault(score, 0) + count);
    }

    public TreeMap<Integer, Integer> getCompletedPathMap() {
        return this.completedPathMap;
    }
}

class ChessBoard {
    public enum ChessSquare {
        s(0), e(0), x(0), p1(1), p2(2), p3(3), p4(4), p5(5), p6(6), p7(7), p8(8), p9(9);

        public int value;
        ChessSquare(int value) {
            this.value = value;
        }

        public static ChessSquare fromString(String v) {
            ChessSquare res;
            switch (v.toLowerCase()) {
                case "s":
                    res = s;
                    break;
                case "e":
                    res = e;
                    break;
                case "x":
                    res = x;
                    break;
                case "1":
                    res = p1;
                    break;
                case "2":
                    res = p2;
                    break;
                case "3":
                    res = p3;
                    break;
                case "4":
                    res = p4;
                    break;
                case "5":
                    res = p5;
                    break;
                case "6":
                    res = p6;
                    break;
                case "7":
                    res = p7;
                    break;
                case "8":
                    res = p8;
                    break;
                case "9":
                    res = p9;
                    break;
                default:
                    res = null;
                    break;
            }
            return res;
        }
    }

    private final ChessSquare[][] board;
    private final int N;

    public ChessBoard(ChessSquare[][] b) {
        this.board = b;
        this.N = board.length; //since the board is of size n*n
    }

    public boolean isValidPoint(Pair<Integer, Integer> point) {
        int x = point.first();
        int y = point.second();
        return x >= 0 && x < N && y >= 0 && y < N && board[x][y] != ChessSquare.x;
    }

    public boolean isTarget(Pair<Integer, Integer> point) {
        int x = point.first();
        int y = point.second();
        return x >= 0 && x < N && y >= 0 && y < N && board[x][y] == ChessSquare.e;
    }

    public int getScore(Pair<Integer, Integer> point) {
        int score = 0;
        if (isValidPoint(point)) {
            score = board[point.first()][point.second()].value;
        }
        return score;
    }
}



class Pair <F, S> {
    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void first(F first) {
        this.first = first;
    }
    public void second(S second) {
        this.second = second;
    }
    public F first() {
        return this.first;
    }
    public S second() {
        return this.second;
    }

    @Override
    public int hashCode() {
        return this.first.hashCode() + this.second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof Pair) {
            Pair<Integer, Integer> that = (Pair<Integer, Integer>) obj;
            result = this.first.equals(that.first) && this.second.equals(that.second);
        }
        return result;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
