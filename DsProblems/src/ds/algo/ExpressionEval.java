package ds.algo;

import ds.common.Either;
import ds.common.Left;
import ds.common.Right;

import java.util.*;

public class ExpressionEval {
    public static void main(String[] args) throws Exception {
        ExpressionEval ev = new ExpressionEval();
        System.out.println(ev.evalExpr("((1 + ($abc - 3)) * ($abc / 2))", Collections.singletonMap("abc", 4)));
    }

    private boolean verbose = false;

    public Either<String, Integer> evalExpr(String expr, Map<String, Integer> varMap) {
        if (verbose) System.out.println("Solving " + expr + " | Vars: " + varMap);
        Either<String, Integer> res = null;
        Stack<Integer> values = new Stack<>();
        Stack<Operator> ops = new Stack<>();
        char[] chars = expr.toCharArray();
        boolean numFlag = false;
        boolean varFlag = false;
        StringBuilder varName = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (isNumber(c)) {
                if (!varFlag) {
                    int temp = numFlag ? values.pop() : 0;
                    temp = temp * 10 + Character.getNumericValue(c);
                    values.push(temp);
                    numFlag = true;
                } else {
                    varName.append(c);
                }
            } else {
                numFlag = false;
                if (varFlag && isVariableNameCharacter(c)) {
                    varName.append(c);
                } else if (c == '$') {
                    varFlag = true;
                } else {
                    if (varFlag) {
                        if (varMap.containsKey(varName.toString())) {
                            int t = varMap.get(varName.toString());
                            values.push(t);
                            if (verbose) System.out.println("Variable '" + varName + "' substituted to '" + t + "'.");
                        } else {
                            res = new Left<>("Variable '" + varName + "' not declared before but used here.");
                            i = chars.length;
                        }
                        varName.delete(0, varName.length());
                    }
                    varFlag = false;
                    if (isOperator(c)) {
                        Operator op = Operator.toOperator(c);
                        while (!ops.empty() && !op.hasHigherPrecedenceThan(ops.peek())) {
                            Either<String, Integer> e = operate(ops.pop(), values.pop(), values.pop());
                            switch (e.which()) {
                                case LEFT:
                                    res = e;
                                    i = chars.length;
                                    break;
                                case RIGHT:
                                    values.push(e.right());
                                    break;
                            }
                        }
                        ops.push(op);
                    } else if (c == '(') {
                        ops.push(Operator.toOperator(c));
                    } else if (c == ')') {
                        StringBuilder sb = new StringBuilder();
                        while (ops.peek() != Operator.Paren) {
                            sb.insert(0, "" + values.pop()).insert(0, ops.pop().toString()).insert(0, "" + values.pop());
                        }
                        ops.pop();
                        if (verbose) System.out.println("Solving nested " + sb.toString());
                        Either<String, Integer> e = evalExpr(sb.toString(), varMap);
                        switch (e.which()) {
                            case LEFT:
                                res = e;
                                i = chars.length;
                                break;
                            case RIGHT:
                                values.push(e.right());
                                break;
                        }
                    }
                }
            }
            if (verbose && c != ' ') System.out.println(c + " v:" + values + " | o:" + ops);
        }

        if (res == null) {
            while (!ops.empty() && res == null) {
                Either<String, Integer> e = operate(ops.pop(), values.pop(), values.pop());
                switch (e.which()) {
                    case LEFT:
                        res = e;
                        break;
                    case RIGHT:
                        values.push(e.right());
                        break;
                }
            }

            res = new Right<>(values.pop());
            if (verbose) System.out.println("Executed (" + expr + ") " + (values.empty() ? "Correctly!" : "Incorrectly!"));
        }
        return res;
    }

    private boolean isNumber(char c) {
        int ascii = (int) c;
        return ascii >= 48 && ascii <= 57;
    }

    private final List<Character> varChars = Arrays.asList('_', '-', '\'');
    private boolean isVariableNameCharacter(char c) {
        int ascii = (int) c;
        return isNumber(c) || (ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122) || varChars.contains(c);
    }

    private boolean isOperator(char c) {
        return c == '/' || c == '*' || c == '+' || c == '-';
    }

    private Either<String, Integer> operate(Operator op, int v2, int v1) {
        Either<String, Integer> res = null;
        switch (op) {
            case Div:
                if (v2 == 0) {
                    res = new Left<>("Cannot Divide by Zero!");
                } else {
                    res = new Right<>(v1 / v2);
                }
                break;
            case Mul:
                res = new Right<>(v1 * v2);
                break;
            case Add:
                res = new Right<>(v1 + v2);
                break;
            case Sub:
                res = new Right<>(v1 - v2);
                break;
            default:
                res = new Left<>("Invalid Operator '" + op + "'!");
                break;
        }
        if (verbose) System.out.println("\t" + v1 + " " + op + " " + v2 + " = " + res);
        return res;
    }

    private enum Operator {
        Div(1), Mul(1), Add(0), Sub(0), Paren(-1);

        private int value;
        private Operator(int value) {
            this.value = value;
        }

        public boolean hasHigherPrecedenceThan(Operator that) {
            return this.value > that.value;
        }

        public boolean hasLowerPrecedenceThan(Operator that) {
            return this.value < that.value;
        }

        public boolean hasSamePrecedenceAs(Operator that) {
            return this.value == that.value;
        }

        public static Operator toOperator(char c) {
            Operator res = null;
            switch (c) {
                case '/':
                    res = Div;
                    break;
                case '*':
                    res = Mul;
                    break;
                case '+':
                    res = Add;
                    break;
                case '-':
                    res = Sub;
                    break;
                case '(':
                    res = Paren;
                    break;
                default:
                    break;
            }
            return res;
        }

        @Override
        public String toString() {
            String s = "";
            switch (this) {
                case Div:
                    s = "/";
                    break;
                case Mul:
                    s = "*";
                    break;
                case Add:
                    s = "+";
                    break;
                case Sub:
                    s = "-";
                    break;
                case Paren:
                    s = "(";
                    break;
                default:
                    break;
            }
            return s;
        }
    }
}
