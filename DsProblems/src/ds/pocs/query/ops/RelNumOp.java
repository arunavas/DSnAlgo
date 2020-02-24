package ds.pocs.query.ops;

import ds.common.Either;
import ds.common.Left;
import ds.common.Pair;
import ds.common.Right;
import ds.pocs.query.contract.IExpr;
import ds.pocs.query.contract.RelOp;
import ds.pocs.query.contract.Type;
import ds.pocs.query.types.Bool;
import ds.pocs.query.types.Number;
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
        return lhs.eval().flatMap(l -> rhs.eval().flatMap(r -> exec(l, r)));
    }

    @Override
    public Pair<Type, Type> typeParameters() {
        return typeParams;
    }
}