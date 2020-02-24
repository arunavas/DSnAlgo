package ds.pocs.query.types;

import ds.common.Either;
import ds.common.Right;
import ds.pocs.query.contract.IExpr;
import ds.pocs.query.contract.Type;

public class Text implements IExpr<Text> {
    private String val;

    public Text(String val) {
        this.val = val;
    }

    public String value() {
        return val == null ? "" : val;
    }

    @Override
    public Type type() {
        return Type.TEXT;
    }

    @Override
    public Either<String, Text> eval() {
        return new Right<>(this);
    }

    @Override
    public String toString() {
        return val;
    }
}