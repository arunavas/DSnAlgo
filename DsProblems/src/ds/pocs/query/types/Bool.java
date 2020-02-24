package ds.pocs.query.types;

import ds.common.Either;
import ds.common.Right;
import ds.pocs.query.contract.IExpr;
import ds.pocs.query.contract.Type;

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