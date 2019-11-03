package com.relcache.core.pocs.query.ops;

import com.relcache.core.ds.Either;
import com.relcache.core.ds.Left;
import com.relcache.core.ds.Pair;
import com.relcache.core.ds.Right;
import com.relcache.core.pocs.query.contract.IExpr;
import com.relcache.core.pocs.query.contract.Op;
import com.relcache.core.pocs.query.contract.Type;
import com.relcache.core.pocs.query.types.Bool;

public enum CondOp implements Op<Bool, Bool> {
    AND, OR, X;

    private Pair<Type, Type> typeParams = new Pair<>(Type.BOOL, Type.BOOL);

    @Override
    public Either<String, Bool> exec(IExpr<Bool> lhs, IExpr<Bool> rhs) {
        System.out.println("Exec: Op " + this);
        Either<String, Bool> res;
        switch (this) {
            case AND:
                res = lhs.eval().flatmap(l -> l.value() ? rhs.eval() : new Right<>(Bool.False()));
                break;
            case OR:
                System.out.println("OR Executed!");
                res = lhs.eval().flatmap(l -> l.value() ? new Right<>(Bool.True()) : rhs.eval());
                break;
            default:
                res = new Left<>("Invalid Operator: " + this);
                break;
        }
        return res;
    }

    @Override
    public Pair<Type, Type> typeParameters() {
        return typeParams;
    }
}