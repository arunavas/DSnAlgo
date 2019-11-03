package com.relcache.core.pocs;

import com.relcache.core.ds.Either;
import com.relcache.core.ds.Left;
import com.relcache.core.ds.Right;

import java.util.Objects;
import java.util.function.Supplier;

public class QueryLazy {
    public static void main(String[] args) {
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
        Number a = new Number(10);
        Number b = new Number(20);
        Number v0 = new Number(0);
        Number v2 = new Number(2);
        Number v5 = new Number(5);
        Number v9 = new Number(9);

        Either<String, Supplier<Bool>> e = null;//CondOp.AND.exec(RelNumOp.LT.exec(() -> a, () -> b), CondOp.OR.exec(RelNumOp.EQ.exec(GenNumOp.MOD.exec(() -> b, () -> v2), () -> v0), RelNumOp.EQ.exec(() -> b, () -> v9)));
        //System.out.println(e.map(Supplier::get));
        //Using Expr
        IExpr<Number> ea = new Number(10);
        IExpr<Number> eb = new Number(20);
        IExpr<Number> ev0 = new Number(0);
        IExpr<Number> ev2 = new Number(2);
        IExpr<Number> ev9 = new Number(9);
        IExpr<Bool> aLTb = new BiExpr<>(ea, RelNumOp.LT, eb);
        IExpr<Number> bMOD2 = new BiExpr<>(eb, GenNumOp.MOD, ev2);
        IExpr<Bool> b2EQ0 = new BiExpr<>(bMOD2, RelNumOp.EQ, v0);
        IExpr<Bool> bEQ9 = new BiExpr<>(b, RelNumOp.EQ, v9);
        IExpr<Bool> or = new BiExpr<>(b2EQ0, CondOp.OR, bEQ9);
        IExpr<Bool> and = new BiExpr<>(aLTb, CondOp.AND, or);
        /*e = and.eval();
        System.out.println(e.map(Supplier::get));*/
        // OR
        IExpr<Bool> ex = new BiExpr<>(
                new BiExpr<>(ea, RelNumOp.LT, ea),
                CondOp.AND,
                new BiExpr<>(
                        new BiExpr<>(
                                new BiExpr<>(
                                        new BiExpr<>(eb, GenNumOp.MOD, ev2),
                                        RelNumOp.EQ,
                                        v0),
                                CondOp.OR,
                                new BiExpr<>(b, RelNumOp.EQ, v9)),
                        CondOp.AND,
                        new BiExpr<>(a, RelNumOp.GT, v5)
                ));
        System.out.println(ex.eval().map(Supplier::get));
        /*System.out.println(new Expr<>(eb, GenNumOp.MOD, ev2).eval().map(Supplier::get));
        System.out.println(new Expr<>(
                new Expr<>(eb, GenNumOp.MOD, ev2),
                RelNumOp.EQ,
                v0).eval().map(Supplier::get));*/
    }

    enum TYPE {
        NUMBER, TEXT, BOOL
    }

    interface Type<T> {
        T value();
        TYPE type();
    }

    interface IExpr<T extends Type> {
        Either<String, Supplier<T>> eval();
    }

    static class Number implements Type<Double>, IExpr<Number> {
        private Double val;
        public Number(double val) {
            this.val = val;
        }
        @Override
        public Double value() {
            return val == null ? 0.0d : val;
        }

        @Override
        public TYPE type() {
            return TYPE.NUMBER;
        }

        @Override
        public Either<String, Supplier<Number>> eval() {
            System.out.println("Eval: Num " + val);
            return new Right<>(() -> this);
        }

        @Override
        public String toString() {
            return "" + val;
        }
    }

    static class Text implements Type<String>, IExpr<Text> {
        private String val;
        public Text(String val) {
            this.val = val;
        }

        @Override
        public String value() {
            return val == null ? "" : val;
        }

        @Override
        public TYPE type() {
            return TYPE.TEXT;
        }

        @Override
        public Either<String, Supplier<Text>> eval() {
            return new Right<>(() -> this);
        }

        @Override
        public String toString() {
            return val;
        }
    }

    static class Bool implements Type<Boolean>, IExpr<Bool> {
        private Boolean val;
        public Bool(boolean val) {
            this.val = val;
        }

        @Override
        public Boolean value() {
            return val == null ? false : val;
        }

        @Override
        public TYPE type() {
            return TYPE.BOOL;
        }

        @Override
        public Either<String, Supplier<Bool>> eval() {
            return new Right<>(() -> this);
        }

        @Override
        public String toString() {
            return "" + val;
        }

        public static Bool True() {
            return new Bool(true);
        }
        public static Bool False() {
            return new Bool(false);
        }
    }

    interface Op<R extends Type, A extends Type> {
        Either<String, Supplier<R>> exec(Supplier<A> lhs, Supplier<A> rhs);
        Either<String, Supplier<R>> exec(Either<String, Supplier<A>> elhs, Supplier<A> rhs);
        Either<String, Supplier<R>> exec(Supplier<A> lhs, Either<String, Supplier<A>> erhs);
        Either<String, Supplier<R>> exec(Either<String, Supplier<A>> elhs, Either<String, Supplier<A>> erhs);
    }

    interface RelOp<A extends Type> extends Op<Bool, A> {

    }

    enum RelNumOp implements RelOp<Number> {
        EQ, NEQ, LT, LTE, GT, GTE, X;

        @Override
        public Either<String, Supplier<Bool>> exec(Supplier<Number> lhs, Supplier<Number> rhs) {
            Either<String, Supplier<Bool>> res;
            switch (this) {
                case EQ:
                    res = new Right<>(() -> new Bool(Objects.equals(lhs.get().value(), rhs.get().value())));
                    break;
                case NEQ:
                    res = new Right<>(() -> new Bool(!Objects.equals(lhs.get().value(), rhs.get().value())));
                    break;
                case LT:
                    res = new Right<>(() -> new Bool(lhs.get().value() < rhs.get().value()));
                    break;
                case LTE:
                    res = new Right<>(() -> new Bool(lhs.get().value() <= rhs.get().value()));
                    break;
                case GT:
                    res = new Right<>(() -> new Bool(lhs.get().value() > rhs.get().value()));
                    break;
                case GTE:
                    res = new Right<>(() -> new Bool(lhs.get().value() >= rhs.get().value()));
                    break;
                default:
                    res = new Left<>("Invalid Operator: " + this);
                    break;
            }
            return res;
        }

        @Override
        public Either<String, Supplier<Bool>> exec(Either<String, Supplier<Number>> elhs, Supplier<Number> rhs) {
            return elhs.flatmap(l -> exec(l, rhs));
        }

        @Override
        public Either<String, Supplier<Bool>> exec(Supplier<Number> lhs, Either<String, Supplier<Number>> erhs) {
            return erhs.flatmap(r -> exec(lhs, r));
        }

        @Override
        public Either<String, Supplier<Bool>> exec(Either<String, Supplier<Number>> elhs, Either<String, Supplier<Number>> erhs) {
            return elhs.flatmap(l -> erhs.flatmap(r -> exec(l, r)));
        }
    }

    interface GenOp<A extends Type> extends Op<A, A> {

    }

    enum GenNumOp implements GenOp<Number> {
        ADD, SUB, MUL, DIV, MOD, X;

        @Override
        public Either<String, Supplier<Number>> exec(Supplier<Number> lhs, Supplier<Number> rhs) {
            Either<String, Supplier<Number>> res;
            switch (this) {
                case ADD:
                    res = new Right<>(() -> new Number(lhs.get().value() + rhs.get().value()));
                    break;
                case SUB:
                    res = new Right<>(() -> new Number(lhs.get().value() - rhs.get().value()));
                    break;
                case MUL:
                    res = new Right<>(() -> new Number(lhs.get().value() * rhs.get().value()));
                    break;
                case DIV:
                    double d = rhs.get().value();
                    res = d == 0 ? new Left<>("Cannot divide by zero!") : new Right<>(() -> new Number(lhs.get().value() + d));
                    break;
                case MOD:
                    res = new Right<>(() -> new Number(lhs.get().value() % rhs.get().value()));
                    break;
                default:
                    res = new Left<>("Invalid Operator: " + this);
                    break;
            }
            return res;
        }

        @Override
        public Either<String, Supplier<Number>> exec(Either<String, Supplier<Number>> elhs, Supplier<Number> rhs) {
            return elhs.flatmap(l -> exec(l, rhs));
        }

        @Override
        public Either<String, Supplier<Number>> exec(Supplier<Number> lhs, Either<String, Supplier<Number>> erhs) {
            return erhs.flatmap(r -> exec(lhs, r));
        }

        @Override
        public Either<String, Supplier<Number>> exec(Either<String, Supplier<Number>> elhs, Either<String, Supplier<Number>> erhs) {
            return elhs.flatmap(l -> erhs.flatmap(r -> exec(l, r)));
        }
    }

    enum CondOp implements Op<Bool, Bool> {
        AND, OR, X;

        @Override
        public Either<String, Supplier<Bool>> exec(Supplier<Bool> lhs, Supplier<Bool> rhs) {
            System.out.println("Exec: Op " + this);
            Either<String, Supplier<Bool>> res;
            switch (this) {
                case AND:
                    res = new Right<>(() -> {
                        if (lhs.get().value()) {
                            System.out.println("lhs is true -> moving to rhs");
                            return new Bool(rhs.get().value());
                        } else {
                            System.out.println("lhs is false - short-circuiting!");
                            return Bool.False();
                        }
                    });
                    break;
                case OR:
                    System.out.println("OR Executed!");
                    res = new Right<>(() -> new Bool(lhs.get().value() || rhs.get().value()));
                    break;
                default:
                    res = new Left<>("Invalid Operator: " + this);
                    break;
            }
            return res;
        }

        @Override
        public Either<String, Supplier<Bool>> exec(Either<String, Supplier<Bool>> elhs, Supplier<Bool> rhs) {
            return elhs.flatmap(l -> exec(l, rhs));
        }

        @Override
        public Either<String, Supplier<Bool>> exec(Supplier<Bool> lhs, Either<String, Supplier<Bool>> erhs) {
            return erhs.flatmap(r -> exec(lhs, r));
        }

        @Override
        public Either<String, Supplier<Bool>> exec(Either<String, Supplier<Bool>> elhs, Either<String, Supplier<Bool>> erhs) {
            return elhs.flatmap(l -> erhs.flatmap(r -> exec(l, r)));
        }
    }

    static class BiExpr<T extends Type, R extends Type> implements IExpr<R> {
        IExpr<T> lhs;
        Op<R, T> op;
        IExpr<T> rhs;

        public BiExpr(IExpr<T> lhs, Op<R, T> op, IExpr<T> rhs) {
            this.lhs = lhs;
            this.op = op;
            this.rhs = rhs;
        }

        @Override
        public Either<String, Supplier<R>> eval() {
            System.out.println("evaling: " + lhs + " " + op + " " + rhs);
            return op.exec(lhs.eval(), rhs.eval());
        }

        @Override
        public String toString() {
            return "(" + lhs + " " + op + " " + rhs + ")";
        }
    }

}
