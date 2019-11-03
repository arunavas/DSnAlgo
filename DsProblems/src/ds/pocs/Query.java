package com.relcache.core.pocs;

import com.relcache.core.query.contract.IExpr;
import com.relcache.core.query.ops.CondOp;
import com.relcache.core.query.ops.GenNumOp;
import com.relcache.core.query.ops.RelNumOp;
import com.relcache.core.query.types.Expr;
import com.relcache.core.query.types.Bool;
import com.relcache.core.query.types.Num;

import java.util.HashMap;
import java.util.Map;

public class Query {

    private static IExpr<Num> a = new Num(10);
    private static Num b = new Num(20);
    private static Num v0 = new Num(0);
    private static Num v2 = new Num(2);
    private static Num v5 = new Num(5);
    private static Num v9 = new Num(9);
    private static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) {
        //           10 < 20 and (20 % 2 == 0 or 20 ==9) and 10 > 5
        //form this - a < b and (b % 2 == 0 or b == 9) and a > 5
        /*
        Flow/Chain
         - And -- short circuits if first is false
         - Or -- short circuits if first is true
        ExpressionChain
         - Expr
         - Chain/Flow
        |Expression
         - LHS Op RHS
         = Expr Op Expr
        Expr - evaluate
         - Val - can be Num or String
         - Expr
        Op
          - Rel - returns bool
          - Gen - returns Num or String

         */

        int choice = -1;
        switch (choice) {
            case -1:
                //Failure Execute - returns Left(Cannot divide by zero)
                failExecute();
                break;
            case 0:
                //Partial Execute (rhs of AND should not execute)
                partialExecute();
                break;
            case 1:
                //Full Execute
                fullExecute();
                break;
            case 2:
                //Full Execute
                numExecute();
                break;
            default:
                System.out.println("Invalid Choice!");
                break;
        }
    }

    private static void fullExecute() {
        IExpr<Bool> ex = new Expr<>(
                new Expr<>(a, RelNumOp.LT, b),
                CondOp.AND,
                new Expr<>(
                        new Expr<>(
                                new Expr<>(
                                        new Expr<>(b, GenNumOp.MOD, v2),
                                        RelNumOp.EQ,
                                        v0),
                                CondOp.OR,
                                new Expr<>(b, RelNumOp.EQ, v9)),
                        CondOp.AND,
                        new Expr<>(a, RelNumOp.GT, v5)
                ));
        System.out.println(ex.eval(map));
    }
    private static void partialExecute() {
        IExpr<Bool> ex = new Expr<>(
                new Expr<>(a, RelNumOp.LT, a),
                CondOp.AND,
                new Expr<>(
                        new Expr<>(
                                new Expr<>(
                                        new Expr<>(b, GenNumOp.MOD, v2),
                                        RelNumOp.EQ,
                                        v0),
                                CondOp.OR,
                                new Expr<>(b, RelNumOp.EQ, v9)),
                        CondOp.AND,
                        new Expr<>(a, RelNumOp.GT, v5)
                ));
        System.out.println(ex.eval(map));
    }
    private static void failExecute() {
        IExpr<Bool> ex = new Expr<>(
                new Expr<>(a, RelNumOp.LT, b),
                CondOp.AND,
                new Expr<>(
                        new Expr<>(
                                new Expr<>(
                                        new Expr<>(b, GenNumOp.DIV, v0),
                                        RelNumOp.EQ,
                                        v0),
                                CondOp.OR,
                                new Expr<>(b, RelNumOp.EQ, v9)),
                        CondOp.AND,
                        new Expr<>(a, RelNumOp.GT, v5)
                ));
        System.out.println(ex.eval(map));
    }
    private static void numExecute() {
        //IExpr<Num> exp = new Expr<>(new Num(2), GenNumOp.ADD, new Expr<>(new Num(3), GenNumOp.MUL, new Num(5)));
        IExpr<Num> exp = new Expr<>(new Num(2), GenNumOp.MUL, new Expr<>(new Num(3), GenNumOp.ADD, new Num(5)));
        System.out.println(exp);
        System.out.println(exp.eval(map));
    }
}
