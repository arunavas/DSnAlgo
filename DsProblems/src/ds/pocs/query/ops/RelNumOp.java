package com.relcache.core.pocs.query.ops;

import com.relcache.core.ds.Either;
import com.relcache.core.ds.Left;
import com.relcache.core.ds.Pair;
import com.relcache.core.ds.Right;
import com.relcache.core.pocs.query.contract.IExpr;
import com.relcache.core.pocs.query.contract.RelOp;
import com.relcache.core.pocs.query.contract.Type;
import com.relcache.core.pocs.query.types.Bool;
import com.relcache.core.pocs.query.types.Number;

import java.util.Objects;

public enum RelNumOp implements RelOp<Number> {
    EQ, NEQ, LT, LTE, GT, GTE, X;

    private Pair<Type, Type> typeParams = new Pair<>(Type.NUMBER, Type.NUMBER);

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
    public Either<String, Bool> exec(IExpr<Number> lhs, IExpr<Number> rhs) {
        return lhs.eval().flatmap(l -> rhs.eval().flatmap(r -> exec(l, r)));
    }

    @Override
    public Pair<Type, Type> typeParameters() {
        return typeParams;
    }
}