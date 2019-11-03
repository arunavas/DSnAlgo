package com.relcache.core.pocs.query.types;

import com.relcache.core.ds.Either;
import com.relcache.core.pocs.query.contract.IExpr;
import com.relcache.core.pocs.query.contract.Op;
import com.relcache.core.pocs.query.contract.Type;

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