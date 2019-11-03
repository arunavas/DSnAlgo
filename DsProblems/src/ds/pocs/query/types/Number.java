package com.relcache.core.pocs.query.types;

import com.relcache.core.ds.Either;
import com.relcache.core.ds.Right;
import com.relcache.core.pocs.query.contract.IExpr;
import com.relcache.core.pocs.query.contract.Type;

public class Number implements IExpr<Number> {
    private Double val;

    public Number(double val) {
        this.val = val;
    }

    public Double value() {
        return val == null ? 0.0d : val;
    }

    @Override
    public Type type() {
        return Type.NUMBER;
    }

    @Override
    public Either<String, Number> eval() {
        System.out.println("Eval: Num " + val);
        return new Right<>(this);
    }

    @Override
    public String toString() {
        return "" + val;
    }
}