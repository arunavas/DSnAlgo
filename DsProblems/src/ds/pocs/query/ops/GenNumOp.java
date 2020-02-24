package ds.pocs.query.ops;

import ds.common.Either;
import ds.common.Left;
import ds.common.Pair;
import ds.common.Right;
import ds.pocs.query.contract.GenOp;
import ds.pocs.query.contract.IExpr;
import ds.pocs.query.contract.Type;
import ds.pocs.query.types.Number;

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
        return lhs.eval().flatMap(l -> rhs.eval().flatMap(r -> exec(l, r)));
    }

    @Override
    public Pair<Type, Type> typeParameters() {
        return typeParams;
    }
}