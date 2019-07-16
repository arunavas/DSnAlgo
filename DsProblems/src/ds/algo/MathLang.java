package ds.algo;

import ds.common.Either;
import ds.common.Left;
import ds.common.Right;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MathLang {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            List<String> input = new ArrayList<>();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("CLEAR: Clears code. EXEC: Executes code!");
            String line;
            while (!(line = br.readLine()).toLowerCase().equals("exec")) {
                if (line.toLowerCase().equals("clear")) {
                    input.clear();
                    System.out.println("CLEARED!");
                    System.out.println("CLEAR: Clears code. EXEC: Executes code!");
                } else {
                    input.add(line);
                }
            }
            Either<String, List<String>> res = new MathLang().execute(input);
            switch (res.which()) {
                case LEFT:
                    System.out.println(res.left());
                    break;
                case RIGHT:
                    res.right().forEach(System.out::println);
                    break;
            }
        } else {
            for (String path : args) {
                File file = new File(path);
                if (!file.exists()) {
                    System.out.println("Error: '" + path + "' does not exist!");
                } else if (file.isDirectory()) {
                    System.out.println("Error: '" + path + "' is a directory!");
                } else if (!file.canRead()) {
                    System.out.println("Error: '" + path + "' does not have read permission!");
                } else {
                    Either<String, List<String>> res = new MathLang().execute(Files.readAllLines(Path.of(path)));
                    System.out.println("Output of '" + path + "'");
                    switch (res.which()) {
                        case LEFT:
                            System.out.println(res.left());
                            break;
                        case RIGHT:
                            res.right().forEach(System.out::println);
                            break;
                    }
                }
            }
        }
    }

    public Either<String, List<String>> execute(List<String> lines) {
        Either<String, List<String>> res = null;
        ExpressionEval eval = new ExpressionEval();
        Map<String, Integer> varMap = new HashMap<>();
        List<String> output = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.toLowerCase().startsWith("let ")) {
                String[] arr = line.substring(4).split(" = ");
                Either<String, Integer> e = eval.evalExpr(arr[1], varMap);
                switch (e.which()) {
                    case LEFT:
                        i = lines.size();
                        res = new Left<>("Error @ Line " + (i + 1) + ": " + e.left());
                        break;
                    case RIGHT:
                        varMap.put(arr[0], e.right());
                        break;
                }
            } else if (line.toLowerCase().startsWith("print ")) {
                String[] arr = line.substring(6).split(" ");
                StringBuilder sb = new StringBuilder();
                for (String k : arr) {
                    sb.append(" ");
                    if (k.charAt(0) == '$') {
                        String varName = k.substring(1);
                        if (varMap.containsKey(varName)) {
                            sb.append(varMap.get(varName));
                        } else {
                            i = lines.size();
                            res = new Left<>("Error @ Line " + (i + 1) + ": Variable '" + k + "' not declared before but used here!");
                        }
                    } else {
                        sb.append(k);
                    }
                }
                output.add(sb.substring(1));
            }
        }
        if (res == null) {
            res = new Right<>(output);
        }
        return res;
    }
}
