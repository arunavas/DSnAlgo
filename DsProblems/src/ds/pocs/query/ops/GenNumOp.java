package com.relcache.core.pocs.query.ops;

import com.relcache.core.ds.Either;
import com.relcache.core.ds.Left;
import com.relcache.core.ds.Pair;
import com.relcache.core.ds.Right;
import com.relcache.core.pocs.query.contract.GenOp;
import com.relcache.core.pocs.query.contract.IExpr;
import com.relcache.core.pocs.query.contract.Type;
import com.relcache.core.pocs.query.types.Number;

public enum GenNumOp implements GenOp<Number> {
    ADD, SUB, MUL, DIV, MOD, X;

    private Pair<Type, Type> typeParams = new Pair<>(Type.NUMBER, Type.NUMBER);

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
                double d = rhs.value();
                res = d == 0 ? new Left<>("Cannot divide by zero!") : new Right<>(new Number(lhs.value() + d));
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
    public Either<String, Number> exec(IExpr<Number> lhs, IExpr<Number> rhs) {
        return lhs.eval().flatmap(l -> rhs.eval().flatmap(r -> exec(l, r)));
    }

    @Override
    public Pair<Type, Type> typeParameters() {
        return typeParams;
    }
}