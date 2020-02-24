package ds.pocs;

import ds.pocs.query.contract.IExpr;
import ds.pocs.query.ops.CondOp;
import ds.pocs.query.ops.GenNumOp;
import ds.pocs.query.ops.RelNumOp;
import ds.pocs.query.types.Bool;
import ds.pocs.query.types.Expr;
import ds.pocs.query.types.Number;
import java.util.HashMap;
import java.util.Map;

public class Query {

    private static IExpr<Number> a = new Number(10);
    private static Number b = new Number(20);
    private static Number v0 = new Number(0);
    private static Number v2 = new Number(2);
    private static Number v5 = new Number(5);
    private static Number v9 = new Number(9);

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
        System.out.println(ex.eval());
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
        System.out.println(ex.eval());
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
        System.out.println(ex.eval());
    }
    private static void numExecute() {
        //IExpr<Num> exp = new Expr<>(new Num(2), GenNumOp.ADD, new Expr<>(new Num(3), GenNumOp.MUL, new Num(5)));
        IExpr<Number> exp = new Expr<>(new Number(2), GenNumOp.MUL, new Expr<>(new Number(3), GenNumOp.ADD, new Number(5)));
        System.out.println(exp);
        System.out.println(exp.eval());
    }
}
