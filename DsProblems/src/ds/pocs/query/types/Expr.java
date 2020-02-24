package ds.pocs.query.types;

import ds.common.Either;
import ds.pocs.query.contract.IExpr;
import ds.pocs.query.contract.Op;
import ds.pocs.query.contract.Type;

public class Expr<T extends IExpr<T>, R extends IExpr<R>> implements IExpr<R> {
    IExpr<T> lhs;
    Op<R, T> op;
    IExpr<T> rhs;

    public Expr(IExpr<T> lhs, Op<R, T> op, IExpr<T> rhs) {
        this.lhs = lhs;
        this.op = op;
        this.rhs = rhs;
    }

    @Override
    public Either<String, R> eval() {
        System.out.println("eval-ing: " + lhs + " " + op + " " + rhs);
        return op.exec(lhs, rhs);
    }

    @Override
    public Type type() {
        return Type.EXPR;
    }

    @Override
    public String toString() {
        return "(" + lhs + " " + op + " " + rhs + ")";
    }
}