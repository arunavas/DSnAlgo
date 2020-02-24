package ds.pocs;

import ds.common.Either;
import ds.common.Left;
import ds.common.Right;
import java.util.Objects;

public class QueryEager {
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

        Either<String, Bool> e = CondOp.AND.exec(RelNumOp.LT.exec(a, b), CondOp.OR.exec(RelNumOp.EQ.exec(GenNumOp.MOD.exec(b, v2), v0), RelNumOp.EQ.exec(b, v9)));
        System.out.println(e);
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
        e = and.eval();
        System.out.println(e);
        // OR
        IExpr<Bool> ex = new BiExpr<>(
                new BiExpr<>(ea, RelNumOp.LT, eb),
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
        System.out.println(ex.eval());
        System.out.println(new BiExpr<>(eb, GenNumOp.MOD, ev2).eval());
        System.out.println(new BiExpr<>(
                new BiExpr<>(eb, GenNumOp.MOD, ev2),
                RelNumOp.EQ,
                v0).eval());
    }

    enum TYPE {
        NUMBER, TEXT, BOOL
    }

    interface Type<T> {
        T value();
        TYPE type();
    }

    interface IExpr<T extends Type> {
        Either<String, T> eval();
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
        public Either<String, Number> eval() {
            return new Right<>(this);
        }

        @Override
        public String toString() {
            return "" + val;
        }
    }

    static class Text implements Type<String> {
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
        public String toString() {
            return val;
        }
    }

    static class Bool implements Type<Boolean> {
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
        public String toString() {
            return "" + val;
        }
    }

    interface Op<R extends Type, A extends Type> {
        Either<String, R> exec(A lhs, A rhs);
        Either<String, R> exec(Either<String, A> elhs, A rhs);
        Either<String, R> exec(A lhs, Either<String, A> erhs);
        Either<String, R> exec(Either<String, A> elhs, Either<String, A> erhs);
    }

    interface RelOp<A extends Type> extends Op<Bool, A> {

    }

    enum RelNumOp implements RelOp<Number> {
        EQ, NEQ, LT, LTE, GT, GTE, X;

        @Override
        public Either<String, Bool> exec(Number lhs, Number rhs) {
            Either<String, Bool> res;
            switch (this) {
                case EQ:
                    res = new Right<>(new Bool(Objects.equals(lhs.value(), rhs.value())));
                    break;
                case NEQ:
                    res = new Right<>(new Bool(!Objects.equals(lhs.value(), rhs.value())));
                    break;
                case LT:
                    res = new Right<>(new Bool(lhs.value() < rhs.value()));
                    break;
                case LTE:
                    res = new Right<>(new Bool(lhs.value() <= rhs.value()));
                    break;
                case GT:
                    res = new Right<>(new Bool(lhs.value() > rhs.value()));
                    break;
                case GTE:
                    res = new Right<>(new Bool(lhs.value() >= rhs.value()));
                    break;
                default:
                    res = new Left<>("Invalid Operator: " + this);
                    break;
            }
            return res;
        }

        @Override
        public Either<String, Bool> exec(Either<String, Number> elhs, Number rhs) {
            return elhs.flatMap(l -> exec(l, rhs));
        }

        @Override
        public Either<String, Bool> exec(Number lhs, Either<String, Number> erhs) {
            return erhs.flatMap(r -> exec(lhs, r));
        }

        @Override
        public Either<String, Bool> exec(Either<String, Number> elhs, Either<String, Number> erhs) {
            return elhs.flatMap(l -> erhs.flatMap(r -> exec(l, r)));
        }
    }

    interface GenOp<A extends Type> extends Op<A, A> {

    }

    enum GenNumOp implements GenOp<Number> {
        ADD, SUB, MUL, DIV, MOD, X;

        @Override
        public Either<String, Number> exec(Number lhs, Number rhs) {
            Either<String, Number> res;
            switch (this) {
                case ADD:
                    res = new Right<>(new Number(lhs.value() + rhs.value()));
                    break;
                case SUB:
                    res = new Right<>(new Number(lhs.value() - rhs.value()));
                    break;
                case MUL:
                    res = new Right<>(new Number(lhs.value() * rhs.value()));
                    break;
                case DIV:
                    res = rhs.value() == 0 ? new Left<>("Cannot divide by zero!") : new Right<>(new Number(lhs.value() + rhs.value()));
                    break;
                case MOD:
                    res = new Right<>(new Number(lhs.value() % rhs.value()));
                    break;
                default:
                    res = new Left<>("Invalid Operator: " + this);
                    break;
            }
            return res;
        }

        @Override
        public Either<String, Number> exec(Either<String, Number> elhs, Number rhs) {
            return elhs.flatMap(l -> exec(l, rhs));
        }

        @Override
        public Either<String, Number> exec(Number lhs, Either<String, Number> erhs) {
            return erhs.flatMap(r -> exec(lhs, r));
        }

        @Override
        public Either<String, Number> exec(Either<String, Number> elhs, Either<String, Number> erhs) {
            return elhs.flatMap(l -> erhs.flatMap(r -> exec(l, r)));
        }
    }

    enum CondOp implements Op<Bool, Bool> {
        AND, OR, X;

        @Override
        public Either<String, Bool> exec(Bool lhs, Bool rhs) {
            Either<String, Bool> res;
            switch (this) {
                case AND:
                    res = new Right<>(new Bool(lhs.value() && rhs.value()));
                    break;
                case OR:
                    res = new Right<>(new Bool(lhs.value() || rhs.value()));
                    break;
                default:
                    res = new Left<>("Invalid Operator: " + this);
                    break;
            }
            return res;
        }

        @Override
        public Either<String, Bool> exec(Either<String, Bool> elhs, Bool rhs) {
            return elhs.flatMap(l -> exec(l, rhs));
        }

        @Override
        public Either<String, Bool> exec(Bool lhs, Either<String, Bool> erhs) {
            return erhs.flatMap(r -> exec(lhs, r));
        }

        @Override
        public Either<String, Bool> exec(Either<String, Bool> elhs, Either<String, Bool> erhs) {
            return elhs.flatMap(l -> erhs.flatMap(r -> exec(l, r)));
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
        public Either<String, R> eval() {
            return op.exec(lhs.eval(), rhs.eval());
        }
    }

}
