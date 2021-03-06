package ds.pocs.query.ops;

import ds.common.Either;
import ds.common.Left;
import ds.common.Pair;
import ds.common.Right;
import ds.pocs.query.contract.IExpr;
import ds.pocs.query.contract.RelOp;
import ds.pocs.query.contract.Type;
import ds.pocs.query.types.Bool;
import ds.pocs.query.types.Text;

public enum RelTxtOp implements RelOp<Text> {
    EQ, NEQ, SW, NSW, HAS, NHAS, EW, NEW, EQ_, NEQ_, SW_, NSW_, HAS_, NHAS_, EW_, NEW_, X;

    private Pair<Type, Type> typeParams = new Pair<>(Type.TEXT, Type.TEXT);

    public Either<String, Bool> exec(Text lhs, Text rhs) {
        Either<String, Bool> res;
        switch (this) {
            case EQ:
                res = new Right<>(new Bool(lhs.value().equals(rhs.value())));
                break;
            case NEQ:
                res = new Right<>(new Bool(!lhs.value().equals(rhs.value())));
                break;
            case SW:
                res = new Right<>(new Bool(lhs.value().startsWith(rhs.value())));
                break;
            case NSW:
                res = new Right<>(new Bool(!lhs.value().startsWith(rhs.value())));
                break;
            case HAS:
                res = new Right<>(new Bool(lhs.value().contains(rhs.value())));
                break;
            case NHAS:
                res = new Right<>(new Bool(!lhs.value().contains(rhs.value())));
                break;
            case EW:
                res = new Right<>(new Bool(lhs.value().endsWith(rhs.value())));
                break;
            case NEW:
                res = new Right<>(new Bool(!lhs.value().endsWith(rhs.value())));
                break;
            case EQ_:
                res = new Right<>(new Bool(lhs.value().equalsIgnoreCase(rhs.value())));
                break;
            case NEQ_:
                res = new Right<>(new Bool(!lhs.value().equalsIgnoreCase(rhs.value())));
                break;
            case SW_:
                res = new Right<>(new Bool(lhs.value().toLowerCase().startsWith(rhs.value().toLowerCase())));
                break;
            case NSW_:
                res = new Right<>(new Bool(!lhs.value().toLowerCase().startsWith(rhs.value().toLowerCase())));
                break;
            case HAS_:
                res = new Right<>(new Bool(lhs.value().toLowerCase().contains(rhs.value().toLowerCase())));
                break;
            case NHAS_:
                res = new Right<>(new Bool(!lhs.value().toLowerCase().contains(rhs.value().toLowerCase())));
                break;
            case EW_:
                res = new Right<>(new Bool(lhs.value().toLowerCase().endsWith(rhs.value().toLowerCase())));
                break;
            case NEW_:
                res = new Right<>(new Bool(!lhs.value().toLowerCase().endsWith(rhs.value().toLowerCase())));
                break;
            default:
                res = new Left<>("Invalid Operator: " + this);
                break;
        }
        return res;
    }

    @Override
    public Either<String, Bool> exec(IExpr<Text> lhs, IExpr<Text> rhs) {
        return lhs.eval().flatMap(l -> rhs.eval().flatMap(r -> exec(l, r)));
    }

    @Override
    public Pair<Type, Type> typeParameters() {
        return typeParams;
    }
}