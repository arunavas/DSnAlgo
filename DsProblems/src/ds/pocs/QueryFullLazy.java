package ds.pocs;

import ds.common.Either;
import ds.common.Left;
import ds.common.Right;
import java.util.Objects;
import java.util.function.Supplier;

public class QueryFullLazy {
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
        NUMBER, TEXT, BOOL, EXPR
    }

    interface IExpr<T> {
        Either<String, Supplier<T>> eval();
        TYPE type();
    }

    static class Number implements IExpr<Number> {
        private Double val;
        public Number(double val) {
            this.val = val;
        }

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

    static class Text implements IExpr<Text> {
        private String val;
        public Text(String val) {
            this.val = val;
        }

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

    static class Bool implements IExpr<Bool> {
        private Boolean val;
        public Bool(boolean val) {
            this.val = val;
        }

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

    static class BiExpr<T extends IExpr<T>, R extends IExpr<R>> implements IExpr<R> {
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
            return op.exec(lhs, rhs);
        }

        @Override
        public TYPE type() {
            return TYPE.EXPR;
        }

        @Override
        public String toString() {
            return "(" + lhs + " " + op + " " + rhs + ")";
        }
    }

    interface Op<R extends IExpr<R>, A extends IExpr<A>> {
        Either<String, Supplier<R>> exec(IExpr<A> lhs, IExpr<A> rhs);
        /*Either<String, Supplier<R>> exec(Either<String, IExpr<A>> elhs, IExpr<A> rhs);
        Either<String, Supplier<R>> exec(IExpr<A> lhs, Either<String, IExpr<A>> erhs);
        Either<String, Supplier<R>> exec(Either<String, IExpr<A>> elhs, Either<String, IExpr<A>> erhs);*/
    }

    interface RelOp<A extends IExpr<A>> extends Op<Bool, A> {

    }

    enum RelNumOp implements RelOp<Number> {
        EQ, NEQ, LT, LTE, GT, GTE, X;

        public Either<String, Supplier<Bool>> exec(Number lhs, Number rhs) {
            Either<String, Supplier<Bool>> res;
            switch (this) {
                case EQ:
                    res = new Right<>(() -> new Bool(Objects.equals(lhs.value(), rhs.value())));
                    break;
                case NEQ:
                    res = new Right<>(() -> new Bool(!Objects.equals(lhs.value(), rhs.value())));
                    break;
                case LT:
                    res = new Right<>(() -> new Bool(lhs.value() < rhs.value()));
                    break;
                case LTE:
                    res = new Right<>(() -> new Bool(lhs.value() <= rhs.value()));
                    break;
                case GT:
                    res = new Right<>(() -> new Bool(lhs.value() > rhs.value()));
                    break;
                case GTE:
                    res = new Right<>(() -> new Bool(lhs.value() >= rhs.value()));
                    break;
                default:
                    res = new Left<>("Invalid Operator: " + this);
                    break;
            }
            return res;
        }

        @Override
        public Either<String, Supplier<Bool>> exec(IExpr<Number> lhs, IExpr<Number> rhs) {
            return lhs.eval().flatMap(l -> rhs.eval().flatMap(r -> exec(l.get(), r.get())));
        }

        /*@Override
        public Either<String, Supplier<Bool>> exec(Either<String, IExpr<Num>> elhs, IExpr<Num> rhs) {
            return elhs.flatMap(l -> exec(l, rhs));
        }

        @Override
        public Either<String, Supplier<Bool>> exec(IExpr<Num> lhs, Either<String, IExpr<Num>> erhs) {
            return erhs.flatMap(r -> exec(lhs, r));
        }

        @Override
        public Either<String, Supplier<Bool>> exec(Either<String, IExpr<Num>> elhs, Either<String, IExpr<Num>> erhs) {
            return elhs.flatMap(l -> erhs.flatMap(r -> exec(l, r)));
        }*/
    }

    interface GenOp<A extends IExpr<A>> extends Op<A, A> {

    }

    enum GenNumOp implements GenOp<Number> {
        ADD, SUB, MUL, DIV, MOD, X;

        public Either<String, Supplier<Number>> exec(Number lhs, Number rhs) {
            Either<String, Supplier<Number>> res;
            switch (this) {
                case ADD:
                    res = new Right<>(() -> new Number(lhs.value() + rhs.value()));
                    break;
                case SUB:
                    res = new Right<>(() -> new Number(lhs.value() - rhs.value()));
                    break;
                case MUL:
                    res = new Right<>(() -> new Number(lhs.value() * rhs.value()));
                    break;
                case DIV:
                    double d = rhs.value();
                    res = d == 0 ? new Left<>("Cannot divide by zero!") : new Right<>(() -> new Number(lhs.value() + d));
                    break;
                case MOD:
                    res = new Right<>(() -> new Number(lhs.value() % rhs.value()));
                    break;
                default:
                    res = new Left<>("Invalid Operator: " + this);
                    break;
            }
            return res;
        }

        @Override
        public Either<String, Supplier<Number>> exec(IExpr<Number> lhs, IExpr<Number> rhs) {
            return lhs.eval().flatMap(l -> rhs.eval().flatMap(r -> exec(l.get(), r.get())));
        }

        /*@Override
        public Either<String, Supplier<Num>> exec(Either<String, IExpr<Num>> elhs, IExpr<Num> rhs) {
            return elhs.flatMap(l -> exec(l, rhs));
        }

        @Override
        public Either<String, Supplier<Num>> exec(IExpr<Num> lhs, Either<String, IExpr<Num>> erhs) {
            return erhs.flatMap(r -> exec(lhs, r));
        }

        @Override
        public Either<String, Supplier<Num>> exec(Either<String, IExpr<Num>> elhs, Either<String, IExpr<Num>> erhs) {
            return elhs.flatMap(l -> erhs.flatMap(r -> exec(l, r)));
        }*/
    }

    enum CondOp implements Op<Bool, Bool> {
        AND, OR, X;

        @Override
        public Either<String, Supplier<Bool>> exec(IExpr<Bool> lhs, IExpr<Bool> rhs) {
            System.out.println("Exec: Op " + this);
            Either<String, Supplier<Bool>> res;
            switch (this) {
                case AND:
                    res = lhs.eval().flatMap(l -> l.get().value() ? rhs.eval() : new Right<>(Bool::False));
                    /*res = new Right<>(() -> {
                        if (lhs.value()) {
                            System.out.println("lhs is true -> moving to rhs");
                            return new Bool(rhs.value());
                        } else {
                            System.out.println("lhs is false - short-circuiting!");
                            return Bool.False();
                        }
                    });*/
                    break;
                case OR:
                    System.out.println("OR Executed!");
                    res = lhs.eval().flatMap(l -> l.get().value() ? new Right<>(Bool::True) : rhs.eval());
                    //res = new Right<>(() -> new Bool(lhs.value() || rhs.value()));
                    break;
                default:
                    res = new Left<>("Invalid Operator: " + this);
                    break;
            }
            return res;
        }

        /*@Override
        public Either<String, Supplier<Bool>> exec(Either<String, IExpr<Bool>> elhs, IExpr<Bool> rhs) {
            return elhs.flatMap(l -> exec(l, rhs));
        }

        @Override
        public Either<String, Supplier<Bool>> exec(IExpr<Bool> lhs, Either<String, IExpr<Bool>> erhs) {
            return erhs.flatMap(r -> exec(lhs, r));
        }

        @Override
        public Either<String, Supplier<Bool>> exec(Either<String, IExpr<Bool>> elhs, Either<String, IExpr<Bool>> erhs) {
            return elhs.flatMap(l -> erhs.flatMap(r -> exec(l, r)));
        }*/
    }

}
