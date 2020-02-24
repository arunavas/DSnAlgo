package ds.pocs.query.ops;

import ds.common.Either;
import ds.common.Left;
import ds.common.Pair;
import ds.common.Right;
import ds.pocs.query.contract.IExpr;
import ds.pocs.query.contract.Op;
import ds.pocs.query.contract.Type;
import ds.pocs.query.types.Bool;

public enum CondOp implements Op<Bool, Bool> {
    AND, OR, X;

    private Pair<Type, Type> typeParams = new Pair<>(Type.BOOL, Type.BOOL);

    @Override
    public Either<String, Bool> exec(IExpr<Bool> lhs, IExpr<Bool> rhs) {
        System.out.println("Exec: Op " + this);
        Either<String, Bool> res;
        switch (this) {
            case AND:
                res = lhs.eval().flatMap(l -> l.value() ? rhs.eval() : new Right<>(Bool.False()));
                break;
            case OR:
                System.out.println("OR Executed!");
                res = lhs.eval().flatMap(l -> l.value() ? new Right<>(Bool.True()) : rhs.eval());
                break;
            default:
                res = new Left<>("Invalid Operator: " + this);
                break;
        }
        return res;
    }

    @Override
    public Pair<Type, Type> typeParameters() {
        return typeParams;
    }
}