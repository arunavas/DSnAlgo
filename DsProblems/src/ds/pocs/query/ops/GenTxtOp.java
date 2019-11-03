package com.relcache.core.pocs.query.ops;

import com.relcache.core.ds.Either;
import com.relcache.core.ds.Left;
import com.relcache.core.ds.Pair;
import com.relcache.core.ds.Right;
import com.relcache.core.pocs.query.contract.GenOp;
import com.relcache.core.pocs.query.contract.IExpr;
import com.relcache.core.pocs.query.contract.Type;
import com.relcache.core.pocs.query.types.Text;

public enum GenTxtOp implements GenOp<Text> {
    CONCAT;

    private Pair<Type, Type> typeParams = new Pair<>(Type.TEXT, Type.TEXT);

    public Either<String, Text> exec(Text lhs, Text rhs) {
        Either<String, Text> res;
        switch (this) {
            case CONCAT:
                res = new Right<>(new Text(lhs.value() + rhs.value()));
                break;
            default:
                res = new Left<>("Invalid Operator: " + this);
                break;
        }
        return res;
    }

    @Override
    public Either<String, Text> exec(IExpr<Text> lhs, IExpr<Text> rhs) {
        return lhs.eval().flatmap(l -> rhs.eval().flatmap(r -> exec(l, r)));
    }

    @Override
    public Pair<Type, Type> typeParameters() {
        return typeParams;
    }
}