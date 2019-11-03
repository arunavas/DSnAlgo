package com.relcache.core.pocs.query.types;

import com.relcache.core.ds.Either;
import com.relcache.core.ds.Right;
import com.relcache.core.pocs.query.contract.IExpr;
import com.relcache.core.pocs.query.contract.Type;

public class Bool implements IExpr<Bool> {
    private Boolean val;

    public Bool(boolean val) {
        this.val = val;
    }

    public Boolean value() {
        return val == null ? false : val;
    }

    @Override
    public Type type() {
        return Type.BOOL;
    }

    @Override
    public Either<String, Bool> eval() {
        return new Right<>(this);
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