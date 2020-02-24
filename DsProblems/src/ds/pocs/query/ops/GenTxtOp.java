package ds.pocs.query.ops;

import ds.common.Either;
import ds.common.Left;
import ds.common.Pair;
import ds.common.Right;
import ds.pocs.query.contract.GenOp;
import ds.pocs.query.contract.IExpr;
import ds.pocs.query.contract.Type;
import ds.pocs.query.types.Text;

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
        return lhs.eval().flatMap(l -> rhs.eval().flatMap(r -> exec(l, r)));
    }

    @Override
    public Pair<Type, Type> typeParameters() {
        return typeParams;
    }
}